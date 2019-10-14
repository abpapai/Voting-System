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

public class votingPageController implements Initializable {



    @FXML
    private TableView<modelTable> table,table1;


    @FXML
    private TableColumn<modelTable,String> c_name,c_name1,achievement,achievement1;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public Button submit;
    @FXML
    public RadioButton dr1,dr2,ds1,ds2,ndr1,nds1;

    private Connection connection;
    private PreparedStatement pst;

    public String c1;
    public int x=0,y=0,nota1=1,nota2=1;
    public static String whichDr,whichDs;
    voterLoginController obj = new voterLoginController();
    String vid = obj.VID;


    ObservableList<modelTable> oblist = FXCollections.observableArrayList();
    ObservableList<modelTable> oblist1 = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        DBHandler handler = new DBHandler();
        connection = handler.getConnection();

        int i,j;

        String ds[]=new String[5];
        String dr[] = new String[5];
        String name[]=new String[20];

        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM candidate WHERE c_id LIKE 'DS%'");

            i=0;

            while (rs.next()) {
                ds[i]=rs.getString("c_id");
                i=i+1;

                oblist.add(new modelTable(rs.getString("c_name"), rs.getString("achievement")));
            }

            c_name.setCellValueFactory(new PropertyValueFactory<>("CandidateName"));

            achievement.setCellValueFactory(new PropertyValueFactory<>("Achievement"));

            table.setItems(oblist);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ResultSet rs1 = connection.createStatement().executeQuery("SELECT * FROM candidate WHERE c_id LIKE 'DR%'");

            j=0;

            while (rs1.next()) {
                dr[j]=rs1.getString("c_id");
                j=j+1;
                oblist1.add(new modelTable(rs1.getString("c_name"), rs1.getString("achievement")));
            }
            c_name1.setCellValueFactory(new PropertyValueFactory<>("CandidateName"));

            achievement1.setCellValueFactory(new PropertyValueFactory<>("Achievement"));

            table1.setItems(oblist1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ToggleGroup check1 = new ToggleGroup();
        dr1.setToggleGroup(check1);
        dr2.setToggleGroup(check1);
        ndr1.setToggleGroup(check1);

        ToggleGroup check = new ToggleGroup();

        ds1.setToggleGroup(check);
        ds2.setToggleGroup(check);
        nds1.setToggleGroup(check);
        dr1.setOnAction(event -> {
            whichDr=dr[0];
            y=1;
        });
        dr2.setOnAction(event -> {
            whichDr=dr[1];
            y=1;
        });
        ndr1.setOnAction(event ->
        {
          nota1=0;
          y=0;
        });

        ds1.setOnAction(event -> {
            whichDs=ds[0];
            x=1;
        });
        ds2.setOnAction(event -> {
            whichDs=ds[1];
            x=1;
        });
        nds1.setOnAction(event ->
        {
            nota2=0;
            x=0;
        });

            submit.setOnAction(event -> {

                if (x==1 && y==1)
                {
                    String update = "UPDATE candidate SET votes = votes+1 WHERE c_id ='"+whichDr+"'";
                    String update1 = "UPDATE candidate SET votes = votes+1 WHERE c_id ='"+whichDs+"'";


                    String update2 = "UPDATE voter SET voted = 'YES' WHERE v_id ='"+vid+"'";

                    try {
                        pst = connection.prepareStatement(update);
                        pst.executeUpdate();
                        pst = connection.prepareStatement(update1);
                        pst.executeUpdate();
                        pst = connection.prepareStatement(update2);
                        pst.executeUpdate();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("You have successfully voted");
                        alert.showAndWait();

                        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/VoterLogin.fxml"));
                        anchorPane.getChildren().setAll(pane);
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if(y==1 && nota2==0) {
                    String update = "UPDATE candidate SET votes = votes+1 WHERE c_id ='"+whichDr+"'";


                    String update2 = "UPDATE voter SET voted = 'YES' WHERE v_id ='"+vid+"'";

                    try {
                        pst = connection.prepareStatement(update);
                        pst.executeUpdate();
                        pst = connection.prepareStatement(update2);
                        pst.executeUpdate();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("You have successfully voted");
                        alert.showAndWait();

                        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/VoterLogin.fxml"));
                        anchorPane.getChildren().setAll(pane);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if(nota1==0 && x==1) {
                    String update1 = "UPDATE candidate SET votes = votes+1 WHERE c_id ='"+whichDs+"'";
                    String update2 = "UPDATE voter SET voted = 'YES' WHERE v_id ='"+vid+"'";

                    try {

                        pst = connection.prepareStatement(update1);
                        pst.executeUpdate();
                        pst = connection.prepareStatement(update2);
                        pst.executeUpdate();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("You have successfully voted");
                        alert.showAndWait();

                        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/VoterLogin.fxml"));
                        anchorPane.getChildren().setAll(pane);
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(nota1==0 && nota2==0) {
                    String update2 = "UPDATE voter SET voted = 'YES' WHERE v_id ='"+vid+"'";

                    try {
                        pst = connection.prepareStatement(update2);
                        pst.executeUpdate();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("You have successfully voted");
                        alert.showAndWait();

                        AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/VoterLogin.fxml"));
                        anchorPane.getChildren().setAll(pane);
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("@@@ Please vote for both section @@ ");
                    alert.showAndWait();
                }


            });




    }
}


