package com.example.academy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.facebook.Profile;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Subscriber extends AppCompatActivity {

    RecyclerView subscriber_recycler_view;
    private DatabaseReference subscriber_ref;
    private SubscriberAdapter adapter;

    //private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriber);

        BottomNavigationView navigationView = findViewById(R.id.navbtn);
        navigationView.setSelectedItemId(R.id.scrpage);
        subscriber_recycler_view = findViewById(R.id.subscriber_recycler_view);

        subscriber_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        subscriber_recycler_view.setNestedScrollingEnabled(true);

        Bundle extra  = getIntent().getExtras();
        String s = extra.getString("name");


        Profile profile = Profile.getCurrentProfile();
        if (profile==null)
        {
            GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            String user_name = googleSignInAccount.getDisplayName();
            subscriber_ref = FirebaseDatabase.getInstance().getReference().child("videos");
            subscriber_ref.keepSynced(true);

            FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("videos").orderByChild("uploader_name").equalTo(s),Post.class)
                    .build();

            adapter = new SubscriberAdapter(options);
            subscriber_recycler_view.setAdapter(adapter);

        }
        else if (profile!=null)
        {
            String user_name = profile.getName();
            subscriber_ref = FirebaseDatabase.getInstance().getReference().child("videos");
            subscriber_ref.keepSynced(true);

            FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("videos").orderByChild("uploader_name").equalTo(s),Post.class)
                    .build();
            adapter = new SubscriberAdapter(options);
            subscriber_recycler_view.setAdapter(adapter);
        }

        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.save_rel);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setTitle("Subscriptions");
        }


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.scrpage)
                {
                    Intent intent = new Intent(Subscriber.this , Subscription.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                else if (id == R.id.toppage)
                {
                    Intent intent = new Intent(Subscriber.this , Top.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                else if (id == R.id.homepage)
                {
                    Intent intent = new Intent(Subscriber.this , After_login.class);
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
    protected void onStop() {
        adapter.stopListening();
        super.onStop();
    }

    @Override
    protected void onStart() {
        adapter.startListening();
        super.onStart();
    }
}

