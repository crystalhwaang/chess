package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;
import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceUnitTest {

    private UserService service;
    private MemoryUserDAO userDAO;
    private MemoryAuthDAO authDAO;

    @BeforeEach
    public void setup() {
        userDAO = new MemoryUserDAO();
        authDAO = new MemoryAuthDAO();
        service = new UserService(userDAO, authDAO);
    }

    @Test
    public void registerSuccess() throws Exception {
        var request = new RegisterRequest("user", "pass", "email@test.com");
        var result = service.register(request);

        assertEquals("user", result.username());
        assertNotNull(result.authToken());
        assertNotNull(userDAO.getUser("user"));
    }

    @Test
    public void registerFails() throws Exception {
        service.register(new RegisterRequest("user", "pass", "email@test.com"));
        var duplicate = new RegisterRequest("user", "pass2", "email2@test.com");
        var ex = assertThrows(Exception.class, () -> service.register(duplicate));
        assertTrue(ex.getMessage().contains("already taken"));
    }
}
