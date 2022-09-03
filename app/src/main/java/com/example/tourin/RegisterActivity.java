package com.example.tourin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password, email;
    Button btnRegister;
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        tvLogin.setOnClickListener(v -> {
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(i);
        });

        btnRegister.setOnClickListener(v -> {
            validate();
        });
    }

    private void validate(){
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();
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

        //success -> masukin ke firebase
        Log.wtf("sukses register","success!");
    }

    private void init(){
        username = findViewById(R.id.edtNameRegister);
        email = findViewById(R.id.edtEmailRegister);
        password = findViewById(R.id.edtPasswordRegister);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLoginRegister);
        tvLogin.setPaintFlags(tvLogin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
}