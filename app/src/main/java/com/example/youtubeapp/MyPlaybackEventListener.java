package com.example.youtubeapp;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.youtube.player.YouTubePlayer;

class MyPlaybackEventListener implements YouTubePlayer.PlaybackEventListener {

    private EventListenerCallback callback;

    void setCallbackInstance(Context context) {
        this.callback = (EventListenerCallback) context;
    }

    @Override
    public void onPlaying() {
        // Called when playback starts, either due to user action or call to play().
        callback.callBack("Playing");
    }

    @Override
    public void onPaused() {
        // Called when playback is paused, either due to user action or call to pause().
        callback.callBack("Paused");
    }

    @Override
    public void onStopped() {
        // Called when playback stops for a reason other than being paused.
        callback.callBack("Stopped");
    }

    @Override
    public void onBuffering(boolean b) {
        // Called when buffering starts or ends.
    }

    @Override
    public void onSeekTo(int i) {
        // Called when a jump in playback position occurs, either
        // due to user scrubbing or call to seekRelativeMillis() or seekToMillis()
    }

    interface EventListenerCallback {
        void callBack(String message);
    }
}