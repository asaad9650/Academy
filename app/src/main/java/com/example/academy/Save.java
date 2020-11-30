package com.example.academy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Save extends AppCompatActivity
{
    RecyclerView save_recycler_view;

    private DatabaseReference save_ref;
    private SaveAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);


        BottomNavigationView navigationView = findViewById(R.id.navbtn);
        navigationView.setSelectedItemId(R.id.savebtn);
        save_recycler_view = findViewById(R.id.save_recycler_view);

        save_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        save_recycler_view.setNestedScrollingEnabled(true);


        Profile profile = Profile.getCurrentProfile();
        if (profile==null)
        {
            GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            String user_name = googleSignInAccount.getDisplayName();
            save_ref = FirebaseDatabase.getInstance().getReference().child("saved").child(user_name);
            save_ref.keepSynced(true);

            FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("saved").child(user_name),Post.class)
                    .build();

            adapter = new SaveAdapter(options);
            save_recycler_view.setAdapter(adapter);

        }
        else if (profile!=null)
        {
            String user_name = profile.getName();
            save_ref = FirebaseDatabase.getInstance().getReference().child("saved").child(user_name);
            save_ref.keepSynced(true);

            FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("saved").child(user_name),Post.class)
                    .build();
            adapter = new SaveAdapter(options);
            save_recycler_view.setAdapter(adapter);
        }



        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.save_rel);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setTitle("Saved");
        }


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.scrpage)
                {
                    Intent intent = new Intent(Save.this , Subscription.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                else if (id == R.id.toppage)
                {
                    Intent intent = new Intent(Save.this , Top.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                else if (id == R.id.homepage)
                {
                    Intent intent = new Intent(Save.this , After_login.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                else if (id == R.id.savebtn)
                {
                 //   startActivity(new Intent(Save.this , Save.class));
                    return true;
                }
                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    @Override
    protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }
}