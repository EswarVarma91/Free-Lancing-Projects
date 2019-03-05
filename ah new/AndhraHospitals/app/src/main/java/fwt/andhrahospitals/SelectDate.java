package fwt.andhrahospitals;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.format.DateTimeFormatter;

import java.sql.Date;
import java.util.Locale;

public class SelectDate extends AppCompatActivity  {

    CalendarView calendarView;
    String day1,mon1,yea1;
    Button selectdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        android.support.v7.widget.Toolbar toolbar_login=findViewById(R.id.toolbar_sdate);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select Date");
        calendarView=findViewById(R.id.calendarView);
        selectdate=findViewById(R.id.selectdate);
        final SharedPreferences pref = this.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final  SharedPreferences.Editor editor = pref.edit();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                day1= String.valueOf(dayOfMonth);
                mon1= String.valueOf(month);
                yea1= String.valueOf(year);
               // Toast.makeText(SelectDate.this, dayOfMonth+"/"+month+"/"+year, Toast.LENGTH_SHORT).show();
            }
        });
        selectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(day1==null && mon1==null && yea1==null){
                   Toast.makeText(SelectDate.this, "Choose Date", Toast.LENGTH_SHORT).show();
               }else{
                   Intent i=new Intent(SelectDate.this,BranchesActivity.class);
                   i.putExtra("DAY",day1);
                   i.putExtra("MON",mon1);
                   i.putExtra("YEA",yea1);
                   editor.putString("SELECTDATE",day1+"-"+mon1+"-"+yea1);
                   editor.commit();
                   Log.d("LLL",day1+mon1+yea1);
                   startActivity(i);
               }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
