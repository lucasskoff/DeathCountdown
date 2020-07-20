package com.example.deathcountdown;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Questionnaire extends AppCompatActivity implements View.OnClickListener, CalendarView.OnDateChangeListener {

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupLayout() {
        CalendarView calendarView = findViewById(R.id.birthday_calender);
        calendarView.setOnDateChangeListener(this);

        LocalDateTime calendarStartDate = LocalDateTime.of(1977,5,23,10,0);

        ZoneId z = ZoneId.of( "America/Phoenix" );
        calendarView.setDate(calendarStartDate.atZone(z).toInstant().toEpochMilli());
        long date = calendarView.getDate();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.systemDefault());


        EditText editText = findViewById(R.id.text_box);
        editText.setText(localDateTime.toString());

        Button submit = findViewById(R.id.birthday_submit);
        submit.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaire);
        setupLayout();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.birthday_submit:
                break;
        }
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

    }
}