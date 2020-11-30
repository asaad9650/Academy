package com.example.academy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.FaceDetector;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Login extends AppCompatActivity {


    private static final String EXTRA_MESSAGE ="" ;
    // FACEBOOK
    FirebaseAuth facebookAuth;
    LoginButton login_with_facebook;
    CallbackManager callbackManager;


    ImageView img;
    Button btn_login;
    TextView txt;
    EditText et_email;
    EditText et_pass;
    TextView forget_pass;
    TextView signup;

    String email;
    String password;
    String str = "";
    private FirebaseAuth mAuth;

    //google code
    SignInButton btn_google;
    private GoogleSignInClient mGoogleSignInClient;
    private String TAG = "LOGIN";
    private FirebaseAuth googleAuth;
    int RC_SIGN_IN = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        String message = intent.getStringExtra(Login.EXTRA_MESSAGE);

        String logout = "logout";
        if (!(message==null) && message.equals(logout))
        {
            //FirebaseAuth.getInstance().signOut();
            googleAuth.signOut();
            facebookAuth.signOut();

        }

        ImageView img = (ImageView) findViewById(R.id.img_logo);
        txt = (TextView) findViewById(R.id.txt_heading);
        forget_pass = (TextView) findViewById(R.id.txt_forget);
        signup = (TextView) findViewById(R.id.txt_signup);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_email = (EditText) findViewById(R.id.et_login_email);
        et_pass = (EditText) findViewById(R.id.et_login_password);
        mAuth = FirebaseAuth.getInstance();

        googleAuth = FirebaseAuth.getInstance();
        btn_google = (SignInButton) findViewById(R.id.btnSignIn_google);

        forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this , ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });


        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                signIn();
            }
            });


            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();

            mGoogleSignInClient = GoogleSignIn.getClient(Login.this, gso);


            //google


            //facebook
            login_with_facebook = (LoginButton) findViewById(R.id.btnSignIn_facebook);
            login_with_facebook.setReadPermissions("email");
            facebookAuth = FirebaseAuth.getInstance();
            callbackManager = CallbackManager.Factory.create();

            login_with_facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signInWithFacebook();
                }
            });


            //User Signup
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Login.this, Signup.class);
                    startActivity(intent);
                    finish();
                }
            });
            //User Login
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_email.getText().toString();
                password = et_pass.getText().toString();
                String varpassword = password;

                if (email.matches(str) || varpassword.matches(str)) {
                    Toast.makeText(Login.this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                } else if (!(email.matches(str) && varpassword.matches(str))) {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Login.this, UploaderActivity.class);
                                        startActivity(intent);
                                        finish();


                                        //updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        //Log.w(Login.this, "signInWithEmail:failure", task.getException());
                                        String err = task.getException().getMessage();
                                        Toast.makeText(Login.this, "" + err, Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }


            }
        });

    }

    //facebook signIn
    public void signInWithFacebook(){
        login_with_facebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel()
            {
                Toast.makeText(Login.this, "Login cancelled.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error)
            {
                Toast.makeText(Login.this, error.getMessage()+".", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Facebook Handler
    public void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        facebookAuth.signInWithCredential(credential)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("ERROR_EDMT", "" + e.getMessage());


                    }
                })

                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        String emailll = authResult.getUser().getEmail();
                        Uri uri = authResult.getUser().getPhotoUrl();
                        String nameeee = uri.toString();
                        String login = "facebook";
                        Toast.makeText(Login.this, "You are signed in as " + emailll, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this , After_login.class);
                        intent.putExtra(Intent.EXTRA_TEXT, emailll);
                        intent.putExtra("id" , nameeee);
                        intent.putExtra("login" , login);
                        //intent.putExtra(Intent.EXTRA_TEXT, nameeee);
                        startActivity(intent);
                        finish();
                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

        //Intent intent = getIntent();
        //String message = intent.getStringExtra(Login.EXTRA_MESSAGE);

        //String logout = "logout";
       // if (!(message==null) && message.equals(logout))
       // {
            //FirebaseAuth.getInstance().signOut();
            //googleAuth.signOut();
          //  facebookAuth.signOut();

        //}



    }
    //Google Handler
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try{

            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            //Intent intent = new Intent( Login.this , After_login.class);
            Toast.makeText(Login.this,"Signed In Successfully",Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        }
        catch (ApiException e)
        {
            Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }
    }
    //Google firebase Auth
    private void FirebaseGoogleAuth(GoogleSignInAccount acct)
    {
        //check if the account is null
        if (acct != null)
        {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            googleAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //Toast.makeText(Login.this, "Successful", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = googleAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        Toast.makeText(Login.this, "Failed.", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                }
            });
        }
        else{
            Toast.makeText(Login.this, "An error occurred, Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(FirebaseUser user)
    {
        //btnSignOut.setVisibility(View.VISIBLE);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account !=  null){
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();

            Intent inte = new Intent(Login.this , After_login.class);
            String login = "google";
            inte.putExtra("login" , login);
            //String emailll = personEmail;
            //inte.putExtra(Intent.EXTRA_TEXT, emailll);
            startActivity(inte);
            finish();

//            Toast.makeText(Login.this,"You are logged in as " + personEmail ,Toast.LENGTH_SHORT).show();


        }
    }


    //Google SignInActivity
    public void signIn ()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();

    }
}


/*


try
                                            {
                                                myRef.child(email).addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                    {
                                                        try
                                                        {
                                                            if (password.equals((dataSnapshot.getValue(insert_user.class).getPassword())))
                                                            {
                                                                Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(Login.this, Complete_registration.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                            et_pass.setError("Incorrect password");
                                                            et_pass.requestFocus();
                                                        } catch (Exception e)
                                                        {
                                                            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError)
                                                    {
                                                        Toast.makeText(Login.this, "Login cancelled", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                            catch (Exception e)
                                            {
                                                Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
 */


/*

mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful())
                                        {
                                            // Sign in success, update UI with the signed-in user's information
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Login.this, Complete_registration.class);
                                            startActivity(intent);
                                            finish();

                                            //updateUI(user);
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            //Log.w(Login.this, "signInWithEmail:failure", task.getException());
                                            String err = task.getException().getMessage();
                                            Toast.makeText(Login.this, "" + err, Toast.LENGTH_SHORT).show();
                                        }

                                        // ...
                                    }
                                });
 */