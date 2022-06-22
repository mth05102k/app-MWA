package com.example.some;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class LoginActivity extends AppCompatActivity {

    private TextView register;
    private EditText editTextEmail, editTextPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register = findViewById(R.id.register);
        mAuth = FirebaseAuth.getInstance();
        initView();
        addEventListener();
    }

    private void addEventListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogin();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterUser.class);
                startActivity(intent);
            }
        });
    }

    private void onLogin() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Sai tên tài khoản hoặc mật khẩu",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initView() {
        editTextEmail = findViewById(R.id.input_email);
        editTextPassword = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.signin);
        register = findViewById(R.id.register);
        progressBar = findViewById(R.id.progessBar);
    }
}