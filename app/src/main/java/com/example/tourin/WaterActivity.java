package com.example.tourin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.tourin.Adapter.MuseumAdapter;
import com.example.tourin.Model.Menu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class WaterActivity extends AppCompatActivity {

    String placeId, placeImage, placeName, placeLocation,placeDescription;
    Menu menu;
    RecyclerView placeMuseum;
    MuseumAdapter museumAdapter;
    Vector<Menu> placeMuseumVector = new Vector<>();
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tourin-839e2-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);
        placeMuseum = findViewById(R.id.placeMuseum);
        museumAdapter = new MuseumAdapter(getApplication());
        museumAdapter.notifyDataSetChanged();
        databaseReference.child("Places").child("Waters").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                placeMuseumVector = new Vector<>();
                placeMuseumVector.clear();
                for (DataSnapshot data: snapshot.getChildren()){
                    placeId = data.getKey().toString();
                    placeImage = data.child("Image").getValue().toString();
                    placeName = data.child("Name").getValue().toString();
                    placeLocation = data.child("Location").getValue().toString();
                    placeDescription = data.child("Description").getValue().toString();
                    Log.wtf("data", placeId + ", " + placeLocation + ", " + placeName);
                    menu = new Menu(placeLocation, placeName, placeImage, placeId,placeDescription);
                    placeMuseumVector.add(menu);

                }
                museumAdapter.setMuseum(placeMuseumVector);
                placeMuseum.setAdapter(museumAdapter);
                placeMuseum.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
}