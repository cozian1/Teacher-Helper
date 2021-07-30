package com.spectrum.task3.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.spectrum.task3.Activities.LoginActivity;
import com.spectrum.task3.Activities.SignupActivity;
import com.spectrum.task3.MainActivity;
import com.spectrum.task3.R;

public class SplashActivity extends AppCompatActivity {

   private static final int SPLASH_DISPLAY_LENGTH = 2000;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_splash);
      if(getSupportActionBar()!=null){
         getSupportActionBar().hide();
      }
//      new Thread(new Runnable() {
//         public void run() {
//            try {
//               Thread.sleep(SPLASH_DISPLAY_LENGTH);
//            } catch (InterruptedException e) {
//               e.printStackTrace();
//            }
//            startActivity(new Intent(SplashActivity.this, MainActivity.class));
//            finish();
//         }
//      }).run();

      new Handler().postDelayed(new Runnable(){
         @Override
         public void run() {
            setContentView(R.layout.welcome_screen);
         }
      },SPLASH_DISPLAY_LENGTH);
   }

   public void logmein(View view) {
      startActivity(new Intent(SplashActivity.this, LoginActivity.class));
   }

   public void Register(View view) {
      startActivity(new Intent(SplashActivity.this, SignupActivity.class));
   }
}