package com.example.tourin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourin.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText username, password, email, retypePassword;
    private Button btnRegister;
    private TextView tvLogin;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        mAuth = FirebaseAuth.getInstance();

        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });

        btnRegister.setOnClickListener(v -> {
            validate();
        });
    }

    private void validate(){
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();
        String retypePasswordText = retypePassword.getText().toString();
        String emailText = email.getText().toString();

        if(usernameText.isEmpty()){
            username.setError("username cannot be empty");
            username.requestFocus();
            return;
        }

        if(usernameText.length() > 20){
            username.setError("username invalid");
            username.requestFocus();
            return;
        }

        if(emailText.isEmpty()){
            email.setError("email cannot be empty");
            email.requestFocus();
            return;
        }

        if(!emailText.endsWith(".com")){
            email.setError("email invalid");
            email.requestFocus();
            return;
        }

        if(passwordText.isEmpty()){
            password.setError("password cannot be empty");
            password.requestFocus();
            return;
        }

        if(!passwordText.equals(retypePasswordText)){
            retypePassword.setError("password must be the same");
            retypePassword.requestFocus();
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful() && task.getResult().getUser()!=null) {
                            progressBar.setVisibility(View.VISIBLE);
                            User newUser = new User(usernameText, emailText, passwordText);
                            FirebaseUser user = task.getResult().getUser();
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(user.getUid())
                                    .setValue(newUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                                reload(user.getUid());
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void init(){
        username = findViewById(R.id.edtNameRegister);
        email = findViewById(R.id.edtEmailRegister);
        password = findViewById(R.id.edtPasswordRegister);
        retypePassword = findViewById(R.id.edtRetypeRegister);
        progressBar = findViewById(R.id.progressBarRegister);
        tvLogin = findViewById(R.id.tvLoginRegister);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin.setPaintFlags(tvLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload(currentUser.getUid());
        }
    }

    private void reload(String uid) {
        Log.wtf("user id", uid);
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("userId", uid);
        startActivity(i);
    }
}