package org.user_management_project_JDBC;

import org.user_management_project_JDBC.model.User;
import org.user_management_project_JDBC.repository.UserRepoImpl;
import org.user_management_project_JDBC.util.DataBaseConnector;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try (Connection conn = DataBaseConnector.getInstance()) {
            UserRepoImpl repository = new UserRepoImpl();
            String option;

            do {
                String[] options = {"Update", "Delete", "Add", "List", "Exit"};
                option = (String) JOptionPane.showInputDialog(
                        null,
                        "Select an option",
                        "User management",
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                if (option == null || option.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "You must select an option");
                    continue;
                }

                switch (option) {
                    case "Update" -> updateUser(repository);
                    case "Delete" -> deleteUser(repository);
                    case "Add" -> addUser(repository);
                    case "List" -> listUsers(repository);
                }
            } while (!"Exit".equalsIgnoreCase(option));
            JOptionPane.showMessageDialog(null, "Exiting...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateUser(UserRepoImpl repository) {
        int id = Integer.parseInt(JOptionPane.showInputDialog("Insert User ID:"));
        User existingUser = repository.perId(id);
        if (existingUser == null) {
            JOptionPane.showMessageDialog(null, "User with ID " + id + " does not exist.");
        } else {
            String username = JOptionPane.showInputDialog("Insert new username:");
            String password = JOptionPane.showInputDialog("Insert new password:");
            String email = JOptionPane.showInputDialog("Insert new email:");
            User user = new User(id, username, password, email);
            repository.save(user);
            JOptionPane.showMessageDialog(null, "User updated successfully");
        }
    }

    private static void deleteUser(UserRepoImpl repository) {
        int id = Integer.parseInt(JOptionPane.showInputDialog("Insert ID of user to delete:"));
        if (repository.perId(id) == null) {
            JOptionPane.showMessageDialog(null, "User with ID " + id + " does not exist.");
        } else {
            repository.delete(id);
            JOptionPane.showMessageDialog(null, "User deleted successfully");
        }
    }

    private static void addUser(UserRepoImpl repository) {
        String username = JOptionPane.showInputDialog("Insert username:");
        String password = JOptionPane.showInputDialog("Insert password:");
        String email = JOptionPane.showInputDialog("Insert email:");
        User user = new User(null, username, password, email);
        repository.save(user);
        JOptionPane.showMessageDialog(null, "User added successfully");
    }

    private static void listUsers(UserRepoImpl repository) {
        List<User> users = repository.list();
        StringBuilder sb = new StringBuilder("Usuarios:\n");
        users.forEach(u -> sb.append(u).append("\n"));
        JOptionPane.showMessageDialog(null, sb.toString());
    }
}
