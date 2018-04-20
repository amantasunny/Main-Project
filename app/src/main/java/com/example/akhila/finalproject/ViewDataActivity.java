package com.example.akhila.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ViewDataActivity extends AppCompatActivity {

    ListView listView;
    List<Person> list;
    ProgressDialog progressDialog;
    MyAdapter myAdapter;
    private DatabaseReference databaseReference;
    String location;
    public Button mapbutton;
    int count=0;



    @Override
    public void onStart() {
        super.onStart();
        databaseReference = FirebaseDatabase.getInstance().getReference(UserInsertActivity.DATABASE_PATH);

        //Query query = databaseReference.child("users").orderByChild("location").equalTo("Enter Location");
        Log.w("location", "location is ");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                list.clear();
                Log.w("location", "location is ");

                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    Person person = snap.getValue(Person.class);


                    list.add(person);
                    Log.w("location", "location is " + person.getLocation());


                }
                myAdapter = new MyAdapter(ViewDataActivity.this, R.layout.activity_data_items, list);
                listView.setAdapter(myAdapter);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdata);
        final EditText loc = (EditText)findViewById(R.id.loc_searching);
        // final EditText rate = (EditText)findViewById(R.id.loc_ratesearching );
        findViewById(R.id.searchbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                location=loc.getText().toString();

                databaseReference = FirebaseDatabase.getInstance().getReference(UserInsertActivity.DATABASE_PATH);

                //Query query = databaseReference.child("users").orderByChild("location").equalTo("Enter Location");
                Log.w("location","location is ");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        progressDialog.dismiss();
                        list.clear();
                        Log.w("location","location is ");

                        for(DataSnapshot snap : dataSnapshot.getChildren()){
                            Person person = snap.getValue(Person.class);

                            if(person.getLocation().equals(location)) {
                                list.add(person);
                                Log.w("location", "location is " + person.getLocation());
                                count++;
                            }
                    if (person.getRate().equals(location)) {
                                list.add(person);
                                count++;
                            }
                     if (person.getAddress().equals(location)) {
                                list.add(person);
                                count++;
                            }
                            Log.i("count",count+"");


                        }
                        if(count==0) {
                            Toast.makeText(getApplicationContext(),"Result not found",Toast.LENGTH_SHORT).show();

                        }
                        myAdapter = new MyAdapter(ViewDataActivity.this,R.layout.activity_data_items,list);
                        listView.setAdapter(myAdapter);

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        listView = (ListView) findViewById(R.id.list1);







        list = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        // progressDialog.setTitle("Fetching Please wait");
//        progressDialog.show();
        init();


    }

    public void init()
    {
        mapbutton=(Button)findViewById(R.id.mapbutton);
        mapbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mymap=new Intent(ViewDataActivity.this,MapsActivity.class);
                startActivity(mymap);
            }
        });
    }
}