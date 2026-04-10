package client;

import chess.ChessGame;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public final class ServerFacade {
    private final String baseUrl;
    private final HttpClient httpClient;
    private final Gson gson = new Gson();

    public ServerFacade(String serverUrl) {
        if (serverUrl == null || serverUrl.isBlank()) {
            throw new IllegalArgumentException("serverUrl must not be blank");
        }
        this.baseUrl = serverUrl.endsWith("/") ? serverUrl.substring(0, serverUrl.length() - 1) : serverUrl;
        this.httpClient = HttpClient.newHttpClient();
    }

    public record AuthResult(String username, String authToken) {}

    public record CreateGameResult(int gameID) {}

    public record ListedGame(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {}

    public sealed interface Result<T> permits Result.Ok, Result.Err {
        record Ok<T>(T value) implements Result<T> {}

        record Err<T>(int statusCode, String message) implements Result<T> {}
    }

    public Result<AuthResult> register(String username, String password, String email) {
        var body = gson.toJson(new RegisterBody(username, password, email));
        var request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/user"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return sendJson(request, AuthResult.class, 200);
    }

    public Result<AuthResult> login(String username, String password) {
        var body = gson.toJson(new LoginBody(username, password));
        var request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/session"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return sendJson(request, AuthResult.class, 200);
    }

    public Result<Void> logout(String authToken) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/session"))
                .header("authorization", authToken)
                .DELETE()
                .build();
        return sendEmpty(request, 200);
    }

    public Result<CreateGameResult> createGame(String authToken, String gameName) {
        var body = gson.toJson(new CreateGameBody(gameName));
        var request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/game"))
                .header("Content-Type", "application/json")
                .header("authorization", authToken)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return sendJson(request, CreateGameResult.class, 200);
    }

    public Result<Void> joinGame(String authToken, String playerColor, int gameID) {
        var body = gson.toJson(new JoinGameBody(playerColor, gameID));
        var request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/game"))
                .header("Content-Type", "application/json")
                .header("authorization", authToken)
                .PUT(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return sendEmpty(request, 200);
    }

    public Result<List<ListedGame>> listGames(String authToken) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/game"))
                .header("authorization", authToken)
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                try {
                    GamesEnvelope envelope = gson.fromJson(response.body(), GamesEnvelope.class);
                    if (envelope != null && envelope.games() != null) {
                        return new Result.Ok<>(envelope.games());
                    }
                    return new Result.Err<>(200, "Server returned an invalid response.");
                } catch (JsonSyntaxException e) {
                    return new Result.Err<>(200, "Unable to parse server response.");
                }
            }
            return new Result.Err<>(response.statusCode(), parseErrorMessage(response.body(), response.statusCode()));
        } catch (IOException e) {
            return new Result.Err<>(0, "Unable to reach server at " + baseUrl + ".");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new Result.Err<>(0, "Request interrupted.");
        }
    }

    private <T> Result<T> sendJson(HttpRequest request, Class<T> successType, int successCode) {
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == successCode) {
                try {
                    T value = gson.fromJson(response.body(), successType);
                    return new Result.Ok<>(value);
                } catch (JsonSyntaxException e) {
                    return new Result.Err<>(successCode, "Unable to parse server response.");
                }
            }
            return new Result.Err<>(response.statusCode(), parseErrorMessage(response.body(), response.statusCode()));
        } catch (IOException e) {
            return new Result.Err<>(0, "Unable to reach server at " + baseUrl + ".");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new Result.Err<>(0, "Request interrupted.");
        }
    }

    private Result<Void> sendEmpty(HttpRequest request, int successCode) {
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == successCode) {
                return new Result.Ok<>(null);
            }
            return new Result.Err<>(response.statusCode(), parseErrorMessage(response.body(), response.statusCode()));
        } catch (IOException e) {
            return new Result.Err<>(0, "Unable to reach server at " + baseUrl + ".");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new Result.Err<>(0, "Request interrupted.");
        }
    }

    private String parseErrorMessage(String body, int statusCode) {
        try {
            var json = gson.fromJson(body, JsonObject.class);
            if (json != null && json.has("message") && !json.get("message").isJsonNull()) {
                return json.get("message").getAsString();
            }
        } catch (Exception ignored) {
            // fall through
        }
        return "HTTP " + statusCode;
    }

    private record RegisterBody(String username, String password, String email) {}

    private record LoginBody(String username, String password) {}

    private record CreateGameBody(String gameName) {}

    private record JoinGameBody(String playerColor, int gameID) {}

    private record GamesEnvelope(List<ListedGame> games) {}
}
