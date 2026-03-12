package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import exception.AlreadyTakenException;
import model.GameData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLGameDAO implements GameDAO {

    private final Gson gson = new Gson();

    @Override
    public int createGame(String gameName, String ownerUsername) throws DataAccessException {
        if (gameName == null || gameName.isBlank()) {
            throw new DataAccessException("Game name required");
        }
        String sql = "INSERT INTO game_data (gameName, whiteUsername, blackUsername, chessGame) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, gameName);
            stmt.setString(2, null);
            stmt.setString(3, null);
            stmt.setString(4, gson.toJson(new ChessGame()));
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // id column
                } else {
                    throw new DataAccessException("Failed to retrieve generated game id");
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error creating game", e);
        }
    }

    @Override
    public List<GameData> listGames() throws DataAccessException {
        List<GameData> games = new ArrayList<>();
        String sql = "SELECT * FROM game_data";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String gameName = rs.getString("gameName");
                String whiteUsername = rs.getString("whiteUsername");
                String blackUsername = rs.getString("blackUsername");
                String gameJson = rs.getString("chessGame");
                ChessGame game = gson.fromJson(gameJson, ChessGame.class);

                games.add(new GameData(id, whiteUsername, blackUsername, gameName, game));
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error listing games", e);
        }
        return games;
    }

    @Override
    public void joinGame(int gameId, String playerColor, String username) throws AlreadyTakenException, DataAccessException {
        GameData game = getGame(gameId);
        if (game == null) {
            throw new DataAccessException("Game not found");
        }

        String sql;
        if ("WHITE".equalsIgnoreCase(playerColor)) {
            if (game.whiteUsername() != null) {
                throw new AlreadyTakenException("White player slot already taken");
            }
            sql = "UPDATE game_data SET whiteUsername = ? WHERE id = ?";
        } else if ("BLACK".equalsIgnoreCase(playerColor)) {
            if (game.blackUsername() != null) {
                throw new AlreadyTakenException("Black player slot already taken");
            }
            sql = "UPDATE game_data SET blackUsername = ? WHERE id = ?";
        } else {
            throw new DataAccessException("Invalid player color");
        }

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setInt(2, gameId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error joining game", e);
        }
    }

    @Override
    public void clearAll() throws DataAccessException {
        String sql = "DELETE FROM game_data";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error clearing game_data", e);
        }
    }

    @Override
    public GameData getGame(int gameId) throws DataAccessException {
        String sql = "SELECT * FROM game_data WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, gameId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String gameName = rs.getString("gameName");
                    String whiteUsername = rs.getString("whiteUsername");
                    String blackUsername = rs.getString("blackUsername");
                    String gameJson = rs.getString("chessGame");
                    ChessGame game = gson.fromJson(gameJson, ChessGame.class);

                    return new GameData(gameId, whiteUsername, blackUsername, gameName, game);
                }
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving game", e);
        }
        return null;
    }
}
