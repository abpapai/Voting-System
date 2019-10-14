package testfx.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import testfx.DBConnection.DBHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class YearController implements Initializable {

    @FXML
    public Button go;
    @FXML
    public TextField year;

    @FXML
    public AnchorPane apane;

    public  String YEAR;
    public static Stage stage;
    public Connection connection;
    public PreparedStatement ps;

    @Override

    public void initialize(URL location, ResourceBundle resources) {

        go.setOnAction(event -> {
            try {
                voting();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void voting() throws IOException {
        try {
            if (year.getText().length()!=4){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Year should be 4 digits");
                alert.showAndWait();
            } else if (!year.getText().isEmpty()) {

                YEAR=year.getText();

                DBHandler handler = new DBHandler();
                connection = handler.getConnection();

                String update = "UPDATE admin SET year='"+YEAR+"'";
                try {
                    ps = connection.prepareStatement(update);
                    ps.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Voting started");
                alert.showAndWait();
                AnchorPane pane=null;

                dashboardController.stage2.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please Enter Voting Year");
                alert.showAndWait();
            }
        }
        catch(Exception e) {
            System.out.print(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

