package com.spectrum.task3.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.spectrum.task3.Adapter.DayAdapter;
import com.spectrum.task3.Adapter.EventAdapter;
import com.spectrum.task3.ModelClasses.DayList;
import com.spectrum.task3.ModelClasses.Mylist;
import com.spectrum.task3.ModelClasses.ReminderList;
import com.spectrum.task3.ModelClasses.TimetableAdapter;
import com.spectrum.task3.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Timetable extends AppCompatActivity {

   String currentday="MONDAY";
   List<DayList> list=new ArrayList<>();
   List<DayList> daylist;
   RecyclerView recyclerView;
   DayAdapter myAdapter0;
   TimetableAdapter myAdapter1;
   List<Mylist> monday=new ArrayList<>();
   List<Mylist> thuday=new ArrayList<>();
   List<Mylist> wedday=new ArrayList<>();
   List<Mylist> thusday=new ArrayList<>();
   List<Mylist> friday=new ArrayList<>();
   List<Mylist> saterday=new ArrayList<>();
   List<Mylist> sunday=new ArrayList<>();
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_timetable);
      getSupportActionBar().hide();

      recyclerView =findViewById(R.id.dayview);
      daylist=new ArrayList<>();
      monday.add(new Mylist("asddas","aadasd","12\n45"));
      monday.add(new Mylist("asddas","aadasd","12\n45"));
      monday.add(new Mylist("asddas","aadasd","12\n45"));
      monday.add(new Mylist("asddas","aadasd","12\n45"));
      thuday.add(new Mylist("asddas","aadasd","12\n45"));
      thuday.add(new Mylist("asddas","aadasd","12\n45"));
      thuday.add(new Mylist("asddas","aadasd","12\n45"));
      wedday.add(new Mylist("asddas","aadasd","12\n45"));
      wedday.add(new Mylist("asddas","aadasd","12\n45"));
      wedday.add(new Mylist("asddas","aadasd","12\n45"));
      thusday.add(new Mylist("asddas","aadasd","12\n45"));
      thusday.add(new Mylist("asddas","aadasd","12\n45"));
      thusday.add(new Mylist("asddas","aadasd","12\n45"));
      friday.add(new Mylist("fdhhdfs","sdhfshasd","34\n4d"));
      friday.add(new Mylist("fdhhdfs","sdhfshasd","34\n4d"));
      friday.add(new Mylist("fdhhdfs","sdhfshasd","34\n4d"));
      saterday.add(new Mylist("fdhhdfs","sdhfshasd","34\n4d"));
      sunday.add(new Mylist("fdhhdfs","sdhfshasd","34\n4d"));

      daylist.add(new DayList("MONDAY","true"));
      daylist.add(new DayList("THUDAY","false"));
      daylist.add(new DayList("WEDDAY","false"));
      daylist.add(new DayList("THUSDAY","false"));
      daylist.add(new DayList("FRIDAY","false"));
      daylist.add(new DayList("SATERDAY","false"));
      daylist.add(new DayList("SUNDAY","false"));
      myAdapter0=new DayAdapter();
      myAdapter1=new TimetableAdapter();
      recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
      recyclerView.setAdapter(myAdapter0);
      myAdapter0.setData(daylist,Timetable.this);

   }
   public void setday(String s){
      RecyclerView rv=findViewById(R.id.mylist);
      rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
      rv.setAdapter(myAdapter1);
      switch (s){
         case "MONDAY":
            myAdapter1.setData(monday);break;
         case "THUDAY":
            myAdapter1.setData(thuday);break;
         case "WEDDAY":
            myAdapter1.setData(wedday);break;
         case "THUSDAY":
            myAdapter1.setData(thusday);break;
         case "FRIDAY":
            myAdapter1.setData(friday);break;
         case "SATERDAY":
            myAdapter1.setData(saterday);break;
         case "SUNDAY":
            myAdapter1.setData(sunday);break;
      }
      myAdapter1.notifyDataSetChanged();
   }
}