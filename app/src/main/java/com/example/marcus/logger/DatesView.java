package com.example.marcus.logger;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marcus.logger.Model.Data;
import com.example.marcus.logger.Model.Date;
import com.example.marcus.logger.Model.User;

import java.util.Calendar;

import static com.example.marcus.logger.R.id.datesListView;
import static com.example.marcus.logger.R.layout.date_item;

public class DatesView extends AppCompatActivity {

    private Drawable defaultSelector;
    private static User currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Data.init(this);
        System.out.println("Logged in successfully: " + Data.getInstance().getUser().getName());
        currentUser = Data.getInstance().getUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dates_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.logDate);
        ListView lv = (ListView)findViewById(datesListView);
        defaultSelector = lv.getSelector();
        updateDisplay();

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
                        currentUser.addDate(d);
                        Data.getInstance().save();
                        Log.d("DATE",d.toString());
                        Toast.makeText(DatesView.this, "New Date Added: "+ d.toString(), Toast.LENGTH_SHORT).show();
                        updateDisplay();
                    }
                });

                picker.updateDate(picker.getYear(),picker.getMonth(),picker.getDayOfMonth());
                builder.show();


                //refresh();
            }
        });

        // the call back received when the user "sets" the date in the dialog


    }

    private class MyListAdapter extends ArrayAdapter<String>{
        private int layout;
        public MyListAdapter(Context context, int resource, java.lang.String[] objects) {
            super(context, resource, objects);
            layout=resource;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewHolder = null;
            if(convertView==null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView=inflater.inflate(layout,parent,false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.date =(TextView)convertView.findViewById(R.id.dateTextView);
                viewHolder.check = (CheckBox)convertView.findViewById(R.id.deleteDateCheckBox);
                viewHolder.time = (TextView)convertView.findViewById(R.id.timeTextView);
                convertView.setTag((viewHolder));

            }
            mainViewHolder = (ViewHolder) convertView.getTag();
            mainViewHolder.date.setText(getItem(position));
            mainViewHolder.time.setText("");

            return convertView;
        }
    }

    public class ViewHolder{
        TextView date;
        CheckBox check;
        TextView time;
    }

    public void updateDisplay(){
        TextView total = (TextView)findViewById(R.id.totalDatesTextView);
        ListView lv = (ListView)findViewById(datesListView);
        String [] uDates = currentUser.getDatesStringArray();
        ArrayAdapter<String> adapter;
        if (uDates.length != 0) {
            adapter = new ArrayAdapter<String>(this, R.layout.date_item, uDates);// This was changed from R.layout.album_list_text
            lv.setSelector(defaultSelector);
        } else {
            String[] titles = {"No albums."};
            adapter = new ArrayAdapter<String>(this, R.layout.date_item, titles);// This was changed from R.layout.album_list_text
            lv.setSelector(defaultSelector);
        }
        lv.setAdapter(new MyListAdapter(this,R.layout.date_item,uDates));
        if(currentUser!=null){
            total.setText("Total Count: "+Data.getInstance().getUser().getDates().size());
        }

    }

    public void refresh(){
        ListView lv = (ListView) findViewById(datesListView);
        String allDates[]={"Testing"};
        ArrayAdapter<String> ad = new ArrayAdapter<>(this,R.layout.date_item,allDates);
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
