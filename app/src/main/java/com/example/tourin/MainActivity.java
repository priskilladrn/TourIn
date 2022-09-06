package com.example.tourin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    private String uid, name, email, password;
    User currUser;
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tourin-839e2-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get current user info
        uid = getIntent().getStringExtra("userId");

        databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                name = snapshot.child("name").getValue().toString();
                email = snapshot.child("email").getValue().toString();
                password = snapshot.child("password").getValue().toString();
                Log.wtf("currUser", name + " " + email + " " + password);

                currUser = new User(name, email, password);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.wtf("userTest", error.toException());
            }
        });

        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_save));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_search));
        bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_profile));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment;

                if(item.getId()==4){
                    fragment = new ProfileFragment();
                }else if(item.getId()==3){
                    fragment = new SearchFragment();
                }else if(item.getId()==2){
                    fragment = new SavedFragment();
                }else{
                    fragment = new HomeFragment(currUser);
                }
                loadFragment(fragment);
            }
        });

        bottomNavigation.show(1,true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });


    }
    private void loadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment,null).commit();
    }
}