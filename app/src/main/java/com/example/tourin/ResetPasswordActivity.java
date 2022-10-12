package com.example.tourin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {
    private EditText edtEmailPopup;
    private Button btnResetPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        init();

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
                                Toast.makeText(ResetPasswordActivity.this, "Email for reset password has been sent", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ResetPasswordActivity.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });
    }

    private void init() {
        edtEmailPopup = findViewById(R.id.edtEmailPopup);
        btnResetPopup = findViewById(R.id.btnResetPopup);
    }


}