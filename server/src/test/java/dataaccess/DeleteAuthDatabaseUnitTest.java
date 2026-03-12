package dataaccess;
import model.AuthData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeleteAuthDatabaseUnitTest {
    private SQLAuthDAO authDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        authDao = new SQLAuthDAO();
        authDao.clearAll();
        authDao.createAuth(new AuthData("deleteUser", "deleteToken"));
    }

    @Test
    public void deleteAuthSuccess() throws DataAccessException {
        authDao.deleteAuth("deleteToken");
        assertNull(authDao.getAuth("deleteToken"));
    }

    @Test
    public void deleteAuthFailure() {
        DataAccessException ex = assertThrows(DataAccessException.class, () -> authDao.deleteAuth("noSuchToken"));
        assertTrue(ex.getMessage().contains("Auth token not found"));
    }
}