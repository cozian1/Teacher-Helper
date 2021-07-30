package com.spectrum.task3.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
 
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.spectrum.task3.R;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Pdfmaker extends AppCompatActivity {

   Button save;
   EditText text;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_pdfmaker);
      text=(EditText)findViewById(R.id.text);
      if(getSupportActionBar()!=null){
         getSupportActionBar().hide();
      }
   }
   public void save(View v) {
      if(Build.VERSION.SDK_INT> Build.VERSION_CODES.M)
      {
         if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
         {
            String[] parmission={Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(parmission,1000);
         }
         else savepdf();
      }
      else savepdf();
   }
   private  void savepdf()
   {
      Document doc=new Document();
      String mfile=new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
      String mfilepath= Environment.getExternalStorageDirectory()+"/"+mfile+".pdf";
      try{
         PdfWriter.getInstance(doc,new FileOutputStream(mfilepath));
         doc.open();
         String mtext=text.getText().toString();
         doc.add(new Paragraph(mtext));
         doc.close();
         Toast.makeText(this, ""+mfile+".pdf"+" is saved to "+mfilepath, Toast.LENGTH_SHORT).show();
      }
      catch (Exception e)
      {
         Toast.makeText(this,"This is Error msg : " +e.getMessage(), Toast.LENGTH_SHORT).show();
      }

   }

   @Override
   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
      switch (requestCode) {
         case 1000:
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               savepdf();
            } else Toast.makeText(this, "parmission denied..", Toast.LENGTH_SHORT).show();
      }
   }

   public void reset(View view) {
      text.setText("");
   }
}