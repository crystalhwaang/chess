import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import server.Server;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws DataAccessException, SQLException {
        // run the testing server
        Server server = new Server();
        server.run(8080);
//        System.out.println("♕ 240 Chess Server");
        DatabaseManager.createDatabase();
    }
}