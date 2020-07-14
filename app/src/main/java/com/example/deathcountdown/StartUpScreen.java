package com.example.deathcountdown;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class StartUpScreen extends AppCompatActivity implements View.OnClickListener {
    private static final Integer INTERVAL = 1000;
    public File projFile;
    Button showTimerButton;
    TextView textView;

    private void setupLayout() {
        Button takeSurvey = (Button) findViewById(R.id.take_survey);
        takeSurvey.setOnClickListener(this);

        Button manualInput = (Button) findViewById(R.id.about_us);
        manualInput.setOnClickListener(this);

        Button showTimer = (Button) findViewById(R.id.show_timer);
        showTimer.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUpTimer() {
        int year = 2080;
        int month = 7;
        int day = 6;
        int hour = 5;
        int minute = 30;
        int second = 20;

        try {
            String dirPath = getFilesDir().getAbsolutePath() + File.separator + "timeLeft";
            File projDir = new File(dirPath);
            if (!projDir.exists())
                projDir.mkdirs();
            projFile = new File(dirPath + File.separator + "timeLeft.txt");
            if(projFile.exists()) {
                projFile.delete();
            }
            if(!projFile.exists()) {
                projFile.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(projFile));
                String csv = year + "," + month + "," + day +"," + hour + "," + minute + "," + second;
                writer.write(csv);
                writer.close();
            }
            BufferedReader brTest = new BufferedReader(new FileReader(projFile));
            String text = brTest.readLine();
            if(text != null) {
                String[] strArray = text.split(",");
                year = Integer.valueOf(strArray[0]);
                month = Integer.valueOf(strArray[1]);
                day = Integer.valueOf(strArray[2]);
                hour = Integer.valueOf(strArray[3]);
                minute = Integer.valueOf(strArray[4]);
                second = Integer.valueOf(strArray[5]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        showTimerButton= (Button) findViewById(R.id.show_timer);
        textView= (TextView) findViewById(R.id.textView);

        final LocalDateTime endDate = LocalDateTime.of(year, month, day, hour, minute, second);

        textView.setText(calculateRemainingTime(LocalDateTime.now(), endDate));

        //This will re-calculate and display the remaining time every second
        showTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimerButton.setEnabled(false);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(calculateRemainingTime(LocalDateTime.now(), endDate));
                        handler.postDelayed(this, INTERVAL);
                    }
                }, INTERVAL);
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.take_survey:
                intent = new Intent(this, Questionaire.class);
                startActivity(intent);
                onDestroy();
                break;
            case R.id.about_us:
                intent = new Intent(this, AboutUs.class);
                startActivity(intent);
                onDestroy();
                break;
            case R.id.show_timer:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setupLayout();
        setUpTimer();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String calculateRemainingTime(LocalDateTime start, LocalDateTime end)
    {
        //calculate each unit of time, then subtract that unit from the end before calculating the next
        long days = ChronoUnit.DAYS.between(start, end);
        LocalDateTime newEnd = end.minusDays(days);

        long hours = ChronoUnit.HOURS.between(start, newEnd);
        newEnd = newEnd.minusHours(hours);

        long minutes = ChronoUnit.MINUTES.between(start, newEnd);
        newEnd = newEnd.minusMinutes(minutes);

        long seconds = ChronoUnit.SECONDS.between(start, newEnd);

        String difference = String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds);

        return difference;
    }
}
