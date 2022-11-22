package com.example.readwritevideointernalstorage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.readwritevideointernalstorage.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeVideoMainDirectory();
            }
        });
        binding.btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readVideoMainDirectory();
            }
        });


    }
    private void writeVideoMainDirectory(){
        Uri uri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.testvideo);
        try {
            OutputStream outputStream=openFileOutput("videoFile.mp4", Context.MODE_PRIVATE);
            InputStream inputStream=getContentResolver().openInputStream(uri);
            byte[] bytes=new byte[1024];
            int ch;
            while((ch=inputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,ch);
            }
            Toast.makeText(MainActivity.this, "Video File written", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void readVideoMainDirectory(){
        File file=new File(getFilesDir(),"videoFile.mp4");
        binding.videoView.setVideoURI(Uri.parse(file.getPath()));
        MediaController controller=new MediaController(MainActivity.this);
        binding.videoView.setMediaController(controller);
        controller.setAnchorView(binding.videoView);
        binding.videoView.start();
    }



    //This Method can be used if you are trying to write to Sub Directory
    private void writeVideoSubDirectory(){
        Uri uri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.testvideo);
        File path=new File(getFilesDir(),"Videos");
        File filename=new File(path,"videoFile.mp4");
        if(!path.exists()){
            path.mkdir();
        }
        try {
            FileOutputStream outputStream=new FileOutputStream(filename,false);
            InputStream inputStream=getContentResolver().openInputStream(uri);
            byte[] bytes=new byte[1024];
            int ch;
            while((ch=inputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,ch);
            }
            Toast.makeText(MainActivity.this, "Video File written", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This Method can be used if you are trying to read from a Sub Directory
    private void readVideoSubDirectory(){
        File file=new File(getFilesDir()+"/Videos/","videoFile.mp4");
        binding.videoView.setVideoURI(Uri.parse(file.getPath()));
        MediaController controller=new MediaController(MainActivity.this);
        binding.videoView.setMediaController(controller);
        controller.setAnchorView(binding.videoView);
        binding.videoView.start();
    }

}