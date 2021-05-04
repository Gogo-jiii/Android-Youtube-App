package com.example.youtubeapp;

import android.os.Handler;
import android.util.Log;

import com.google.android.youtube.player.YouTubePlayer;

import java.util.concurrent.TimeUnit;

class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {

    private YouTubePlayer youTubePlayer;
    private int duration;

    void setYoutubePlayerInstance(YouTubePlayer youTubePlayer) {
        this.youTubePlayer = youTubePlayer;
    }

    @Override
    public void onLoading() {
        // Called when the player is loading a video
        // At this point, it's not ready to accept commands affecting playback such as play() or
        // pause()
    }

    @Override
    public void onLoaded(String s) {
        // Called when a video is done loading.
        // Playback methods such as play(), pause() or seekToMillis(int) may be called after this
        // callback.
    }

    @Override
    public void onAdStarted() {
        // Called when playback of an advertisement starts.
    }

    @Override
    public void onVideoStarted() {
        Log.d("TAG", "started");
        // Called when playback of the video starts.

        delayPlaylistAutoPlay();
    }

    private void delayPlaylistAutoPlay() {
        duration = youTubePlayer.getDurationMillis();

        long cutoffTime =
                TimeUnit.MILLISECONDS.toSeconds(duration - 20000);

        final Handler handler = new Handler();
        final int delay = 5000; // 1000 milliseconds == 1 second
        final boolean[] isVideoPaused = {false};
        final int[] counter = {0};

        handler.postDelayed(new Runnable() {
            public void run() {
                long currentTime =
                        TimeUnit.MILLISECONDS.toSeconds(youTubePlayer.getCurrentTimeMillis());

                if (currentTime > cutoffTime) {

                    if (isVideoPaused[0] == false) {
                        isVideoPaused[0] = true;
                    }

                    if (isVideoPaused[0] == true) {
                        counter[0] = counter[0] + 1;
                    }

                    if (youTubePlayer.isPlaying()) {
                        youTubePlayer.pause();
                    }

                    if (counter[0] == 6) {
                        counter[0] = 0;
                        youTubePlayer.next();
                    }
                }

                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    @Override
    public void onVideoEnded() {
        Log.d("TAG", "ended");
    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {
        // Called when an error occurs.
    }
}