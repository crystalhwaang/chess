package service;

import dataaccess.AuthDAO;
import dataaccess.UserDAO;

public class DatabaseService {
    private final UserDAO userDAO;
    private final AuthDAO authDAO;

    public DatabaseService(UserDAO userDAO, AuthDAO authDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
    }

    public void clearDatabase() {
        userDAO.clearAll();
        authDAO.clearAll();
        // Add gameDAO.clearAll() here
    }
}
