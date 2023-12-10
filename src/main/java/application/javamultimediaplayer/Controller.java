package application.javamultimediaplayer;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    static List<File> mediaFiles = new ArrayList<>();
    static Media media;
    static MediaPlayer mediaPlayer;
    static int mediaNumber = 0;
    static Timer timer;
    static TimerTask timerTask;
    static boolean running;
    static boolean repeating;

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