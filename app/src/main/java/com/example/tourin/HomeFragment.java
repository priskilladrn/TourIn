package com.example.tourin;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {
    Button btnTodetail;
    CardView cardViewMuseum,cardViewWaters,cardViewMountain,cardViewCeremonies,cardViewDances,cardViewFoods;

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