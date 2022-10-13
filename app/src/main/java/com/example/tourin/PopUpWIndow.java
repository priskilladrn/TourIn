package com.example.tourin;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class PopUpWIndow extends Activity {
    private ImageView ivClosePopup;
    private EditText edtEmailPopup;
    private Button btnResetPopup;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window);

        init();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager(). getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.8));

        ivClosePopup.setOnClickListener( v -> {
            finish();
        });

        btnResetPopup.setOnClickListener(v -> {
            String email = edtEmailPopup.getText().toString().trim();
            Log.wtf("email reset", email);
            FirebaseAuth.getInstance().setLanguageCode("en");
            FirebaseAuth.getInstance()
                    .sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(PopUpWIndow.this, "Email for reset password has been sent", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(PopUpWIndow.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });
    }

    private void init() {
        ivClosePopup = findViewById(R.id.ivClosePopup);
        edtEmailPopup = findViewById(R.id.edtEmailPopup);
        btnResetPopup = findViewById(R.id.btnResetPopup);
    }
}
