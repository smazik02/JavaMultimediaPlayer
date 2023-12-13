package application.javamultimediaplayer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

enum Repeating {NO, ONE, WHOLE}

public class Controller {

    @FXML
    public Pane controlPane;
    @FXML
    public Slider volumeBar, songProgress;
    @FXML
    public Button playpauseButton, resetButton, previousButton, nextButton;
    @FXML
    public ToggleButton repeatButton, muteButton;
    @FXML
    public ListView<String> fileListView;
    @FXML
    public Label progressLabel, volumeLabel;
    static List<File> mediaFiles = new ArrayList<>();
    static Media media;
    static MediaPlayer mediaPlayer;
    static int mediaNumber = 0;
    static Timer timer;
    static TimerTask timerTask;
    static Repeating repeating;

    public void addFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3 files", "*.mp3"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            mediaFiles.add(selectedFile);
            fileListView.getItems().add(mediaFiles.getFirst().getAbsolutePath());
        }
    }

    public void addFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3 files", "*.mp3"));
        List<File> chosenFiles = fileChooser.showOpenMultipleDialog(null);
        if (chosenFiles != null) {
            for (File chosenFile : chosenFiles) {
                fileListView.getItems().add(chosenFile.getAbsolutePath());
                mediaFiles.add(chosenFile);
            }
        }
    }

    public void playPauseMedia() {
        if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            pauseMedia();
        } else {
            playMedia();
        }
    }

    public void playMedia() {
        mediaPlayer.setVolume(volumeBar.getValue() * 0.01);
        mediaPlayer.play();
        playpauseButton.setText("⏸");
    }

    public void pauseMedia() {
        mediaPlayer.pause();
        playpauseButton.setText("⏵");
    }

    public void resetMedia() {
        pauseMedia();
        songProgress.setValue(0);
        mediaPlayer.seek(Duration.seconds(0));
    }

    public void seekMedia() {
        double duration = songProgress.getValue() / 100;
        mediaPlayer.seek(new Duration(duration * media.getDuration().toMillis()));
    }

    public void muteMedia() {
        if (muteButton.isSelected()) {
            volumeBar.setDisable(true);
            mediaPlayer.setMute(true);
            volumeLabel.setText("0%");
        } else {
            volumeBar.setDisable(false);
            mediaPlayer.setMute(false);
            volumeLabel.setText((int) volumeBar.getValue() + "%");
        }
    }

    public void setRepeat() {
        switch (repeating) {
            case NO -> repeating = Repeating.WHOLE;
            case WHOLE -> {
                repeating = Repeating.ONE;
                repeatButton.setSelected(true);
                repeatButton.setText("\uD83D\uDD02");
            }
            case ONE -> {
                repeating = Repeating.NO;
                repeatButton.setText("\uD83D\uDD01");
            }
        }
    }

    public void beginTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Duration current = mediaPlayer.getCurrentTime();
                Duration end = media.getDuration();
                songProgress.setValue(current.toSeconds() / end.toSeconds() * 100);
                Platform.runLater(() -> progressLabel.setText((int) current.toMinutes() + ":" + String.format("%02d", (int) current.toSeconds() % 60) + " / "
                        + (int) end.toMinutes() + ":" + String.format("%02d", (int) end.toSeconds() % 60)));
                if (current.toSeconds() / end.toSeconds() == 1) {
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    public void cancelTimer() {
        timer.cancel();
    }

    public void viewAbout() throws IOException {
        FXMLLoader aboutLoader = new FXMLLoader(App.class.getResource("aboutScene.fxml"));
        Scene scene = new Scene(aboutLoader.load());
        Stage stage = new Stage();
        stage.setTitle("About");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void closeApp() {
        Platform.exit();
        System.exit(0);
    }

}