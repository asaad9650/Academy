package com.example.academy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    Handler handler;
    Runnable runnable;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




       // printKeyHash();




        imageView = (ImageView) findViewById(R.id.img_main);
        //imageView = (ImageView ) findViewById(R.id.img);
        imageView.animate().alpha(4000).setDuration(0);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent i = new Intent(MainActivity.this , Login.class);
                startActivity(i);
                finish();
                //Intent intent = new Intent(MainActivity.this , Login.class);
                //startActivity(intent)
                //;
                //finish();
            }
        },2000 );
    }
    /*private void printKeyHash()
    {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.academy" , PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures)
            {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA");
                messageDigest.update(signature.toByteArray());
                Log.e("KEYHASH" , Base64.encodeToString(messageDigest.digest(),Base64.DEFAULT));

            }
        }
         catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }*/

}
