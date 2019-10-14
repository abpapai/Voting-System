package testfx.Controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import testfx.DBConnection.DBHandler;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class signIncontroller implements Initializable {
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public Button admin_login,voter_login;
    @FXML
    public Menu about,help;

    public static int strt,stp;

    public DBHandler handler;
    public Connection connection;
    public PreparedStatement pst;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

       admin_login.setOnAction((event) ->
        {
            makeFadeOut();
                try{
                adminLogin();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        voter_login.setOnAction((event) ->
        {
            handler=new DBHandler();
            connection=handler.getConnection();
            String get = "select start,stop from admin";

            try {
                pst = connection.prepareStatement(get);

                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    strt = rs.getInt("start");
                    stp = rs.getInt("stop");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (strt == 1 && stp == 0)
            {
                makeFadeOut();
                try {
                    voterLogin();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Voting cannot be done now because 'ADMIN' has not started yet ");
                alert.showAndWait();
            }
        });

        about.setOnAction(event ->
        {
            aboutDev();
        });
        
        help.setOnAction(event -> 
        {
            helpApp();
        });
        
    }

    private void helpApp() {

        Alert alertDialog= new Alert(Alert.AlertType.INFORMATION);
        alertDialog.setTitle("e- Voting ");
        alertDialog.setHeaderText("How to use App ?");
        alertDialog.setContentText("Voter section : \n Here you would be asked to enter your login credentials," +
                " PLEASE ENTER IT CORRECTLY to vote for desired candidate \n" +
                "Admin section : \n Admin has the full privilege to ADD,UPDATE,DELETE eligible CANDIDATES and VOTERS");
        alertDialog.showAndWait();
    }

    private void aboutDev() {
        Alert alertDialog= new Alert(Alert.AlertType.INFORMATION);
        alertDialog.setTitle("e - Voting");
        alertDialog.setHeaderText("About Developers");
        alertDialog.setContentText("This app is a full fledged app developed and copyright by SAIFUL , SUMIT and AZIZ");
        alertDialog.showAndWait();
    }

    private void makeFadeOut() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(3000));
        fadeTransition.setNode(anchorPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        fadeTransition.play();
    }

    private  void adminLogin() throws IOException {
        AnchorPane pane= FXMLLoader.load(getClass().getResource("../View/AdminLogin.fxml"));
        anchorPane.getChildren().setAll(pane);
   }

    private  void voterLogin() throws IOException {
        AnchorPane pane= FXMLLoader.load(getClass().getResource("../View/VoterLogin.fxml"));
        anchorPane.getChildren().setAll(pane);
    }
    }
