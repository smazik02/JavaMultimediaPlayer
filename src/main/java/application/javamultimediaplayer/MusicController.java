package application.javamultimediaplayer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MusicController extends Controller implements Initializable {

    @FXML
    private Button playpauseButton, resetButton, previousButton, nextButton, repeatButton;
    @FXML
    private Slider volumeBar, songProgress;
    @FXML
    private ListView<String> fileListView;
    @FXML
    private Label songTitle;

    public void addFile() {
    }

    public void addFiles() {
    }

    public void playPauseMedia() {
        if (running) {
            mediaPlayer.pause();
            running = false;
        } else {
            mediaPlayer.play();
            running = true;
        }
    }

    public void resetMedia() {
        mediaPlayer.seek(Duration.seconds(0));
    }

    public void previousMedia() {
        if (mediaNumber > 0) {
            --mediaNumber;
            mediaPlayer.stop();
            media = new Media(mediaFiles.get(mediaNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songTitle.setText(mediaFiles.get(mediaNumber).getName());
            mediaPlayer.play();
        } else {
            mediaNumber = mediaFiles.size() - 1;
            mediaPlayer.stop();
            running = false;
            media = new Media(mediaFiles.get(mediaNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songTitle.setText(mediaFiles.get(mediaNumber).getName());
        }
        mediaPlayer.setVolume(volumeBar.getValue() * 0.01);
    }

    public void nextMedia() {
        if (mediaNumber < mediaFiles.size() - 1) {
            ++mediaNumber;
            mediaPlayer.stop();
            media = new Media(mediaFiles.get(mediaNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songTitle.setText(mediaFiles.get(mediaNumber).getName());
            mediaPlayer.play();
        } else {
            mediaNumber = 0;
            mediaPlayer.stop();
            running = false;
            media = new Media(mediaFiles.get(mediaNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songTitle.setText(mediaFiles.get(mediaNumber).getName());
        }
        mediaPlayer.setVolume(volumeBar.getValue() * 0.01);
    }

    public void setRepeat() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        media = new Media(mediaFiles.get(mediaNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        songTitle.setText(mediaFiles.get(mediaNumber).getName());

        for (File mediaFile : mediaFiles) {
            fileListView.getItems().add(mediaFile.getName());
        }

        volumeBar.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mediaPlayer.setVolume(volumeBar.getValue() * 0.01);
            }
        });
    }
}
