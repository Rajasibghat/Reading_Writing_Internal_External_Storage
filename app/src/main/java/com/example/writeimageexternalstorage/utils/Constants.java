package com.example.writeimageexternalstorage.utils;

import android.os.Environment;

import java.io.File;

public class Constants {
    public static final File MAIN_PATH=
            new File(Environment.getExternalStorageDirectory().getPath(),"/Pictures/MyImages/");

}
