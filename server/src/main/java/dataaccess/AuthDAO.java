package dataaccess;
import model.AuthData;

public interface AuthDAO {
    public void createAuth(AuthData authData);
    public AuthData getAuth(String token);
    public void clearAll();
    public void deleteAuth(String token);
}
