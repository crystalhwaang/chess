package client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Locale;
import java.util.Scanner;

public class ClientMain {
    private static final String SERVER_URL = "http://localhost:8080";
    private static final Gson GSON = new Gson();
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static LoginSuccess currentSession;

    public static void main(String[] args) {
        System.out.println("240 Chess Client");
        System.out.println(preloginHelp());

        try (var scanner = new Scanner(System.in)) {
            var running = true;
            while (running) {
                System.out.print("\n[PRELOGIN] >> ");
                if (!scanner.hasNextLine()) {
                    break;
                }

                var input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    continue;
                }

                var lowerCased = input.toLowerCase(Locale.ROOT);
                var command = lowerCased.split("\\s+")[0];
                running = handlePreloginCommand(command, scanner);
            }
        }
    }

    private static boolean handlePreloginCommand(String command, Scanner scanner) {
        return switch (command) {
            case "help" -> {
                System.out.println(preloginHelp());
                yield true;
            }
            case "quit" -> {
                System.out.println("Exiting the program.");
                yield false;
            }
            case "login" -> {
                login(scanner);
                yield true;
            }
            case "register" -> {
                System.out.println("Welcome! Please enter your username, password, and email.");
                yield true;
            }
            default -> {
                System.out.println("Unknown command. Type 'help' to see available prelogin commands.");
                yield true;
            }
        };
    }

    private static void login(Scanner scanner) {
        System.out.print("Username: ");
        if (!scanner.hasNextLine()) {
            return;
        }
        var username = scanner.nextLine().trim();

        System.out.print("Password: ");
        if (!scanner.hasNextLine()) {
            return;
        }
        var password = scanner.nextLine().trim();

        if (username.isBlank() || password.isBlank()) {
            System.out.println("Login failed. Username and password required.");
            return;
        }

        var requestBody = GSON.toJson(new LoginRequest(username, password));
        var request = HttpRequest.newBuilder()
                .uri(URI.create(SERVER_URL + "/session"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                currentSession = GSON.fromJson(response.body(), LoginSuccess.class);
                if (currentSession != null
                        && currentSession.authToken() != null
                        && !currentSession.authToken().isBlank()) {
                    System.out.printf("Logged in as %s.%n", currentSession.username());
                    return;
                }
                currentSession = null;
                System.out.println("Login failed. Server returned an invalid response.");
                return;
            }

            System.out.printf("Login failed. %s%n", parseErrorMessage(response.body(), response.statusCode()));
        } catch (IOException | InterruptedException e) {
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            System.out.printf("Login failed. Unable to reach server at %s.%n", SERVER_URL);
        } catch (JsonSyntaxException e) {
            System.out.println("Login failed. Unable to parse server response.");
        }
    }

    private static String parseErrorMessage(String body, int statusCode) {
        try {
            var json = GSON.fromJson(body, JsonObject.class);
            if (json != null && json.has("message") && !json.get("message").isJsonNull()) {
                return json.get("message").getAsString();
            }
        } catch (Exception ignored) {
            // fall through
        }
        return "HTTP " + statusCode;
    }

    private static String preloginHelp() {
        return """
                Possible Actions:
                - help: Displays this help text and available actions.
                - register <username> <password> <email>: Creates a new account.
                - login: Prompts for username and password, then logs into the server.
                - quit: Exits the program.
                """;
    }

    private record LoginRequest(String username, String password) {}

    private record LoginSuccess(String username, String authToken) {}
}
