module application.javamultimediaplayer {
    requires javafx.controls;
    requires javafx.fxml;


    opens application.javamultimediaplayer to javafx.fxml;
    exports application.javamultimediaplayer;
}