package service;

import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import exception.UnauthorizedException;
import model.AuthData;
import request.CreateGameRequest;
import result.CreateGameResult;

public class GameService {
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public GameService(AuthDAO authDAO, GameDAO gameDAO) {
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
    }

    public CreateGameResult createGame(String authToken, CreateGameRequest request) throws UnauthorizedException {
        AuthData auth = authDAO.getAuth(authToken);
        if (auth == null) {
            throw new UnauthorizedException("Invalid auth token");
        }

        if (request.gameName() == null || request.gameName().isBlank()) {
            throw new IllegalArgumentException("Game name cannot be blank");
        }

        int gameID = gameDAO.createGame(request.gameName(), auth.username());
        return new CreateGameResult(gameID);
    }
}

