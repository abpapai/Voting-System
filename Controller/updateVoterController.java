package testfx.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import testfx.DBConnection.DBHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import java.util.ResourceBundle;

public class updateVoterController implements Initializable {



    @FXML
    private TableView<modelTable1>table;

    @FXML
    private TableColumn<modelTable1,Integer> v_id;
    @FXML
    private TableColumn<modelTable1,String> v_name;

    @FXML
    private TableColumn<modelTable1,Long> ph_no;
    @FXML
    private TableColumn<modelTable1,String> session;

    @FXML
    public TextField vname,phn,sess;
    @FXML
    public Button back,update,delete;
    @FXML
    public AnchorPane anchor_pane;

    private Connection connection;
    private PreparedStatement pst;

    ObservableList<modelTable1> oblist1 = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DBHandler handler = new DBHandler();
        connection = handler.getConnection();

        CheckVIDController call=new CheckVIDController();
        String v = call.VID;

        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM voter WHERE v_id ='"+v+"'");

            while (rs.next()) {
                oblist1.add(new modelTable1(rs.getString("v_id"),rs.getString("v_name"),rs.getLong("ph_no"),rs.getString("session")));
            }

            v_id.setCellValueFactory(new PropertyValueFactory<>("v_id"));
            v_name.setCellValueFactory(new PropertyValueFactory<>("VoterName"));
            ph_no.setCellValueFactory(new PropertyValueFactory<>("Phn"));
            session.setCellValueFactory(new PropertyValueFactory<>("Session"));

            table.setItems(oblist1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        back.setOnAction(event -> {
            CheckVIDController.stage.close();
        });

        update.setOnAction(event -> {
            try {

                if (!vname.getText().isEmpty() && !phn.getText().isEmpty() && !sess.getText().isEmpty())
                {
                    System.out.print(v);
                    String x = "UPDATE voter SET v_name = '" + vname.getText() + "',ph_no='"+phn.getText()+"',session ='" + sess.getText() + "' WHERE v_id='"+v+"'";
                    pst = connection.prepareStatement(x);
                    pst.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("<--Successfully Updated-->");
                    alert.show();

                    CheckVIDController.stage.close();

                    AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Dashboard.fxml"));
                    anchor_pane.getChildren().setAll(pane);



                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("!!Please Fill ALL the Fields!!");
                    alert.showAndWait();
                }
            } catch(SQLException | IOException e){
                        e.printStackTrace();
                    }
            finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        });

        delete.setOnAction(event ->
        {
            String del="DELETE  FROM voter  WHERE v_id='"+v+"'";
            try {
                pst = connection.prepareStatement(del);
                pst.executeUpdate();
                CheckVIDController.stage.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("<--Successfully Deleted-->");
                alert.showAndWait();
                AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Dashboard.fxml"));
                anchor_pane.getChildren().setAll(pane);

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}


