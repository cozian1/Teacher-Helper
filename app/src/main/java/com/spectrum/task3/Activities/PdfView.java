package com.spectrum.task3.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.spectrum.task3.R;

public class PdfView extends AppCompatActivity {

   StorageReference storageRef;
   FirebaseApp app;
   FirebaseStorage storage;
   PDFView pdfView;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_pdf_view);

      storageRef = FirebaseStorage.getInstance().getReference();
      app = FirebaseApp.getInstance();
      storage = FirebaseStorage.getInstance(app);
      pdfView=(PDFView) findViewById(R.id.pdfview);
      Intent i=getIntent();
      String filename=i.getStringExtra("name");
      getSupportActionBar().setTitle(filename);

      storageRef = storage.getReference().child("Uploads/"+filename);

      storageRef.getStream().addOnSuccessListener(new OnSuccessListener<StreamDownloadTask.TaskSnapshot>() {
         @Override
         public void onSuccess(StreamDownloadTask.TaskSnapshot taskSnapshot) {
            pdfView.fromStream(taskSnapshot.getStream()).load();
         }
      }).addOnFailureListener(new OnFailureListener() {
         @Override
         public void onFailure(@NonNull Exception e) {
            Toast.makeText(PdfView.this, "Fail :"+e.getMessage(), Toast.LENGTH_SHORT).show();
         }
      });


   }
}