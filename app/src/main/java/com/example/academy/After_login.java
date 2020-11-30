package com.example.academy;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import de.hdodenhof.circleimageview.CircleImageView;

public class After_login extends AppCompatActivity
{

    //Slider
    LinearLayout linearLayout;
    ImageView imageView;
    TextView name_textView;
    String nameeee;
    ImageView img_view;

    RelativeLayout relay;
    LinearLayout linlay;
    ImageView image_nav_head;
    Button btn_play;


    FirebaseAuth facebookAuth;
    private FirebaseAuth googleAuth;



    /*//Slider
    LinearLayout linearLayout;
    ImageView imageView;
    String nameeee;

    LinearLayout linlay;
    ImageView image_nav_head;

    Button upload_file , select_file;
    TextView text_upload, txt_fileDetails;
    EditText et_video_description , et_video_title , et_institute_name , et_course_name;
    Intent myFileIntent;
    String empty;
    String video_description;
    String video_title;
    String institute;
e    String course;
    String file_details;//Slider
    LinearLayout linearLayout;
    ImageView imageView;
    String nameeee;

e    LinearLayout linlay;
    ImageView image_nav_head;

    Button upload_file , select_file;
    TextView text_upload, txt_fileDetails;
    EditText et_video_description , et_video_title , et_institute_name , et_course_name;
    Intent myFileIntent;
    String empty;
    String video_description;
    String video_title;
    String institute;
    String course;
    String file_details;
    private static final int PICK_IMAGE_REQUEST = 234;
    private Uri filePath;



    FirebaseAuth facebookAuth;
    private FirebaseAuth googleAuth;

   // private DatabaseReference mDatabase;
   // FirebaseAuth mAuth;
/*


    private static final int PICK_IMAGE_REQUEST = 234;
    private Uri filePath;



    FirebaseAuth facebookAuth;
    private FirebaseAuth googleAuth;

   // private DatabaseReference mDatabase;
   // FirebaseAuth mAuth;
/*

     */
    //Slider


    private DatabaseReference search_ref;
    private RecyclerView recyclerView;
    private PostAdapter adapter;
    ImageButton imgbtn_search;
    EditText et_search;
    ImageButton imgbtn_back;
    ArrayList<Post> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);

        linearLayout = (LinearLayout) findViewById(R.id.ll_linearLayout);
        imageView = (ImageView) findViewById(R.id.img_imageView);

       // image_nav_head = (ImageView) findViewById(R.id.image_nav_head);
        //linlay = (LinearLayout) findViewById(R.id.linlay);
        //relay = (RelativeLayout) findViewById(R.id.relay);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setNaestedScrollingEnabled(true);
        //Searchhhh
        search_ref = FirebaseDatabase.getInstance().getReference().child("videos");
        search_ref.keepSynced(true);

        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("videos"),Post.class)
                .build();

        adapter = new PostAdapter(options);
        recyclerView.setAdapter(adapter);

        arrayList = new ArrayList<>();
        imgbtn_search = (ImageButton)findViewById(R.id.img_btn_search);
        imgbtn_back = (ImageButton)findViewById(R.id.img_btn_back);
        imgbtn_back.setVisibility(View.GONE);
        et_search = (EditText)findViewById(R.id.et_search);
        et_search.setVisibility(View.GONE);
        imgbtn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                et_search.setVisibility(View.VISIBLE);
                imgbtn_back.setVisibility(View.VISIBLE);
                imgbtn_search.setVisibility(View.GONE);
            }
        });
        imgbtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setVisibility(View.GONE);
                imgbtn_search.setVisibility(View.VISIBLE);
                imgbtn_back.setVisibility(View.GONE);
                et_search.setText("");
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (!s.toString().isEmpty())
                {
                    search(s.toString());
                }
                else
                    {
                        search("");
                    }

            }
        });


       /* Query search  = search_ref.orderByChild("video_title");
        search.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren())
                {
                    Log.i(TAG , String.valueOf(zoneSnapshot.child("video_title").getValue(VideoDetails.class)));
                    Toast.makeText(After_login.this, ""+TAG, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        /*search_ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                Map<String , Object> data = (Map<String, Object>) dataSnapshot.getValue();
                Toast.makeText(After_login.this, ""+ data, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
        /*search_ref.orderByChild("video_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                String gg = dataSnapshot.child("video_title").getValue().toString();
                naam.setText(gg);
                String pp = dataSnapshot.child("video_description").getValue().toString();
                pata.setText(pp);
                //Toast.makeText(After_login.this, ""+ dataSnapshot.getValue(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(After_login.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
        */



     /*   recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        search_ref = FirebaseDatabase.getInstance().getReference().child("videos");

        //navigation
        /*RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.R_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("");
        }

        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        View headerView = nav_view.getHeaderView(0);
        TextView nav_username = (TextView) headerView.findViewById(R.id.text_nav_head);
        View imageView = nav_view.getHeaderView(0);
        CircleImageView img_head_ = (CircleImageView) imageView.findViewById(R.id.image_nav_head);


        final Profile profile = Profile.getCurrentProfile();


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.Open_nav_drawer, R.string.Close_nav_drawer) {
            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
            }
        };

        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getTitle().equals("Logout")) {
                    LoginManager.getInstance().logOut();
                    googleAuth.getInstance().signOut();
                    Intent intent = new Intent(After_login.this, Login.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(After_login.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                    drawer.closeDrawers();
                    return true;
                } else {
                    Toast.makeText(getApplicationContext(), menuItem.getTitle() + " selected", Toast.LENGTH_SHORT).show();
                    drawer.closeDrawers();
                    return true;
                }
            }

        });


        googleAuth = FirebaseAuth.getInstance();
        FirebaseUser user = googleAuth.getCurrentUser();
        facebookAuth = FirebaseAuth.getInstance();


        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());


        Bundle extras = getIntent().getExtras();
        if (profile != null) {
            nameeee = profile.getName();
            // name_textView.setText(nameeee);
            Uri uri = profile.getProfilePictureUri(200, 200);
            //Picasso.get().load(uri).into(img_view);
            nav_username.setText(nameeee);
            Picasso.get().load(uri).into(img_head_);

        } else if (profile == null) {

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

            Picasso.get().load(imageUri).into(img_head_);
            nav_username.setText(var2);
        }
*/

        //RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.R_layout);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //if (getSupportActionBar() != null) {
          //  getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            //getSupportActionBar().setHomeButtonEnabled(false);
            //getSupportActionBar().setTitle("Home");
        //}

        BottomNavigationView navigationView = findViewById(R.id.navbtn);
        navigationView.setSelectedItemId(R.id.homepage);


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.scrpage) {
                    Intent intent = new Intent(After_login.this, Subscription.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.toppage) {
                    Intent intent = new Intent(After_login.this, Top.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.homepage) {
    //                drawer.closeDrawers();
                    return true;
                } else if (id == R.id.savebtn) {
                    Intent intent = new Intent(After_login.this, Save.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();;
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar, menu);
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView)menu.findItem(R.id.search_bar).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                newText = newText.toLowerCase();
                List<Post> myList = new ArrayList<>();
                return true;
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        googleAuth.getInstance().signOut();
        facebookAuth.getInstance().signOut();
        Intent intent = new Intent(After_login.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void search (String s)
    {
        DatabaseReference vid_title = FirebaseDatabase.getInstance().getReference().child("videos");
        DatabaseReference vid_desc = FirebaseDatabase.getInstance().getReference().child("videos");
        DatabaseReference up_name = FirebaseDatabase.getInstance().getReference().child("videos");
        DatabaseReference course_name = FirebaseDatabase.getInstance().getReference().child("videos");
        DatabaseReference date_time = FirebaseDatabase.getInstance().getReference().child("videos");
        Query query_vid_title = vid_title.orderByChild("video_title")
                .startAt(s)
                .endAt(s+"\uf8ff");
        query_vid_title.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.hasChildren())
                {
                    arrayList.clear();
                    for (DataSnapshot dss: dataSnapshot.getChildren())
                    {
                        final Post post = dss.getValue(Post.class);
                        arrayList.add(post);
                    }

                    Search_adapter search_adapter = new Search_adapter(getApplicationContext(), arrayList);
                    recyclerView.setAdapter(search_adapter);
                    search_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query query_uploader = up_name.orderByChild("uploader_name")
                .startAt(s)
                .endAt(s+"\uf8ff");
        query_uploader.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.hasChildren())
                {
                    arrayList.clear();
                    for (DataSnapshot dss: dataSnapshot.getChildren())
                    {
                        final Post post = dss.getValue(Post.class);
                        arrayList.add(post);
                    }

                    Search_adapter search_adapter = new Search_adapter(getApplicationContext(), arrayList);
                    recyclerView.setAdapter(search_adapter);
                    search_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query query_date_time = date_time.orderByChild("data_time")
                .startAt(s)
                .endAt(s+"\uf8ff");
        query_date_time.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.hasChildren())
                {
                    arrayList.clear();
                    for (DataSnapshot dss: dataSnapshot.getChildren())
                    {
                        final Post post = dss.getValue(Post.class);
                        arrayList.add(post);
                    }

                    Search_adapter search_adapter = new Search_adapter(getApplicationContext(), arrayList);
                    recyclerView.setAdapter(search_adapter);
                    search_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query query_course_name = course_name.orderByChild("course_name")
                .startAt(s)
                .endAt(s+"\uf8ff");
        query_course_name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.hasChildren())
                {
                    arrayList.clear();
                    for (DataSnapshot dss: dataSnapshot.getChildren())
                    {
                        final Post post = dss.getValue(Post.class);
                        arrayList.add(post);
                    }

                    Search_adapter search_adapter = new Search_adapter(getApplicationContext(), arrayList);
                    recyclerView.setAdapter(search_adapter);
                    search_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query query_video_discription = vid_desc.orderByChild("video_description")
                .startAt(s)
                .endAt(s+"\uf8ff");
        query_video_discription.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.hasChildren())
                {
                    arrayList.clear();
                    for (DataSnapshot dss: dataSnapshot.getChildren())
                    {
                        final Post post = dss.getValue(Post.class);
                        arrayList.add(post);
                    }

                    Search_adapter search_adapter = new Search_adapter(getApplicationContext(), arrayList);
                    recyclerView.setAdapter(search_adapter);
                    search_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
        //   mAuth = FirebaseAuth.getInstance();
        //database = FirebaseDatabase.getInstance();
        //ref = database.getReference().child("user");
        //mAuth = FirebaseAuth.getInstance();

/*        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("user");

        txt_fileDetails = (TextView)findViewById(R.id.txt_file_details);
        text_upload = (TextView)findViewById(R.id.txt_upload) ;
        upload_file = (Button)  findViewById(R.id.btn_upload);
        select_file = (Button) findViewById(R.id.btn_select_file);
        et_video_title = (EditText) findViewById(R.id.et_video_name);
        et_video_description = (EditText)findViewById(R.id.et_video_description);
        et_course_name = (EditText)findViewById(R.id.et_course_name);
        et_institute_name  = (EditText)findViewById(R.id.et_institute_name);

        //Slider
        linearLayout = (LinearLayout) findViewById(R.id.ll_linearLayout);
        imageView = (ImageView) findViewById(R.id.img_imageView);

        image_nav_head = (ImageView)findViewById(R.id.image_nav_head);
        linlay = (LinearLayout) findViewById(R.id.linlay);
        relay  = (RelativeLayout)findViewById(R.id.relay);

        final Profile profile = Profile.getCurrentProfile();
        select_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent , "Select an image") , PICK_IMAGE_REQUEST);
            }
        });
         upload_file.setOnClickListener(new View.OnClickListener()
         {
            @Override
            public void onClick(View v)
            {
                empty= "";
                video_description = et_video_description.getText().toString();
                video_title = et_video_title.getText().toString();
                institute = et_institute_name.getText().toString();
                course = et_course_name.getText().toString();
                file_details = txt_fileDetails.getText().toString();
                if(video_description.matches(empty) || video_title.matches(empty) || institute.matches(empty) || course.matches(empty))
                {

                    Toast.makeText(After_login.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                }

                else
                    {
                        if (file_details.matches(empty))
                        {
                            Toast.makeText(After_login.this, "Please insert a video file ", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String current_date = new SimpleDateFormat("dd-MM-yyyy" , Locale.getDefault()).format(new Date());
                            String current_time = new SimpleDateFormat("HH:mm:ss" , Locale.getDefault()).format(new Date());
                            String date_time = current_date + " "+current_time;
                            VideoDetails videoDetails = new VideoDetails();
                            String details = "details";
                            videoDetails.setInstitute(institute);
                            videoDetails.setCourse(course);
                            videoDetails.setVideo_title(video_title);
                            videoDetails.setVideo_description(video_description);
                            videoDetails.setDate_time(date_time);
                            myRef.child(institute);
                            myRef.child(institute).child(course);
                            myRef.child(institute).child(course).setValue(videoDetails);
                            uploadFile();
                            //upload(v);


                            /*for (int upload_count=0; upload_count<uriArrayList.size();upload_count++)
                            {

                                Uri indivdualFile=uriArrayList.get(upload_count);
                                final StorageReference FileName=fileFolder.child("File"+ indivdualFile.getLastPathSegment());

                                FileName.putFile(indivdualFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                                {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                    {
                                        FileName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                                        {
                                            @Override
                                            public void onSuccess(Uri uri)
                                            {
                                                String url=String.valueOf(uri);
                                                StoreLink(url);
                                            }
                                        });
                                    }
                                });
                            }*/
/*
                        }
                    }
                }
            });

        //navigation
        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.R_layout);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Dashboard");
        }

        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        View headerView = nav_view.getHeaderView(0);
        TextView nav_username = (TextView)headerView.findViewById(R.id.text_nav_head);
        View imageView = nav_view.getHeaderView(0);
        CircleImageView img_head_ = (CircleImageView) imageView.findViewById(R.id.image_nav_head);

        final Profile profile = Profile.getCurrentProfile();


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer , toolbar,
                R.string.Open_nav_drawer, R.string.Close_nav_drawer)
        {
            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                select_file.setVisibility(View.INVISIBLE);
                upload_file.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                if(menuItem.getTitle().equals("Logout"))
                {
                    LoginManager.getInstance().logOut();
                    googleAuth.getInstance().signOut();
                    Intent intent = new Intent(After_login.this , Login.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(After_login.this , "Logged out successfully" , Toast.LENGTH_SHORT).show();
                    drawer.closeDrawers();
                    return true;
                }
                else
                    {
                        Toast.makeText(getApplicationContext(), menuItem.getTitle()+ " selected", Toast.LENGTH_SHORT).show();
                        drawer.closeDrawers();
                        return true;
                    }
            }

        });


*/
/*
        googleAuth = FirebaseAuth.getInstance();
        FirebaseUser user = googleAuth.getCurrentUser();
        facebookAuth = FirebaseAuth.getInstance();


        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());


        Bundle extras = getIntent().getExtras();
        if (profile != null)
        {
            nameeee = profile.getName();
           // name_textView.setText(nameeee);
            Uri uri = profile.getProfilePictureUri(200 , 200);
            //Picasso.get().load(uri).into(img_view);
           // nav_username.setText(nameeee);
            //Picasso.get().load(uri).into(img_head_);

        }
        else if (profile == null)
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

          //  Picasso.get().load(imageUri).into(img_head_);
           // nav_username.setText(var2);


        }


/*
        BottomNavigationView navigationView = findViewById(R.id.navbtn);
       navigationView.setSelectedItemId(R.id.homepage);


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                int id = menuItem.getItemId();
                if (id == R.id.scrpage)
                {
                    Intent intent = new Intent(After_login.this , Subscription.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                else if (id == R.id.toppage)
                {
                    Intent intent = new Intent(After_login.this , Top.class);
                    startActivity(intent);
                    finish();
                    return true;
                }
                else if (id == R.id.homepage)
                {
                    //drawer.closeDrawers();
                    return true;
                }
                else if (id == R.id.savebtn)
                {
                    Intent intent = new Intent(After_login.this , Save.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });




*/
/*
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData()!=null)
        {

            filePath = data.getData();
            txt_fileDetails.setText(filePath.getLastPathSegment());
            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver() , filePath);
            }
            catch (IOException e)
            {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadFile()
    {
        StorageReference fileFolder= FirebaseStorage.getInstance().getReference().child("Files");
        if (filePath!=null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading ... ");
            progressDialog.show();
            final StorageReference fileName = fileFolder.child(filePath.getLastPathSegment());
            fileName.putFile(filePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        /*
                        fileName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url=String.valueOf(uri);
                                storeLink(url);
                            }
                        });*/
          /*              progressDialog.dismiss();
                        Toast.makeText(After_login.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(After_login.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                        // Handle unsuccessful uploads
                        // ...
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot)
                {
                    double prog = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage( ((int) prog) + "% Uploaded .."  );
                }
            });
        }

    }
    private void storeLink(String url)
    {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("User");
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("FileLink",url);
        databaseReference.push().setValue(hashMap);
    }



    /*public void upload(View view)
    {
        if (video_Uri !=null)
        {
            UploadTask uploadTask = video_ref.putFile(video_Uri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Toast.makeText(After_login.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    Toast.makeText(After_login.this, "Upload Complete", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot)
                {
                    updateProgress(taskSnapshot);
                }
            });
        }
        else
            {
                Toast.makeText(this, "Nothing to upload", Toast.LENGTH_SHORT).show();
            }
    }*/

   /* public void updateProgress(UploadTask.TaskSnapshot taskSnapshot)
    {
        long fileSize = taskSnapshot.getTotalByteCount();
        long uploadBytes = taskSnapshot.getBytesTransferred();
        long progress = (100 * uploadBytes)/fileSize;
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.pg_progress_bar);
        progressBar.setProgress((int) progress);

    }
    /*
    private void StoreLink(String url){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("User");
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("FileLink",url);
        databaseReference.push().setValue(hashMap);
        progressDialog.dismiss();
        txt_fileDetails.setText("File Upload Succesfull");
        uriArrayList.clear();
    }*/



    /*@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.search_bar,menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query) {
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
    public void onBackPressed() {
        super.onBackPressed();
        googleAuth.getInstance().signOut();
        facebookAuth.getInstance().signOut();
        Intent intent = new Intent(After_login.this , Login.class);
        startActivity(intent);
        finish();
        }
*/
//}
