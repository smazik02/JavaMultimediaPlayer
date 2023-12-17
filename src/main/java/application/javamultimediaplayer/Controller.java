package application.javamultimediaplayer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    @FXML
    public Pane controlPane;
    @FXML
    public GridPane controlGrid;
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

    static MultimediaController multimediaController;
    static Timer timer;
    static TimerTask timerTask;

    public void addFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3 files", "*.mp3"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP4 files", "*.mp4"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            multimediaController.addMediaFile(selectedFile);
            fileListView.getItems().add(selectedFile.getName());
        }
    }

    public void addFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3 files", "*.mp3"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP4 files", "*.mp4"));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        if (selectedFiles != null) {
            for (File selectedFile : selectedFiles) {
                fileListView.getItems().add(selectedFile.getName());
            }
            multimediaController.addMediaFiles(selectedFiles);
        }
    }

    public void playPauseMedia() {
        if (multimediaController.getMediaPlayer().getStatus().equals(MediaPlayer.Status.PLAYING)) {
            pauseMedia();
        } else {
            playMedia();
        }
    }

    public void playMedia() {
        multimediaController.playMedia();
        beginTimer();
        playpauseButton.setText("⏸");
    }

    public void pauseMedia() {
        multimediaController.pauseMedia();
        cancelTimer();
        playpauseButton.setText("⏵");
    }

    public void resetMedia() {
        multimediaController.resetMedia();
        cancelTimer();
        songProgress.setValue(0);
        playpauseButton.setText("⏵");
    }

    public void seekMedia() {
        multimediaController.seekMedia(songProgress.getValue() / 100);
    }

    public void muteMedia() {
        if (muteButton.isSelected()) {
            volumeBar.setDisable(true);
            multimediaController.setMute(true);
            volumeLabel.setText("0%");
        } else {
            volumeBar.setDisable(false);
            multimediaController.setMute(false);
            volumeLabel.setText((int) volumeBar.getValue() + "%");
        }
    }

    public void setRepeat() {
        switch (multimediaController.getRepeating()) {
            case NO -> multimediaController.setRepeating(Repeating.WHOLE);
            case WHOLE -> {
                multimediaController.setRepeating(Repeating.ONE);
                repeatButton.setSelected(true);
                repeatButton.setText("\uD83D\uDD02");
            }
            case ONE -> {
                multimediaController.setRepeating(Repeating.NO);
                repeatButton.setText("\uD83D\uDD01");
            }
        }
    }

    public void beginTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Duration current = multimediaController.getMediaPlayer().getCurrentTime();
                Duration end = multimediaController.getMedia().getDuration();
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