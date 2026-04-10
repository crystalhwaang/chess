package client;

import java.util.Locale;
import java.util.Scanner;

public class ClientMain {
    private static final String SERVER_URL = "http://localhost:8080";
    private static final ServerFacade SERVER = new ServerFacade(SERVER_URL);
    private static ServerFacade.AuthResult currentSession;

    public static void main(String[] args) {
        System.out.println("240 Chess Client");
        System.out.println(preloginHelp());

        try (var scanner = new Scanner(System.in)) {
            var running = true;
            while (running) {
                if (currentSession != null) {
                    System.out.print("\n[POSTLOGIN] >> ");
                } else {
                    System.out.print("\n[PRELOGIN] >> ");
                }
                if (!scanner.hasNextLine()) {
                    break;
                }

                var input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    continue;
                }

                var lowerCased = input.toLowerCase(Locale.ROOT);
                var command = lowerCased.split("\\s+")[0];
                if (currentSession != null) {
                    running = handlePostloginCommand(command);
                } else {
                    running = handlePreloginCommand(command, scanner);
                }
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
                register(scanner);
                yield true;
            }
            default -> {
                System.out.println("Unknown command. Type 'help' to see available prelogin commands.");
                yield true;
            }
        };
    }

    private static boolean handlePostloginCommand(String command) {
        return switch (command) {
            case "help" -> {
                System.out.println(postloginHelp());
                yield true;
            }
            case "quit" -> {
                System.out.println("Exiting the program.");
                yield false;
            }
            case "logout" -> {
                logout();
                yield true;
            }
            default -> {
                System.out.println("Unknown command. Type 'help' to see available postlogin commands.");
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

        switch (SERVER.login(username, password)) {
            case ServerFacade.Result.Ok(ServerFacade.AuthResult auth) -> {
                if (auth.authToken() != null && !auth.authToken().isBlank()) {
                    currentSession = auth;
                    System.out.printf("Logged in as %s.%n", auth.username());
                    System.out.println(postloginHelp());
                } else {
                    System.out.println("Login failed. Server returned an invalid response.");
                }
            }
            case ServerFacade.Result.Err(var code, var message) ->
                    System.out.printf("Login failed. %s%n", message);
        }
    }

    private static void register(Scanner scanner) {
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

        System.out.print("Email: ");
        if (!scanner.hasNextLine()) {
            return;
        }
        var email = scanner.nextLine().trim();

        if (username.isBlank() || password.isBlank() || email.isBlank()) {
            System.out.println("Registration failed. Username, password, and email are required.");
            return;
        }

        switch (SERVER.register(username, password, email)) {
            case ServerFacade.Result.Ok(ServerFacade.AuthResult auth) -> {
                if (auth.authToken() != null && !auth.authToken().isBlank()) {
                    currentSession = auth;
                    System.out.printf("Registered and logged in as %s.%n", auth.username());
                    System.out.println(postloginHelp());
                } else {
                    System.out.println("Registration failed. Server returned an invalid response.");
                }
            }
            case ServerFacade.Result.Err(var code, var message) ->
                    System.out.printf("Registration failed. %s%n", message);
        }
    }

    private static void logout() {
        if (currentSession == null) {
            return;
        }
        var token = currentSession.authToken();
        var username = currentSession.username();
        switch (SERVER.logout(token)) {
            case ServerFacade.Result.Ok(var ignored) -> {
                System.out.printf("Logged out %s.%n", username);
                currentSession = null;
                System.out.println(preloginHelp());
            }
            case ServerFacade.Result.Err(var code, var message) ->
                    System.out.printf("Logout failed. %s%n", message);
        }
    }

    private static String preloginHelp() {
        return """
                Possible Actions:
                - help: Displays this help text and available actions.
                - register: Prompts for username, password, and email, then creates an account and logs you in.
                - login: Prompts for username and password, then logs into the server.
                - quit: Exits the program.
                """;
    }

    private static String postloginHelp() {
        return """
                Possible Actions:
                - help: Displays this help text and available actions.
                - logout: Ends your session and returns to the pre-login menu.
                - quit: Exits the program.
                """;
    }
}
