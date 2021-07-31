package com.spectrum.task3.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.spectrum.task3.Activities.Timetable;
import com.spectrum.task3.ModelClasses.DayList;
import com.spectrum.task3.ModelClasses.Mylist;
import com.spectrum.task3.ModelClasses.TimetableAdapter;
import com.spectrum.task3.R;

import java.util.ArrayList;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder>{
   List<DayList> list=new ArrayList<>();
   Context context;
   TimetableAdapter myAdapter;


   public class ViewHolder extends RecyclerView.ViewHolder{
      private TextView title;
      private RecyclerView recyclerView;
      CardView card;
      public ViewHolder(@NonNull View itemView) {
         super(itemView);
         title=itemView.findViewById(R.id.myday);
         card=itemView.findViewById(R.id.daycard);
         recyclerView=itemView.findViewById(R.id.mylist);
      }
   }

   @NonNull
   @Override
   public DayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_day,parent,false);
      ViewHolder holder=new ViewHolder(view);
      return holder;
   }

   @Override
   public void onBindViewHolder(@NonNull DayAdapter.ViewHolder holder, int position) {
      holder.title.setText(list.get(position).getTitle());
      if(list.get(position).getState().equals("true")){
         holder.card.setCardBackgroundColor(Color.parseColor("#EC407A"));
      }else{
         holder.card.setCardBackgroundColor(Color.parseColor("#5c6bc0"));
      }
      holder.title.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            for(DayList d:list) {
               d.setState("false");
            }
            DayList d=list.get(position);
            d.setState("true");
            list.remove(position);
            list.add(position,d);
            notifyDataSetChanged();
            ((Timetable)context).setday(list.get(position).getTitle());
         }
      });
   }

   @Override
   public int getItemCount() {
      return  list.size();
   }

   public void setData(List<DayList> list,Context c) {
      this.list = list;
      context=c;
      notifyDataSetChanged();
   }
}
