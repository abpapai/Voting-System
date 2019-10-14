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
import org.omg.CORBA.PRIVATE_MEMBER;
import testfx.DBConnection.DBHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CheckVIDController implements Initializable {

    @FXML
    public Button go;
    @FXML
    public TextField VID1;
    @FXML
    public AnchorPane apane;

    public static String VID;
    public static Stage stage;

    private Connection connection;
    public PreparedStatement ps;

    @Override

    public void initialize(URL location, ResourceBundle resources) {

        go.setOnAction(event -> {
            try {
                addVoter();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void addVoter() throws IOException {
        try {
            if (!VID1.getText().isEmpty()) {

                VID=VID1.getText();

                DBHandler handler = new DBHandler();
                connection = handler.getConnection();

                String insert = ("SELECT * FROM voter WHERE v_id=?");
                try {
                    ps = connection.prepareStatement(insert);
                }catch (SQLException e) {
                    e.printStackTrace();
                }

                try {
                    ps.setString(1,VID1.getText());
                    ResultSet rs=ps.executeQuery();

                    int count=0;

                    while(rs.next())
                    {
                        count=count+1;
                    }

                    if(count==1)
                    {
                        AnchorPane rootNode = FXMLLoader.load(getClass().getResource("../View/UpdateVoter.fxml"));

                        stage = new Stage();
                        stage.setTitle("e - VOTING SYSTEM");
                        stage.setScene(new Scene(rootNode, 700, 600));
                        stage.setResizable(false);
                        stage.show();

                        dashboardController.stage1.close();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Voter Id Not Found!!");
                        alert.showAndWait();
                    }
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please Enter Voter Id");
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

