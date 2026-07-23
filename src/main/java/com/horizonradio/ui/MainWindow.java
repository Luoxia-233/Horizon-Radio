package com.horizonradio.ui;

import com.horizonradio.core.PlayerState;
import com.horizonradio.core.Song;
import com.horizonradio.player.PlayerController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class MainWindow {

    private final PlayerController controller;
    private final ListView<Song> songListView;
    private final Label statusLabel;
    private final Button playPauseButton;
    private final ToggleButton shuffleButton;
    private final ObservableList<Song> songs;
    private final BorderPane root;

    public MainWindow(PlayerController controller) {
        this.controller = controller;
        this.songs = FXCollections.observableArrayList();

        songListView = new ListView<>(songs);
        songListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Song song, boolean empty) {
                super.updateItem(song, empty);
                if (empty || song == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(song.title() + " - " + song.artist());
                    Song current = controller.getCurrentSong();
                    if (current != null && current.equals(song)) {
                        setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        Button selectDirButton = new Button("选择音乐目录");
        selectDirButton.setOnAction(e -> selectDirectory());

        playPauseButton = new Button("播放");
        playPauseButton.setOnAction(e -> togglePlayPause());

        Button prevButton = new Button("上一首");
        prevButton.setOnAction(e -> controller.previous());

        Button nextButton = new Button("下一首");
        nextButton.setOnAction(e -> controller.next());

        shuffleButton = new ToggleButton("随机播放");
        shuffleButton.setOnAction(e -> controller.toggleShuffle());

        statusLabel = new Label("IDLE");

        HBox controls = new HBox(8, prevButton, playPauseButton, nextButton, shuffleButton);
        VBox bottom = new VBox(8, controls, statusLabel);

        root = new BorderPane();
        root.setTop(selectDirButton);
        root.setCenter(songListView);
        root.setBottom(bottom);

        refresh();
    }

    public BorderPane getRoot() {
        return root;
    }

    public void refresh() {
        songs.setAll(controller.getSongs());
        PlayerState state = controller.getState();

        boolean isPlaying = state == PlayerState.PLAYING || state == PlayerState.VOICE_PLAYING;
        playPauseButton.setText(isPlaying ? "暂停" : "播放");

        shuffleButton.setSelected(controller.isShuffleEnabled());
        statusLabel.setText(state.name());
        songListView.refresh();
    }

    private void togglePlayPause() {
        PlayerState state = controller.getState();
        if (state == PlayerState.PLAYING || state == PlayerState.VOICE_PLAYING) {
            controller.pause();
        } else {
            controller.play();
        }
        refresh();
    }

    private void selectDirectory() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("选择音乐目录");
        File dir = chooser.showDialog(null);
        if (dir != null) {
            refresh();
        }
    }
}
