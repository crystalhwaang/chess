package dataaccess;
import model.AuthData;
import java.util.HashMap;
import java.util.Map;

public class MemoryAuthDAO implements AuthDAO{
    private final Map<String, AuthData> authTokens = new HashMap<>();

    public MemoryAuthDAO() {
    }
    @Override
    public void createAuth(AuthData authData) {
        authTokens.put(authData.authToken(), authData);
    }

    @Override
    public AuthData getAuth(String token) {
        return authTokens.get(token);
    }

    public void clearAll() {
        authTokens.clear();
    }

    @Override
    public void deleteAuth(String token) {
        authTokens.remove(token);
    }
}
