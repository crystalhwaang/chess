package dataaccess;
import model.UserData;

// how user program interacts with database
public interface UserDAO {
    public UserData getUser(String username);
    public void createUser(UserData user);
    public void clearAll();
}