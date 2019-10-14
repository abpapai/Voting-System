package testfx.Controller;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import testfx.DBConnection.DBHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {

    @FXML
    public AnchorPane anchorPane;
    @FXML
    public Button add_voter,update_voter,add_candidate,update_candidate,voting_result,back,start,stop;

    public static int strt,stp;

    public static Stage stage1,stage2;
    public DBHandler handler;
    public Connection connection;
    public PreparedStatement pst,pst1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handler = new DBHandler();
        connection = handler.getConnection();

        back.setOnAction((event )->
                {
                    AnchorPane pane = null;
                    try {
                        pane = FXMLLoader.load(getClass().getResource("../View/SignInPage.fxml"));
                        anchorPane.getChildren().setAll(pane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                });


        add_voter.setOnAction((event) ->
        {
            try {

                String check = "SELECT start,stop from admin";
                try {
                    pst = connection.prepareStatement(check);
                    ResultSet rs = pst.executeQuery();
                    while (rs.next()) {
                        strt = rs.getInt("start");
                        stp = rs.getInt("stop");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (strt == 0 && stp == 0) {
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/AddVoter.fxml"));
                    anchorPane.getChildren().setAll(pane);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Cannot add now");
                    alert.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        update_voter.setOnAction((event) ->
        {
            try {

                String check = "SELECT start,stop from admin";
                try {
                    pst = connection.prepareStatement(check);
                    ResultSet rs = pst.executeQuery();
                    while (rs.next()) {
                        strt = rs.getInt("start");
                        stp = rs.getInt("stop");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (strt == 0 && stp == 0) {
                    AnchorPane rootNode = FXMLLoader.load(getClass().getResource("../View/CheckVID.fxml"));

                    stage1 = new Stage();
                    stage1.setTitle("e - VOTING SYSTEM");
                    stage1.setScene(new Scene(rootNode, 400, 200));
                    stage1.setResizable(false);
                    stage1.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Cannot update now");
                    alert.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        add_candidate.setOnAction((event) ->
        {
            try {

                String check = "SELECT start,stop from admin";
                try {
                    pst = connection.prepareStatement(check);
                    ResultSet rs = pst.executeQuery();
                    while (rs.next()) {
                        strt = rs.getInt("start");
                        stp = rs.getInt("stop");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (strt == 0 && stp == 0) {
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/AddCandidate.fxml"));
                    anchorPane.getChildren().setAll(pane);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Cannot add now");
                    alert.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        update_candidate.setOnAction((event) ->
        {
            try {

                String check = "SELECT start,stop from admin";
                try {
                    pst = connection.prepareStatement(check);
                    ResultSet rs = pst.executeQuery();
                    while (rs.next()) {
                        strt = rs.getInt("start");
                        stp = rs.getInt("stop");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (strt == 0 && stp == 0) {
                    AnchorPane rootNode = FXMLLoader.load(getClass().getResource("../View/CheckCID.fxml"));

                    stage1 = new Stage();
                    stage1.setTitle("e - VOTING SYSTEM");
                    stage1.setScene(new Scene(rootNode, 400, 200));
                    stage1.setResizable(false);
                    stage1.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Cannot update now");
                    alert.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        voting_result.setOnAction(event -> {
            try {
                String check = "SELECT start,stop from admin";
                try {
                    pst = connection.prepareStatement(check);
                    ResultSet rs = pst.executeQuery();
                    while (rs.next()) {
                        strt = rs.getInt("start");
                        stp = rs.getInt("stop");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if ((strt == 1 && stp == 1)) {
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Result.fxml"));
                    anchorPane.getChildren().setAll(pane);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Cannot see results now");
                    alert.showAndWait();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        start.setOnAction(event1 -> {

            String check = "SELECT start,stop from admin";
            try {
                pst = connection.prepareStatement(check);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    strt = rs.getInt("start");
                    stp = rs.getInt("stop");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (strt ==0 && stp==0) {
                String update = "UPDATE admin SET start=1";
                String update1 = "SELECT start from admin";
                try {
                    pst = connection.prepareStatement(update);
                    pst.executeUpdate();
                    pst1 = connection.prepareStatement(update1);


                    ResultSet rs = pst1.executeQuery();
                    while (rs.next()) {
                        strt = rs.getInt("start");

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                AnchorPane rootNode = null;
                try {
                    rootNode = FXMLLoader.load(getClass().getResource("../View/VotingYear.fxml"));
                    stage2 = new Stage();
                    stage2.setTitle("e - VOTING SYSTEM");
                    stage2.setScene(new Scene(rootNode, 400, 200));
                    stage2.setResizable(false);
                    stage2.show();



                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (strt==1 && stp==0){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Currently voting is going on !!!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Voting has been ended !!!");
                alert.showAndWait();
            }
        });

        stop.setOnAction(event2 -> {

            String count = "SELECT start,stop from admin";
            try {
                pst = connection.prepareStatement(count);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    strt = rs.getInt("start");
                    stp = rs.getInt("stop");

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (strt == 1 && stp == 0) {
                String update = "UPDATE admin SET stop=1";
                String update1 = "SELECT stop from admin";
                try {
                    pst = connection.prepareStatement(update);
                    pst.executeUpdate();
                    pst1 = connection.prepareStatement(update1);

                    ResultSet rs = pst1.executeQuery();
                    while (rs.next()) {
                        stp = rs.getInt("stop");

                    }
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Voting  stopped");
                    alert.showAndWait();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (strt==0 && stp==0){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Voting hasn't been started yet !!!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Voting has been ended");
                alert.showAndWait();
            }
        });

    }
}