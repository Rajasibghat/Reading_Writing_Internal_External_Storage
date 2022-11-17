package com.example.readwritetextfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.readwritetextfile.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnWriteMainDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=binding.inputText.getText().toString()+"\n";
                OutputStream outputStream=null;
                try {
                    outputStream=openFileOutput("testFile.txt",Context.MODE_APPEND);
                    outputStream.write(text.getBytes());
                    outputStream.close();
                    Toast.makeText(MainActivity.this, "File written", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "File cannot be created", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.btnWriteSubDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=binding.inputText.getText().toString()+"\n";
                File path=new File(getFilesDir(),"TextFilesFolder");
                if(!path.exists()){
                    path.mkdir();
                }
                File fileName=new File(path,"test2file.txt");
                try {
                    FileOutputStream outputStream=new FileOutputStream(fileName,true);
                    outputStream.write(text.getBytes());
                    outputStream.close();
                    Toast.makeText(MainActivity.this, "File written", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "File Not created", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnReadMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputStream inputStream=openFileInput("testFile.txt");
                    int character;
                    StringBuilder stringBuilder=new StringBuilder();
                    while((character=inputStream.read())!=-1){
                        stringBuilder.append((char)character);
                    }
                    inputStream.close();
                    binding.textMainDirectory.setText(stringBuilder.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        binding.btnReadSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File path=new File(getFilesDir(),"TextFilesFolder");
                File fileName=new File(path,"test2file.txt");
                try {
                    FileInputStream inputStream=new FileInputStream(fileName);
                    int character;
                    StringBuilder builder=new StringBuilder();
                    while ((character=inputStream.read())!=-1){
                        builder.append((char)character);
                    }
                    inputStream.close();
                    binding.textSubDirectory.setText(builder.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    //This is another method to read line by line main directory
    private void readMainDirectoryLineByLine(){
        try {
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(openFileInput("testFile.txt")));
            String line;
            StringBuilder builder=new StringBuilder();
            while ((line=bufferedReader.readLine())!=null){
                builder.append(line);
            }
            bufferedReader.close();
            binding.textMainDirectory.setText(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This is another method to read line by line sub directory
    private void readSubDirectoryLineByLine(){
        File path=new File(getFilesDir(),"TextFilesFolder");
        File fileName=new File(path,"test2file.txt");
        try {
            FileInputStream inputStream=new FileInputStream(fileName);
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
            BufferedReader reader=new BufferedReader(inputStreamReader);
            String line;
            StringBuilder builder=new StringBuilder();
            while ((line=reader.readLine())!=null){
                builder.append(line);
            }
            reader.close();
            binding.textSubDirectory.setText(builder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}