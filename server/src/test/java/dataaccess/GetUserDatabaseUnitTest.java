package dataaccess;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GetUserDatabaseUnitTest {
    private SQLUserDAO dao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        dao = new SQLUserDAO();
        dao.clearAll();
        dao.createUser(new UserData("getUser", "password123", "get@example.com"));
    }

    @Test
    public void getUserSuccess() throws DataAccessException {
        UserData retrieved = dao.getUser("getUser");
        assertNotNull(retrieved);
        assertEquals("getUser", retrieved.username());
        assertEquals("get@example.com", retrieved.email());
    }

    @Test
    public void getUserFailure() throws DataAccessException {
        UserData retrieved = dao.getUser("doesNotExist");
        assertNull(retrieved);
    }
}