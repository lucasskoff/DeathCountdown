package com.example.deathcountdown;

import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private static final Integer INIT_TIME = 30000;
    private static final Integer INTERVAL = 1000;
    public int counter;
    public File projFile;
    Button button;
    TextView textView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= (Button) findViewById(R.id.button);
        textView= (TextView) findViewById(R.id.textView);

        String millisFromFile = "";
        try {
            String dirPath = getFilesDir().getAbsolutePath() + File.separator + "timeLeft";
            File projDir = new File(dirPath);
            if (!projDir.exists())
                projDir.mkdirs();
            projFile = new File(dirPath + File.separator + "time.txt");
            if(!projFile.exists()) {
                projFile.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(projFile));
                int hoursToGo = 6;
                int minutesToGo = 25;
                int secondsToGo = 30;
                long millis = secondsToGo*1000+minutesToGo*1000*60+hoursToGo*1000*60*60;
                writer.write("" + millis + "\n");
                writer.close();
            }
            System.out.println("Rejoice!" + projFile.exists());
            BufferedReader brTest = new BufferedReader(new FileReader(projFile));
            String text = brTest.readLine();
            if(text != null) {
                String[] strArray = text.split(",");
                millisFromFile = strArray[0];
                System.out.println(Arrays.toString(strArray));

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        int hoursToGo = 6;
        int minutesToGo = 25;
        int secondsToGo = 30;
        long millis = secondsToGo*1000+minutesToGo*1000*60+hoursToGo*1000*60*60;
        if(!millisFromFile.isEmpty()) {
            millis = Long.valueOf(millisFromFile);
        }
        final long millisToGo = millis;
        setClockText(millisToGo);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                button.setEnabled(false);
                new CountDownTimer(millisToGo, INTERVAL){
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
        String text = String.format("%02d:%02d:%02d",hours,minutes,seconds);
        textView.setText(text);
    }
}
