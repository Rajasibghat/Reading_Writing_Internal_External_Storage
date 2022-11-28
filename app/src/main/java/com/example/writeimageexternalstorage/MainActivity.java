package com.example.writeimageexternalstorage;

import static com.example.writeimageexternalstorage.utils.Constants.MAIN_PATH;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.example.writeimageexternalstorage.databinding.ActivityMainBinding;
import com.example.writeimageexternalstorage.utils.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(ActivityCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
        }

        binding.btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeImage();
            }
        });

        binding.btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap=readImage();
                if(bitmap!=null){
                    binding.resultImage.setImageBitmap(bitmap);
                }else{
                    Toast.makeText(MainActivity.this, "Image Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void writeImage(){
        File fileName=new File(MAIN_PATH,"TestImage.jpg");
        if(!MAIN_PATH.exists()){
            MAIN_PATH.mkdir();
        }
        try {
            FileOutputStream outputStream=new FileOutputStream(fileName);
            Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.test_image);
            Boolean result=bitmap.compress(Bitmap.CompressFormat.JPEG,85,outputStream);
            if(result){
                Toast.makeText(this, "Image Written", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Image Not Written", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Error: "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private Bitmap readImage(){
        Bitmap bitmap=null;
        File fileName=new File(MAIN_PATH,"TestImage.jpg");
        try {
            FileInputStream inputStream=new FileInputStream(fileName);
            bitmap=BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Error: "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return bitmap;
    }
}