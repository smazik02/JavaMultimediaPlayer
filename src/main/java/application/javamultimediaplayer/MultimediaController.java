package application.javamultimediaplayer;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MultimediaController {

    private List<File> mediaFiles;
    private Media media;
    private MediaPlayer mediaPlayer;
    private int mediaNumber;
    private Repeating repeating;
    private Controller controller;

    MultimediaController (List<File> files) {
        this.mediaNumber = 0;
        this.mediaFiles = new ArrayList<>(files);
        this.media = new Media(files.get(mediaNumber).toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);
        this.repeating = Repeating.NO;
    }

    public void playMedia() {
        mediaPlayer.play();
    }

    public void pauseMedia() {
        mediaPlayer.pause();
    }

    public void resetMedia() {
        mediaPlayer.pause();
        mediaPlayer.seek(Duration.seconds(0));
    }

    public void seekMedia(double duration) {
        mediaPlayer.seek(new Duration(duration * media.getDuration().toMillis()));
    }

    public void selectMedia(int toMediaNumber) {
        this.mediaNumber = toMediaNumber;
        mediaPlayer.stop();
        media = new Media(mediaFiles.get(mediaNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        setEventHandler();
        mediaPlayer.play();
    }

    public void previousMedia() {
        if (mediaNumber > 0) {
            mediaNumber--;
            mediaPlayer.stop();
            media = new Media(mediaFiles.get(mediaNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            setEventHandler();
            mediaPlayer.play();
        } else {
            mediaNumber = mediaFiles.size() - 1;
            mediaPlayer.stop();
            media = new Media(mediaFiles.get(mediaNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            setEventHandler();
            if (repeating.equals(Repeating.WHOLE)) {
                mediaPlayer.play();
            }
        }
    }

    public void nextMedia() {
        if (mediaNumber < mediaFiles.size() - 1) {
            mediaNumber++;
            mediaPlayer.stop();
            media = new Media(mediaFiles.get(mediaNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            setEventHandler();
            mediaPlayer.play();
        } else {
            mediaPlayer.stop();
            if (repeating.equals(Repeating.NO)) {
                return;
            }
            mediaNumber = 0;
            media = new Media(mediaFiles.get(mediaNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            setEventHandler();
            if (repeating.equals(Repeating.WHOLE)) {
                mediaPlayer.play();
            }
        }
    }

    public void setMute(boolean mute) {
        mediaPlayer.setMute(mute);
    }

    public void setVolume(double volume) {
        mediaPlayer.setVolume(volume);
    }

    public List<File> getMediaFiles() {
        return mediaFiles;
    }

    public String getMediaName() {
        return mediaFiles.get(mediaNumber).getName();
    }

    public void setMediaFiles(List<File> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }

    public void addMediaFile(File file) {
        this.mediaFiles.add(file);
    }

    public void addMediaFiles(List<File> files) {
        this.mediaFiles.addAll(files);
    }

    public Media getMedia() {
        return media;
    }

    public Duration getTotalDuration() {
        return media.getDuration();
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public MediaPlayer.Status getStatus() {
        return mediaPlayer.getStatus();
    }

    public Duration getCurrentDuration() {
        return mediaPlayer.getCurrentTime();
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public int getMediaNumber() {
        return mediaNumber;
    }

    public void setMediaNumber(int mediaNumber) {
        this.mediaNumber = mediaNumber;
    }

    public Repeating getRepeating() {
        return repeating;
    }

    public void setRepeating(Repeating repeating) {
        this.repeating = repeating;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setEventHandler() {
        mediaPlayer.setOnEndOfMedia(controller::nextMedia);
    }

}
