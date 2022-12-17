package four_in_a_row.graphics.controller;

import four_in_a_row.Main;
import four_in_a_row.core.logic.Player;
import four_in_a_row.graphics.use_case.AudioSecondsCounterUseCase;
import four_in_a_row.graphics.use_case.ParentAndControllerRetrieverUseCase;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerItemController implements Initializable {

    @FXML
    public Button pauseBtn, playBtn, editBtn;
    @FXML
    public Label usernameLbl, timeLabel;
    @FXML
    public Slider audioSlider;
    @FXML
    public HBox viewLayout;

    private Media media;
    private MediaPlayer mediaPlayer;

    private double audioDuration = 0;

    private SimpleObjectProperty<Player> player = new SimpleObjectProperty<>();
    private AudioSecondsCounterUseCase useCase;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewLayout.getChildren().remove(pauseBtn);
    }

    public void onAudioTimeSliderValueChanged(ObservableValue<? extends Number> obs,
                                              Number old, Number newVal) {
        System.out.println(audioDuration * ((double) newVal) + "< " + audioDuration);
        mediaPlayer.seek(Duration.seconds(audioDuration * ((double) newVal)));
    }

    @FXML
    public void onEdit() {
        ParentAndControllerRetrieverUseCase<PlayerItemEditController> controllerRetrieverUseCase = new ParentAndControllerRetrieverUseCase<>();
        ParentAndControllerRetrieverUseCase<PlayerItemEditController>
                .ParentControllerPair parentControllerPair =
                controllerRetrieverUseCase.getParentControllerPair("player_item_edit.fxml");

        Stage stage = new Stage();
        Scene scene = new Scene(parentControllerPair.getParent());
        stage.setScene(scene);
        stage.show();
    }

    public void initializeOnPlayerSet(Player player) {
        this.player.set(player);
        usernameLbl.setText(player.getName());
        try {
            this.media = new Media(Main.class.getResource("./audios/" + player.getName() + ".mp3").toURI().toString());
            this.mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(() -> {
                this.audioDuration = media.getDuration().toSeconds();

                playBtn.setOnMouseClicked(mouseEvent -> {
                    mediaPlayer.play();
                    this.useCase.apply();
                });
                pauseBtn.setOnMouseClicked(mouseEvent -> mediaPlayer.pause());
                setAudioActions(media, mediaPlayer);

                audioSlider.valueProperty().addListener(this::onAudioTimeSliderValueChanged);
            });
        } catch (Exception e) {
            System.out.println("The player " + player.getName() + " does not have an audio file!");
            viewLayout.getChildren().remove(playBtn);
            viewLayout.getChildren().remove(timeLabel);
            viewLayout.getChildren().remove(audioSlider);
        }

    }

    private void setAudioActions(Media media, MediaPlayer mediaPlayer) {
        mediaPlayer.setOnPlaying(() -> {
            viewLayout.getChildren().remove(playBtn);
            viewLayout.getChildren().add(1, pauseBtn);
        });
        mediaPlayer.setOnPaused(() -> {
            viewLayout.getChildren().remove(pauseBtn);
            viewLayout.getChildren().add(1, playBtn);
        });
        this.useCase = new AudioSecondsCounterUseCase(media.getDuration(), mediaPlayer, currSecond -> {
            int minutes = currSecond / 60;
            int seconds = currSecond % 60;
            Platform.runLater(() -> timeLabel.setText((minutes < 10 ? "0" + minutes : minutes)+":"+(seconds < 10 ? "0" + seconds : seconds)));
        });
    }

}