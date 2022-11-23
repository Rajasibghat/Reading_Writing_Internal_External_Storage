package com.example.textfileexternalstorage.utils;

import android.os.Environment;

import java.io.File;

public class Constants {
    public static final File MAIN_PATH=
            new File(Environment.getExternalStorageDirectory().getPath(),"/Documents/TextFiles");

}
