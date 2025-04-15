package com.example.googlesignin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomeActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private TextView tvWelcome;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnLogout = findViewById(R.id.btnLogout);

        // Get User Name
        String userName = getIntent().getStringExtra("USER_NAME");
        tvWelcome.setText("Welcome, " + userName + "!");

        // Google Sign-In Client
        mGoogleSignInClient = GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build());

        // Logout Button
        btnLogout.setOnClickListener(v -> logout());
    }

    private void logout() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        });
    }
}
