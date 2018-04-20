package com.example.akhila.finalproject;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class UserInsertActivity extends AppCompatActivity {

    ImageView imageView;
    EditText location, address, contact, rate;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;
SharedPreferences sharedPreferences;
    public static final String STORAGE_PATH = "images/";
    public static final String DATABASE_PATH = "users";
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_insert);
    sharedPreferences=getSharedPreferences("tony",MODE_PRIVATE);
        imageView = (ImageView) findViewById(R.id.insertImages);
        location = (EditText) findViewById(R.id.insertLocation);
        address = (EditText) findViewById(R.id.insertAddress);
        contact = (EditText) findViewById(R.id.insertContact);
        String phone=sharedPreferences.getString("phone","");
        contact.setText(phone);
        rate = (EditText) findViewById(R.id.insertRate);
        findViewById(R.id.upload).setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadData(view);
            }
        });
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);
    }

    public void browseImages(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK){
            imageUri = data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getActualImage(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void uploadData(View view){

        if(imageUri != null){
            // insert data

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            final StorageReference reference = storageReference.child(STORAGE_PATH + System.currentTimeMillis() + "." + getActualImage(imageUri));

            reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String LOCATION = location.getText().toString();
                    String ADDRESS = address.getText().toString();
                    String CONTACT = contact.getText().toString();
                    String RATE = rate.getText().toString();

                    Person person = new Person(LOCATION,ADDRESS,CONTACT,RATE,taskSnapshot.getDownloadUrl().toString());

                    String id = databaseReference.push().getKey();

                    databaseReference.child(id).setValue(person);

                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Data uploaded",Toast.LENGTH_LONG).show();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"check"+e.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @SuppressWarnings("VisibleForTests")
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double totalProgress = (100*taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded % " + (int)totalProgress);
                        }
                    });


        } else {
            // show message
            Toast.makeText(getApplicationContext(),"Please select data first",Toast.LENGTH_LONG).show();
        }

    }

    public void viewAllData(View view){
        Intent intent = new Intent(UserInsertActivity.this, ViewDataActivity.class);
        startActivity(intent);
    }
}


