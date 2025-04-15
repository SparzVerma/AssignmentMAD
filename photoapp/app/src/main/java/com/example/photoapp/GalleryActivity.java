package com.example.photoapp;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {

    private static final String TAG = "GalleryActivity";
    private GridView gridView;
    private ImageAdapter adapter;
    private List<Uri> imageUris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        gridView = findViewById(R.id.gridView);
        imageUris = new ArrayList<>();

        Uri folderUri = Uri.parse(getIntent().getStringExtra("folderUri"));
        Log.d(TAG, "Received folder URI: " + folderUri);

        DocumentFile folder = DocumentFile.fromTreeUri(this, folderUri);
        if (folder != null && folder.isDirectory()) {
            for (DocumentFile file : folder.listFiles()) {
                String type = file.getType();
                if (type != null && type.startsWith("image/")) {
                    imageUris.add(file.getUri());
                    Log.d(TAG, "Image added: " + file.getUri());
                }
            }
        } else {
            Log.e(TAG, "Folder is null or not a directory");
        }

        adapter = new ImageAdapter(this, imageUris);
        gridView.setAdapter(adapter);
    }
}

