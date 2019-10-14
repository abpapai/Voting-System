package testfx.Controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import testfx.DBConnection.DBHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class addCandidateController  implements Initializable {

    @FXML
    public AnchorPane anchor_pane;
    @FXML
    public TextField c_id,c_name,c_pos;
    @FXML
    public TextArea achievement;
    @FXML
    public Button submit,browse,back;
    @FXML
    public ImageView imageView;

    private Connection connection;
    private DBHandler handler;
    private PreparedStatement pst,pst1;
    private FileChooser fileChooser;
    private File file;
    private Stage stage;

    public static int i=0,drcount=0,dscount=0;
    public void initialize(URL location, ResourceBundle resources) {

            submit.setOnAction((event) ->
            {
                try {
                    if (i==1) {
                        insertValue();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Please insert photo!!!");
                        alert.showAndWait();
                    }
                } catch (IOException e) {
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

        browse.setOnAction((event) ->
        {
            fileChooser = new FileChooser();
            FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
            FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
            FileChooser.ExtensionFilter ext3 = new FileChooser.ExtensionFilter("JPEG files(*.jpeg)", "*.JPEG");

            fileChooser.getExtensionFilters().addAll(ext1, ext2, ext3);
            file = fileChooser.showOpenDialog(stage);
            String s=file.getName();
            BufferedImage bf;
            try {
                bf = ImageIO.read(file);
                Image image = SwingFXUtils.toFXImage(bf, null);
                imageView.setImage(image);

                i=1;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
    });
}


    private void insertValue() throws IOException {

        FileInputStream fin = new FileInputStream(file.getAbsolutePath());
        int len = (int) file.length();

        handler = new DBHandler();
        connection = handler.getConnection();

        String insert = "INSERT INTO candidate (c_id,c_name,c_pos,achievement,image)" + "VALUES(?,?,?,?,?)";
        String check = "select c_id from candidate";

        try {
            pst = connection.prepareStatement(insert);
            if (c_id.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText(" Candidate ID is empty");
                alert.showAndWait();
            } else {
                pst.setString(1, c_id.getText());
                try {
                    Integer count=0;
                    pst1=connection.prepareStatement(check);
                    ResultSet rs=pst1.executeQuery();
                    String ch1=c_id.getText();
                    while(rs.next()) {
                        if(ch1.equals(rs.getString("c_id")))
                        {
                            count=count+1;
                        }
                    }
                    if(count==0) {
                        pst.setString(1, c_id.getText());
                    } else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Same Candidate ID exists");
                        alert.showAndWait();
                    }
                } catch(SQLException e) {
                    System.out.print(e);
                }

                if (c_name.getText().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Candidate - Name is empty");
                    alert.showAndWait();
                } else {
                    pst.setString(2, c_name.getText());

                    if (c_pos.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Candidate position field is empty");
                        alert.showAndWait();


                    } else if(!c_pos.getText().equals("DR")&& !c_pos.getText().equals("DS")) {
                        System.out.println(!c_pos.getText().equals("DR") || !c_pos.getText().equals("DS"));
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Candidate position can either be DR or DS(please make sure Caps Lock is On)");
                        alert.showAndWait();
                    } else {
                        pst.setString(3, c_pos.getText());

                        if (achievement.getText().isEmpty()) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setContentText("Achievement field is empty");
                            alert.showAndWait();
                        } else {
                            pst.setString(4, achievement.getText());
                            pst.setBinaryStream(5  , fin, len);

                            if (c_pos.getText().equals("DR"))
                            {
                                ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM candidate where c_pos='DR'");
                                while (rs.next())
                                {
                                    drcount++;
                                }

                                if (drcount>=2){
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText(" Already Two candidate exist Maximum number of Department Representative can be 2!!");
                                    alert.showAndWait();
                                } else{
                                    pst.executeUpdate();

                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText(" Successfully Added Candidate");
                                    alert.showAndWait();

                                    AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Dashboard.fxml"));
                                    anchor_pane.getChildren().setAll(pane);
                                }
                            }

                            if (c_pos.getText().equals("DS"))
                            {
                                ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM candidate where c_pos='DS'");

                                while (rs.next())
                                {
                                    dscount++;
                                }

                                if (dscount>=2){
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText("Already Two candidate exist Maximum number of Department Secretary can be 2!!");
                                    alert.showAndWait();
                                }
                                else{
                                    pst.executeUpdate();

                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setContentText(" Successfully Added Candidate");
                                    alert.showAndWait();
                                    AnchorPane pane = FXMLLoader.load(getClass().getResource("../View/Dashboard.fxml"));
                                    anchor_pane.getChildren().setAll(pane);
                                }
                            }
                        }
                    }
                }
            }

            connection.close();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Check all Fields properly");
            alert.showAndWait();
            System.out.print(e);
        }

        }

    }


