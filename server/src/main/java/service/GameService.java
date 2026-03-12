package service;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
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

    public CreateGameResult createGame(String authToken, CreateGameRequest request) throws UnauthorizedException, DataAccessException {
        AuthData auth = authDAO.getAuth(authToken);
        // validate user auth token
        if (auth == null) {
            throw new UnauthorizedException("Invalid auth token");
        }
        // validate game name
        if (request.gameName() == null || request.gameName().isBlank()) {
            throw new IllegalArgumentException("Game name cannot be blank");
        }
        // create the game
        int gameID = gameDAO.createGame(request.gameName(), auth.username());
        return new CreateGameResult(gameID);
    }

    public void joinGame(String authToken, JoinGameRequest request) throws UnauthorizedException, AlreadyTakenException, DataAccessException {
        AuthData auth = authDAO.getAuth(authToken);
        if (auth == null) {
            throw new UnauthorizedException("Invalid auth token");
        }
        // validate player color
        if (request.playerColor() == null || (!request.playerColor().equalsIgnoreCase("WHITE") && !request.playerColor().equalsIgnoreCase("BLACK"))) {
            throw new IllegalArgumentException("Invalid color");
        }

        try {
            gameDAO.joinGame(request.gameID(), request.playerColor(), auth.username());
        } catch (DataAccessException e) {
            if (e.getMessage() != null && e.getMessage().contains("Game not found")) {
                throw new IllegalArgumentException("Game not found");
            }
            throw e;
        }
    }

    public List<GameData> listGames(String authToken) throws UnauthorizedException, DataAccessException {
        AuthData auth = authDAO.getAuth(authToken);
        if (auth == null) {
            throw new UnauthorizedException("Invalid auth token");
        }
        return gameDAO.listGames();
    }
}