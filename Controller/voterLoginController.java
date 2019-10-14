package testfx.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
//import org.apache.commons.codec.digest.DigestUtils;
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

public class voterLoginController implements Initializable {

    @FXML
    public AnchorPane anchorPane;
    @FXML
    public TextField v_id;
    @FXML
    public PasswordField v_pass;
    @FXML
    public Button login,back;

    private Connection connection;
    private DBHandler handler;
    private PreparedStatement pst,pst1;
    public int check=0;
    public static String VID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        login.setOnAction((event) ->
        {
            try {
                login();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        });
        back.setOnAction((event) ->
        {
            try {
                bAck();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }
    private void bAck() throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/SignInPage.fxml"));
        anchorPane.getChildren().setAll(pane);

    }

 private void login() throws IOException {

     handler = new DBHandler();
     connection = handler.getConnection();

     if (!v_pass.getText().isEmpty() && !v_id.getText().isEmpty()) {


//SELECT aes_decrypt(v_password,'key') from voter;
         String insert1 = "SELECT * from voter where V_id='" + v_id.getText() + "' AND voted='YES'";
         try {
             pst1 = connection.prepareStatement(insert1);
             ResultSet rs1 = pst1.executeQuery();
             while (rs1.next()) {
                 check = check + 1;
             }
             System.out.print(check);

         } catch (SQLException e) {
             e.printStackTrace();
         }
// if(VID.equals(rs.getString("v_id")))
         if (check == 0) {
             String insert = "SELECT v_id,aes_decrypt(v_password,'key')as decryptpas from voter WHERE VOTED IS NULL";

             try {
                 int count = 0;
                 String session = null, current_session = null;
                 pst = connection.prepareStatement(insert);
                 ResultSet rs = pst.executeQuery();
                 VID = v_id.getText();
                 String VPAS = v_pass.getText();
                 while (rs.next()) {
                     if (VID.equals(rs.getString("v_id")) && VPAS.equals(rs.getString("decryptpas"))) {
                         count = 1;

                     }
                 }


                 if(count==1)
                 {

                     System.out.print("DONE");
                 String select = "SELECT session FROM voter WHERE v_id='" + VID + "' ";
                 pst = connection.prepareStatement(select);
                 ResultSet rs1 = pst.executeQuery();
                 while (rs1.next()) {
                     session = rs1.getString("session");
                 }
                 int voterSession = Integer.parseInt(session);

                 String select1 = "SELECT year FROM admin ";
                 pst = connection.prepareStatement(select1);
                 ResultSet rs2 = pst.executeQuery();
                 while (rs2.next()) {
                     current_session = rs2.getString("year");
                 }

                 int adminSession = Integer.parseInt(current_session);


                 if ((voterSession <= (adminSession - 4)) || (voterSession > adminSession)) {
                     Alert alert = new Alert(Alert.AlertType.WARNING);
                     alert.setContentText("You are not eligible to vote for this year");
                     alert.showAndWait();
                 } else {

                     AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/VotingPage.fxml"));
                     anchorPane.getChildren().setAll(pane);
                     connection.close();
                 }
             }
             else
                 {

                     Alert alert = new Alert(Alert.AlertType.WARNING);
                     alert.setContentText("Invalid Name OR Password");
                     alert.showAndWait();
                    }
             } catch (SQLException e) {
                 e.printStackTrace();
             }

         } else {
             check=0;
             Alert alert = new Alert(Alert.AlertType.WARNING);
             alert.setContentText("You have Already Voted!!");
             alert.showAndWait();
         }

     }
     else {
         Alert alert = new Alert(Alert.AlertType.WARNING);
         alert.setContentText("Please fill all fields properly!!");
         alert.showAndWait();
     }

//         try {
//             connection.close();
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
     }
 }

