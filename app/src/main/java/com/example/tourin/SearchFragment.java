package com.example.tourin;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;

import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tourin.Adapter.SavedAdapter;
import com.example.tourin.Adapter.SearchAdapter;
import com.example.tourin.Model.Place;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class SearchFragment extends Fragment {
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tourin-839e2-default-rtdb.firebaseio.com/");
    Vector<Place> list;
    RecyclerView rv;
    SearchView searchView;
    SearchAdapter searchAdapter;
    Place place;

    String placeId, placeImage, placeName, placeLocation;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        rv = view.findViewById(R.id.rvSearch);
        searchView = view.findViewById(R.id.searchView);

        if (databaseReference != null) {
            databaseReference.child("Places").child("Museum").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list = new Vector<>();
                    list.clear();
                    for (DataSnapshot data: snapshot.getChildren()){
                        placeId = data.getKey().toString();
                        placeImage = data.child("Image").getValue().toString();
                        placeName = data.child("Name").getValue().toString();
                        placeLocation = data.child("Location").getValue().toString();

                        Log.wtf("data", placeId + ", " + placeLocation + ", " + placeName);

                        place = new Place(placeLocation, placeName, placeImage, placeId);
                        list.add(place);
                    }
                    SearchAdapter searchAdapter = new SearchAdapter(getContext(),list);
                    searchAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "error.getMessage()", Toast.LENGTH_SHORT).show();
                }
            });
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

        return view;
    }

    @Override
    public void onPause() {

        // hide the keyboard in order to avoid getTextBeforeCursor on inactive InputConnection
        InputMethodManager inputMethodManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

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

        searchAdapter = new SearchAdapter(getContext(), myList);
        rv.setAdapter(searchAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter.notifyDataSetChanged();
    }
}