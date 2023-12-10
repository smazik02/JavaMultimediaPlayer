module application.javamultimediaplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens application.javamultimediaplayer to javafx.fxml;
    exports application.javamultimediaplayer;
}