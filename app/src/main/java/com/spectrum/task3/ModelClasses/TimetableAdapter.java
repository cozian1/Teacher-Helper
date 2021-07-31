package com.spectrum.task3.ModelClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.spectrum.task3.Adapter.DayAdapter;
import com.spectrum.task3.Adapter.EventAdapter;
import com.spectrum.task3.R;

import java.util.ArrayList;
import java.util.List;

public class TimetableAdapter extends RecyclerView.Adapter<TimetableAdapter.ViewHolder>{
   List<Mylist> list=new ArrayList<>();
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
      Mylist d=list.get(position);
      holder.title.setText(d.getTitle());
      holder.description.setText(d.getDescription());
      holder.time.setText(d.getDate());
   }

   @Override
   public int getItemCount() {
      return  list.size();
   }

   public void setData(List<Mylist> list) {
      this.list = list;
      notifyDataSetChanged();
   }
}
