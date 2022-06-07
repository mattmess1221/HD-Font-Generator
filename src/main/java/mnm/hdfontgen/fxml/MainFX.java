package mnm.hdfontgen.fxml;

import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        var fxml = MainFX.class.getResource("main.fxml");
        Parent root = FXMLLoader.load(Objects.requireNonNull(fxml));

        stage.setTitle("HD Font Generator");
        stage.setScene(new Scene(root));
        stage.setX(100);
        stage.setY(100);

        stage.show();
    }
}
