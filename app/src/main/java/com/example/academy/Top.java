package com.example.academy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Top extends AppCompatActivity {
    // Toolbar toolbar;
    SearchView searchView;
    TextView dash_user_name;
    CircleImageView dash_user_img;
    Button user_logout , dash_paid_courses , dash_uploaders;
    FirebaseAuth facebookAuth;
    private FirebaseAuth googleAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);

        //BottomNavigationView navigationView = findViewById(R.id.navbtn);
        //navigationView.setSelectedItemId(R.id.toppage);
        user_logout = (Button)findViewById(R.id.btn_dash_logout);
        dash_paid_courses = (Button)findViewById(R.id.dash_paid_courses);
        dash_uploaders = (Button)findViewById(R.id.dash_uploaders);
        dash_user_img = (CircleImageView)findViewById(R.id.dash_img_user);
        dash_user_name = (TextView)findViewById(R.id.dash_user_name);

        googleAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = googleAuth.getCurrentUser();
        facebookAuth = FirebaseAuth.getInstance();
        final Profile profile = Profile.getCurrentProfile();

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        if (profile != null) {
            String nameeee = profile.getName();
            // name_textView.setText(nameeee);
            Uri uri = profile.getProfilePictureUri(200, 200);
            //Picasso.get().load(uri).into(img_view);
            dash_user_name.setText(nameeee);
            Picasso.get().load(uri).into(dash_user_img);

        } else if (profile == null)
        {

            // if (googleSignInAccount != null) {
            String personName = googleSignInAccount.getDisplayName();
            String personGivenName = googleSignInAccount.getGivenName();
            String personFamilyName = googleSignInAccount.getFamilyName();
            String personEmail = googleSignInAccount.getEmail();
            String personId = googleSignInAccount.getId();
            Uri personPhoto = googleSignInAccount.getPhotoUrl();

            String var1 = personEmail;
            String var2 = personName;
            //name_textView.setText(var2);
            String imageUri = personPhoto.toString();

            Picasso.get().load(imageUri).into(dash_user_img);
            dash_user_name.setText(var2);
        }

        //toolbar = (Toolbar)findViewById(R.id.tool_bar);
//        searchView = (SearchView)findViewById(R.id.search_bar);


        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.top_rel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setTitle("");
        }

        user_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                googleAuth.getInstance().signOut();
                facebookAuth.getInstance().signOut();
                googleAuth.signOut();
                facebookAuth.signOut();
                Toast.makeText(Top.this, "Successfully logged out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Top.this , Login.class);
                startActivity(intent);
                finish();
            }
        });

        BottomNavigationView navigationView = findViewById(R.id.navbtn);
        navigationView.setSelectedItemId(R.id.toppage);


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.scrpage) {
                    Intent intent = new Intent(Top.this, Subscription.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.toppage)
                {
 //                   Intent intent = new Intent(Toast.this, Top.class);
   //                 startActivity(intent);
    //                finish();
                    return true;
                } else if (id == R.id.homepage)
                {
                    Intent intent = new Intent(Top.this, After_login.class);
                    startActivity(intent);
                    finish();
                    //                drawer.closeDrawers();
                    return true;
                } else if (id == R.id.savebtn) {
                    Intent intent = new Intent(Top.this, Save.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.search_bar, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();

        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }


/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();

        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }*/

}
        /*NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        View headerView = nav_view.getHeaderView(0);
        TextView nav_username = (TextView) headerView.findViewById(R.id.text_nav_head);
        View imageView = nav_view.getHeaderView(0);
        CircleImageView img_head_ = (CircleImageView) imageView.findViewById(R.id.image_nav_head);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.Open_nav_drawer, R.string.Close_nav_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Toast.makeText(getApplicationContext(), menuItem.getTitle() + " selected", Toast.LENGTH_SHORT).show();
                drawer.closeDrawers();
                return true;
            }
        });

/*
        Profile profile = Profile.getCurrentProfile();

        if (profile != null) {
            String nameeee = profile.getName();
            Uri uri = profile.getProfilePictureUri(200, 200);
            nav_username.setText(nameeee);
            Picasso.get().load(uri).into(img_head_);
        } else if (profile == null) {
            GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            String personName = googleSignInAccount.getDisplayName();
            String personGivenName = googleSignInAccount.getGivenName();
            String personFamilyName = googleSignInAccount.getFamilyName();
            String personEmail = googleSignInAccount.getEmail();
            String personId = googleSignInAccount.getId();
            Uri personPhoto = googleSignInAccount.getPhotoUrl();


            String var1 = personEmail;
            String var2 = personName;
            String imageUri = personPhoto.toString();

            Picasso.get().load(imageUri).into(img_head_);
            nav_username.setText(var2);
        }


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.scrpage) {
                    Intent intent = new Intent(Top.this, Subscription.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.toppage) {
                    drawer.closeDrawers();
                    return true;
                } else if (id == R.id.homepage) {
                    Intent intent = new Intent(Top.this, After_login.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.savebtn) {
                    Intent intent = new Intent(Top.this, Save.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.search_bar, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();

        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);*/
