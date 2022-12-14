package four_in_a_row.graphics.use_case;

import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class AudioSecondsCounterUseCase {

    private AudioCounter counter;

    public AudioSecondsCounterUseCase(Duration duration, MediaPlayer mediaPlayer, Consumer<Integer> actionMadeDuringSpecificSecond) {
        this.counter = new AudioCounter(duration, mediaPlayer, actionMadeDuringSpecificSecond);
    }

    public void apply() {
        Thread thread = new Thread(counter);
        thread.start();
    }

    private static class AudioCounter implements Runnable {

        AtomicInteger lastSavedSecond = new AtomicInteger(0);
        Duration duration;
        Consumer<Integer> actionMadeDuringSpecificSecond;
        MediaPlayer mediaPlayer;

        public AudioCounter(Duration duration, MediaPlayer player, Consumer<Integer> actionMadeDuringSpecificSecond) {
            this.duration = duration;
            this.mediaPlayer = player;
            this.actionMadeDuringSpecificSecond = actionMadeDuringSpecificSecond;
        }

        @Override
        public void run() {
            System.out.println(mediaPlayer.getCurrentTime());
            while(mediaPlayer.getCurrentTime().lessThan(duration)) {
                int currentTime = (int) mediaPlayer.getCurrentTime().toSeconds();
                System.out.println(currentTime);
                if (currentTime > lastSavedSecond.get()) {
                    actionMadeDuringSpecificSecond.accept(currentTime);
                    lastSavedSecond.set(currentTime);
                }
            }
        }

    }

}