package com.example.ruterbillett4;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PeriodeBillettActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodebillett);

        createTitle();

        EditText edittext = (EditText) findViewById(R.id.date_time_field);
        if (edittext.getText().length() != 0){
            start_countdown_timer();
        }
        else {
            Button timeButton = (Button) findViewById(R.id.pick_time_and_date);
            timeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setDate();
                }
            });
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void start_countdown_timer()
    {
        final Button dayTxt = (Button) findViewById(R.id.day);
        final Button timeTxt = (Button) findViewById(R.id.time);
        final Button minTxt = (Button) findViewById(R.id.min);
        final Button secTxt = (Button) findViewById(R.id.sec);

        TextView textview = (TextView) findViewById(R.id.date_time_field);
        String tmpTime = textview.getText().toString();
        String[] splitStr = tmpTime.split("\\s+");
        String dt = splitStr[0];
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 30);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy");
        String output = sdf1.format(c.getTime());
        String endTime = output + ", " + splitStr[1];

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        formatter.setLenient(false);
        long milliseconds=0;

        Date endDate;
        try {
            endDate = formatter.parse(endTime);
            milliseconds = endDate.getTime();

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        final CountDownTimer mCountDownTimer;

        final long[] startTime = {System.currentTimeMillis()};
        mCountDownTimer = new CountDownTimer(milliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                startTime[0] = startTime[0] -1;
                Long serverUptimeSeconds =
                        (millisUntilFinished - startTime[0]) / 1000;

                String daysLeft = String.format("%d", serverUptimeSeconds / 86400);
                dayTxt.setText(daysLeft);

                String hoursLeft = String.format("%d", (serverUptimeSeconds % 86400) / 3600);
                timeTxt.setText(hoursLeft);

                String minutesLeft = String.format("%d", ((serverUptimeSeconds % 86400) % 3600) / 60);
                minTxt.setText(minutesLeft);

                String secondsLeft = String.format("%d", ((serverUptimeSeconds % 86400) % 3600) % 60);
                secTxt.setText(secondsLeft);
            }
            @Override
            public void onFinish() {

            }
        }.start();
    }

     private void setTime() {
        final EditText edittext = (EditText) findViewById(R.id.date_time_field);
        final String content = edittext.getText().toString();
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        int secon = mcurrentTime.get(Calendar.SECOND);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(PeriodeBillettActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                edittext.setText( content + " " + selectedHour + ":" + selectedMinute + ":" + "00");
                start_countdown_timer();
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void setDate(){
        final EditText edittext = (EditText) findViewById(R.id.date_time_field);
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        if(month <12){
            month++;
        }
        else {
            month = 1;
        }
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(PeriodeBillettActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthofyear, int dayofmonth) {
                edittext.setText(dayofmonth + "." + monthofyear + "." + year + ", ");
                setTime();
            }
        }, year, month, day);
        dpd.show();
    }


    private void createTitle(){
        toolbar = findViewById(R.id.periode_toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.periode_toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
