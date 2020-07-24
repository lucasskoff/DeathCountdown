package com.example.deathcountdown;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Questionnaire extends AppCompatActivity implements View.OnClickListener, CalendarView.OnDateChangeListener {
    public File projFile;
    Button submit;
    CalendarView calendarView;
    EditText textBox;

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupLayout() {
        calendarView = findViewById(R.id.birthday_calender);
        calendarView.setOnDateChangeListener(this);

        LocalDateTime calendarStartDate = LocalDateTime.of(1977,5,23,10,0);

        ZoneId z = ZoneId.of( "America/Phoenix" );
        calendarView.setDate(calendarStartDate.atZone(z).toInstant().toEpochMilli());
        updateDateTextBox();

        submit = findViewById(R.id.birthday_submit);
        submit.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateDateTextBox() {
        long date = calendarView.getDate();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String formattedDate = localDateTime.format(formatter);

        textBox = findViewById(R.id.text_box);
        textBox.setText(formattedDate);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaire);
        setupLayout();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        try{
            //get the date from the calendar and switch screens
            String dirPath = getFilesDir().getAbsolutePath() + File.separator + "timeLeft";
            File projDir = new File(dirPath);
            if (!projDir.exists()) {
                projDir.mkdirs();
            }
            projFile = new File(dirPath + File.separator + "timeLeft.txt");
            if(projFile.exists()) {
                projFile.delete();
            }
            long date = calendarView.getDate();

            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault());
            localDateTime = localDateTime.plusYears(80);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy,MM,dd,hh,mm,ss");
            String formattedDate = localDateTime.format(formatter);

            if(!projFile.exists()) {
                projFile.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(projFile));
                String csv = formattedDate;
                writer.write(csv);
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, StartUpScreen.class);
        startActivity(intent);
        onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
        EditText editText = findViewById(R.id.text_box);
        editText.setText(String.format("%s/%s/%s", month + 1, dayOfMonth, year));
    }
}