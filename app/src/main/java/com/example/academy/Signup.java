package com.example.academy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.nio.file.Files;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {


    FirebaseAuth mAuth;
    String email;
    String pass;
    String user_name;
    String str = "";


    ImageView img;
    TextView txt_signup;
    EditText et_email;
    EditText et_pass;
    EditText et_cnfPass;
    EditText et_username;
    Button btn_signup;
    TextView forget_pass;
    TextView signin;
    String cnf_pass;
    int len;

    private DatabaseReference rootRef,userRef;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        img = (ImageView)findViewById(R.id.signup_logo);
        txt_signup = (TextView)findViewById(R.id.txt_heading);
        et_email = (EditText)findViewById(R.id.et_email);
        et_pass = (EditText)findViewById(R.id.et_password);
        et_cnfPass = (EditText)findViewById(R.id.et_confirm_password);
        et_username = (EditText)findViewById(R.id.et_username);
        signin = (TextView)findViewById(R.id.txt_signin);
        btn_signup = (Button)findViewById(R.id.btn_signup);



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent( Signup.this , Login.class);
                startActivity(intent);
                finish();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {




                email = et_email.getText().toString();

                pass = et_pass.getText().toString();
                cnf_pass = et_cnfPass.getText().toString();
                user_name = et_username.getText().toString();


                if (user_name.matches(str) || email.matches(str) || pass.matches(str) || cnf_pass.matches(str))
                {
                    Toast.makeText(Signup.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                }








                else if (! ( email.matches(str) && pass.matches(str) && cnf_pass.matches(str) && user_name.matches(str) ))
                {

                    if (( pass.matches(cnf_pass) ))
                    {

                        if(pass.length()<6)
                        {
                            Toast.makeText(Signup.this , "Password should  be atleast 6 characters." , Toast.LENGTH_SHORT).show();
                        }

                        else
                            {
                                createUser();
                        }

/*firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener() {

                        @Override
                        public void onComplete(@NonNull Task task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(MainActivity.this.getApplicationContext(),
                                    "SignUp unsuccessful: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                            {

                            Toast.makeText(MainActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                            //   startActivity(new Intent(MainActivity.this, UserActivity.class));
                        }
                    }
                        });*/
                    }

                    else
                    {
                        Toast.makeText(Signup.this , "Password Doesn't match." , Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(Signup.this, "Error.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    public void createUser()
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("registered_users");

        rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child("registered_users");


        Query queries = userRef.orderByChild("user_name").equalTo(user_name);
       // insert_user insert = new insert_user();
       // insert.setUser_name(user_name);
        //insert.setEmail(email);
        //insert.setPassword(pass);
       // myRef.child(user_name).setValue(insert);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists())
                {
                    String regex = "(.)*(\\d)(.)*";
                    String string = user_name;
                    Pattern pattern = Pattern.compile(regex);
                    boolean containsNumber = pattern.matcher(string).matches();
                    if (containsNumber)
                    {
                        mAuth.createUserWithEmailAndPassword(email, pass)
                                .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful())
                                        {
                                            String user = mAuth.getCurrentUser().getUid();
                                            insert_user insert = new insert_user();
                                            insert.setUser_name(user_name);
                                            insert.setEmail(email);
                                            insert.setPassword(pass);
                                            myRef.child(user).setValue(insert);
                                            Toast.makeText(Signup.this, "Registered successfully.", Toast.LENGTH_SHORT).show();

                                            et_username.setText("");
                                            et_email.setText("");
                                            et_pass.setText("");
                                            et_cnfPass.setText("");
                                        } else
                                            {
                                            Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    else
                        {
                            et_username.setError("User name must contain 1 or more numbers.");
                            et_username.requestFocus();
                        }
                }
                else {
                    et_username.setError("User name already exist.");
                    et_username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(Signup.this, "Registration cancelled.", Toast.LENGTH_SHORT).show();
            }
        };
        queries.addListenerForSingleValueEvent(eventListener);
    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(Signup.this , Login.class);
        startActivity(intent);
        finish();
    }
}

/*
insert_user insert = new insert_user();
        insert.setUser_name(user_name);
        insert.setEmail(email);
        insert.setPassword(pass);
        myRef.child(user_name).setValue(insert);

        mAuth.createUserWithEmailAndPassword(email, pass)
        .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
@Override
public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
        et_username.setText("");
        et_email.setText("");
        et_pass.setText("");
        et_cnfPass.setText("");
        Toast.makeText(Signup.this, "User created", Toast.LENGTH_SHORT).show();
        } else {
        Toast.makeText(Signup.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
        }
        }
        });*/