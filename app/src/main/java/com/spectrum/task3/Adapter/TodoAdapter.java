package com.spectrum.task3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.spectrum.task3.Activities.Todo;
import com.spectrum.task3.Dialog.TodoDialog;
import com.spectrum.task3.ModelClasses.TODO;
import com.spectrum.task3.R;

import java.io.Externalizable;
import java.io.Serializable;
import java.util.*;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
   private List<TODO> list=new ArrayList<>();


   public class ViewHolder extends RecyclerView.ViewHolder{
      private TextView head,description;
      ToggleButton done;
      CardView parent;
      public ViewHolder(@NonNull View itemView) {
         super(itemView);
         head=itemView.findViewById(R.id.head);
         description=itemView.findViewById(R.id.description);
         done=itemView.findViewById(R.id.done);
         parent=itemView.findViewById(R.id.parent);
      }
   }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_todo,parent,false);
      ViewHolder holder=new ViewHolder(view);
      return holder;
   }

   @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      holder.head.setText(list.get(position).getTitle());
      holder.description.setText(list.get(position).getDescription());
      if(list.get(position)==null) return;
      if(list.get(position).getF().equals("true")){
         holder.parent.setCardBackgroundColor(Color.parseColor("#E8E8E8"));
         holder.done.setChecked(true);
      }else{
         holder.parent.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
         holder.done.setChecked(false);
      }
      holder.done.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            list.get(position).setF((list.get(position).getF().equals("true"))? "false":"true");
            TODO t=list.get(position);
            list.remove(position);
            if(!t.getF().equals("true")){
               list.add(list.size(),t);
            }else{
               list.add(0,t);
            }
            notifyDataSetChanged();
         }
      });
   }

   @Override
   public int getItemCount() {
      return  list.size();
   }

   public void setData(List<TODO> list) {
      this.list = list;
      notifyDataSetChanged();
   }
}
