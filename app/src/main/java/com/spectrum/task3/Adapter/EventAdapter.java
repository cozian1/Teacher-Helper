package com.spectrum.task3.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.spectrum.task3.Activities.Reminder;
import com.spectrum.task3.ModelClasses.ReminderList;
import com.spectrum.task3.ModelClasses.TODO;
import com.spectrum.task3.R;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{

   List<ReminderList> list=new ArrayList<>();
   public class ViewHolder extends RecyclerView.ViewHolder{
      private TextView title,description,time;
      public ViewHolder(@NonNull View itemView) {
         super(itemView);
         title=itemView.findViewById(R.id.demotitle);
         description=itemView.findViewById(R.id.demodetails);
         time=itemView.findViewById(R.id.demotime);
      }
   }


   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.model_reminder,parent,false);
      ViewHolder holder=new ViewHolder(view);
      return holder;
   }

   @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      String s=list.get(position).getDate().split("!")[1];
      holder.title.setText(list.get(position).getTitle());
      holder.description.setText(list.get(position).getDescription());
      holder.time.setText(s.split(":")[0]+"\n"+s.split(":")[1]);
   }

   @Override
   public int getItemCount() {
      return  list.size();
   }

   public void setData(List<ReminderList> list) {
      this.list = list;
      notifyDataSetChanged();
   }



}
