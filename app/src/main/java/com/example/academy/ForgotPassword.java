package com.example.academy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.facebook.appevents.suggestedevents.ViewOnClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity
{
    private Toolbar toolbar;
    Button btn_send;
    EditText enter_email;
    FirebaseAuth firebaseAuth;
    TextView txt_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        enter_email = (EditText)findViewById(R.id.enter_email);
        btn_send = (Button)findViewById(R.id.btn_send);
        txt_signup = (TextView)findViewById(R.id.txt_signup);

        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(ForgotPassword.this , Signup.class));
            }
        });



        firebaseAuth = FirebaseAuth.getInstance();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email = enter_email.getText().toString();
                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(ForgotPassword.this,"Please provide your email" , Toast.LENGTH_SHORT).show();
                }

                else
                    {
                        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(ForgotPassword.this, "Email successfully sent", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ForgotPassword.this, Login.class));
                                }

                                else
                                {
                                    String exp = task.getException().getMessage();
                                    Toast.makeText(ForgotPassword.this, "An error occurred " + exp, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ForgotPassword.this, Login.class));
    }
}
