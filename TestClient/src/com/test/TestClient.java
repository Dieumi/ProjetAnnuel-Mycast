package com.test;

import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.mrl.RtspMrl;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;

/**
 * Created by thomasfouan on 09/03/2016.
 */
public class TestClient {

    private static MediaPlayerFactory mFactory;

    private static HeadlessMediaPlayer mPlayer;

    public static void main(final String[] args) {
        new NativeDiscovery().discover();
        mFactory = new MediaPlayerFactory();
        mPlayer = mFactory.newHeadlessMediaPlayer();

        String mrl = new RtspMrl().host("127.0.0.1").port(12345).path("/demo").value();

        //String mrl = "rtsp://@" + "127.0.0.1" + ":" + "12345";
        String file = "/Users/thomasfouan/Music/Daft Punk/Discovery/01 - One More Time.mp3";

        mPlayer.playMedia(mrl);
    }
}
