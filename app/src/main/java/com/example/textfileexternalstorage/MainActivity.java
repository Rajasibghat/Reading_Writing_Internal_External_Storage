package com.example.textfileexternalstorage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.textfileexternalstorage.databinding.ActivityMainBinding;
import com.example.textfileexternalstorage.utils.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                100
                );
            }
        }

        binding.btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=binding.inputText.getText().toString();
                if(writeText(message)){
                    Toast.makeText(MainActivity.this, "File Written", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "File Not Written", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=readText();
                if(msg.length()==0){
                    Toast.makeText(MainActivity.this, "File empty or doesn't Exist", Toast.LENGTH_SHORT).show();
                }else{
                    binding.textResult.setText(msg);
                }
            }
        });
    }
    private Boolean writeText(String text){
        if(!Constants.MAIN_PATH.exists()){
            Constants.MAIN_PATH.mkdir();
        }
        File fileName=new File(Constants.MAIN_PATH,"textFile.txt");
        try {
            FileOutputStream outputStream=new FileOutputStream(fileName);
            outputStream.write(text.getBytes());
            outputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    private String readText(){
        StringBuilder text=new StringBuilder();
        try {
            FileInputStream inputStream=new FileInputStream(new File(Constants.MAIN_PATH,"textFile.txt"));
            InputStreamReader streamReader=new InputStreamReader(inputStream);
            BufferedReader reader=new BufferedReader(streamReader);
            String line;
            while((line=reader.readLine())!=null){
                text.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

}