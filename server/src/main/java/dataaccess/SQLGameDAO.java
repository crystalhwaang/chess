package dataaccess;

import chess.ChessBoard;
import chess.ChessGame;
import com.google.gson.Gson;
import exception.AlreadyTakenException;
import model.GameData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLGameDAO implements GameDAO {

    private final Gson gson = new Gson();

    @Override
    public int createGame(String gameName, String ownerUsername) throws DataAccessException {
        var sql = "INSERT INTO GAME_DATA (gameName, whiteUsername, blackUsername, chessGame) VALUES (?, ?, ?, ?)";
        try (var conn = DatabaseManager.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, gameName);
            stmt.setString(2, null);
            stmt.setString(3, null);
            stmt.setString(4, gson.toJson(new ChessGame()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error creating game", e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GameData> listGames() throws DataAccessException {
        List<GameData> games = new ArrayList<>();
        var sql = "SELECT * FROM GAME_DATA";
        try (var conn = DatabaseManager.getConnection();
             var stmt = conn.prepareStatement(sql);
             var rs = stmt.executeQuery()) {
            while (rs.next()) {
                int gameID = rs.getInt("gameID");
                String gameName = rs.getString("gameName");
                String whiteUsername = rs.getString("whiteUsername");
                String blackUsername = rs.getString("blackUsername");
                String gameJson = rs.getString("chessGame");
                ChessGame game = gson.fromJson(gameJson, ChessGame.class);
                games.add(new GameData(gameID, gameName, whiteUsername, blackUsername, game));
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException("Error listing games", e);
        }
        return games;
    }

    @Override
    public void joinGame(int gameID, String playerColor, String username) throws AlreadyTakenException, DataAccessException {
        var game = getGame(gameID);
        if (game == null) {
            throw new DataAccessException("Game not found");
        }
        String sql;
        if ("WHITE".equalsIgnoreCase(playerColor)) {
            if (game.whiteUsername() != null) {
                throw new AlreadyTakenException("White player slot already taken");
            }
            sql = "UPDATE GAME_DATA SET whiteUsername = ? WHERE gameID = ?";
        } else if ("BLACK".equalsIgnoreCase(playerColor)) {
            if (game.blackUsername() != null) {
                throw new AlreadyTakenException("Black player slot already taken");
            }
            sql = "UPDATE GAME_DATA SET blackUsername = ? WHERE gameID = ?";
        } else {
            throw new DataAccessException("Invalid player color");
        }

        try (var conn = DatabaseManager.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setInt(2, gameID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error joining game", e);
        }
    }

    @Override
    public void clearAll() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection();
             var stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM GAME_DATA");
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException("Error clearing GAME_DATA", e);
        }
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        var sql = "SELECT * FROM GAME_DATA WHERE gameID = ?";
        try (var conn = DatabaseManager.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gameID);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    var gameName = rs.getString("gameName");
                    var whiteUsername = rs.getString("whiteUsername");
                    var blackUsername = rs.getString("blackUsername");
                    var gameJson = rs.getString("chessGame");
                    ChessGame game = gson.fromJson(gameJson, ChessGame.class);
                    return new GameData(gameID, gameName, whiteUsername, blackUsername, game);
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException("Error retrieving game", e);
        }
        return null;
    }
}
