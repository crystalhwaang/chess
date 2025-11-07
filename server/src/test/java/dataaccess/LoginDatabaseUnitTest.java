package dataaccess;

import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginDatabaseUnitTest {

    private SQLUserDAO dao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        dao = new SQLUserDAO();
        dao.clearAll();
        UserData user = new UserData("loginUser", "password123", "login@example.com");
        dao.createUser(user);
    }

    @Test
    public void loginSuccess() throws DataAccessException {
        UserData retrieved = dao.loginUser("loginUser", "password123");

        assertNotNull(retrieved);
        assertEquals("loginUser", retrieved.username());
        assertEquals("login@example.com", retrieved.email());
        assertTrue(retrieved.password().startsWith("$2"));
    }

    @Test
    public void loginFailure() throws DataAccessException {
        DataAccessException ex = assertThrows(DataAccessException.class, () -> {
            dao.loginUser("loginUser", "wrongPassword");
        });
        assertTrue(ex.getMessage().contains("Invalid password"));
    }
}
