package com.spectrum.task3.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.spectrum.task3.R;

public class POC extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_p_o_c);
   }

   public void call(View view) {
      ImageButton b=(ImageButton) view;
      Uri u= Uri.parse("tel:"+b.getTag().toString());
      startActivity(new Intent(Intent.ACTION_DIAL,u));
   }

   public void whats(View view) {
      ImageButton b=(ImageButton) view;
      Uri u= Uri.parse("https://api.whatsapp.com/send?phone="+b.getTag().toString());
      startActivity(new Intent(Intent.ACTION_VIEW,u));
   }
}