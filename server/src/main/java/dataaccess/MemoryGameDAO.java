package dataaccess;

import chess.ChessGame;
import exception.AlreadyTakenException;
import model.GameData;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

// stores game data
public class MemoryGameDAO implements GameDAO {
    private final AtomicInteger idCounter = new AtomicInteger(1);
    private final ConcurrentHashMap<Integer, GameData> games = new ConcurrentHashMap<>();

    @Override
    public int createGame(String gameName, String ownerUsername) {
        int id = idCounter.getAndIncrement();
        games.put(id, new GameData(id, null, null, gameName, new ChessGame()));
        return id;
    }

    @Override
    public List<GameData> listGames() {
        return games.values().stream().collect(Collectors.toList());
    }

    @Override
    public void joinGame(int gameID, String playerColor, String username) throws AlreadyTakenException {
        GameData game = games.get(gameID);
        if (game == null) {
            throw new IllegalArgumentException("Game does not exist");
        }
        if ("WHITE".equalsIgnoreCase(playerColor)) {
            if (game.whiteUsername() != null) throw new AlreadyTakenException("White already taken");
            games.put(gameID, new GameData(game.gameID(), username, game.blackUsername(), game.gameName(), game.game()));
        } else if ("BLACK".equalsIgnoreCase(playerColor)) {
            if (game.blackUsername() != null) throw new AlreadyTakenException("Black already taken");
            games.put(gameID, new GameData(game.gameID(), game.whiteUsername(), username, game.gameName(), game.game()));
        } else {
            throw new IllegalArgumentException("Invalid color: " + playerColor);
        }
    }

    @Override
    public void clearAll() {
        games.clear();
    }
}
