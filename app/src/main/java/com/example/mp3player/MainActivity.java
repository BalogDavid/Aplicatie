package com.example.mp3player;

import android.os.Bundle;
import android.media.MediaPlayer;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private MediaPlayer mediaPlayer;
    private Handler timerHandler = new Handler();
    private Runnable timerRunnable;
    private int seconds = 0;
    private boolean isRunning = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        /////////////////////
        Button playButton = findViewById(R.id.playButton);
        Button stopButton = findViewById(R.id.stopButton);

        mediaPlayer = MediaPlayer.create(this, R.raw.song);
        //pt mediaplayer////


        playButton.setOnClickListener(v -> {
            mediaPlayer.start();
            playButton.setEnabled(false);
            stopButton.setEnabled(true);
        });

        stopButton.setOnClickListener(v -> {
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(this, R.raw.song);
            playButton.setEnabled(true);
            stopButton.setEnabled(false);
        });

        // Timer Controls
        TextView timerTextView = findViewById(R.id.timerTextView);
        Button startTimerButton = findViewById(R.id.startTimerButton);
        Button stopTimerButton = findViewById(R.id.stopTimerButton);

        timerRunnable = new Runnable() {
            @Override
            public void run() {
                int minutes = seconds / 60;
                int secs = seconds % 60;
                String time = String.format("%02d:%02d", minutes, secs);
                timerTextView.setText(time);
                if (isRunning) {
                    seconds++;
                    timerHandler.postDelayed(this, 1000);
                }
            }
        };

        startTimerButton.setOnClickListener(v -> {
            isRunning = true;
            timerHandler.post(timerRunnable);
            startTimerButton.setEnabled(false);
            stopTimerButton.setEnabled(true);
        });

        stopTimerButton.setOnClickListener(v -> {
            isRunning = false;
            startTimerButton.setEnabled(true);
            stopTimerButton.setEnabled(false);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}