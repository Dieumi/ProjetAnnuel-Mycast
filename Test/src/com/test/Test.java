package com.test;

import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import javax.swing.*;
import java.util.Scanner;

/**
 * Created by thomasfouan on 26/11/2015.
 */
public class Test {

    private static MediaPlayerFactory mediaPlayerFactory;
    private static HeadlessMediaPlayer mediaPlayer;

    public static void main(final String[] args) {

        Thread admin = new Thread() {

            @Override
            public void run() {
                Scanner sc = new Scanner(System.in);
                String entry;
                while(true) {
                    entry = sc.nextLine();

                    if(entry.equals("pause")) {
                        mediaPlayer.pause();
                        System.out.println("Pause !");
                    } else if(entry.equals("play")) {
                        mediaPlayer.play();
                        System.out.println("Play !");
                    } else {
                        System.out.println("Unknown command ^^");
                    }
                }
            }
        };

        new NativeDiscovery().discover();
        /*SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Player().start(mrl);
            }
        });*/

        try {
            admin.start();
            streamRTSP(args);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void streamRTP(String[] args) throws InterruptedException {

        String media = "/Users/thomasfouan/Music/Daft Punk/Discovery/01 - One More Time.mp3";
        String options = formatRtpStream("127.0.0.1", 12345);

        System.out.println("Streaming '" + media + "' to '" + options + "'");

        this.mediaPlayerFactory = new MediaPlayerFactory(args);
        this.mediaPlayer = this.mediaPlayerFactory.newHeadlessMediaPlayer();

        this.mediaPlayer.playMedia(media,
                options,
                ":no-sout-rtp-sap",
                ":no-sout-standard-sap",
                ":sout-all",
                ":sout-keep"
        );

        // Don't exit
        Thread.currentThread().join();
    }

    public static void streamRTSP(String[] args) throws InterruptedException {

        String media = "/Users/thomasfouan/Music/Daft Punk/Discovery/01 - One More Time.mp3";
        String options = formatRtspStream("127.0.0.1", 12345, "demo");

        System.out.println("Streaming '" + media + "' to '" + options + "'");

        mediaPlayerFactory = new MediaPlayerFactory(args);
        mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();
        mediaPlayer.playMedia(media,
                options,
                ":no-sout-rtp-sap",
                ":no-sout-standard-sap",
                ":sout-all",
                ":sout-keep"
        );

        // Don't exit
        Thread.currentThread().join();
    }

    private static String formatRtspStream(String serverAddress, int serverPort, String id) {
        StringBuilder sb = new StringBuilder(60);
        sb.append(":sout=#rtp{sdp=rtsp://@");
        sb.append(serverAddress);
        sb.append(':');
        sb.append(serverPort);
        sb.append('/');
        sb.append(id);
        sb.append("}");
        return sb.toString();
    }

    private static String formatRtpStream(String serverAddress, int serverPort) {
        StringBuilder sb = new StringBuilder(60);
        sb.append(":sout=#rtp{dst=");
        sb.append(serverAddress);
        sb.append(",port=");
        sb.append(serverPort);
        sb.append(",mux=ts}");
        return sb.toString();
    }

    /*
    private final JFrame frame;

    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

    public static void main(final String[] args) {
        new NativeDiscovery().discover();
        SwingUtilities.invokeLater(new Runnable() {
            //@Override
            public void run() {
                new Test(args);
            }
        });
    }

    public Test(String[] args) {
        frame = new JFrame("My First Media Player");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        frame.setContentPane(mediaPlayerComponent);
        frame.setVisible(true);
        mediaPlayerComponent.getMediaPlayer().playMedia("/Users/thomasfouan/Movies/GOPR0052.MP4");
    }*/

    /*
    public static void main(final String[] args) throws InterruptedException {
        new NativeDiscovery().discover();
        AudioMediaPlayerComponent audioPlayer = new AudioMediaPlayerComponent() {
            @Override
            public void playing(MediaPlayer mediaPlayer) {
                System.out.println("test");
            }

            @Override
            public void finished(MediaPlayer mediaPlayer) {
                System.exit(0);
            }

            @Override
            public void error(MediaPlayer mediaPlayer) {
                System.out.println("Failed to play media");
                System.exit(1);
            }
        };

        audioPlayer.getMediaPlayer().playMedia("/Users/thomasfouan/Music/Daft Punk/Discovery/01 - One More Time.mp3");

        Thread.currentThread().join();
    }*/
}
