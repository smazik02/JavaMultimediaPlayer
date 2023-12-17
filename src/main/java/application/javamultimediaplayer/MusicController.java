package application.javamultimediaplayer;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MusicController extends Controller implements Initializable {

    @FXML
    private Label songTitle;

    public void previousMedia() {
        if (multimediaController.getRepeating().equals(Repeating.ONE)) {
            multimediaController.previousMedia();
            return;
        }

        if (multimediaController.getMediaPlayer().getCurrentTime().toSeconds() > 5) {
            multimediaController.previousMedia();
            return;
        }

        if (multimediaController.getMediaNumber() > 0) {
            multimediaController.previousMedia();
            songTitle.setText(multimediaController.getMediaFiles().get(multimediaController.getMediaNumber()).getName());
            Platform.runLater(() -> fileListView.getSelectionModel().select(multimediaController.getMediaNumber()));
        } else {
            multimediaController.previousMedia();
            cancelTimer();
            if (multimediaController.getRepeating().equals(Repeating.WHOLE)) {
                beginTimer();
            }
            playpauseButton.setText("⏵");
            songTitle.setText(multimediaController.getMediaFiles().get(multimediaController.getMediaNumber()).getName());
            Platform.runLater(() -> fileListView.getSelectionModel().select(multimediaController.getMediaNumber()));
        }
    }

    public void nextMedia() {
        if (multimediaController.getRepeating().equals(Repeating.ONE)) {
            songProgress.setValue(0);
            multimediaController.nextMedia();
            return;
        }
        if (multimediaController.getMediaNumber() < multimediaController.getMediaFiles().size() - 1) {
            multimediaController.nextMedia();
            songTitle.setText(multimediaController.getMediaFiles().get(multimediaController.getMediaNumber()).getName());
            Platform.runLater(() -> fileListView.getSelectionModel().select(multimediaController.getMediaNumber()));
        } else {
            multimediaController.nextMedia();
            cancelTimer();
            if (multimediaController.getRepeating().equals(Repeating.NO))
                return;
            beginTimer();
            playpauseButton.setText("⏵");
            songTitle.setText(multimediaController.getMediaFiles().get(multimediaController.getMediaNumber()).getName());
            Platform.runLater(() -> fileListView.getSelectionModel().select(multimediaController.getMediaNumber()));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        songProgress.prefWidthProperty().bind(controlPane.widthProperty());
        controlGrid.prefWidthProperty().bind(controlPane.widthProperty());

        songTitle.setText(multimediaController.getMediaFiles().get(multimediaController.getMediaNumber()).getName());

        for (File mediaFile : multimediaController.getMediaFiles()) {
            fileListView.getItems().add(mediaFile.getName());
        }

        Platform.runLater(() -> fileListView.getSelectionModel().select(multimediaController.getMediaNumber()));

        fileListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            multimediaController.selectMedia(fileListView.getSelectionModel().getSelectedIndex());
            songTitle.setText(multimediaController.getMediaFiles().get(multimediaController.getMediaNumber()).getName());
            playMedia();
        });

        volumeBar.valueProperty().addListener((observable, oldValue, newValue) -> {
            multimediaController.setVolume(volumeBar.getValue() * 0.01);
            volumeLabel.setText((int) volumeBar.getValue() + "%");
        });
    }
}
