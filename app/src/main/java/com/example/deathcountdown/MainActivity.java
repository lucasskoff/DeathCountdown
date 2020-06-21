package com.example.deathcountdown;

import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.CountDownTimer;
import android.os.Handler;
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
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.Locale;
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

        int year = 2020;
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

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, second);

        final Date endDate = calendar.getTime();
        textView.setText(ymwdhmsDifference(new Date(), endDate));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(ymwdhmsDifference(new Date(), endDate));
                        handler.postDelayed(this, 1000);
                    }
                }, 1000)  ;
            }
        button.
        });
    }

    public void setClockText(long millisUntilFinished) {
        int seconds = (int) (millisUntilFinished / 1000) % 60 ;
        int minutes = (int) ((millisUntilFinished / (1000*60)) % 60);
        int hours   = (int) ((millisUntilFinished / (1000*60*60)) % 24);
        String text = String.format("%02d:%02d:%02d",hours,minutes,seconds);
        textView.setText(text);
    }

    public static String ymwdhmsDifference(Date d1, Date d2)
    {
        long diff = d2.getTime() - d1.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long totalDiffDays = diff / (24 * 60 * 60 * 1000);
        long diffDays = totalDiffDays % 7;

        long diffWeeks = totalDiffDays/7;   // Full weeks are simply days / 7.
        diffDays = diffDays % 7;        // now we get the remaining days.

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(d1);
        cal2.setTime(d2);


        long totalDiffYears = cal2.get(Calendar.YEAR)-cal1.get(Calendar.YEAR);
        long totalDiffMonths = Math.max(totalDiffYears*12+cal2.get(Calendar.MONTH)-cal1.get(Calendar.MONTH)-1,0);
        long diffYears = totalDiffMonths / 12;
        // remaining full months
        long diffMonths = totalDiffMonths % 12;

        // now we have to count how many weeks those full months represent, to substract them from the number of weeks...
        Calendar cal3 = Calendar.getInstance();
        cal3.setTime(d1);
        int month = cal1.get(Calendar.MONTH)+1, year = cal1.get(Calendar.YEAR), monthDays=0;
        for (int m=0;m<totalDiffMonths;m++) {
            cal3.set(Calendar.MONTH, month++);
            monthDays+=cal3.getActualMaximum(Calendar.DAY_OF_MONTH);
            if (month>=12) {
                month = 0;
                year++;
                cal3.set(Calendar.YEAR, year);
            }
        }
        diffWeeks-=monthDays/7;

        // Note that the number of weeks can be greater than 4 because they are part of non full months.
        //String difference=String.format("%02d:%02d:%02d:%02d:%02d:%02d:%02d",Long.toString(diffYears),Long.toString(diffMonths),Long.toString(diffWeeks),Long.toString(diffDays),Long.toString(diffHours),Long.toString(diffMinutes),Long.toString(diffSeconds));
        String years = Long.toString(diffYears);
        String months = Long.toString(diffMonths);
        String weeks = Long.toString(diffWeeks);
        String days = Long.toString(diffDays);
        String hours = Long.toString(diffHours);
        String minutes = Long.toString(diffMinutes);
        String seconds = Long.toString(diffSeconds);
        //String value = String.format("%02d:%02d:%02d:%02d:%02d:%02d:%02d", years, months, weeks, days, hours, minutes, seconds);
        String difference="Y: "+Long.toString(diffYears)+" M: "+Long.toString(diffMonths)+" W: "+Long.toString(diffWeeks)+" D: "+Long.toString(diffDays)+" H: "+String.format("%02d", diffHours)+" M: "+String.format("%02d", diffMinutes)+" S: "+String.format("%02d", diffSeconds);

        return difference;
    }
}
