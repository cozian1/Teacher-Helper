package com.spectrum.task3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.spectrum.task3.Activities.Calculator;
import com.spectrum.task3.Activities.DocStorage;
import com.spectrum.task3.Activities.NewReminder;
import com.spectrum.task3.Activities.POC;
import com.spectrum.task3.Activities.Pdfmaker;
import com.spectrum.task3.Activities.Reminder;
import com.spectrum.task3.Activities.Timetable;
import com.spectrum.task3.Activities.Todo;
import com.spectrum.task3.splash.SplashActivity;

public class MainActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

   }

   public void pdfmaker(View view) {
      startActivity(new Intent(MainActivity.this, Pdfmaker.class));
   }

   public void timetable(View view) {
      startActivity(new Intent(MainActivity.this, Timetable.class));
   }

   public void reminder(View view) {
      startActivity(new Intent(MainActivity.this, Reminder.class));
   }

   public void docsrorage(View view) {
      startActivity(new Intent(MainActivity.this, DocStorage.class));
   }

   public void todo(View view) {
      startActivity(new Intent(MainActivity.this, Todo.class));
   }

   public void calc(View view) {
      startActivity(new Intent(MainActivity.this, Calculator.class));
   }

   public void poc(View view) {
      startActivity(new Intent(MainActivity.this, POC.class));
   }
}