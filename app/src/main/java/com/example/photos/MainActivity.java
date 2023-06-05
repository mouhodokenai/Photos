package com.example.photos;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
        List<Cell> allFilesPaths;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
                } else {
                        showImages();
                }
        }
        private void showImages() {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Images/";
                allFilesPaths = new ArrayList<>();
                allFilesPaths = listAllFiles(path);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.gallery);
                recyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
                recyclerView.setLayoutManager(layoutManager);

                //оптимизация
                recyclerView.setItemViewCacheSize(20);
                recyclerView.setDrawingCacheEnabled(true);
                recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

                ArrayList<Cell> cells = prepareDate();
                MyAdapter adapter = new MyAdapter(getApplicationContext(), cells);
                recyclerView.setAdapter(adapter);
        }

        /**
         * Подготовка изображений для списка
         *
         * @return список
         */
        private ArrayList<Cell> prepareDate() {
                ArrayList<Cell> allImages = new ArrayList<>();
                for (Cell c : allFilesPaths) {
                        Cell cell = new Cell();
                        cell.setTitle(c.getTitle());
                        cell.setPath(c.getPath());
                        allImages.add(cell);
                }
                return allImages;
        }


        /**
         * Загружает список файлов из папки
         *
         * @param pathName имя папки
         * @return список
         */
        private List<Cell> listAllFiles(String pathName) {
                List<Cell> allFiles = new ArrayList<>();
                File file = new File(pathName);
                File[] files = file.listFiles();
                if (files != null) {
                        for (File f : files) {
                                Cell cell = new Cell();
                                cell.setTitle(f.getName());
                                cell.setPath(f.getAbsolutePath());
                                allFiles.add(cell);
                        }
                }
                return allFiles;
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                if (requestCode == 1000) {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                                showImages();
                        } else {
                                Toast.makeText(this, "Разрешения на чтение нет", Toast.LENGTH_SHORT).show();
                                finish();
                        }
                }
        }
}
