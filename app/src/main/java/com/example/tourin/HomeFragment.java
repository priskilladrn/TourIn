package com.example.tourin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.tourin.Adapter.TurEditoAdapter;
import com.example.tourin.Model.Editor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class HomeFragment extends Fragment {
    ImageSlider mainSlider;
    CardView cardViewMuseum,cardViewWaters,cardViewMountain,cardViewDances;
    ScrollView myScrollView;
    String placeId, placeImage, placeName;
    Editor editor;
    RecyclerView rvEditor;
    TurEditoAdapter turEditoAdapter;
    Vector<Editor> editorVector = new Vector<>();
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tourin-839e2-default-rtdb.firebaseio.com/");
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        cardViewMuseum = view.findViewById(R.id.boxMuseum);
        cardViewWaters = view.findViewById(R.id.boxWaters);
        cardViewMountain = view.findViewById(R.id.boxMountain);
        cardViewDances = view.findViewById(R.id.boxDances);
        myScrollView = view.findViewById(R.id.scrollView);
        myScrollView.setVerticalScrollBarEnabled(false);
        myScrollView.setHorizontalScrollBarEnabled(false);
        mainSlider = view.findViewById(R.id.image_slider);

        rvEditor = view.findViewById(R.id.turEditorPick);

        final List<SlideModel> remoteimages = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference().child("Slider")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren())
                            remoteimages.add(new SlideModel(data.child("url").getValue().toString(), ScaleTypes.FIT));

                        mainSlider.setImageList(remoteimages, ScaleTypes.FIT);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            if (databaseReference != null){
                editorVector = new Vector<>();
                editorVector.clear();
            databaseReference.child("Recommend").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot data2: dataSnapshot.getChildren()){
                        placeId = data2.getKey().toString();
                        placeImage = data2.child("Image").getValue().toString();
                        placeName = data2.child("Name").getValue().toString();

                        editor = new Editor(placeName,placeImage,placeId);
                        editorVector.add(editor);
                    }
                    turEditoAdapter = new TurEditoAdapter(getContext(),editorVector);
                    rvEditor.setAdapter(turEditoAdapter);
//                    rvEditor.setLayoutManager(new LinearLayoutManager(getContext()));
                    LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    rvEditor.setLayoutManager(horizontalLayoutManager);
                    turEditoAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            }



        cardViewMuseum.setOnClickListener(v -> {
            Intent museum = new Intent(getContext(), MuseumActivity.class);
            startActivity(museum);
        });

        cardViewWaters.setOnClickListener(v -> {
            Intent waters = new Intent(getContext(), WaterActivity.class);
            startActivity(waters);
        });
        cardViewMountain.setOnClickListener(v -> {
            Intent mountain = new Intent(getContext(), MountainActivity.class);
            startActivity(mountain);
        });

        cardViewDances.setOnClickListener(v -> {
            Intent dance = new Intent(getContext(), DancesActivity.class);
            startActivity(dance);
        });


    return view;
    }
}