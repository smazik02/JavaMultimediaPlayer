package application.javamultimediaplayer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;

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

    public void addFile() {}

    public void addFiles() {}

    public void playPauseMedia() {}

    public void resetMedia() {}

    public void previousMedia() {}

    public void nextMedia() {}

    public void setRepeat() {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
