package com.example.tourin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {
    Button btnTodetail;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnTodetail = view.findViewById(R.id.btnTodetail);

        btnTodetail.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), DetailActivity.class);
            i.putExtra("id", "MU002");
            startActivity(i);
        });


        return view;
    }
}