package bloodbankdonationsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;


public class RegisterController implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private Button registerBtn;
    @FXML
    private Label registerMessageLabel;
    @FXML
    private Label confirmPasswordMessageLabel;

    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField fullnameTextField;
    @FXML
    private TextField emailAddressTextField;
    @FXML
    private TextField usernameTextField;

    private Connection connect;
    Alert alert;

    public void initialize(URL url, ResourceBundle resourceBundle){

    }
    public void registerButtonOnAction(ActionEvent event) {
        if (areFieldsEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else if (enterPasswordField.getText().equals(confirmPasswordField.getText())) {
            registerUser();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Password does not match.");
            alert.showAndWait();
        }
    }
    private boolean areFieldsEmpty() {
        return fullnameTextField.getText().isEmpty() ||
                emailAddressTextField.getText().isEmpty() ||
                usernameTextField.getText().isEmpty() ||
                enterPasswordField.getText().isEmpty() ||
                confirmPasswordField.getText().isEmpty();
    }
    public void closeButtonOnAction(ActionEvent event){
        Stage stage=(Stage) closeButton.getScene().getWindow();
        stage.close();

    }
    public void registerUser() {
        connect = database.connectDb();

        String insertToRegister = "INSERT INTO user_account (full_name, email, username, password) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement statement = connect.prepareStatement(insertToRegister);
            statement.setString(1, fullnameTextField.getText());
            statement.setString(2, emailAddressTextField.getText());
            statement.setString(3, usernameTextField.getText());
            statement.setString(4, enterPasswordField.getText());

            statement.executeUpdate();
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Registered Successfully!");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

