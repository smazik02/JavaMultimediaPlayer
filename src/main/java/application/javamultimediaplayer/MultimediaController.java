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

    MultimediaController (List<File> files) {
        this.mediaNumber = 0;
        this.mediaFiles = new ArrayList<>(files);
        this.media = new Media(files.get(mediaNumber).toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(this::nextMedia);
        this.repeating = Repeating.NO;
    }

    public void playPauseMedia() {
        if (mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING)) {
            mediaPlayer.play();
        } else {
            mediaPlayer.pause();
        }
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
        mediaPlayer.setOnEndOfMedia(this::nextMedia);
        mediaPlayer.play();
    }

    public void previousMedia() {
        if (repeating.equals(Repeating.ONE)) {
            mediaPlayer.pause();
            mediaPlayer.seek(new Duration(0));
            mediaPlayer.play();
            return;
        }

        if (mediaPlayer.getCurrentTime().toSeconds() > 5) {
            mediaPlayer.pause();
            mediaPlayer.seek(new Duration(0));
            mediaPlayer.play();
            return;
        }

        if (mediaNumber > 0) {
            mediaNumber--;
            mediaPlayer.stop();
            media = new Media(mediaFiles.get(mediaNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnEndOfMedia(this::nextMedia);
            mediaPlayer.play();
        } else {
            mediaNumber = mediaFiles.size() - 1;
            mediaPlayer.stop();
            media = new Media(mediaFiles.get(mediaNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnEndOfMedia(this::nextMedia);
            if (repeating.equals(Repeating.WHOLE)) {
                mediaPlayer.play();
            }
        }
    }

    public void nextMedia() {
        if (repeating.equals(Repeating.ONE)) {
            mediaPlayer.pause();
            mediaPlayer.seek(Duration.seconds(0));
            mediaPlayer.play();
            return;
        }
        if (mediaNumber < mediaFiles.size() - 1) {
            mediaNumber++;
            mediaPlayer.stop();
            media = new Media(mediaFiles.get(mediaNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnEndOfMedia(this::nextMedia);
            mediaPlayer.play();
        } else {
            mediaPlayer.stop();
            if (repeating.equals(Repeating.NO)) {
                return;
            }
            mediaNumber = 0;
            media = new Media(mediaFiles.get(mediaNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnEndOfMedia(this::nextMedia);
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

    public void setMedia(Media media) {
        this.media = media;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
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

}
