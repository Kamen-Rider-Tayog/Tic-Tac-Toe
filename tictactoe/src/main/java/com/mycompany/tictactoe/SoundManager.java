package com.mycompany.tictactoe;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
    private static Clip buttonSound;
    private static Clip clickSound;
    private static Clip winSound;
    private static Clip loseSound;
    private static Clip drawSound;
    private static Clip backgroundMusic;
    private static boolean isMusicPlaying = false;

    static {
        try {
            buttonSound = AudioSystem.getClip();
            clickSound = AudioSystem.getClip();
            winSound = AudioSystem.getClip();
            loseSound = AudioSystem.getClip();
            drawSound = AudioSystem.getClip();
            backgroundMusic = AudioSystem.getClip();

            AudioInputStream buttonStream = AudioSystem.getAudioInputStream(
                new File("src/main/resources/audio/game.wav"));
            AudioInputStream clickStream = AudioSystem.getAudioInputStream(
                new File("src/main/resources/audio/click.wav"));
            AudioInputStream winStream = AudioSystem.getAudioInputStream(
                new File("src/main/resources/audio/win.wav"));
            AudioInputStream loseStream = AudioSystem.getAudioInputStream(
                new File("src/main/resources/audio/lose.wav"));
            AudioInputStream drawStream = AudioSystem.getAudioInputStream(
                new File("src/main/resources/audio/draw.wav"));
            AudioInputStream musicStream = AudioSystem.getAudioInputStream(
                new File("src/main/resources/audio/music.wav"));

            buttonSound.open(buttonStream);
            clickSound.open(clickStream);
            winSound.open(winStream);
            loseSound.open(loseStream);
            drawSound.open(drawStream);
            backgroundMusic.open(musicStream);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void playButtonSound() {
        playSound(buttonSound);
    }

    public static void playClickSound() {
        playSound(clickSound);
    }

    public static void playWinSound() {
        stopAllSounds();
        playSound(winSound);
    }

    public static void playLoseSound() {
        stopAllSounds();
        playSound(loseSound);
    }

    public static void playDrawSound() {
        stopAllSounds();
        playSound(drawSound);
    }

    public static void playBackgroundMusic() {
        if (!isMusicPlaying && backgroundMusic != null) {
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundMusic.setFramePosition(0);
            backgroundMusic.start();
            isMusicPlaying = true;
        }
    }

    public static void stopBackgroundMusic() {
        if (isMusicPlaying && backgroundMusic != null) {
            backgroundMusic.stop();
            isMusicPlaying = false;
        }
    }

    public static void stopAllSounds() {
        stopBackgroundMusic();
        if (buttonSound != null) buttonSound.stop();
        if (clickSound != null) clickSound.stop();
        if (winSound != null) winSound.stop();
        if (loseSound != null) loseSound.stop();
        if (drawSound != null) drawSound.stop();
    }

    private static void playSound(Clip clip) {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }
}