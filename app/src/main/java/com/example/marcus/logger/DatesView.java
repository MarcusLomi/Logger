package com.example.marcus.logger;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marcus.logger.Model.Data;
import com.example.marcus.logger.Model.Date;
import com.example.marcus.logger.Model.User;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.marcus.logger.R.id.datesListView;
import static com.example.marcus.logger.R.layout.date_item;

public class DatesView extends AppCompatActivity {

    private Drawable defaultSelector;
    private static User currentUser;
    private ArrayList<Date> markedForDeletion = new ArrayList<Date>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
        ListView lv = (ListView) findViewById(datesListView);
        CheckBox check = (CheckBox) findViewById(R.id.deleteDateCheckBox);
        FloatingActionButton delete = (FloatingActionButton) findViewById(R.id.deleteConfirmButton);
        defaultSelector = lv.getSelector();

//        check.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(DatesView.this, "hi there it works", Toast.LENGTH_SHORT).show();
//            }
//        });
        updateDisplay();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (markedForDeletion.size() == 0) {
                    Toast.makeText(DatesView.this, "You must select a date to be deleted", Toast.LENGTH_LONG).show();
                } else {
                    AlertDialog.Builder deleteConfirmation = new AlertDialog.Builder(DatesView.this);
                    deleteConfirmation.setTitle("Delete the " + markedForDeletion.size() + " Selected Dates?");

                    deleteConfirmation.setPositiveButton("Confirm", (new DialogInterface.OnClickListener() {
                        @Override

                        public void onClick(DialogInterface dialogInterface, int which) {
                            Toast.makeText(DatesView.this, "Deleted Albums", Toast.LENGTH_SHORT).show();
                            for (Date d : markedForDeletion) {
                                currentUser.deleteDate(d);
                                Data.getInstance().save();
                            }

                            markedForDeletion.clear();
                            updateDisplay();
                        }
                    }));
                    deleteConfirmation.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override

                        public void onClick(DialogInterface dialogInterface, int which) {
                            Toast.makeText(DatesView.this, "Did Not Delete Albums", Toast.LENGTH_SHORT).show();
                            markedForDeletion.clear();
                            dialogInterface.cancel();

                        }
                    });
                    deleteConfirmation.show();
                    updateDisplay();
                }
                Data.getInstance().save();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                updateDisplay();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DatesView.this);
                builder.setTitle("What was the time?");

                final EditText input = new EditText(DatesView.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                input.setPadding(20, 20, 20, 20);
                input.setHint("Title");
                builder.setView(input);

                builder.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Set the time for the day
                        String aTitle = input.getText().toString();
                        if (aTitle.equals("")) {
                            dialog.dismiss();
                            return;
                        }
                        Date[] selection = currentUser.getDateArray();
                        selection[position].setTime(aTitle);
                        Data.getInstance().save();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                Log.v("Long clicked","Pos: "+position);
                return true;
            }

        });

        fab.setOnClickListener(new OnClickListener() {
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
                        Date d = new Date(picker.getDayOfMonth(), picker.getMonth(), picker.getYear());
                        currentUser.addDate(d);
                        Data.getInstance().save();
                        Log.d("DATE", d.toString());
                        Toast.makeText(DatesView.this, "New Date Added: " + d.toString(), Toast.LENGTH_SHORT).show();
                        updateDisplay();
                    }
                });

                picker.updateDate(picker.getYear(), picker.getMonth(), picker.getDayOfMonth());
                builder.show();

            }
        });

        // the call back received when the user "sets" the date in the dialog


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("DatesView Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class MyListAdapter extends ArrayAdapter<String> {
        private int layout;

        public MyListAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewHolder = null;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.date = (TextView) convertView.findViewById(R.id.dateTextView);
                viewHolder.check = (CheckBox) convertView.findViewById(R.id.deleteDateCheckBox);
                viewHolder.time = (TextView) convertView.findViewById(R.id.timeTextView);
                convertView.setTag((viewHolder));

            }
            mainViewHolder = (ViewHolder) convertView.getTag();
            mainViewHolder.date.setText(getItem(position));
            mainViewHolder.time.setText(currentUser.getDateArray()[position].getTime());
            mainViewHolder.check.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(),"Checkbox for item:"+position+" was pressed",Toast.LENGTH_SHORT).show();  //Add this this toast to show that clicking the checkbox does in fact keep track of the index
                    String test = "" + markedForDeletion.size();
                    Date[] toDelete = new Date[currentUser.getDates().size()];
                    toDelete=currentUser.getDateArray();               //Gets the total list of user albums
                    if (markedForDeletion.contains(toDelete[position])) {       //Checks to see if the album was already marked for deletion
                        markedForDeletion.remove(toDelete[position]);           //If yes, it removes the album being marked for deletion
                        test="Removing item at "+position+". Total Size: "+markedForDeletion.size();
                       Toast.makeText(DatesView.this,test,Toast.LENGTH_SHORT).show();
                        Log.d(test, " Items are marked for deletion");
                    } else {
                        markedForDeletion.add(toDelete[position]);              //If it's not in the list it adds it in
                        test="Adding item at "+position+"Total Size: "+markedForDeletion.size();
                      //  Toast.makeText(DatesView.this,test,Toast.LENGTH_SHORT).show();
                    }

                }
            });
            return convertView;
        }
    }

    public class ViewHolder {
        TextView date;
        CheckBox check;
        TextView time;
    }

    public void updateDisplay() {
        currentUser = Data.getInstance().getUser();
        TextView total = (TextView) findViewById(R.id.totalDatesTextView);
        TextView recent = (TextView) findViewById(R.id.recentDateTextView);
        ListView lv = (ListView) findViewById(datesListView);
        String[] uDates = currentUser.getDatesStringArray();
        String[] uTimes = currentUser.getTimesStringArray();
        Toast.makeText(DatesView.this,""+uDates.length,Toast.LENGTH_LONG);
        ArrayAdapter<String> adapter;
        ArrayAdapter<String> adapter2;



        if (uDates.length != 0) {
            adapter = new ArrayAdapter<String>(this, R.layout.date_item, uDates);
            adapter2 = new ArrayAdapter<String>(this, R.layout.date_item, R.id.dateTextView,uDates);
            lv.setSelector(defaultSelector);
        } else {
            String[] titles = {"No albums."};
            adapter = new ArrayAdapter<String>(this, R.layout.date_item, titles);
            lv.setSelector(defaultSelector);
        }
        lv.setAdapter(new MyListAdapter(this, R.layout.date_item, uDates));

        if (currentUser != null) {
            String[] allDates = Data.getInstance().getUser().getDatesStringArray();
            String[] allTimes = Data.getInstance().getUser().getTimesStringArray();
            total.setText("Total Count: " + Data.getInstance().getUser().getDates().size());
            recent.setText("Most Recent: "+allDates[allDates.length-1]+" @ "+allTimes[allTimes.length-1]);
        }

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
