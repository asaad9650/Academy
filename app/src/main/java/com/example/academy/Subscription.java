package com.example.academy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Subscription extends AppCompatActivity
{
    GoogleSignInAccount googleSignInAccount;
    private RecyclerView subs_list;
    private SubsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        BottomNavigationView navigationView = findViewById(R.id.navbtn);
        navigationView.setSelectedItemId(R.id.scrpage);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        subs_list = (RecyclerView)findViewById(R.id.recycler_view1);
        subs_list.setLayoutManager(new LinearLayoutManager(this));
        subs_list.setNestedScrollingEnabled(true);

        final Profile profile = Profile.getCurrentProfile();

        if (profile!=null)
        {
            final String name = profile.getName();
            FirebaseRecyclerOptions<Subscribe> options = new FirebaseRecyclerOptions.Builder<Subscribe>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("subscriptions").child(name) , Subscribe.class)
                    .build();
            adapter = new SubsAdapter(options);
            subs_list.setAdapter(adapter);
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("subscriptions").child(name);
            ref.keepSynced(true);

        }
        if (profile==null)
        {

            googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            String name = googleSignInAccount.getDisplayName();
            String personName = googleSignInAccount.getDisplayName();
            String var2 = personName;



            FirebaseRecyclerOptions<Subscribe> options = new FirebaseRecyclerOptions.Builder<Subscribe>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("subscriptions").child(var2) , Subscribe.class)
                    .build();
            adapter = new SubsAdapter(options);
            subs_list.setAdapter(adapter);
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("subscriptions").child(var2);
            ref.keepSynced(true);


        }



        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

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
                    //Intent intent = new Intent(Subscription.this , Subscription.class);
                    //startActivity(intent);
                    return true;
                }
                else if (id == R.id.toppage)
                {
                    Intent intent = new Intent(Subscription.this , Top.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                else if (id == R.id.homepage)
                {
                    Intent intent = new Intent(Subscription.this , After_login.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                else if (id == R.id.savebtn)
                {
                    Intent intent = new Intent(Subscription.this , Save.class);
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
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}