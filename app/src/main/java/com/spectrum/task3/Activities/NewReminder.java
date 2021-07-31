package com.spectrum.task3.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.spectrum.task3.ModelClasses.ReminderList;
import com.spectrum.task3.R;

public class NewReminder extends AppCompatActivity {
   EditText title,description;
   String date1,time1;
   DatabaseReference databaseReference;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_new_reminder);
      getSupportActionBar().hide();

      title=findViewById(R.id.eventTitle);
      description=findViewById(R.id.eventDescription);
      databaseReference = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Reminders");

   }

   public void lunch(View view) {
      if(Build.VERSION.SDK_INT> Build.VERSION_CODES.M)
      {
         if(checkSelfPermission(Manifest.permission.WRITE_CALENDAR)== PackageManager.PERMISSION_DENIED)
         {
            String[] parmission={Manifest.permission.WRITE_CALENDAR};
            requestPermissions(parmission,1000);
         }
         else addReminderInCalendar();
      }
      else addReminderInCalendar();

   }

   @SuppressLint("NewApi")
   private void addReminderInCalendar() {
       Calendar cal = Calendar.getInstance();
      Uri EVENTS_URI = Uri.parse(getCalendarUriBase(true) + "events");
      ContentResolver cr = getContentResolver();
      TimeZone timeZone = TimeZone.getDefault();

      cal.set(Integer.valueOf(date1.split("/")[2]),
            Integer.valueOf(date1.split("/")[1]),
            Integer.valueOf(date1.split("/")[0]),
            Integer.valueOf(time1.split(":")[0]),
            Integer.valueOf(time1.split(":")[1]), 0);

      /** Inserting an event in calendar. */
      ContentValues values = new ContentValues();
      values.put(CalendarContract.Events.CALENDAR_ID, 1);
      values.put(CalendarContract.Events.TITLE, title.getText().toString());
      values.put(CalendarContract.Events.DESCRIPTION, description.getText().toString());
      values.put(CalendarContract.Events.ALL_DAY, 0);
      // event starts at 11 minutes from now
      values.put(CalendarContract.Events.DTSTART, cal.getTimeInMillis());
      // ends 60 minutes from now
      values.put(CalendarContract.Events.DTEND, cal.getTimeInMillis() + 60 * 60 * 1000);
      values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
      values.put(CalendarContract.Events.HAS_ALARM, 1);
      Uri event = cr.insert(EVENTS_URI, values);

      // Display event id
      Toast.makeText(getApplicationContext(), "Event added" , Toast.LENGTH_SHORT).show();

      /** Adding reminder for event added. */
      Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(true) + "reminders");
      values = new ContentValues();
      values.put(CalendarContract.Reminders.EVENT_ID, Long.parseLong(event.getLastPathSegment()));
      values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
      values.put(CalendarContract.Reminders.MINUTES, 10);
      cr.insert(REMINDERS_URI, values);
      ReminderList t=new ReminderList(title.getText().toString(),description.getText().toString(),date1+"!"+time1,String.valueOf(event.getLastPathSegment()));
      addfdata(t);
   }

   /** Returns Calendar Base URI, supports both new and old OS. */
   private String getCalendarUriBase(boolean eventUri) {
      Uri calendarURI = null;
      try {
         if (android.os.Build.VERSION.SDK_INT <= 7) {
            calendarURI = (eventUri) ? Uri.parse("content://calendar/") : Uri.parse("content://calendar/calendars");
         } else {
            calendarURI = (eventUri) ? Uri.parse("content://com.android.calendar/") : Uri
                  .parse("content://com.android.calendar/calendars");
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return calendarURI.toString();
   }




   @RequiresApi(api = Build.VERSION_CODES.N)
   protected Dialog onCreateDialog(int id) {

      // Get the calander
      Calendar c = Calendar.getInstance();

      // From calander get the year, month, day, hour, minute
      int year = c.get(Calendar.YEAR);
      int month = c.get(Calendar.MONTH);
      int day = c.get(Calendar.DAY_OF_MONTH);
      int hour = c.get(Calendar.HOUR_OF_DAY);
      int minute = c.get(Calendar.MINUTE);

      switch (id) {
         case 0:

            // Open the datepicker dialog
            return new DatePickerDialog(NewReminder.this, date_listener, year,
                  month, day);
         case 1:

            // Open the timepicker dialog
            return new TimePickerDialog(NewReminder.this, time_listener, hour,
                  minute, false);

      }
      return null;
   }

   // Date picker dialog
   DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {

      @Override
      public void onDateSet(DatePicker view, int year, int month, int day) {
         // store the data in one string and set it to text
         date1 = String.valueOf(day) + "/" + String.valueOf(month)
               + "/" + String.valueOf(year);
         TextView b=findViewById(R.id.dateo);
         b.setText(date1);
      }
   };
   TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {

      @Override
      public void onTimeSet(TimePicker view, int hour, int minute) {
         // store the data in one string and set it to text
         time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
         //set_time.setText(time1);
         TextView m=findViewById(R.id.timeo);
         m.setText(time1);
      }
   };

   public void date(View view) {
      showDialog(0);
   }

   public void time(View view) {
      showDialog(1);
   }


   private void addfdata(ReminderList t) {
      databaseReference.child(databaseReference.push().getKey()).setValue(t);
   }
}
