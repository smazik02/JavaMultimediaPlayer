package application.javamultimediaplayer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LaunchController extends Controller {

    @FXML
    private ListView<String> fileListView;
    @FXML
    private Button confirmButton;

    public void selectFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3 files", "*.mp3"));
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP4 files", "*.mp4"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            selectedFiles.add(selectedFile);
            fileListView.getItems().add(selectedFiles.getFirst().getAbsolutePath());
            confirmButton.setDisable(false);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("File doesn't exist");
            alert.show();
        }
    }

    public void selectFiles(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3 files", "*.mp3"));
//        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP4 files", "*.mp4"));
        List<File> chosenFiles = fileChooser.showOpenMultipleDialog(null);
        if (chosenFiles != null) {
            for (File chosenFile : chosenFiles) {
                fileListView.getItems().add(chosenFile.getAbsolutePath());
                selectedFiles.add(chosenFile);
            }
            confirmButton.setDisable(false);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("File doesn't exist");
            alert.show();
        }
    }

    public void confirm(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("musicScene.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

}
