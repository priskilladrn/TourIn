package com.example.tourin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment implements View.OnClickListener{
    Button detail;
    private User currUser;

    public HomeFragment(User currUser) {
        this.currUser = currUser;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        detail = view.findViewById(R.id.toDetail);
        detail.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v==detail){
            Intent i = new Intent(v.getContext(), DetailActivity.class);
            //send id di extras
            startActivity(i);
        }
    }
}