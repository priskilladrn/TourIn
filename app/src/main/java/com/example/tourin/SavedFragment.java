package com.example.tourin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tourin.Adapter.SavedAdapter;
import com.example.tourin.Model.Place;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class SavedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    String placeId, placeImage, placeName, placeLocation;
    Place place;
    RecyclerView placeRv;
    SavedAdapter savedAdapter;
    Vector<Place> placeVector = new Vector<>();
    SwipeRefreshLayout swipeRefreshLayout;

    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tourin-839e2-default-rtdb.firebaseio.com/");

    public SavedFragment() {
        // Required empty public constructor
    }

    public static SavedFragment newInstance(String param1, String param2) {
        SavedFragment fragment = new SavedFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.fragment_saved, container, false);
        placeVector.clear();
        placeRv = view.findViewById(R.id.placeRv);
        savedAdapter = new SavedAdapter(getContext());
        savedAdapter.notifyDataSetChanged();

        enableSwipeToDeleteAndUndo();


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

        savedAdapter.setSaved(placeVector);
        placeRv.setAdapter(savedAdapter);
        placeRv.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }

    public void getData(){
        swipeRefreshLayout.setRefreshing(true);
        placeVector.clear();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        databaseReference.child("Users").child(currentUser.getUid()).child("post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    placeId = dataSnapshot.child("placeId").getValue().toString();
                    placeName = dataSnapshot.child("name").getValue().toString();
                    placeLocation = dataSnapshot.child("region").getValue().toString();
                    placeImage = dataSnapshot.child("imageUrl").getValue().toString();

                    place = new Place(placeLocation, placeName, placeImage, placeId);
                    placeVector.add(place);
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

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final int position = viewHolder.getAdapterPosition();
                String placeId = placeVector.get(position).getPlaceId();
                Log.wtf("testSaveDelete", placeId);
                deleteData(placeId);

                savedAdapter.removeItem(position);
            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(placeRv);
    }

    private void deleteData(String placeId){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        databaseReference.child("Users").child(currentUser.getUid()).child("post").orderByChild("placeId").equalTo(placeId).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //id exist then remove from saved
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        dataSnapshot.getRef().removeValue();
                    }
                    Toast.makeText(getContext(), "Removed from Saved List", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}