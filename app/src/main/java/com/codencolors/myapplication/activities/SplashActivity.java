package com.codencolors.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.codencolors.myapplication.R;
import com.codencolors.myapplication.fragments.HomeFragment;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private static final int READ_PERMISSION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        Runnable delayTask = new Runnable() {
            @Override
            public void run() {
                if (ActivityCompat.checkSelfPermission(getApplication(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    startActivity(new Intent(getApplication(), HomeActivity.class));
                    finish();
                }
                else
                    requestForRead();
            }
        };
        mainThreadHandler.postDelayed(delayTask, 1000);
    }

    private void requestForRead(){
        ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: " + requestCode);
        switch (requestCode) {
            case READ_PERMISSION:
                Log.d(TAG, "onRequestPermissionsResult: " + READ_PERMISSION);
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(this, HomeActivity.class));
                    finish();
                }
                else
                    requestForRead();
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
