package com.example.photoapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int FOLDER_REQUEST_CODE = 300;
    private DocumentFile selectedFolder;
    private Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermissions();

        Button selectFolderButton = findViewById(R.id.selectedFolderButton);
        selectFolderButton.setOnClickListener(v -> selectFolder());

        Button captureButton = findViewById(R.id.captureButton);
        captureButton.setOnClickListener(v -> takePhoto());

        Button galleryButton = findViewById(R.id.galleryButton);
        galleryButton.setOnClickListener(v -> viewGallery());
    }

    private void requestPermissions() {
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA
            }, 1);
        }
    }

    private void selectFolder() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, FOLDER_REQUEST_CODE);
    }

    private void takePhoto() {
        if (selectedFolder == null) {
            Toast.makeText(this, "Please select a folder first.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            DocumentFile photoFile = selectedFolder.createFile("image/jpeg", "photo_" + System.currentTimeMillis());
            if (photoFile != null) {
                photoUri = photoFile.getUri();
                Log.d("MainActivity", "Photo URI: " + photoUri);

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            } else {
                Toast.makeText(this, "Failed to create photo file.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error creating photo file.", Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "Error: ", e);
        }
    }

    private void viewGallery() {
        if (selectedFolder == null) {
            Toast.makeText(this, "Please select a folder first.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, GalleryActivity.class);
        intent.putExtra("folderUri", selectedFolder.getUri().toString());
        Log.d("MainActivity", "Folder URI passed to GalleryActivity: " + selectedFolder.getUri());
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FOLDER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri folderUri = data.getData();
            selectedFolder = DocumentFile.fromTreeUri(this, folderUri);

            if (folderUri != null) {
                getContentResolver().takePersistableUriPermission(
                        folderUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                );
            }

            if (selectedFolder != null && selectedFolder.isDirectory()) {
                Toast.makeText(this, "Folder selected successfully.", Toast.LENGTH_SHORT).show();
                Log.d("MainActivity", "Selected folder: " + selectedFolder.getUri());
            } else {
                Toast.makeText(this, "Selected folder is not valid.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
