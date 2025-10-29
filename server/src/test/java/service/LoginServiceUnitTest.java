package service;

import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import request.RegisterRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginServiceUnitTest {
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
    public void loginSuccess() throws Exception {
        var request = new RegisterRequest("user", "pass", "email@test.com");
        var result = service.register(request);

        assertEquals("user", result.username());
        assertNotNull(result.authToken());
        assertNotNull(userDAO.getUser("user"));

        var result2 = service.login(new LoginRequest("user", "pass"));
        assertEquals("user", result2.username());
        assertNotNull(result2.authToken());
    }

    @Test
    public void loginFails() throws Exception {
        service.register(new RegisterRequest("user", "pass", "email@test.com"));
        var ex = assertThrows(Exception.class, () -> service.login(new LoginRequest("user", "password")));
        assertTrue(ex.getMessage().contains("Invalid username or password"));
    }
}
