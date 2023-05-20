package com.myapp.serviceapp.helper;

import android.content.Context;
import android.widget.Toast;

public class Toasty {
    public static void show(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
