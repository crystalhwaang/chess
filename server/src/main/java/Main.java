import server.Server;

public class Main {
    public static void main(String[] args) {
        // run the testing server here
        Server server = new Server();
        server.run(8080);
        System.out.println("♕ 240 Chess Server");
    }
}