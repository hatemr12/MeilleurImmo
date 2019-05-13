package com.example.hp.immob.config;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * Created by hp on 03/08/2017.
 */

public class Constant {

    public static Bitmap convert(String img){
        byte[] b = Base64.decode(img, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }
}
