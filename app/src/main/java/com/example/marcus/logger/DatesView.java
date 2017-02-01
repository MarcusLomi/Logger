package com.example.marcus.logger;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.example.marcus.logger.Model.Date;

import java.util.Calendar;

import static com.example.marcus.logger.R.layout.date_item;

public class DatesView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dates_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.logDate);
        ListView lv = (ListView)findViewById(R.id.datesListView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DatesView.this);
                final DatePicker picker = new DatePicker(DatesView.this);
                builder.setTitle("Pick the date");
                builder.setView(picker);
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override

                    public void onClick(DialogInterface dialogInterface, int which) {
                        Date d = new Date(picker.getDayOfMonth(),picker.getMonth(),picker.getYear());
                        Log.d("DATE",d.toString());
                        Toast.makeText(DatesView.this, "New Date Added: "+ d.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

                picker.updateDate(picker.getYear(),picker.getMonth(),picker.getDayOfMonth());
                builder.show();


                //refresh();
            }
        });

        // the call back received when the user "sets" the date in the dialog


    }

    public void updateDisplay(){

    }

    public void refresh(){
        ListView lv = (ListView) findViewById(R.id.datesListView);
        String allDates[]={"Testing"};
        ArrayAdapter<String> ad = new ArrayAdapter<>(this, date_item,allDates);
        lv.setAdapter(ad);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dates_view, menu);
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
