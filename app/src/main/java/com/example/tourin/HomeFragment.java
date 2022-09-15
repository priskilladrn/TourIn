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
        cardViewCeremonies = view.findViewById(R.id.boxCeremonies);
        cardViewDances = view.findViewById(R.id.boxDances);
        cardViewFoods = view.findViewById(R.id.boxFoods);


        cardViewMuseum.setOnClickListener(v -> {
            Intent museum = new Intent(getContext(), MuseumActivity.class);
            museum.putExtra("id", "MU001");
            museum.putExtra("id", "MU002");
            startActivity(museum);
        });

        cardViewWaters.setOnClickListener(v -> {
            Intent waters = new Intent(getContext(), WaterActivity.class);
            waters.putExtra("id", "WA001");
            startActivity(waters);
        });
        cardViewMountain.setOnClickListener(v -> {
            Intent mountain = new Intent(getContext(), MountainActivity.class);
            mountain.putExtra("id", "MO001");
            mountain.putExtra("id", "MO002");
            startActivity(mountain);
        });

        cardViewDances.setOnClickListener(v -> {
            Intent dance = new Intent(getContext(), DancesActivity.class);
            dance.putExtra("id", "DA001");
            startActivity(dance);
        });

//        cardViewCeremonies.setOnClickListener(v -> {
//            Intent ceremonies = new Intent(getContext(), CeremoniesActivity.class);
//            ceremonies.putExtra("id", "CE001");
//            startActivity(ceremonies);
//        });
//
//        cardViewFoods.setOnClickListener(v -> {
//            Intent foods = new Intent(getContext(), FoodsActivity.class);
//            foods.putExtra("id", "FO001");
//            startActivity(foods);
//        });


        return view;
    }
}