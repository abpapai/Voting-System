package testfx.Model;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class main extends Application {

    public static void main(String args[])
    {
        launch(args);
    }
    @Override
        public void start(Stage stage) throws Exception
        {
            AnchorPane rootNode= FXMLLoader.load(getClass().getResource("../View/SignInPage.fxml"));
            stage.setTitle("e - VOTING SYSTEM");
            Image icon=new Image(getClass().getResourceAsStream("../Images/logo2.jpg"));
            stage.getIcons().add(icon);
            stage.setScene(new Scene(rootNode,700,600));
            stage.setResizable(false);
            stage.show();
        }
}
