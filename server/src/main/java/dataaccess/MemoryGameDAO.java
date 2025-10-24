package dataaccess;

import chess.ChessGame;
import model.GameData;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryGameDAO implements GameDAO {
    private final AtomicInteger idCounter = new AtomicInteger(1);
    private final ConcurrentHashMap<Integer, GameData> games = new ConcurrentHashMap<>();

    @Override
    public int createGame(String gameName, String ownerUsername) {
        int id = idCounter.getAndIncrement();
        games.put(id, new GameData(id, null, null, gameName, new ChessGame()));
        return id;
    }
}
