package com.example.youtubeapp;

import android.os.Handler;
import android.util.Log;

import com.google.android.youtube.player.YouTubePlayer;

import java.util.concurrent.TimeUnit;

class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {

    private YouTubePlayer youTubePlayer;
    private int duration;
    private Handler handler;

    void setYoutubePlayerInstance(YouTubePlayer youTubePlayer) {
        this.youTubePlayer = youTubePlayer;
        handler = new Handler();
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
        Log.d("DEBUG_" + "duarion_", String.valueOf(duration));

        long cutoffTime = duration - 20000;
        Log.d("DEBUG_" + "cutoffTime", String.valueOf(cutoffTime));


        final int delay = 5000; // 1000 milliseconds == 1 second
        final boolean[] isVideoPaused = {false};
        final int[] counter = {0};

        handler.postDelayed(new Runnable() {
            private boolean killMe = false;

            private void killRunnable() {
                killMe = true;
            }

            public void run() {
                if (killMe) {
                    return;
                }

                long currentTime = youTubePlayer.getCurrentTimeMillis();
                Log.d("DEBUG_" + "currentTime", String.valueOf(currentTime));

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
                        killRunnable();
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