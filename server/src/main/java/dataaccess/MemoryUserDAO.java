package dataaccess;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import java.util.HashMap;
import java.util.Map;

// stores user accounts
public class MemoryUserDAO implements UserDAO {
    private final Map<String, UserData> users = new HashMap<>();

    public UserData getUser(String username) {
        return users.get(username);
    }

    public void createUser(UserData user) {
        String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());
        users.put(user.username(), new UserData(user.username(), hashedPassword, user.email()));
    }

    public void clearAll() {
        users.clear();
    }
}