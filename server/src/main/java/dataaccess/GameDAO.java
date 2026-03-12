package dataaccess;
import exception.AlreadyTakenException;
import model.GameData;
import java.util.List;

// how game program interacts with database
public interface GameDAO {
    int createGame(String gameName, String ownerUsername) throws DataAccessException;
    List<GameData> listGames() throws DataAccessException;
    void joinGame(int gameID, String playerColor, String username) throws AlreadyTakenException, DataAccessException;
    public void clearAll() throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
}