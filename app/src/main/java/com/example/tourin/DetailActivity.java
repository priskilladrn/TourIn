package com.example.tourin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tourin.Model.Place;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvRegion, tvName, tvDescription;
    ImageView imageView;
    Button start;
    FloatingActionButton floatingActionButton;

    public String region, name, description, imageUrl, PlaceId, Longitude, Audio, Latitude;

    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tourin-839e2-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //init
        tvRegion = findViewById(R.id.detailRegion);
        tvName = findViewById(R.id.detailName);
        tvDescription = findViewById(R.id.detailDescription);
        start = findViewById(R.id.buttonStart);
        start.setOnClickListener(this);
        imageView = findViewById(R.id.detailPic);
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);

        //get id from intent
        PlaceId = getIntent().getStringExtra("id");

        //find data from place Id
        databaseReference.child("Places").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child: snapshot.getChildren()){
                    for(DataSnapshot id : child.getChildren()){
                        String key = id.getKey();
                        Log.wtf("keyhasil", key);

                        if(key.equals(PlaceId)){
                            region = id.child("Location").getValue().toString();
                            name = id.child("Name").getValue().toString();
                            description = id.child("Description").getValue().toString();
                            imageUrl = id.child("Image").getValue().toString();
                            Audio = id.child("Audio").getValue().toString();
                            Latitude = id.child("Latitude").getValue().toString();
                            Longitude = id.child("Longitude").getValue().toString();

                            //set data here
                            setData(region, name, description, imageUrl);
                        }
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    void setData(String region, String name, String description, String imageUrl){
        tvRegion.setText(region);
        tvName.setText(name);
        tvDescription.setText(description);
        Glide.with(this).load(imageUrl).into(imageView);
    }

    @Override
    public void onClick(View v) {
        if(v == floatingActionButton){
            Place places = new Place(region, name, imageUrl, PlaceId);
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = auth.getCurrentUser();
            databaseReference.child("Users").child(currentUser.getUid()).child("post").orderByChild("placeId").equalTo(PlaceId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        //id exist then remove from saved
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            dataSnapshot.getRef().removeValue();
                        }
                        Toast.makeText(DetailActivity.this, "Removed from Saved List", Toast.LENGTH_SHORT).show();
                    }else{
                        //add to saved
                        DatabaseReference reference = databaseReference.child("Users").child(currentUser.getUid()).child("post").push();
                        reference.setValue(places);

                        Toast.makeText(DetailActivity.this, "Added to Saved List", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else if(v == start){
            //when button start is pressed, intent page to audio guidance page
            Intent i = new Intent(DetailActivity.this, AudioGuidance.class);
            //send extras

            //start intent
            this.startActivity(i);
        }
    }
}