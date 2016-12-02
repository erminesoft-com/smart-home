package com.kozhurov.gate;

import javax.sound.sampled.*;
import java.io.*;
import java.util.TimerTask;

public final class SoundGate {

    public static final long RECORD_TIME = 6000;  // 1 minute

    // path of the wav file
    File wavFile = new File("/home/andrey/test.wav");

    // format of audio file
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

    // the line from which audio data is captured
    TargetDataLine line;

    public SoundGate() {
    }

    AudioFormat getAudioFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    public void stageOne() {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing

            System.out.println("Start capturing...");

            AudioInputStream ais = new AudioInputStream(line);

            System.out.println("Start recording...");

            while (true) {

                if (ais.getFrameLength() != -1) {
                    System.out.println("Frame size = " + ais.getFrameLength());
                    System.out.println("Frame size = " + ais.read());
                }
            }

        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void finish() {
        line.stop();
        line.close();
        System.out.println("Finished");
    }
}
