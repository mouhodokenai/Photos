package com.example.photos;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.io.File;
import java.util.ArrayList;
import com.github.chrisbanes.photoview.PhotoView;


public class Image extends AppCompatActivity {
    private ArrayList<Cell> imagePaths;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        PhotoView image = findViewById(R.id.activity_image);
        Intent intent = getIntent();
        imagePaths = intent.getParcelableArrayListExtra("list");
        currentPosition = intent.getIntExtra("position", 0);

        showImage(currentPosition);
    }

    public void showNextImage(View view) {
        if (currentPosition < imagePaths.size() - 1) {
            currentPosition++;
            showImage(currentPosition);
        }
    }

    public void showPreviousImage(View view) {
        if (currentPosition > 0) {
            currentPosition--;
            showImage(currentPosition);
        }
    }

    private void showImage(int position) {
        PhotoView image = findViewById(R.id.activity_image);
        File imgFile = new File(imagePaths.get(position).getPath());
        if (imgFile.exists()) {
            image.setImageURI(Uri.fromFile(imgFile));
        }
    }
}
