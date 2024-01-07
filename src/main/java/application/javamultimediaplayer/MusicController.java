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

    @Override
    public void previousMedia() {
        if (multimediaController.getRepeating().equals(Repeating.ONE)) {
            this.resetMedia();
            this.playMedia();
            return;
        }

        if (multimediaController.getCurrentDuration().toSeconds() > 5) {
            this.resetMedia();
            this.playMedia();
            return;
        }

        if (multimediaController.getMediaNumber() > 0) {
            multimediaController.previousMedia();
        } else {
            multimediaController.previousMedia();
            cancelTimer();
            if (multimediaController.getRepeating().equals(Repeating.WHOLE)) {
                beginTimer();
            }
            playpauseButton.setText("⏵");
        }
        songTitle.setText(multimediaController.getMediaName());
        Platform.runLater(() -> fileListView.getSelectionModel().select(multimediaController.getMediaNumber()));
    }

    @Override
    public void nextMedia() {
        if (multimediaController.getRepeating().equals(Repeating.ONE)) {
            this.resetMedia();
            return;
        }
        if (multimediaController.getMediaNumber() < multimediaController.getMediaFiles().size() - 1) {
            multimediaController.nextMedia();
        } else {
            multimediaController.nextMedia();
            cancelTimer();
            if (multimediaController.getRepeating().equals(Repeating.NO))
                return;
            beginTimer();
            playpauseButton.setText("⏵");
        }
        songTitle.setText(multimediaController.getMediaName());
        Platform.runLater(() -> fileListView.getSelectionModel().select(multimediaController.getMediaNumber()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        songProgress.prefWidthProperty().bind(controlPane.widthProperty());
        controlGrid.prefWidthProperty().bind(controlPane.widthProperty());

//        multimediaController.setEventHandler();

        songTitle.setText(multimediaController.getMediaName());

        for (File mediaFile : multimediaController.getMediaFiles()) {
            fileListView.getItems().add(mediaFile.getName());
        }

        Platform.runLater(() -> {
            fileListView.getSelectionModel().select(multimediaController.getMediaNumber());
            fileListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
                multimediaController.selectMedia(fileListView.getSelectionModel().getSelectedIndex());
                songTitle.setText(multimediaController.getMediaName());
                playMedia();
            });
        });

        volumeBar.valueProperty().addListener((observable, oldValue, newValue) -> {
            multimediaController.setVolume(volumeBar.getValue() * 0.01);
            volumeLabel.setText((int) volumeBar.getValue() + "%");
        });

//        multimediaController.getMediaPlayer().setOnEndOfMedia(() -> {
//            multimediaController.nextMedia();
//            songProgress.setValue(0);
//            songTitle.setText(multimediaController.getMediaName());
//            Platform.runLater(() -> fileListView.getSelectionModel().select(multimediaController.getMediaNumber()));
//        });
    }
}
