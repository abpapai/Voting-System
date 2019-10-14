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

public class updateCanController implements Initializable {



    @FXML
    private TableView<modelTable> table;

    @FXML
    private TableColumn<modelTable,String> c_id,c_name,achievement;
    @FXML
    public TextField cname;
    @FXML
    public TextArea achi;
    @FXML
    public Button back,update,delete;
    @FXML
    public AnchorPane anchorPane;

    private Connection connection;
    private PreparedStatement pst;

    ObservableList<modelTable> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DBHandler handler = new DBHandler();
        connection = handler.getConnection();

        CheckCIDController call=new CheckCIDController();
        String c= call.CID;

        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM candidate WHERE c_id='"+c+"'");

            while (rs.next()) {
                oblist.add(new modelTable(rs.getString("c_id"),rs.getString("c_name"),rs.getString("achievement")));
            }

            c_id.setCellValueFactory(new PropertyValueFactory<>("c_id"));
            c_name.setCellValueFactory(new PropertyValueFactory<>("CandidateName"));
            achievement.setCellValueFactory(new PropertyValueFactory<>("Achievement"));

            table.setItems(oblist);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        back.setOnAction(event -> {
            CheckCIDController.stage.close();
        });

        update.setOnAction(event -> {
            try {
                if (!cname.getText().isEmpty()&& !achi.getText().isEmpty())
                {

                String x = "UPDATE candidate SET c_name = '"+cname.getText()+"',achievement ='"+achi.getText()+"' WHERE c_id='"+c+"'";
                pst = connection.prepareStatement(x);
                pst.executeUpdate();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("<--Successfully Updated-->");
                    alert.showAndWait();
                    CheckCIDController.stage.close();
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Dashboard.fxml"));
                    anchorPane.getChildren().setAll(pane);
            } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("!!Please Fill ALL the Fields!!");
                    alert.show();
                }
            }catch (SQLException | IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        });

        delete.setOnAction(event ->
        {
            String del="DELETE  FROM candidate WHERE c_id='"+c+"'";
            try {
                pst = connection.prepareStatement(del);
                pst.executeUpdate();
                CheckCIDController.stage.close();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("<--Successfully Deleted-->");
                alert.showAndWait();
                AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Dashboard.fxml"));
                anchorPane.getChildren().setAll(pane);

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


