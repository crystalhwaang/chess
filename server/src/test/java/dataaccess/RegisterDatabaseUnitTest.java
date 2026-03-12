package dataaccess;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterDatabaseUnitTest {
    private SQLUserDAO dao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        dao = new SQLUserDAO();
        dao.clearAll();
    }

    @Test
    public void registerSuccess() throws DataAccessException {
        UserData user = new UserData("registerUser", "password123", "register@example.com");
        dao.createUser(user);

        UserData retrieved = dao.getUser("registerUser");
        assertNotNull(retrieved);
        assertEquals("registerUser", retrieved.username());
        assertEquals("register@example.com", retrieved.email());
        assertNotEquals("password123", retrieved.password());
        assertTrue(retrieved.password().startsWith("$2"));
    }

    @Test
    public void registerFailure() throws DataAccessException {
        UserData user1 = new UserData("registerUser2", "password123", "register@example.com");
        dao.createUser(user1);

        UserData user2 = new UserData("registerUser2", "differentPassword", "other@example.com");
        DataAccessException ex = assertThrows(DataAccessException.class, () -> dao.createUser(user2));
        assertTrue(ex.getMessage().contains("User already exists"));
    }
}
