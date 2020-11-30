package com.example.academy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Complete_registration extends AppCompatActivity
{
    TextView txt_complete_registration , txt_userName_check, txt_cnf_email_pass, iddd;
    EditText et_uploader_user_name , et_uploader_email , et_uploader_password;
    Button btn_proceed;
    String uploader_name , uploader_email , uploader_password;
    String empty="";
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_registration);
        txt_complete_registration = (TextView)findViewById(R.id.txt_complete_details);
        txt_userName_check = (TextView)findViewById(R.id.txt_view_user_check);
        txt_cnf_email_pass = (TextView)findViewById(R.id.txt_confirm_pass_email);
        iddd = (TextView)findViewById(R.id.id);
        et_uploader_user_name = (EditText)findViewById(R.id.et_uploader_name);
        et_uploader_email = (EditText)findViewById(R.id.et_uploader_cnf_email);
        et_uploader_password = (EditText)findViewById(R.id.et_uploader_cnf_pass);
        btn_proceed = (Button)findViewById(R.id.btn_proceed);
        firebaseAuth = FirebaseAuth.getInstance();



        final String uid = FirebaseAuth.getInstance().getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("registered_users");
        //DatabaseReference uidRef = myRef.child("user").child(uid);

        btn_proceed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                uploader_email = et_uploader_email.getText().toString();
                uploader_name = et_uploader_user_name.getText().toString();
                uploader_password = et_uploader_password.getText().toString();
                Query queries = myRef.orderByChild("uploader_name").equalTo(uploader_name);

                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (!dataSnapshot.exists())
                        {
                            firebaseAuth.signInWithEmailAndPassword(uploader_email , uploader_password)
                                    .addOnCompleteListener(Complete_registration.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task)
                                        {
                                            if (task.isSuccessful())
                                            {
                                                String idd = FirebaseAuth.getInstance().getUid();
                                                iddd.setText(idd);
                                                String id = FirebaseAuth.getInstance().getUid();
                                                uploader_details details = new uploader_details();
                                                details.setUploader_email(uploader_email);
                                                details.setUploader_name(uploader_name);
                                                details.setUploader_password(uploader_password);
                                                myRef.child(uploader_name).setValue(details);

                                                Toast.makeText(Complete_registration.this, "User Created successfully", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(Complete_registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else
                            {
                                Toast.makeText(Complete_registration.this, "Username already exist, try a new one", Toast.LENGTH_SHORT).show();
                                txt_userName_check.setText("user name Already exist");
                                et_uploader_user_name.setError("user name already exist");
                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {
                        Toast.makeText(Complete_registration.this, "Registration cancelled", Toast.LENGTH_SHORT).show();
                    }
                };
                queries.addListenerForSingleValueEvent(valueEventListener);

/*
                firebaseAuth.signInWithEmailAndPassword(uploader_email , uploader_password)
                        .addOnCompleteListener(Complete_registration.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful())
                                {
                                    String idd = FirebaseAuth.getInstance().getUid();
                                    iddd.setText(idd);
                                    String id = FirebaseAuth.getInstance().getUid();
                                    uploader_details details = new uploader_details();
                                    details.setUploader_email(uploader_email);
                                    details.setUploader_name(uploader_name);
                                    details.setUploader_password(uploader_password);
                                    myRef.child(id).child(uploader_name).setValue(details);

                                    Toast.makeText(Complete_registration.this, "User Created successfully", Toast.LENGTH_SHORT).show();
                                }
                                else
                                    {
                                        Toast.makeText(Complete_registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                        });*/

                ValueEventListener eventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        /*if (!dataSnapshot.exists())
                        {
                            String id = FirebaseAuth.getInstance().getUid();
                            uploader_details details = new uploader_details();
                            details.setUploader_email(uploader_email);
                            details.setUploader_name(uploader_name);
                            details.setUploader_password(uploader_password);
                            myRef.child(id).child(uploader_name).setValue(details);
                            Toast.makeText(Complete_registration.this, "User Created successfully", Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                            Toast.makeText(Complete_registration.this, "User already Exist", Toast.LENGTH_SHORT).show();
                        }*/
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
            }
        });


    }
}
