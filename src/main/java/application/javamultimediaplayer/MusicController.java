package application.javamultimediaplayer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MusicController extends Controller implements Initializable {

    @FXML
    private ListView<String> fileListView;
    @FXML
    private Label songTitle;

    @Override
    public void addFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3 files", "*.mp3"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            multimediaController.addMediaFile(selectedFile);
            fileListView.getItems().add(selectedFile.getName());
        }
    }

    @Override
    public void addFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3 files", "*.mp3"));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        if (selectedFiles != null) {
            for (File selectedFile : selectedFiles) {
                fileListView.getItems().add(selectedFile.getName());
            }
            multimediaController.addMediaFiles(selectedFiles);
        }
    }

    @Override
    public void previousMedia() {
        super.previousMedia();
        songTitle.setText(multimediaController.getMediaName());
        Platform.runLater(() -> fileListView.getSelectionModel().select(multimediaController.getMediaNumber()));
    }

    @Override
    public void nextMedia() {
        super.nextMedia();
        songTitle.setText(multimediaController.getMediaName());
        Platform.runLater(() -> fileListView.getSelectionModel().select(multimediaController.getMediaNumber()));
    }

    public void zenMode(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("zenScene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        multimediaController.setController(loader.getController());
        multimediaController.setEventHandler();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
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
    }

}
