package com.example.some;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterUser extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etFullName, etAge, etEmail, etPass;
    private TextView banner, registerUser;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth = FirebaseAuth.getInstance();
        banner = findViewById(R.id.banner);
        etFullName = findViewById(R.id.name);
        etAge = findViewById(R.id.age);
        etEmail = findViewById(R.id.email);
        etPass = findViewById(R.id.pass);
        registerUser = findViewById(R.id.registerUser);
        progressBar = findViewById(R.id.progessBar);
        addEventListener();
    }

    private void addEventListener() {
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterUser.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser() {
        String mail = etEmail.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        String fullname = etFullName.getText().toString().trim();
        String age = etAge.getText().toString().trim();

        if (fullname.isEmpty()) {
            etFullName.setError("Nhập thiếu tên");
            etFullName.requestFocus();
            return;
        }

        if (pass.isEmpty()) {
            etPass.setError("Nhập thiếu mật khẩu");
            etPass.requestFocus();
            return;
        }
        if (pass.length() < 6) {
            etPass.setError("Vui lòng nhập đúng định dạng mật khẩu");
            etPass.requestFocus();
            return;
        }
        if (mail.isEmpty()) {
            etEmail.setError("Nhập thiếu mail");
            etFullName.requestFocus();
            return;
        }

        if (age.isEmpty()) {
            etAge.setError("Nhập thiếu tuổi");
            etAge.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest req = new UserProfileChangeRequest
                                    .Builder()
                                    .build();
                            user.updateProfile(req).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful() == false) {
                                        System.err.println("Thiet lap ten nguoi dung that bai");
                                    }

                                    Intent intent = new Intent(RegisterUser.this, MainActivity.class);
                                    startActivity(intent);
                                    finishAffinity();
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            System.err.println(task.getException());
                            Toast.makeText(RegisterUser.this, "Tạo tài khoản thất bại",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}