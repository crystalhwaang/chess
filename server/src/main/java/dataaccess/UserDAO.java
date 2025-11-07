package dataaccess;
import model.UserData;

// how user program interacts with database
public interface UserDAO {
    public UserData getUser(String username) throws DataAccessException;
    public void createUser(UserData user) throws DataAccessException;
    public void clearAll() throws DataAccessException;
}
