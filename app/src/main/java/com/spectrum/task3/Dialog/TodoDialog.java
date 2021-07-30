package com.spectrum.task3.Dialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.spectrum.task3.Activities.Todo;
import com.spectrum.task3.ModelClasses.TODO;
import com.spectrum.task3.R;

public class TodoDialog extends AppCompatDialogFragment {
   EditText title,description;
   private DialogListener listener;
   private int position=0;
   AlertDialog.Builder builder;
   LayoutInflater inflater;
   View v;
   public TodoDialog(){
   }

   @NonNull
   @Override
   public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
      builder=new AlertDialog.Builder(getActivity());

      inflater=getActivity().getLayoutInflater();
      v=inflater.inflate(R.layout.activity_todo_dialog,null);

      title=v.findViewById(R.id.titled);
      description=v.findViewById(R.id.descriptiond);

      builder.setView(v).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {

         }
      }).setPositiveButton("Save", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int which) {
            String s1=title.getText().toString();
            String s2=description.getText().toString();
            listener.setText(s1,s2,position);
         }
      });

      return builder.create();
   }

   @Override
   public void onAttach(@NonNull Context context) {
      super.onAttach(context);
      try {
         listener=(DialogListener) context;
      } catch (ClassCastException e){
         throw new ClassCastException(context.toString()+"Forgot to implement");
      }
   }
   public void setData(TODO t,int p){
      title.setText(t.getTitle().toString());
      description.setText(t.getDescription().toString());
      position=p;
   }

   public interface DialogListener{
      void setText(String t,String d,int position);
   }
}