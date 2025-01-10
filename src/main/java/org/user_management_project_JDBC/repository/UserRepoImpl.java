package org.user_management_project_JDBC.repository;

import org.user_management_project_JDBC.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.user_management_project_JDBC.util.DataBaseConnector;

public class UserRepoImpl implements Repository<User> {

    private final Connection getConnection() throws SQLException {
        return DataBaseConnector.getInstance();
    }

    @Override
    public List<User> list() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                User user = CreateUser(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User perId(Integer id) {
        User user = null;
        String query = "SELECT * FROM users WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = CreateUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
    }
        return user;
    }

    @Override
    public void save(User user) {
        String sql;
        if (user.getId() != null && user.getId() > 0) {
            sql = "UPDATE users SET username=?, password=?, email=? WHERE id=?";
        } else {
            sql = "INSERT INTO users(username, password, email) VALUES(?,?,?)";
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
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Integer id) {
        String query = "DELETE FROM users WHERE id = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private User CreateUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        return user;
    }


}
