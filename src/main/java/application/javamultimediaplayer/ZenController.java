package application.javamultimediaplayer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ZenController extends Controller implements Initializable {

    public void exitZen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("musicScene.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        songProgress.prefWidthProperty().bind(controlPane.widthProperty());
        controlGrid.prefWidthProperty().bind(controlPane.widthProperty());

        volumeBar.valueProperty().addListener((observable, oldValue, newValue) -> {
            multimediaController.setVolume(volumeBar.getValue() * 0.01);
            volumeLabel.setText((int) volumeBar.getValue() + "%");
        });
    }

}
