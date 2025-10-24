package dataaccess;
import exception.AlreadyTakenException;
import model.GameData;
import java.util.List;

public interface GameDAO {
    int createGame(String gameName, String ownerUsername);
    List<GameData> listGames();
    void joinGame(int gameID, String playerColor, String username) throws AlreadyTakenException;
}
