package com.spectrum.task3.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spectrum.task3.Adapter.TodoAdapter;
import com.spectrum.task3.Dialog.TodoDialog;
import com.spectrum.task3.MainActivity;
import com.spectrum.task3.ModelClasses.TODO;
import com.spectrum.task3.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Todo extends AppCompatActivity implements TodoDialog.DialogListener {
   FirebaseAuth auth;
   DatabaseReference databaseReference;
   FirebaseDatabase firebaseDatabase;
   FirebaseUser user;

   int op=new Random().nextInt();

   List<TODO> list;
   TodoAdapter myAdapter;
   RecyclerView recyclerView;
   LinearLayoutManager linearLayoutManager;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_todo);
      if(getSupportActionBar()!=null){
         getSupportActionBar().hide();
      }
      recyclerView=findViewById(R.id.recyclerview);
      list=new ArrayList<>();

      auth = FirebaseAuth.getInstance();
      user=auth.getCurrentUser();
      firebaseDatabase=FirebaseDatabase.getInstance();
      databaseReference = firebaseDatabase.getReference().child(user.getUid()).child("TodoList");

      getfdata();

      myAdapter=new TodoAdapter();
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
            list.remove(viewHolder.getAdapterPosition());
            updatefdata();
            myAdapter.notifyDataSetChanged();
         }
      };
      new ItemTouchHelper(itemTouched).attachToRecyclerView(recyclerView);
      recyclerView.setAdapter(myAdapter);
   }

   public void addNew(View view) {
      TodoDialog dialog=new TodoDialog();
      dialog.show(getSupportFragmentManager(),"todoDialog");
   }

   @Override
   public void setText(String t, String d, int position) {
      list.add(new TODO(t,d,"false"));
      addfdata(new TODO(t,d,"false"));
      myAdapter.notifyDataSetChanged();
   }

   private void addfdata(TODO t) {
      databaseReference.child(databaseReference.push().getKey()).setValue(t);
   }

   private void getfdata(){
      databaseReference.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
            if(list.size()!=0) return;
            for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
            {
               TODO p=dataSnapshot1.getValue(TODO.class);
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
      for(TODO t:list)
         databaseReference.child(databaseReference.push().getKey()).setValue(t);
     }
}