/**
 * DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 * Version 2, December 2004
 *
 * Copyright (C) 2016 Max Roncace <me@caseif.net>
 *
 * Everyone is permitted to copy and distribute verbatim or modified copies of this
 * license document, and changing it is allowed as long as the name is changed. 
 *
 * DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE
 * TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND MODIFICATION
 *
 * 0. You just DO WHAT THE FUCK YOU WANT TO.
*/
package net.caseif.binks;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Binks {

    private static final int MIN_DELAY_SECONDS = 120; // inclusive
    private static final int MAX_DELAY_SECONDS = 300; // exclusive
    private static final Random RANDOM = new Random();

    private static final byte[] AUDIO_BYTES;

    static {
        try (InputStream is = Binks.class.getResourceAsStream("/binks.wav")) {
            AUDIO_BYTES = new byte[is.available()];
            for (int i = 0; i < AUDIO_BYTES.length; i++) {
                AUDIO_BYTES[i] = (byte) is.read();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                int delay = (RANDOM.nextInt(MAX_DELAY_SECONDS - MIN_DELAY_SECONDS) + MIN_DELAY_SECONDS) * 1000;
                try (
                        ByteArrayInputStream bais = new ByteArrayInputStream(AUDIO_BYTES);
                        AudioInputStream audioIn = AudioSystem.getAudioInputStream(bais);
                ) {
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioIn);

                    Thread.currentThread().sleep(delay);
                    clip.start();
                } catch (InterruptedException | IOException | LineUnavailableException
                        | UnsupportedAudioFileException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

}
