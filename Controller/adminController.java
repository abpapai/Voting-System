package testfx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class adminController implements Initializable {

    @FXML
    public AnchorPane anchorPane;
    @FXML
    public TextField name;
    @FXML
    public PasswordField adminPassword;
    @FXML
    public Button login,Back1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        login.setOnAction((event) ->
        {
            if (name.getText().equals("a") && adminPassword.getText().equals("b")) {
                try {
                     newPage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Invalid Name OR Password");
                alert.showAndWait();
            }
        });
        Back1.setOnAction((event) ->
        {

            AnchorPane pane = null;
            try {
                pane = FXMLLoader.load(getClass().getResource("../View/SignInPage.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            anchorPane.getChildren().setAll(pane);

        });
    }

    private void newPage() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Dashboard.fxml"));
        anchorPane.getChildren().setAll(pane);
    }
}

