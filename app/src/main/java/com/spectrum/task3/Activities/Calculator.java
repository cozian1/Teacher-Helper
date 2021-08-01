package com.spectrum.task3.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.spectrum.task3.R;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class Calculator extends AppCompatActivity {
   EditText inputTxt, outPuttxt;
   Button btn_c;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_calculator);
      getSupportActionBar().setTitle("Calculator");

      outPuttxt = findViewById(R.id.outPuttxt);
      inputTxt = findViewById(R.id.inputTxt);
      btn_c = findViewById(R.id.clear);

      inputTxt.setMovementMethod(new ScrollingMovementMethod());
      outPuttxt.setMovementMethod(new ScrollingMovementMethod());

      btn_c.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            String s=outPuttxt.getText().toString();
            if(!s.equals(""))
               s=s.substring(0,s.length()-1);
            outPuttxt.setText(s);
         }

      });
      btn_c.setOnLongClickListener(new View.OnLongClickListener() {
         @Override
         public boolean onLongClick(View v) {
            inputTxt.setText("");
            outPuttxt.setText("");
            return true;
         }
      });

   }

   public void koko(View view) {
      Button t=(Button)view;
      String s =outPuttxt.getText().toString()+t.getText().toString();
      outPuttxt.setText(s);
   }
   public void avg(View view) {
      String s=outPuttxt.getText().toString(),out="";
      int c=0;
      long sum=0;
      for(String temp:s.split("[^0-9]")){
         c++;
         sum+=Long.parseLong(temp);
      }
      sum/=c;
      out+=sum;
      s="("+s+")"+"/"+c;
      inputTxt.setText(s);
      outPuttxt.setText(out);
   }


   public void equal(View view) {
      String s=outPuttxt.getText().toString(),out="";
      out=s.replaceAll("%","/100*");
      if(s.equals("")) return;
      Context rhino= Context.enter();
      rhino.setOptimizationLevel(-1);
      try {
         Scriptable scriptable=rhino.initStandardObjects();
         out=rhino.evaluateString(scriptable,out,"Javsscript",1,null).toString();
      }catch (Exception e){
         outPuttxt.setText("Invalid data");
      }
      if(s.equals(out.replaceAll("\100*","%"))) return;
      inputTxt.setText(s);
      outPuttxt.setText(out);
   }
}