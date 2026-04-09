package client;

import java.util.Locale;
import java.util.Scanner;

public class ClientMain {
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
                running = handlePreloginCommand(command);
            }
        }
    }

    private static boolean handlePreloginCommand(String command) {
        return switch (command) {
            case "help" -> {
                System.out.println(preloginHelp());
                yield true;
            }
            case "quit" -> {
                System.out.println("Bye!");
                yield false;
            }
            case "login" -> {
                System.out.println("Welcome back! Please enter your username and password.");
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

    private static String preloginHelp() {
        return """
                Possible Actions:
                - help: Displays this help text and available actions.
                - register <username> <password> <email>: Creates a new account.
                - login <username> <password>: Logs into an existing account.
                - quit: Exits the chess game.
                """;
    }
}
