package com.example.deathcountdown;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private static final Integer INIT_TIME = 30000;
    private static final Integer INTERVAL = 1000;
    public int counter;
    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= (Button) findViewById(R.id.button);
        textView= (TextView) findViewById(R.id.textView);
        int hoursToGo = 6;
        int minutesToGo = 25;
        int secondsToGo = 30;

        final long millisToGo = secondsToGo*1000+minutesToGo*1000*60+hoursToGo*1000*60*60;
        setClockText(millisToGo);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                button.setEnabled(false);
                new CountDownTimer(millisToGo, INTERVAL){
                    private final Integer DISPLAY_INITIAL_TIME = INIT_TIME / INTERVAL;
                    @Override
                    public void onTick(long millisUntilFinished){
                        setClockText(millisUntilFinished);
                        counter++;
                    }
                    @Override
                    public  void onFinish(){
                        textView.setText("YOU DIED!!");
                    }
                }.start();
            }
        });
    }

    public void setClockText(long millisUntilFinished) {
        int seconds = (int) (millisUntilFinished / 1000) % 60 ;
        int minutes = (int) ((millisUntilFinished / (1000*60)) % 60);
        int hours   = (int) ((millisUntilFinished / (1000*60*60)) % 24);
        String text = String.format("%02d hours, %02d minutes, %02d seconds",hours,minutes,seconds);
        textView.setText(text);
    }
}
