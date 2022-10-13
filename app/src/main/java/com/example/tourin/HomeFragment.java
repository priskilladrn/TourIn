package com.example.tourin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    Button btnTodetail;
    CardView cardViewMuseum,cardViewWaters,cardViewMountain,cardViewCeremonies,cardViewDances,cardViewFoods;
    ScrollView myScrollView;

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