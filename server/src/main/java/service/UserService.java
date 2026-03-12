package service;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.UserDAO;
import exception.AlreadyTakenException;
import exception.UnauthorizedException;
import model.AuthData;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import request.LoginRequest;
import request.RegisterRequest;
import result.LoginResult;
import result.RegisterResult;
import java.util.UUID;

public class UserService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public UserService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public RegisterResult register(RegisterRequest request) throws AlreadyTakenException, DataAccessException {
        // check if username exists
        UserData existing = userDAO.getUser(request.username());
        if (existing != null) {
            throw new AlreadyTakenException("Username already taken");
        }
        // creating new user
        UserData newUser = new UserData(request.username(), request.password(), request.email());
        userDAO.createUser(newUser);
        // new authToken
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(request.username(), authToken);
        authDAO.createAuth(authData);
        return new RegisterResult(request.username(), authToken);
    }

    public LoginResult login(LoginRequest request) throws UnauthorizedException, DataAccessException {
        UserData user = userDAO.getUser(request.username());
        // check if password or username is valid
        if (user == null || !BCrypt.checkpw(request.password(), user.password())) {
            throw new UnauthorizedException("Invalid username or password");
        }
        // new authToken
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(user.username(), authToken);
        authDAO.createAuth(authData);
        return new LoginResult(user.username(), authToken);
    }

    public void logout(String authToken) throws UnauthorizedException, DataAccessException {
        AuthData auth = authDAO.getAuth(authToken);
        // check if authToken is invalid
        if (auth == null) {
            throw new UnauthorizedException("Invalid auth token");
        }
        // if not, delete
        authDAO.deleteAuth(authToken);
    }
}