package com.spectrum.task3.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spectrum.task3.Adapter.EventAdapter;
import com.spectrum.task3.Adapter.TodoAdapter;
import com.spectrum.task3.ModelClasses.ReminderList;
import com.spectrum.task3.ModelClasses.TODO;
import com.spectrum.task3.R;

import java.util.ArrayList;
import java.util.List;

public class Reminder extends AppCompatActivity {

   DatabaseReference databaseReference;
   List<ReminderList> list=new ArrayList<>();
   RecyclerView recyclerView;
   EventAdapter myAdapter;
   LinearLayoutManager linearLayoutManager;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_reminder);
      getSupportActionBar().hide();

      recyclerView=findViewById(R.id.Rminderrecyclerview);
      databaseReference = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Reminders");


      getfdata();

      myAdapter=new EventAdapter();
      myAdapter.setData(list);
      linearLayoutManager = new LinearLayoutManager(this);
      linearLayoutManager.setReverseLayout(true);
      linearLayoutManager.setStackFromEnd(true);
      recyclerView.setLayoutManager(linearLayoutManager);

      ItemTouchHelper.SimpleCallback itemTouched= new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
         @Override
         public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
         }
         @Override
         public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            long id=Long.valueOf(list.get(viewHolder.getAdapterPosition()).getKey());
            list.remove(viewHolder.getAdapterPosition());
            Uri deleteUri = null;
            deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, id);
            int rows = getContentResolver().delete(deleteUri, null, null);
            Toast.makeText(Reminder.this, "Event deleted", Toast.LENGTH_LONG).show();
            updatefdata();
            myAdapter.notifyDataSetChanged();
         }
      };
      new ItemTouchHelper(itemTouched).attachToRecyclerView(recyclerView);
      recyclerView.setAdapter(myAdapter);

   }

   public void newReminder(View view) {
      startActivity(new Intent(Reminder.this,NewReminder.class));
   }
   private void addfdata(ReminderList t) {
      databaseReference.child(databaseReference.push().getKey()).setValue(t);
   }
   private void getfdata(){
      databaseReference.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
            if(list.size()!=0) return;
            for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
            {
               ReminderList p=dataSnapshot1.getValue(ReminderList.class);
               list.add(p);
            }
            myAdapter.notifyDataSetChanged();
         }
         @Override
         public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
         }
      });
   }
   private void updatefdata(){
      databaseReference.removeValue();
      for(ReminderList t:list)
         databaseReference.child(databaseReference.push().getKey()).setValue(t);
   }
}