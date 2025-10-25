package service;
import dataaccess.AuthDAO;
import dataaccess.GameDAO;
import exception.AlreadyTakenException;
import exception.UnauthorizedException;
import model.AuthData;
import model.GameData;
import request.CreateGameRequest;
import request.JoinGameRequest;
import result.CreateGameResult;
import java.util.List;

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

    public void joinGame(String authToken, JoinGameRequest request) throws UnauthorizedException, AlreadyTakenException {
        AuthData auth = authDAO.getAuth(authToken);
        if (auth == null) {
            throw new UnauthorizedException("Invalid auth token");
        }
        if (request.playerColor() == null || (!request.playerColor().equalsIgnoreCase("WHITE") && !request.playerColor().equalsIgnoreCase("BLACK"))) {
            throw new IllegalArgumentException("Invalid color");
        }
        gameDAO.joinGame(request.gameID(), request.playerColor(), auth.username());
    }

    public List<GameData> listGames(String authToken) throws UnauthorizedException {
        AuthData auth = authDAO.getAuth(authToken);
        if (auth == null) {
            throw new UnauthorizedException("Invalid auth token");
        }
        return gameDAO.listGames();
    }
}

