package org.user_management_project_JDBC;


import org.user_management_project_JDBC.model.User;
import org.user_management_project_JDBC.repository.Repository;
import org.user_management_project_JDBC.repository.UserRepoImpl;
import org.user_management_project_JDBC.util.DataBaseConnector;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DataBaseConnector.getInstance()) {
            Repository<User> repository = new UserRepoImpl();
            String option = "";

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
                if (option == null) break;

                switch (option) {
                    case "Update" -> {
                        int id = Integer.parseInt(JOptionPane.showInputDialog("Insert User ID:"));
                        String username = JOptionPane.showInputDialog("Insert new username:");
                        String password = JOptionPane.showInputDialog("Insert new password:");
                        String email = JOptionPane.showInputDialog("Insert new email:");
                        User usuario = new User(id, username, password, email);
                        repository.save(usuario);
                        JOptionPane.showMessageDialog(null, "User updated successfully");
                    }
                    case "Delete" -> {
                        int id = Integer.parseInt(JOptionPane.showInputDialog("Insert ID of user that will be deleted:"));
                        repository.delete(id);
                        JOptionPane.showMessageDialog(null, "User deleted successfully");
                    }
                    case "Add" -> {
                        String username = JOptionPane.showInputDialog("Insert username:");
                        String password = JOptionPane.showInputDialog("Insert password:");
                        String email = JOptionPane.showInputDialog("Insert email:");
                        User user = new User(null, username, password, email);
                        repository.save(user);
                        JOptionPane.showMessageDialog(null, "User added successfully");
                    }
                    case "List" -> {
                        List<User> users = repository.list();
                        StringBuilder sb = new StringBuilder("Usuarios:\n");
                        users.forEach(u -> sb.append(u).append("\n"));
                        JOptionPane.showMessageDialog(null, sb.toString());
                    }
                }
            } while (!option.equalsIgnoreCase("Exit"));
            JOptionPane.showMessageDialog(null, "Exiting...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}