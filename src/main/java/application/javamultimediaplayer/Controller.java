package application.javamultimediaplayer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    static List<File> selectedFiles = new ArrayList<>();

    public void viewAbout() throws IOException {
        FXMLLoader aboutLoader = new FXMLLoader(Main.class.getResource("aboutScene.fxml"));
        Scene scene = new Scene(aboutLoader.load());
        Stage stage = new Stage();
        stage.setTitle("About");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void closeApp() {
        System.out.println("Exit");
        Platform.exit();
        System.exit(0);
    }

}