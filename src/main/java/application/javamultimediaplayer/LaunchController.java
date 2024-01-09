package application.javamultimediaplayer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LaunchController extends Controller implements Initializable {

    @FXML
    private ListView<String> fileListView;
    @FXML
    private Button confirmButton;

    private List<File> chosenFiles;

    public void selectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3 files", "*.mp3"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            fileListView.getItems().add(selectedFile.getAbsolutePath());
            chosenFiles.add(selectedFile);
            confirmButton.setDisable(false);
        }
    }

    public void selectFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP3 files", "*.mp3"));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        if (selectedFiles != null) {
            for (File selectedFile : selectedFiles) {
                fileListView.getItems().add(selectedFile.getAbsolutePath());
            }
            chosenFiles.addAll(selectedFiles);
            confirmButton.setDisable(false);
        }
    }

    public void confirm(ActionEvent event) throws IOException {
        multimediaController = new MultimediaController(chosenFiles);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("musicScene.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        multimediaController.setController(fxmlLoader.getController());
        multimediaController.setEventHandler();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chosenFiles = new ArrayList<>();
    }
}
