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
        Integer initialTime = INIT_TIME / INTERVAL;
        String initialTimerText = String.valueOf(initialTime);
        textView.setText(initialTimerText);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new CountDownTimer(INIT_TIME, INTERVAL){
                    private final Integer DISPLAY_INITIAL_TIME = INIT_TIME / INTERVAL;
                    public void onTick(long millisUntilFinished){
                        textView.setText(String.valueOf(DISPLAY_INITIAL_TIME - counter));
                        counter++;
                    }
                    public  void onFinish(){
                        textView.setText("FINISH!!");
                    }
                }.start();
            }
        });
    }
}
