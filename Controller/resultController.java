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
import java.util.ResourceBundle;

public class resultController implements Initializable {
    @FXML
    private TableView<modelTable> rtable;


    @FXML
    private TableColumn<modelTable,String> c_name,votes;
    @FXML
    public Label dr,ds,drVote,dsVote,Tvote,Nvote;
    @FXML
    public Button sendResult;
    @FXML
    public AnchorPane anchorPane;

    public Connection connection;
    private PreparedStatement pst;
    public  String drName,dsName,voteDr,voteDs;public static String tvts;

    public int tie=0,tie1=0;
    ObservableList<modelTable> oblist = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        DBHandler handler = new DBHandler();
        connection = handler.getConnection();

        sendResult.setOnAction(event -> {
            try {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Successfully sent messages to all voters ");
                alert.showAndWait();

                String update="UPDATE admin SET START=0";
                pst=connection.prepareStatement(update);
                pst.executeUpdate();

                String update1="UPDATE ADMIN SET STOP=0";
                pst=connection.prepareStatement(update1);
                pst.executeUpdate();
                String update2="UPDATE voter SET voted=NULL";
                pst=connection.prepareStatement(update2);
                pst.executeUpdate();
                String update3="UPDATE candidate SET votes=0";
                pst=connection.prepareStatement(update3);
                pst.executeUpdate();
                go();
            } catch(IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });


        try {
            String dis="SELECT DISTINCT(votes) from candidate WHERE c_pos='DR'";
            pst=connection.prepareStatement(dis);
            ResultSet D1=pst.executeQuery();
            while(D1.next()) {
                tie = tie + 1;
                voteDr = D1.getString("votes");

            }



            if(tie==2) {
                String max = "SELECT c_name,votes from candidate  WHERE c_pos='DR' AND votes=(SELECT max(votes) from candidate where c_pos='DR')";
                pst = connection.prepareStatement(max);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    drName = rs.getString("c_name");
                    voteDr = rs.getString("votes");

                }
                dr.setText(drName);
                drVote.setText(voteDr);
            } else {
               drVote.setText("<--TIE-->");

            }


            String dis1="SELECT DISTINCT(votes) from candidate WHERE c_pos='DS'";
            pst=connection.prepareStatement(dis1);
            ResultSet D2=pst.executeQuery();
            while (D2.next()) {
                tie1=tie1+1;
                voteDs = D2.getString("votes");
            }
            if(tie1==2) {
                String max1 = "SELECT c_name,votes from candidate WHERE c_pos='DS' AND votes=(SELECT max(votes) from candidate where c_pos='DS')";
                pst = connection.prepareStatement(max1);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    dsName = rs.getString("c_name");
                    voteDs = rs.getString("votes");                }
                    ds.setText(dsName);
                    dsVote.setText(voteDs);


            } else {
                    dsVote.setText("<--TIE-->");
                }
            ResultSet rs1 = connection.createStatement().executeQuery("SELECT count(*) AS Total FROM  voter where voted='YES'");

            while (rs1.next()) {
                tvts = rs1.getString("Total");

            }
            Tvote.setText(tvts);
            System.out.print(tvts);
            //Tvote.setText(rs1.getString("voted"));


        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM candidate");

            while (rs.next()) {
                oblist.add(new modelTable(rs.getString("c_name"), rs.getInt("votes")));
            }

            c_name.setCellValueFactory(new PropertyValueFactory<>("CandidateName"));

            votes.setCellValueFactory(new PropertyValueFactory<>("Votes"));

            rtable.setItems(oblist);




        } catch (SQLException e) {
            e.printStackTrace();
        }






        }

    private void go() throws IOException{
        AnchorPane pane= FXMLLoader.load(getClass().getResource("../View/SignInPage.fxml"));
        anchorPane.getChildren().setAll(pane);
    }



}
