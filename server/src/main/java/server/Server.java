package server;

import dataaccess.*;
import exception.AlreadyTakenException;
import exception.UnauthorizedException;
import io.javalin.*;
import com.google.gson.Gson;
import io.javalin.http.Context;
import model.GameData;
import org.jetbrains.annotations.NotNull;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.LoginRequest;
import request.RegisterRequest;
import result.CreateGameResult;
import result.ErrorResponse;
import result.LoginResult;
import result.RegisterResult;
import service.DatabaseService;
import service.GameService;
import service.UserService;
import java.util.Collections;
import java.util.List;

public class Server {
    private final Javalin javalin;
    private final Gson gson = new Gson();
    private final UserService userService;
    private final DatabaseService databaseService;
    private final GameService gameService;

    public Server() {
        UserDAO userDAO = new MemoryUserDAO();
        AuthDAO authDAO = new MemoryAuthDAO();
        GameDAO gameDAO = new MemoryGameDAO();
        userService = new UserService(userDAO, authDAO);
        databaseService = new DatabaseService(userDAO, authDAO, gameDAO);
        gameService = new GameService(authDAO, gameDAO);
        javalin = Javalin.create(config -> config.staticFiles.add("web"));
        registerEndpoints();
    }

    //registering each endpoint according to their http methods and paths
    private void registerEndpoints() {
        javalin.post("/user", this::handleRegister);
        javalin.delete("/db", this::handleClear);
        javalin.post("/session", this::handleLogin);
        javalin.delete("/session", this::handleLogout);
        javalin.post("/game", this::handleCreateGame);
        javalin.put("/game", this::handleJoinGame);
        javalin.get("/game", this::handleListGame);
    }

    // clear application -- success response & 500 failure response
    private void handleClear(@NotNull Context context) {
            try {
                databaseService.clearDatabase();
                context.status(200).result(gson.toJson(new java.util.HashMap<>()));
            } catch (Exception e) {
                context.status(500).result(gson.toJson(new ErrorResponse(null,"Error: " + e.getMessage())));
            }
    }

    // login -- success response & 400 & 401 & 500 failure response
    private void handleLogin(@NotNull Context context) {
        LoginRequest request;
        try {
            request = gson.fromJson(context.body(), LoginRequest.class);
        } catch (Exception e) {
            context.status(400).result(gson.toJson(new ErrorResponse(null, "Error: bad request")));
            return;
        }
        if (request.username() == null || request.username().isBlank() ||
                request.password() == null || request.password().isBlank()) {
            context.status(400).result(gson.toJson(new ErrorResponse(null, "Error: bad request")));
            return;
        }
        try {
            LoginResult result = userService.login(request);
            context.status(200).result(gson.toJson(result));
        } catch (UnauthorizedException e) {
            context.status(401).result(gson.toJson(new ErrorResponse(null, "Error: unauthorized")));
        } catch (Exception e) {
            context.status(500).result(gson.toJson(new ErrorResponse(null, "Error: " + e.getMessage())));
        }
    }

    // register -- success response & 400 & 403 & 500 failure response
    private void handleRegister(@NotNull Context context) {
        RegisterRequest request;
        try {
            request = gson.fromJson(context.body(), RegisterRequest.class);
        } catch (Exception e) {
            context.status(400).result(gson.toJson(new ErrorResponse(null,"Error: bad request")));
            return;
        }
        if (request.username() == null || request.username().isBlank() || request.password() == null || request.password().isBlank() || request.email() == null || request.email().isBlank()) {
            context.status(400).result(gson.toJson(new ErrorResponse(null, "Error: bad request")));
            return;
        }
        try {
            RegisterResult result = userService.register(request);
            context.status(200).result(gson.toJson(result));
        } catch (AlreadyTakenException e) {
            context.status(403).result(gson.toJson(new ErrorResponse(null,"Error: already taken")));
        } catch (Exception e) {
            context.status(500).result(gson.toJson(new ErrorResponse(null,"Error: " + e.getMessage())));
        }
    }

    // logout -- success response & 401 & 500 failure response
    private void handleLogout(@NotNull Context context) {
        String authToken = context.header("authorization");
        if (authToken == null || authToken.isBlank()) {
            context.status(401).result(gson.toJson(new ErrorResponse(null,"Error: unauthorized")));
            return;
        }
        try {
            userService.logout(authToken);
            context.status(200).result(gson.toJson(new java.util.HashMap<>()));
        } catch (UnauthorizedException e) {
            context.status(401).result(gson.toJson(new ErrorResponse(null, "Error: unauthorized")));
        } catch (Exception e) {
            context.status(500).result(gson.toJson(new ErrorResponse(null, "Error: " + e.getMessage())));
        }
    }

    // create game -- success response & 400 & 401 & 500 failure response
    private void handleCreateGame(@NotNull Context context) {
        String authToken = context.header("authorization");
        if (authToken == null || authToken.isBlank()) {
            context.status(401).result(gson.toJson(new ErrorResponse(null,"Error: unauthorized")));
            return;
        }
        CreateGameRequest request;
        try {
            request = gson.fromJson(context.body(), CreateGameRequest.class);
        } catch (Exception e) {
            context.status(400).result(gson.toJson(new ErrorResponse(null,"Error: bad request")));
            return;
        }
        if (request.gameName() == null || request.gameName().isBlank()) {
            context.status(400).result(gson.toJson(new ErrorResponse(null,"Error: bad request")));
            return;
        }
        try {
            CreateGameResult result = gameService.createGame(authToken, request);
            context.status(200).result(gson.toJson(result));
        } catch (UnauthorizedException e) {
            context.status(401).result(gson.toJson(new ErrorResponse(null,"Error: unauthorized")));
        } catch (IllegalArgumentException e) {
            context.status(400).result(gson.toJson(new ErrorResponse(null,"Error: " + e.getMessage())));
        } catch (Exception e) {
            context.status(500).result(gson.toJson(new ErrorResponse(null,"Error: " + e.getMessage())));
        }
    }

    // join game -- success response & 400 & 401 & 403 & 500 failure response
    private void handleJoinGame(@NotNull Context context) {
        String authToken = context.header("authorization");
        if (authToken == null || authToken.isBlank()) {
            context.status(401).result(gson.toJson(new ErrorResponse(null, "Error: unauthorized")));
            return;
        }
        JoinGameRequest request;
        try {
            request = gson.fromJson(context.body(), JoinGameRequest.class);
        } catch (Exception e) {
            context.status(400).result(gson.toJson(new ErrorResponse(null, "Error: bad request")));
            return;
        }
        try {
            gameService.joinGame(authToken, request);
            context.status(200).result(gson.toJson(new java.util.HashMap<>()));
        } catch (UnauthorizedException e) {
            context.status(401).result(gson.toJson(new ErrorResponse(null, "Error: unauthorized")));
        } catch (AlreadyTakenException e) {
            context.status(403).result(gson.toJson(new ErrorResponse(null, "Error: already taken")));
        } catch (IllegalArgumentException e) {
            context.status(400).result(gson.toJson(new ErrorResponse(null, "Error: bad request")));
        } catch (Exception e) {
            context.status(500).result(gson.toJson(new ErrorResponse(null, "Error: " + e.getMessage())));
        }
    }

    // list games -- success response & 401 & 500 failure response
    private void handleListGame(@NotNull Context context) {
        String authToken = context.header("authorization");
        if (authToken == null || authToken.isBlank()) {
            context.status(401).result(gson.toJson(new ErrorResponse(null, "Error: unauthorized")));
            return;
        }
        try {
            List<GameData> games = gameService.listGames(authToken);
            context.status(200).result(gson.toJson(Collections.singletonMap("games", games)));
        } catch (UnauthorizedException e) {
            context.status(401).result(gson.toJson(new ErrorResponse(null, "Error: unauthorized")));
        } catch (Exception e) {
            context.status(500).result(gson.toJson(new ErrorResponse(null, "Error: " + e.getMessage())));
        }
    }

    // start javalin server
    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    // end javalin server
    public void stop() {
        javalin.stop();
    }
}