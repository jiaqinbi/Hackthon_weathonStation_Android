package com.njut.hackthon_weatherstation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

public class activity_Welcome extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
              //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);

  /*  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(activity_Welcome.this, LoginActivity.class);
                activity_Welcome.this.startActivity(mainIntent);
                activity_Welcome.this.finish();
            }
        }, 2000);

    }
}