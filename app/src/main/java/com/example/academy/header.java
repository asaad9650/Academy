package com.example.academy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class header extends AppCompatActivity {

    LinearLayout linearLayout;
    ImageView imageView;
    TextView name_textView;
    TextView email_textView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        linearLayout = (LinearLayout) findViewById(R.id.ll_linearLayout);
        imageView = (ImageView) findViewById(R.id.img_imageView);
        email_textView = (TextView)findViewById(R.id.email_textView);
        name_textView = (TextView)findViewById(R.id.name_textView);



    }
}
