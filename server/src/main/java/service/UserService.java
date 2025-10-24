package service;

import dataaccess.AuthDAO;
import dataaccess.UserDAO;
import exception.AlreadyTakenException;
import exception.UnauthorizedException;
import model.AuthData;
import model.UserData;
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

    public RegisterResult register(RegisterRequest request) throws AlreadyTakenException {
        UserData existing = userDAO.getUser(request.username());
        if (existing != null) {
            throw new AlreadyTakenException("username already taken");
        }

        UserData newUser = new UserData(request.username(), request.password(), request.email());
        userDAO.createUser(newUser);
        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(request.username(), authToken);
        authDAO.createAuth(authData);
        return new RegisterResult(request.username(), authToken);
    }

    public LoginResult login(LoginRequest request) throws UnauthorizedException {
        UserData user = userDAO.getUser(request.username());
        if (user == null || !user.password().equals(request.password())) {
            throw new UnauthorizedException("Invalid username or password");
        }

        String authToken = UUID.randomUUID().toString();
        AuthData authData = new AuthData(user.username(), authToken);
        authDAO.createAuth(authData);
        return new LoginResult(user.username(), authToken);
    }
}
