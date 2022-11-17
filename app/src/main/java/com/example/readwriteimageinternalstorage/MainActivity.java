package com.example.readwriteimageinternalstorage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.readwriteimageinternalstorage.databinding.ActivityMainBinding;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity{
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnWriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeMainDirectory();
            }
        });
        binding.btnReadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readMainDirectory();
            }
        });
    }
    private void writeMainDirectory(){
        try {
            OutputStream outputStream=openFileOutput("abc.jpg", Context.MODE_PRIVATE);
            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.image_file);
            Boolean resultTrue=bitmap.compress(Bitmap.CompressFormat.JPEG,85,outputStream);
            if(resultTrue){
                Toast.makeText(this, "Image Written", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Image Not Written", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Error: "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    private void readMainDirectory(){
        try {
            InputStream inputStream=openFileInput("abc.jpg");
            Bitmap bitmap=BitmapFactory.decodeStream(inputStream);
            binding.imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Image Not Found", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    //This method is used to read Image from Sub Directory
    private void readSubDirectory(){
        File path=new File(getFilesDir(),"Images");
        File fileName=new File(path,"abc.jpg");
        try {
            FileInputStream inputStream=new FileInputStream(fileName);
            Bitmap bitmap=BitmapFactory.decodeStream(inputStream);
            binding.imageView.setImageBitmap(bitmap);
            inputStream.close();
        } catch (IOException e) {
            Toast.makeText(this, "No such directory", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    //This method is used to write Image to Sub Directory
    private void writeSubDirectory(){
        File path=new File(getFilesDir(),"Images");
        File filename=new File(path,"abc.jpg");
        if(!path.exists()){
            path.mkdir();
        }
        try {
            FileOutputStream outputStream=new FileOutputStream(filename,false);
            Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.image_file);
            Boolean result=bitmap.compress(Bitmap.CompressFormat.JPEG,85,outputStream);
            if(result){
                Toast.makeText(MainActivity.this, "Image Written", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this, "Image Not Written", Toast.LENGTH_SHORT).show();
            }
        }catch (FileNotFoundException e) {
            Toast.makeText(MainActivity.this, "Error:"+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


}