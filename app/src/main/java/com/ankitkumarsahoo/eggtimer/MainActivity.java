package com.ankitkumarsahoo.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public SeekBar setTimer = null;
    public ImageView imageView = null;
    public TextView time = null;
    public Button controlButton = null;
    public MediaPlayer mp = null;

    public boolean counterIsActive = false;
    public CountDownTimer countDownTimer = null;

    public void SetTime() {

        setTimer = findViewById(R.id.setTimer);
        setTimer.setMax(600);
        setTimer.setProgress(30);
        FinalTimeSet(30);
        setTimer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Progress", Integer.toString(progress));

                FinalTimeSet(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void FinalTimeSet(int t) {


        int minutes = (int) t / 60;
        int seconds = t - minutes * 60;

        String secondString = Integer.toString(seconds);
        if (seconds <= 9) {

            secondString = "0" + secondString;
        }
        String minuteString = Integer.toString(minutes);
        if (minutes <= 9) {

            minuteString = "0" + minuteString;
        }

        time.setText(minuteString + ":" + secondString );
    }


    @SuppressLint("SetTextI18n")
    public void controlTimer(View view) {

        if (!counterIsActive) {

            counterIsActive = true;
            setTimer.setEnabled(false);
            controlButton.setText("stop");

            Log.i("Info", "Button Pressed");

            countDownTimer = new CountDownTimer(setTimer.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    FinalTimeSet((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {

                    Log.i("Finished", "Timer Done");
                    mp.start();
                    resetTimer();
                }
            }.start();
        } else {

            resetTimer();
        }
    }

    @SuppressLint("SetTextI18n")
    public void resetTimer() {

        time.setText("00:30");
        setTimer.setProgress(30);
        countDownTimer.cancel();
        controlButton.setText("start");
        setTimer.setEnabled(true);
        counterIsActive = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.cutegg);

        time = findViewById(R.id.time);

        mp = MediaPlayer.create(this, R.raw.airhorn);

        controlButton = findViewById(R.id.controlButton);

        SetTime();
    }
}