package mnm.hdfontgen.fxml;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class Controller implements Initializable {

    private int clickCount = 0;
    public Button btn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Loaded main.fxml");
    }

    public void onClick() {
        btn.setText("Thank you for clicking %d times".formatted(++clickCount));
    }
}
