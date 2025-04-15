package com.example.lottieanimation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    private LottieAnimationView lottieView;
    private Button btnPlay, btnPause, btnResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lottieView = findViewById(R.id.lottieView);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnResume = findViewById(R.id.btnResume);

        // Play Animation
        btnPlay.setOnClickListener(v -> lottieView.playAnimation());

        // Pause Animation
        btnPause.setOnClickListener(v -> lottieView.pauseAnimation());

        // Resume Animation
        btnResume.setOnClickListener(v -> lottieView.resumeAnimation());
    }
}
