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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    }

}