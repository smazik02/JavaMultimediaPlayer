package application.javamultimediaplayer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MusicController extends Controller implements Initializable {

    @FXML
    private Label songTitle;

    public void previousMedia() {
        if (repeating.equals(Repeating.ONE)) {
            pauseMedia();
            resetMedia();
            playMedia();
            return;
        }

        if (mediaPlayer.getCurrentTime().toSeconds() > 5) {
            resetMedia();
            playMedia();
            return;
        }

        if (mediaNumber > 0) {
            --mediaNumber;
            mediaPlayer.stop();
            if (running)
                cancelTimer();
            media = new Media(mediaFiles.get(mediaNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songTitle.setText(mediaFiles.get(mediaNumber).getName());
            playMedia();
        } else {
            mediaNumber = mediaFiles.size() - 1;
            mediaPlayer.stop();
            if (running)
                cancelTimer();
            playpauseButton.setText("⏵");
            media = new Media(mediaFiles.get(mediaNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songTitle.setText(mediaFiles.get(mediaNumber).getName());
            if (repeating.equals(Repeating.WHOLE)) {
                playMedia();
            }
        }
    }

    public void nextMedia() {
        if (repeating.equals(Repeating.ONE)) {
            pauseMedia();
            resetMedia();
            playMedia();
            return;
        }
        if (mediaNumber < mediaFiles.size() - 1) {
            ++mediaNumber;
            mediaPlayer.stop();
            if (running)
                cancelTimer();
            media = new Media(mediaFiles.get(mediaNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songTitle.setText(mediaFiles.get(mediaNumber).getName());
            playMedia();
        } else {
            mediaNumber = 0;
            mediaPlayer.stop();
            if (running)
                cancelTimer();
            playpauseButton.setText("⏵");
            media = new Media(mediaFiles.get(mediaNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songTitle.setText(mediaFiles.get(mediaNumber).getName());
            if (repeating.equals(Repeating.WHOLE)) {
                playMedia();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        media = new Media(mediaFiles.get(mediaNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(this::nextMedia);
        songTitle.setText(mediaFiles.get(mediaNumber).getName());

        for (File mediaFile : mediaFiles) {
            fileListView.getItems().add(mediaFile.getName());
        }

        volumeBar.valueProperty().addListener((observable, oldValue, newValue) -> {
            mediaPlayer.setVolume(volumeBar.getValue() * 0.01);
            volumeLabel.setText((int) volumeBar.getValue() + "%");
        });

        repeating = Repeating.NO;
    }
}
