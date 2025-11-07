package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUserDAO implements UserDAO{


    @Override
    public UserData getUser(String username) throws DataAccessException {
        String sql = "SELECT username, password, email FROM USER_DATA WHERE username = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new UserData(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                );
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException("Error fetching user", e);
        }
        return null;
    }

    @Override
    public void createUser(UserData user) throws DataAccessException {
        String sql = "INSERT INTO USER_DATA (username, password, email) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.username());
            stmt.setString(2, hashUserPassword(user.password()));
            stmt.setString(3, user.email());
            stmt.executeUpdate();

        } catch (SQLException | DataAccessException e) {
            if (e.getMessage().contains("Duplicate")) {
                throw new DataAccessException("User already exists", e);
            }
            throw new DataAccessException("Error creating user", e);
        }
    }

    private String hashUserPassword(    String clearTextPassword) {
        return BCrypt.hashpw(clearTextPassword, BCrypt.gensalt());
    }

    @Override
    public void clearAll() throws DataAccessException {
        String sql = "DELETE FROM USER_DATA";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException("Error clearing users", e);
        }
    }
}
