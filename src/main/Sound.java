package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL [30];
//    UtilityTool ut = new UtilityTool();

    public Sound() {

        soundURL[0] = getClass().getResource("/sound/acquire_book.wav");
        soundURL[1] = getClass().getResource("/sound/key_wav.wav");
        soundURL[2] = getClass().getResource("/sound/sad_piano_wav.wav");
        soundURL[3] = getClass().getResource("/sound/opendoor_wav.wav");
        soundURL[4] = getClass().getResource("/sound/door_locked_wav.wav");

        for (int i = 0; i < 3; i++) {
            System.out.println("Sound file " + i + " path: " + soundURL[i]);
        }
    }

    public void setFile(int i) {
        try {
            if (soundURL[i] == null) {
                throw new RuntimeException("Error: Sound file not found at index " + i);
            }
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void play() {

        clip.start();

    }

    public void loop() {

        clip.loop(Clip.LOOP_CONTINUOUSLY);

    }

    public void stop() {

        clip.stop();

    }

}
