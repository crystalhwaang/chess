package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import request.RegisterRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogoutServiceUnitTest {
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
    public void logoutSuccess() throws Exception {
        var request = new RegisterRequest("user", "pass", "email@test.com");
        var result = service.register(request);

        assertEquals("user", result.username());
        assertNotNull(result.authToken());
        assertNotNull(userDAO.getUser("user"));

        var result2 = service.login(new LoginRequest("user", "pass"));
        assertEquals("user", result2.username());
        assertNotNull(result2.authToken());

        service.logout(result2.authToken());
        var ex = assertThrows(Exception.class, () -> service.logout(result2.authToken()));
        assertFalse(ex.getMessage().contains("Invalid authToken"));
    }

    @Test
    public void logoutFails() throws Exception {
        service.register(new RegisterRequest("user", "pass", "email@test.com"));

        var ex = assertThrows(Exception.class, () -> service.login(new LoginRequest("user", "password")));
        assertTrue(ex.getMessage().contains("Invalid username or password"));

        var ex2 = assertThrows(Exception.class, () -> service.logout("invalid token"));
        assertFalse(ex2.getMessage().contains("Invalid authToken"));
    }
}
