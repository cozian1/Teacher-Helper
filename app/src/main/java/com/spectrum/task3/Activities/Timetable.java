package com.spectrum.task3.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.spectrum.task3.Adapter.DayAdapter;
import com.spectrum.task3.Adapter.EventAdapter;
import com.spectrum.task3.ModelClasses.DayList;
import com.spectrum.task3.ModelClasses.ReminderList;
import com.spectrum.task3.ModelClasses.TimetableAdapter;
import com.spectrum.task3.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Timetable extends AppCompatActivity {

   List<DayList> list=new ArrayList<>();
   List<DayList> daylist;
   RecyclerView recyclerView;
   DayAdapter myAdapter0;
   TimetableAdapter myAdapter1;
   LinearLayoutManager linearLayoutManager;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_timetable);
      recyclerView =findViewById(R.id.dayview);
      daylist=new ArrayList<>();
      daylist.add(new DayList("MONDAY","true"));
      daylist.add(new DayList("THUDAY","false"));
      daylist.add(new DayList("WEDDAY","false"));
      daylist.add(new DayList("THUSDAY","false"));
      daylist.add(new DayList("FRIDAY","false"));
      daylist.add(new DayList("SATERDAY","false"));
      daylist.add(new DayList("SUNDAY","false"));
      myAdapter0=new DayAdapter();
      myAdapter0.setData(daylist,Timetable.this);
      recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
      recyclerView.setAdapter(myAdapter0);
   }
}