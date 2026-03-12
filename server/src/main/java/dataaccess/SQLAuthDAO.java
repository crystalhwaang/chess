package dataaccess;
import model.AuthData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLAuthDAO implements AuthDAO {
    @Override
    public void createAuth(AuthData authData) throws DataAccessException {
        String sql = "INSERT INTO AUTH_DATA (authToken, username) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authData.authToken());
            stmt.setString(2, authData.username());
            stmt.executeUpdate();
        } catch (SQLException | DataAccessException e) {
            if (e.getMessage().contains("Duplicate")) {
                throw new DataAccessException("Auth token already exists", e);
            }
            throw new DataAccessException("Error creating auth token", e);
        }
    }

    @Override
    public AuthData getAuth(String token) throws DataAccessException {
        String sql = "SELECT authToken, username FROM AUTH_DATA WHERE authToken = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new AuthData(rs.getString("username"), rs.getString("authToken"));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching auth token", e);
        }
        return null;
    }

    public void logoutUser(String authToken) throws DataAccessException {
        String sql = "DELETE FROM AUTH_DATA WHERE authToken = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new DataAccessException("Auth token not found");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting auth token", e);
        }
    }

    @Override
    public void clearAll() throws DataAccessException {
        String sql = "DELETE FROM AUTH_DATA";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException("Error clearing auth tokens", e);
        }
    }

    @Override
    public void deleteAuth(String token) throws DataAccessException {
        String sql = "DELETE FROM AUTH_DATA WHERE authToken = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted == 0) {
                throw new DataAccessException("Auth token not found");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting auth token", e);
        }
    }
}