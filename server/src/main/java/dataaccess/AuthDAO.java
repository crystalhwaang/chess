package dataaccess;
import model.AuthData;

// how auth program interacts with database
public interface AuthDAO {
    public void createAuth(AuthData authData);

    public AuthData getAuth(String token);

    public void clearAll();

    public void deleteAuth(String token);
}