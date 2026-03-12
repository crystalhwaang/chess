package dataaccess;
import model.AuthData;

// how auth program interacts with database
public interface AuthDAO {
    public void createAuth(AuthData authData) throws DataAccessException;
    public AuthData getAuth(String token) throws DataAccessException;
    public void clearAll() throws DataAccessException;
    public void deleteAuth(String token) throws DataAccessException;
}