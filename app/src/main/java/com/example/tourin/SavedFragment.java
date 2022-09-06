package com.example.tourin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tourin.Adapter.SavedAdapter;
import com.example.tourin.Model.Place;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class SavedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    String imageUrl, name, placeId, region;
    Place places;
    RecyclerView savedRv;
    SavedAdapter savedAdapter;
    Vector<Place> placesVector = new Vector<>();
    SwipeRefreshLayout swipeRefreshLayout;

    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tourin-839e2-default-rtdb.firebaseio.com/");

    public SavedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved, container, false);
        placesVector.clear();
        savedRv = view.findViewById(R.id.savedRv);
        savedAdapter = new SavedAdapter(getContext());
        savedAdapter.notifyDataSetChanged();

        //swipe refresh layout
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);

                //fetch data
                getData();
            }
        });

        savedAdapter.setSaved(placesVector);
        savedRv.setAdapter(savedAdapter);
        savedRv.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    void getData(){
        swipeRefreshLayout.setRefreshing(true);
        placesVector.clear();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        databaseReference.child("Users").child(currentUser.getUid()).child("post").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    imageUrl = dataSnapshot.child("imageUrl").getValue().toString();
                    name = dataSnapshot.child("name").getValue().toString();
                    placeId = dataSnapshot.child("placeId").getValue().toString();
                    region = dataSnapshot.child("region").getValue().toString();

                    places = new Place(region, name, imageUrl, placeId);
                    placesVector.add(places);
                }

                savedAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onRefresh() {
        getData();
    }
}