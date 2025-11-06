package dataaccess;

import exception.AlreadyTakenException;
import model.GameData;

import java.util.List;

public class SQLGameDAO implements GameDAO {
    @Override
    public int createGame(String gameName, String ownerUsername) {
        return 0;
    }

    @Override
    public List<GameData> listGames() {
        return List.of();
    }

    @Override
    public void joinGame(int gameID, String playerColor, String username) throws AlreadyTakenException {

    }

    @Override
    public void clearAll() {

    }

    @Override
    public GameData getGame(int gameID) {
        return null;
    }
}
