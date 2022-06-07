module hdfontgen {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    opens mnm.hdfontgen.fxml to javafx.graphics, javafx.fxml;

    requires com.google.gson;
    opens mnm.hdfontgen.pack to com.google.gson;
    opens mnm.hdfontgen.pack.provider to com.google.gson;

    requires jdk.zipfs;
}