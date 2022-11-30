package com.example.writevideoexternalstorage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;

import com.example.writevideoexternalstorage.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private File MAIN_PATH=new File(Environment.getExternalStorageDirectory().getPath(),"/Movies/MyVideos/");
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},100);
            }
        }
        binding.btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeVideo();
            }
        });
        binding.btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readVideo();
            }
        });



    }
    private void writeVideo(){
        File fileName=new File(MAIN_PATH,"TestVideo.mp4");
        Uri uri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.testvideo);
        if(!MAIN_PATH.exists()){
            MAIN_PATH.mkdir();
        }
        try {
            FileOutputStream outputStream=new FileOutputStream(fileName);
            InputStream inputStream=getContentResolver().openInputStream(uri);
            byte[] bytes=new byte[1024];
            int ch;
            while((ch=inputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,ch);
            }
            inputStream.close();
            outputStream.close();
            Toast.makeText(this, "Video File Written", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error: "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void readVideo(){
        File fileName=new File(MAIN_PATH,"TestVideo.mp4");
        binding.videoPlayer.setVideoURI(Uri.parse(fileName.getPath()));
        MediaController controller=new MediaController(this);
        binding.videoPlayer.setMediaController(controller);
        controller.setAnchorView(binding.videoPlayer);
        binding.videoPlayer.start();

        /*Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(fileName.getPath()),"video/*");
        startActivity(intent);*/

    }
}