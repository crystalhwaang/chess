package dataaccess;
import exception.AlreadyTakenException;
import model.GameData;
import java.util.List;

// how game program interacts with database
public interface GameDAO {
    int createGame(String gameName, String ownerUsername);
    List<GameData> listGames();
    void joinGame(int gameID, String playerColor, String username) throws AlreadyTakenException;
    public void clearAll();
    GameData getGame(int gameID);
}