package org.user_management_project_JDBC.repository;

import org.user_management_project_JDBC.model.User;
import org.user_management_project_JDBC.util.DataBaseConnector;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepoImpl implements Repository<User> {

    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String UPDATE_USER = "UPDATE users SET username=?, password=?, email=? WHERE id=?";
    private static final String INSERT_USER = "INSERT INTO users(username, password, email) VALUES(?, ?, ?)";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";

    private static final Logger LOGGER = Logger.getLogger(UserRepoImpl.class.getName());

    private final Connection getConnection() throws SQLException {
        return DataBaseConnector.getInstance();
    }

    @Override
    public List<User> list() {
        List<User> users = new ArrayList<>();
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_USERS)) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error listing users", e);
        }
        return users;
    }

    @Override
    public User perId(Integer id) {
        User user = null;
        try (PreparedStatement stmt = getConnection().prepareStatement(SELECT_USER_BY_ID)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching user by ID", e);
        }
        return user;
    }

    @Override
    public void save(User user) {
        String sql = (user.getId() != null && user.getId() > 0) ? UPDATE_USER : INSERT_USER;

        if (user.getId() != null && user.getId() > 0) {
            if (perId(user.getId()) == null) {
                JOptionPane.showMessageDialog(null, "User with ID " + user.getId() + " does not exist.");
                return;
            }
        }

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());

            if (user.getId() != null && user.getId() > 0) {
                stmt.setInt(4, user.getId());
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error saving user", e);
        }
    }

    @Override
    public void delete(Integer id) {
        if (perId(id) == null) {
            JOptionPane.showMessageDialog(null, "User with ID " + id + " does not exist.");
            return;
        }

        try (PreparedStatement stmt = getConnection().prepareStatement(DELETE_USER)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting user", e);
        }
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        return user;
    }
}
