package com.example.tourin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    BottomNavigationView bottomNavigation;
  //  MeowBottomNavigation bottomNavigation;
    String username, email, uid;
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://tourin-839e2-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        uid = getIntent().getStringExtra("userId");

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        bottomNavigation = findViewById(R.id.bottomNav);
        bottomNavigation.setOnItemSelectedListener(this);
        switchFragment(new HomeFragment());
        databaseReference.child("Users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username = snapshot.child("name").getValue().toString();
                email = snapshot.child("email").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//            bottomNavigation = findViewById(R.id.bottomNavigationView);
//            bottomNavigation.setOnItemSelectedListener(item -> {
//                switch (item.getItemId()){
//                    case R.id.home:
//                        replaceFragment(new HomeFragment());
//                        break;
//                    case R.id.save:
//                        replaceFragment(new SavedFragment());
//                        break;
//                    case R.id.search:
//                        replaceFragment(new SearchActivity().class);
//                        break;
//                    case R.id.profile:
//                        break;
//                }
//                return  true;
//            });


//        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
//        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_save));
//        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_search));
//        bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_profile));
//
//        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
//            @Override
//            public void onShowItem(MeowBottomNavigation.Model item) {
//                Fragment fragment;
//
//                if(item.getId()==4){
//                    fragment = new ProfileFragment(username, email);
//                }else if(item.getId()==3){
//                    startActivity(new Intent(MainActivity.this, SearchActivity.class));
//                    finish();
//                    fragment = new HomeFragment();
//                }else if(item.getId()==2){
//                    fragment = new SavedFragment();
//                }else{
//                    fragment = new HomeFragment();
//                }
//                loadFragment(fragment);
//            }
//        });

//        bottomNavigation.show(1,true);
//
//        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
//            @Override
//            public void onClickItem(MeowBottomNavigation.Model item) {
//
//            }
//        });
//
//        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
//            @Override
//            public void onReselectItem(MeowBottomNavigation.Model item) {
//
//            }
//        });


    }
    private void switchFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == R.id.home) {
//            switchFragment(new HomeFragment());
//        } else
        if(item.getItemId() == R.id.search) {
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
            finish();
            switchFragment(new HomeFragment());
        } else if(item.getItemId() == R.id.save){
            switchFragment(new SavedFragment());
        } else if(item.getItemId() == R.id.profile){
            switchFragment(new ProfileFragment(username,email));
        } else{
            switchFragment(new HomeFragment());
        }
        return true;
    }
//    private void replaceFragment(Fragment fragment){
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fr);
//        fragmentTransaction.commit();
//    }
//    private void loadFragment(Fragment fragment){
//        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment,null).commit();
//    }
}