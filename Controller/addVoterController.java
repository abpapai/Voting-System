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
//import org.apache.commons.codec.digest.DigestUtils;
import testfx.DBConnection.DBHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class addVoterController  implements Initializable {

    @FXML
    public AnchorPane anchor_pane;
    @FXML
    public TextField v_id,v_name,v_password,ph_no,session;
    @FXML
    public Button submit,back;

    private Connection connection;
    private DBHandler handler;
    private PreparedStatement pst,pst1;

    public void initialize(URL location, ResourceBundle resources) {

        submit.setOnAction((event) ->
        {
            try {
                insertValue();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        back.setOnAction((event) ->
        {
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Dashboard.fxml"));
                anchor_pane.getChildren().setAll(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void insertValue() throws IOException, SQLException {

        //saving data to database

        handler = new DBHandler();
        String insert = "INSERT  INTO voter (v_id,v_name,v_password,ph_no,session)" + "VALUES(?,?,aes_encrypt(?,'key'),?,?)";
        String check = "select v_id from voter";

        connection = handler.getConnection();
        try {

            if (v_id.getText().isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(" Voter ID is empty");
                alert.showAndWait();
            }
            else
            {
                boolean flag1=false;

                pst=connection.prepareStatement(insert);
                try {
                    Integer count=0;

                    pst1=connection.prepareStatement(check);
                    ResultSet rs=pst1.executeQuery();
                    String VID=v_id.getText();

                    while(rs.next()) {
                        if(VID.equals(rs.getString("v_id")))
                        {
                            count=count+1;
                            flag1=true;
                        }
                    }
                    if(count==0) {
                        pst.setString(1, v_id.getText());
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Same Voter ID exists");
                        alert.showAndWait();
                    }
                }
                catch(SQLException e) {
                    System.out.print(e);
                }

                if (v_name.getText().isEmpty())
                {
                    if(flag1==false) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Voter - Name is empty");
                        alert.showAndWait();
                    }
                }
                else {
                    pst.setString(2, v_name.getText());

                    if (v_password.getText().isEmpty())
                    {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Password field is empty");
                        alert.showAndWait();
                    } else {
                        pst.setString(3, v_password.getText());

                        if ( ph_no.getText().isEmpty())
                        {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setContentText("Phone_no field is empty");
                            alert.showAndWait();
                        } else {
                            boolean flag=false;
                            String phno=ph_no.getText();
                            Integer len=phno.length();
                            System.out.print(len);
                                if(len==10)
                                {
                                    pst.setString(4, ph_no.getText());
                                    flag=true;
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setContentText("Phone no must be of 10 digits");
                                    alert.showAndWait();
                                }

                            if(flag==true) {
                                if (session.getText().isEmpty()) {

                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setContentText("Session field is empty");
                                    alert.showAndWait();

                                } else if (session.getText().length()!=4) {

                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setContentText("Session must be of 4 digit");
                                    alert.showAndWait();

                                } else {
                                    pst.setString(5, session.getText());

                                    pst.executeUpdate();

                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText(" Successfully Submitted");
                                    alert.showAndWait();

                                    AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Dashboard.fxml"));
                                    anchor_pane.getChildren().setAll(pane);
                                }
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Check all your fields Properly");
            alert.showAndWait();
            System.out.print(e);
        }finally {
            connection.close();
        }

        }

}