package com.example.movierental.movierental;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final String DATABASE_URL = "jdbc:mysql://localhost:3306/movierental";
    private final String USERNAME = "root";
    private final String PASSWORD = "1234";

    @FXML
    void signIn(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Connect to the database and validate user credentials
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)) {
            String query = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Sign in successful!");
            } else {
                System.out.println("Invalid username or password!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void registerUser(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try (Connection conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD)) {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User registered successfully!");
                // You can add code here to show a success message to the user
            } else {
                System.out.println("Failed to register user!");
                // You can add code here to show an error message to the user
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            // You can add code here to handle database errors and show an error message to the user
        }
    }
}
