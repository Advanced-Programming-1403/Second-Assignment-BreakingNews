package AP.javafx;

import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class MainPage extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        CSSFX.start();
        FXMLLoader loader = new FXMLLoader(FXML_Loader.loadURL("/fxml/MainController.fxml"));

        primaryStage.setMaximized(true);
        loader.setControllerFactory(c -> new MainController(primaryStage));
        Parent root = loader.load();
        Scene scene = new Scene(root);


        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
//        Image img = new Image(String.valueOf(FXML_Loader.loadURL("/icons/icon.png")));
//        primaryStage.getIcons().add(img);
        primaryStage.setTitle("Advanced Programming");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}