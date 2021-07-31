package com.spectrum.task3.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.spectrum.task3.Activities.DocStorage;
import com.spectrum.task3.Activities.PdfView;
import com.spectrum.task3.ModelClasses.UploadFile;
import com.spectrum.task3.R;

import java.util.ArrayList;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder>{
   List<UploadFile> list=new ArrayList<>();
   Context context;

   public class ViewHolder extends RecyclerView.ViewHolder{
      private TextView title;
      CardView parent;
      ImageButton Dbtn;
      public ViewHolder(@NonNull View itemView) {
         super(itemView);
         title=itemView.findViewById(R.id.uploadtitle);
         parent=itemView.findViewById(R.id.uploadparent);
         Dbtn=itemView.findViewById(R.id.download);
      }
   }

   @NonNull
   @Override
   public FileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_uploadfile,parent,false);
      ViewHolder holder=new ViewHolder(view);
      return holder;
   }

   @Override
   public void onBindViewHolder(@NonNull FileAdapter.ViewHolder holder, int position) {
      UploadFile u=list.get(position);
      holder.title.setText(u.getName());
      holder.Dbtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(u.getUri()));
            context.startActivity(intent);
         }
      });
      holder.parent.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent ox=new Intent(context, PdfView.class);
            ox.putExtra("name",u.getName());
            context.startActivity(ox);
         }
      });
   }

   public int getItemCount() {
      return  list.size();
   }

   public void setData(List<UploadFile> list,Context c) {
      this.list = list;
      context=c;
      notifyDataSetChanged();
   }


}
