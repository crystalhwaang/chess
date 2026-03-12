package dataaccess;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreateDatabaseUnitTest {
    private SQLUserDAO dao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        dao = new SQLUserDAO();
        dao.clearAll();
    }

    @Test
    public void createSuccess() throws DataAccessException {
        UserData user = new UserData("test", "password123", "test@example.com");
        dao.createUser(user);
        UserData retrieved = dao.getUser("test");

        assertNotNull(retrieved);
        assertEquals("test", retrieved.username());
        assertEquals("test@example.com", retrieved.email());
        assertNotEquals("password123", retrieved.password());
        assertTrue(retrieved.password().startsWith("$2"));
    }

    @Test
    public void createFailure() throws DataAccessException {
        UserData user1 = new UserData("test2", "password123", "test@example.com");
        dao.createUser(user1);
        UserData user2 = new UserData("test2", "password456", "test2@example.com");
        DataAccessException ex = assertThrows(DataAccessException.class, () -> dao.createUser(user2));
        assertTrue(ex.getMessage().contains("User already exists"));
    }
}