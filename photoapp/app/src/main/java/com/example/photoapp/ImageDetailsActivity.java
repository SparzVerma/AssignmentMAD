package com.example.photoapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.Date;

public class ImageDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        String filePath = getIntent().getStringExtra("filePath");
        File file = new File(filePath);

        TextView detailsText = findViewById(R.id.detailsText);
        detailsText.setText("Name: " + file.getName() +
                "\nPath: " + file.getAbsolutePath() +
                "\nSize: " + file.length() / 1024 + " KB" +
                "\nDate Taken: " + new Date(file.lastModified()));

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to delete this image?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        if (file.delete()) {
                            Toast.makeText(this, "Image deleted!", Toast.LENGTH_SHORT).show();

                            // Pass deleted image position back to GalleryActivity
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("deletedImagePath", file.getAbsolutePath());
                            setResult(RESULT_OK, resultIntent);
                            finish(); // Close details activity
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}

