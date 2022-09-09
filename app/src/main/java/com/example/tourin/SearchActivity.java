package com.example.tourin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tourin.Adapter.SearchAdapter;
import com.example.tourin.Model.Place;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class SearchActivity extends AppCompatActivity {
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tourin-839e2-default-rtdb.firebaseio.com/");
    Vector<Place> list;
    RecyclerView rv;
    ImageView btnBackSearch;

    SearchView searchView;
    Place place;
    String placeId, placeImage, placeName, placeLocation;
    String category[] = {"Museum", "Dances", "Mountains", "Waters"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        rv = findViewById(R.id.rvSearch);
        searchView = findViewById(R.id.searchView);
        btnBackSearch = findViewById(R.id.btnBackSearch);

        btnBackSearch.setOnClickListener(v -> {
            finish();
        });

        if (databaseReference != null) {
            list = new Vector<>();
            list.clear();
            for (int i = 0; i < 4; i++){
                databaseReference.child("Places").child(category[i]).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data: snapshot.getChildren()){
                            placeId = data.getKey().toString();
                            placeImage = data.child("Image").getValue().toString();
                            placeName = data.child("Name").getValue().toString();
                            placeLocation = data.child("Location").getValue().toString();

                            Log.wtf("lokasi + nama", placeName + " + " + placeLocation);
                            place = new Place(placeLocation, placeName, placeImage, placeId);
                            list.add(place);
                        }
                        SearchAdapter searchAdapter = new SearchAdapter(SearchActivity.this,list);
                        searchAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(SearchActivity.this, "error.getMessage()", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        if(searchView != null && searchView.toString().length() > 0){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.wtf("isi text field", newText);
                    search(newText);
                    return false;
                }
            });
        }
    }

    @Override
    public void onPause() {

        // hide the keyboard in order to avoid getTextBeforeCursor on inactive InputConnection
        InputMethodManager inputMethodManager = (InputMethodManager)SearchActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(searchView.getWindowToken(), 0);

        super.onPause();
    }

    private void search(String str){
        Vector<Place> myList = new Vector<>();
        for(Place object : list){
            if(object.getName().toLowerCase().startsWith(str.toLowerCase())){
                myList.add(object);
            }
        }

        SearchAdapter searchAdapter = new SearchAdapter(SearchActivity.this, myList);
        rv.setAdapter(searchAdapter);
        rv.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        searchAdapter.notifyDataSetChanged();
    }
}