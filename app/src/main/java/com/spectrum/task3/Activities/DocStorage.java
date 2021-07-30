package com.spectrum.task3.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.spectrum.task3.ModelClasses.TODO;
import com.spectrum.task3.ModelClasses.UploadFile;
import com.spectrum.task3.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DocStorage extends AppCompatActivity {

   String displayName = " ";
   final static int PICK_PDF_CODE = 2342;
   List<UploadFile> uploadList;
   ListView listView;

   StorageReference storageReference;
   DatabaseReference databaseReference;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_doc_storage);
      uploadList=new ArrayList<>();

      storageReference = FirebaseStorage.getInstance().getReference();
      databaseReference = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("Uploads");

      getfdata();
      listView = (ListView) findViewById(R.id.listview);


      listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
         @Override
         public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            UploadFile upload = uploadList.get(position);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(upload.getUri()));
            startActivity(intent);
            return true;
         }
      });
      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            UploadFile upload = uploadList.get(position);
            Intent ox=new Intent(DocStorage.this,PdfView.class);
            ox.putExtra("name",upload.getName());
            startActivity(ox);
         }
      });
   }


   private void getPDF() {
      //for greater than lolipop versions we need the permissions asked on runtime
      //so if the permission is not available user will go to the screen to allow storage permission
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
         Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
               Uri.parse("package:" + getPackageName()));
         startActivity(intent);
         return;
      }

      //creating an intent for file chooser
      Intent intent = new Intent();
      intent.setType("application/pdf");
      intent.setAction(Intent.ACTION_GET_CONTENT);
      startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_CODE);
   }


   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      //when the user choses the file
      if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
         //if a file is selected
         if (data.getData() != null) {
            //uploading the file
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();

            if (uriString.startsWith("content://")) {
               Cursor cursor = null;
               try {
                  cursor = this.getContentResolver().query(uri, null, null, null, null);
                  if (cursor != null && cursor.moveToFirst()) {
                     displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                  }
               } finally {
                  cursor.close();
               }
            } else if (uriString.startsWith("file://")) {
               displayName = myFile.getName();
            }
            uploadFile(data.getData());
         }else{
            Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
         }
      }
   }


   private void uploadFile(Uri data) {
      //progressBar.setVisibility(View.VISIBLE);
      StorageReference sRef = storageReference.child("Uploads/" + displayName );
      sRef.putFile(data)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @SuppressWarnings("VisibleForTests")
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                  //progressBar.setVisibility(View.GONE);
                  Toast.makeText(DocStorage.this, "File Uploaded", Toast.LENGTH_SHORT).show();

                  sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                     @Override
                     public void onSuccess(Uri uri) {
                        UploadFile upload = new UploadFile(displayName, uri.toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(upload);
                     }});
               }
            })
            .addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception exception) {
                  Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
               }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
               @SuppressWarnings("VisibleForTests")
               @Override
               public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                  int progress = (int) ((100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount());
                  Toast.makeText(DocStorage.this, progress+"Uploading...", Toast.LENGTH_SHORT).show();
               }
            });

   }
   private void getfdata(){
      databaseReference.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
            uploadList.clear();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
               UploadFile upload = postSnapshot.getValue(UploadFile.class);
               uploadList.add(upload);
            }

            String[] uploads = new String[uploadList.size()];

            for (int i = 0; i < uploads.length; i++) {
               uploads[i] = uploadList.get(i).getName();
            }

            //displaying it to list
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, uploads);
            listView.setAdapter(adapter);
         }

         @Override
         public void onCancelled(DatabaseError databaseError) {

         }
      });
   }


   public void selectFile(View view) {
      getPDF();
   }
}