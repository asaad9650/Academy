package com.example.academy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.facebook.Profile;
import com.facebook.ProfileManager;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class Video_activity extends AppCompatActivity {

    String url;
    private MediaController ctlr;

    TextView vid_title , up_cr_name , vid_descrip , date_time;
    ImageButton img_btn;
    VideoView videoView;
    FrameLayout frameLayout;
    public String var_uploader_name , var_date_time , var_video_title , var_vidoe_description , var_Course_name, var_video_link;

    private RecyclerView recyclerView;
    private SuggestionAdapter adapter;
    ImageButton btn_fullscreen;

    ImageButton btn_subscribe , btn_save;

    TextView tv_img_btn_save , tv_img_btn_subscribe;
    ProgressBar progressBar_video;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_activity);

        progressBar_video = (ProgressBar)findViewById(R.id.progressBar_video);
        frameLayout = (FrameLayout)findViewById(R.id.frame_layout);
        vid_title = (TextView)findViewById(R.id.vi_ti);
        up_cr_name = (TextView)findViewById(R.id.up_name);
        vid_descrip = (TextView)findViewById(R.id.vid_desc);
        date_time = (TextView)findViewById(R.id.dt);
        img_btn = (ImageButton) findViewById(R.id.img_btn);
        btn_fullscreen = (ImageButton)findViewById(R.id.btn_fullscreen);
        btn_save = (ImageButton)findViewById(R.id.img_btn_save);
        tv_img_btn_save= (TextView)findViewById(R.id.tv_img_btn_save);
        btn_subscribe = (ImageButton)findViewById(R.id.img_btn_subscribe);
        tv_img_btn_subscribe = (TextView)findViewById(R.id.tv_img_btn_subscribe);
        videoView = (VideoView)findViewById(R.id.vid_view);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        Bundle extra  = getIntent().getExtras();
        String s = extra.getString("save");
        var_video_link = extra.getString("video_link");
        var_uploader_name = extra.getString("up_name");
        var_date_time = extra.getString("dt");
        var_video_title = extra.getString("vt");
        var_Course_name = extra.getString("cr_na");
        var_vidoe_description = extra.getString("vid_desc");


        videoView.setVideoURI(Uri.parse(var_video_link));
        videoView.setMediaController(ctlr);
        videoView.requestFocus();

        videoView.start();



/*        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp)
            {
                progressBar_video.setVisibility(GONE);
                videoView.start();
            }
        });
*/

        MediaController mediaController = new MediaController(Video_activity.this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        //ctlr = new MediaController(this);
        //ctlr.setMediaPlayer(videoView);
        vid_title.setText(var_video_title);
        up_cr_name.setText(var_uploader_name + " - " + var_Course_name);
        vid_descrip.setText(var_vidoe_description);
        date_time.setText(var_date_time);

        up_cr_name.setVisibility(View.GONE);
        vid_descrip.setVisibility(View.GONE);
        date_time.setVisibility(View.GONE);
        btn_subscribe.setVisibility(GONE);
        btn_save.setVisibility(GONE);
        tv_img_btn_subscribe.setVisibility(GONE);
        tv_img_btn_save.setVisibility(GONE);
        Drawable replacer = getResources().getDrawable(R.drawable.show_more_24dp);
        img_btn.setBackgroundDrawable(replacer);
        final Drawable save = getResources().getDrawable(R.drawable.ic_save_video);
        btn_save.setBackgroundDrawable(save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                //{
                Profile profile = Profile.getCurrentProfile();

                //DatabaseReference save_ref = FirebaseDatabase.getInstance().getReference().child();
                //facebook
                if (profile!=null)
                {
                    final String fb_name = profile.getName();
                    final DatabaseReference refer = FirebaseDatabase.getInstance().getReference().child("saved").child(fb_name);
                    Query queries= refer.orderByChild("video_title").equalTo(var_video_title);
                    ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            if (dataSnapshot.exists())
                            {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(Video_activity.this, R.style.AlertDialogStyle);
                                builder1.setTitle("Already saved");
                                builder1.setMessage("Do you want to unsave?.");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(final DialogInterface dialog, int id)
                                            {


                                                final DatabaseReference delete_ref = FirebaseDatabase.getInstance().getReference().child("saved").child(fb_name);
                                                final Query delete_query = delete_ref.orderByChild("video_title").equalTo(var_video_title);
                                                delete_query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                    {
                                                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                                                        {
                                                            dataSnapshot1.getRef().removeValue();
                                                            dialog.cancel();
                                                            Toast.makeText(Video_activity.this, "Video removed from your saved list", Toast.LENGTH_SHORT).show();
                                                            Drawable save = getResources().getDrawable(R.drawable.ic_save_video);
                                                            btn_save.setBackgroundDrawable(save);
                                                            btn_save.setVisibility(VISIBLE);
                                                            tv_img_btn_save.setVisibility(VISIBLE);
                                                            tv_img_btn_save.setText("Save");
                                                            tv_img_btn_save.setTextColor(Color.parseColor("#838383"));
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError)
                                                    {

                                                    }
                                                });


                                            }
                                        });

                                builder1.setNegativeButton(
                                        "No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.setCanceledOnTouchOutside(true);
                                alert11.show();
                            }
                            else if (!dataSnapshot.exists())
                            {
                                DatabaseReference save_ref = FirebaseDatabase.getInstance().getReference().child("saved").child(fb_name);
                                UploaderActivityDetails uploaderActivityDetails = new UploaderActivityDetails();
                                uploaderActivityDetails.setVideo_title(var_video_title);
                                uploaderActivityDetails.setVideo_link(var_video_link);
                                uploaderActivityDetails.setUploader_name(var_uploader_name);
                                uploaderActivityDetails.setData_time(var_date_time);
                                uploaderActivityDetails.setCourse_name(var_Course_name);
                                uploaderActivityDetails.setVideo_description(var_vidoe_description);
                                save_ref.push().setValue(uploaderActivityDetails);
                                Toast.makeText(Video_activity.this, "Saved", Toast.LENGTH_SHORT).show();
                                Drawable save = getResources().getDrawable(R.drawable.ic_save_blue);
                                btn_save.setBackgroundDrawable(save);
                                tv_img_btn_save.setText("Saved");
                                tv_img_btn_save.setTextColor(Color.parseColor("#123288"));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError)
                        {
                            Toast.makeText(Video_activity.this, "Process cancelled", Toast.LENGTH_SHORT).show();
                        }
                    };
                    queries.addListenerForSingleValueEvent(valueEventListener);


                }
                //Google
                else if (profile == null)
                {
                    GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                    final String google_name =  googleSignInAccount.getDisplayName();
                    final DatabaseReference refer = FirebaseDatabase.getInstance().getReference().child("saved").child(google_name);
                    Query queries= refer.orderByChild("video_title").equalTo(var_video_title);
                    ValueEventListener valueEventListener = new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            if (dataSnapshot.exists())
                            {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(Video_activity.this, R.style.AlertDialogStyle);
                                builder1.setTitle("Already Saved.");
                                builder1.setMessage("Do you want to unsave video?");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(final DialogInterface dialog, int id)
                                            {
                                                final DatabaseReference delete_ref = FirebaseDatabase.getInstance().getReference().child("saved").child(google_name);
                                                final Query delete_query = delete_ref.orderByChild("video_title").equalTo(var_video_title);
                                                delete_query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                    {
                                                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                                                        {
                                                            dataSnapshot1.getRef().removeValue();
                                                            dialog.cancel();
                                                            Toast.makeText(Video_activity.this, "Video removed from your saved list", Toast.LENGTH_SHORT).show();
                                                            Drawable save = getResources().getDrawable(R.drawable.ic_save_video);
                                                            btn_save.setBackgroundDrawable(save);
                                                            btn_save.setVisibility(VISIBLE);
                                                            tv_img_btn_save.setVisibility(VISIBLE);
                                                            tv_img_btn_save.setText("Save");
                                                            tv_img_btn_save.setTextColor(Color.parseColor("#838383"));
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError)
                                                    {

                                                    }
                                                });
                                            }
                                        });

                                builder1.setNegativeButton(
                                        "No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.setCanceledOnTouchOutside(true);
                                alert11.show();
                            }
                            else if (!dataSnapshot.exists())
                            {
                                DatabaseReference save_ref = FirebaseDatabase.getInstance().getReference().child("saved").child(google_name);
                                UploaderActivityDetails uploaderActivityDetails = new UploaderActivityDetails();
                                uploaderActivityDetails.setVideo_title(var_video_title);
                                uploaderActivityDetails.setVideo_link(var_video_link);
                                uploaderActivityDetails.setUploader_name(var_uploader_name);
                                uploaderActivityDetails.setData_time(var_date_time);
                                uploaderActivityDetails.setCourse_name(var_Course_name);
                                uploaderActivityDetails.setVideo_description(var_vidoe_description);
                                save_ref.push().setValue(uploaderActivityDetails);
                                Toast.makeText(Video_activity.this, "Saved", Toast.LENGTH_SHORT).show();
                                Drawable save = getResources().getDrawable(R.drawable.ic_save_blue);
                                btn_save.setBackgroundDrawable(save);
                                tv_img_btn_save.setText("Saved");
                                tv_img_btn_save.setTextColor(Color.parseColor("#123288"));
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError)
                        {
                            Toast.makeText(Video_activity.this, "Process cancelled", Toast.LENGTH_SHORT).show();
                        }
                    };
                    queries.addListenerForSingleValueEvent(valueEventListener);
                }
                //}
            }
        });



        btn_fullscreen.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(Video_activity.this, "Full screen clicked", Toast.LENGTH_SHORT).show();
            }
        });

        btn_subscribe.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final Profile profile = Profile.getCurrentProfile();
                GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                if (profile != null)
                {
                    final String nameeee = profile.getName();
                    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("subscriptions").child(nameeee);
                    Query queries= ref.orderByChild("uplaoder_name").equalTo(var_uploader_name);
                    ValueEventListener valueEventListener = new ValueEventListener()
                    {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            if (dataSnapshot.exists())
                            {

                                AlertDialog.Builder builder1 = new AlertDialog.Builder(Video_activity.this , R.style.AlertDialogStyle);
                                builder1.setTitle("Already subscribed");
                                builder1.setMessage("Do you want to unsubscribe?");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(final DialogInterface dialog, int id)
                                            {

                                                final DatabaseReference sub_ref = FirebaseDatabase.getInstance().getReference().child("subscriptions").child(nameeee);
                                                final Query delete_sub_query = sub_ref.orderByChild("uploader_name").equalTo(var_uploader_name);
                                                delete_sub_query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                    {
                                                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                                                        {
                                                            dataSnapshot1.getRef().removeValue();
                                                            dialog.cancel();
                                                            Toast.makeText(Video_activity.this, "Unsubscribed this user", Toast.LENGTH_SHORT).show();
                                                            Drawable save = getResources().getDrawable(R.drawable.ic_subscription_button);
                                                            btn_subscribe.setBackgroundDrawable(save);
                                                            btn_subscribe.setVisibility(VISIBLE);
                                                            tv_img_btn_subscribe.setVisibility(VISIBLE);
                                                            tv_img_btn_subscribe.setText("Subscribe");
                                                            tv_img_btn_subscribe.setTextColor(Color.parseColor("#838383"));
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });


                                            }
                                        });

                                builder1.setNegativeButton(
                                        "No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.setCanceledOnTouchOutside(true);
                                alert11.show();
                            }
                            else if (!(dataSnapshot.exists()))
                            {
                                FirebaseAuth auth = FirebaseAuth.getInstance();
                                String uid = auth.getCurrentUser().getUid();
                                Subscribe subscribe = new Subscribe();
                                subscribe.setUploader_name(var_uploader_name);
                                subscribe.setUser_name(nameeee);
                                ref.push().setValue(subscribe);
                                Toast.makeText(Video_activity.this, "Subscribed", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError)
                        {
                            Toast.makeText(Video_activity.this, databaseError.getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                    };
                    queries.addListenerForSingleValueEvent(valueEventListener);





                }
                else if (profile == null)
                {

                    // if (googleSignInAccount != null) {
                    final String personName = googleSignInAccount.getDisplayName();
                    final String var2 = personName;

                    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("subscriptions").child(var2);
                    Query queries = ref.orderByChild("uploader_name").equalTo(var_uploader_name);
                    ValueEventListener eventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            if (dataSnapshot.exists())
                            {
                               // Drawable tick = getResources().getDrawable(R.drawable.ic_check_black_24dp);
                              //  tv_img_btn_subscribe.setText("Subscribed");
                                //tv_img_btn_subscribe.setTextColor(Color.parseColor("#123288"));
                                //btn_subscribe.setBackgroundDrawable(tick);
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(Video_activity.this, R.style.AlertDialogStyle);
                                builder1.setTitle("Already subscribed.");
                                builder1.setMessage("Do you want to unsubscribe?");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(final DialogInterface dialog, int id)
                                            {
                                                final DatabaseReference sub_ref = FirebaseDatabase.getInstance().getReference().child("subscriptions").child(personName);
                                                final Query delete_sub_query = sub_ref.orderByChild("uploader_name").equalTo(var_uploader_name);
                                                delete_sub_query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                    {

                                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                                                        {
                                                            dataSnapshot1.getRef().removeValue();
                                                            dialog.cancel();
                                                            Toast.makeText(Video_activity.this, "Unsubscribed this user", Toast.LENGTH_SHORT).show();
                                                            Drawable save = getResources().getDrawable(R.drawable.ic_subscription_button);
                                                            btn_subscribe.setBackgroundDrawable(save);
                                                            btn_subscribe.setVisibility(VISIBLE);
                                                            tv_img_btn_subscribe.setVisibility(VISIBLE);
                                                            tv_img_btn_subscribe.setText("Subscribe");
                                                            tv_img_btn_subscribe.setTextColor(Color.parseColor("#838383"));
                                                        }


                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
                                            }
                                        });

                                builder1.setNegativeButton(
                                        "No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.setCanceledOnTouchOutside(true);
                                alert11.show();
                            }
                            else if (!(dataSnapshot.exists()))
                            {
                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                Subscribe subscribe = new Subscribe();
                                subscribe.setUploader_name(var_uploader_name);
                                subscribe.setUser_name(var2);
                                ref.push().setValue(subscribe);
                                Toast.makeText(Video_activity.this, "Subscribed", Toast.LENGTH_SHORT).show();
                                tv_img_btn_subscribe.setText("Subscribed");
                                tv_img_btn_subscribe.setTextColor(Color.parseColor("#123288"));
                                Drawable tick = getResources().getDrawable(R.drawable.ic_check_black_24dp);
                                btn_subscribe.setBackgroundDrawable(tick);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError)
                        {
                            Toast.makeText(Video_activity.this, databaseError.getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                    };
                    queries.addListenerForSingleValueEvent(eventListener);
                }

            }
        });
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (up_cr_name.getVisibility() == GONE && vid_descrip.getVisibility()== GONE && date_time.getVisibility()== GONE && btn_subscribe.getVisibility()==GONE && btn_save.getVisibility()==GONE)
                {
                    up_cr_name.setVisibility(VISIBLE);
                    vid_descrip.setVisibility(VISIBLE);
                    date_time.setVisibility(VISIBLE);
                    btn_save.setVisibility(VISIBLE);
                    tv_img_btn_save.setVisibility(VISIBLE);
                    Drawable replacer = getResources().getDrawable(R.drawable.arrow_up_24dp);
                    img_btn.setBackgroundDrawable(replacer);
                    Profile profile = Profile.getCurrentProfile();

                    if (profile !=null)
                    {
                        //check if already subscribed or not ...
                        String facebook_name = profile.getName();
                        final DatabaseReference subscriptions_ref = FirebaseDatabase.getInstance().getReference().child("subscriptions").child(facebook_name);
                        Query query = subscriptions_ref.orderByChild("uploader_name").equalTo(var_uploader_name);
                        ValueEventListener valueEventListener = new ValueEventListener()
                        {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                if (dataSnapshot.exists())
                                {
                                    Drawable ti = getResources().getDrawable(R.drawable.ic_check_black_24dp);
                                    btn_subscribe.setBackgroundDrawable(ti);
                                    btn_subscribe.setVisibility(VISIBLE);
                                    tv_img_btn_subscribe.setVisibility(VISIBLE);
                                    tv_img_btn_subscribe.setText("Subscribed");
                                    tv_img_btn_subscribe.setTextColor(Color.parseColor("#123288"));
                                }
                                else if (!dataSnapshot.exists())
                                {
                                    Drawable sub = getResources().getDrawable(R.drawable.ic_subscription_button);
                                    btn_subscribe.setBackgroundDrawable(sub);
                                    btn_subscribe.setVisibility(VISIBLE);
                                    tv_img_btn_subscribe.setVisibility(VISIBLE);
                                    tv_img_btn_subscribe.setText("Subscribe");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError)
                            {

                            }
                        };
                        query.addListenerForSingleValueEvent(valueEventListener);

                        //Check if video already saved or not
                        final DatabaseReference save_ref = FirebaseDatabase.getInstance().getReference().child("saved").child(facebook_name);
                        Query query_for_save = save_ref.orderByChild("video_title").equalTo(var_video_title);
                        ValueEventListener valueEventListener_for_save = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                if (dataSnapshot.exists())
                                {
                                    Drawable save = getResources().getDrawable(R.drawable.ic_save_blue);
                                    btn_save.setBackgroundDrawable(save);
                                    btn_save.setVisibility(VISIBLE);
                                    tv_img_btn_save.setVisibility(VISIBLE);
                                    tv_img_btn_save.setText("Saved");
                                    tv_img_btn_save.setTextColor(Color.parseColor("#123288"));
                                }

                                else if (!dataSnapshot.exists())
                                {
                                    Drawable save = getResources().getDrawable(R.drawable.ic_save_video);
                                    btn_save.setBackgroundDrawable(save);
                                    btn_save.setVisibility(VISIBLE);
                                    tv_img_btn_save.setVisibility(VISIBLE);
                                    tv_img_btn_save.setText("Save");
                                    tv_img_btn_save.setTextColor(Color.parseColor("#838383"));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        };
                        query_for_save.addListenerForSingleValueEvent(valueEventListener_for_save);

                    }
                    else if (profile == null)
                    {
                        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                        String user_name = googleSignInAccount.getDisplayName();

                        //check for button if already subscribed ....
                        final DatabaseReference subscriptions_ref = FirebaseDatabase.getInstance().getReference("subscriptions").child(user_name);
                        Query query = subscriptions_ref.orderByChild("uploader_name").equalTo(var_uploader_name);

                        ValueEventListener valueEventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists())
                                {
                                    Drawable ti = getResources().getDrawable(R.drawable.ic_check_black_24dp);
                                    btn_subscribe.setBackgroundDrawable(ti);
                                    btn_subscribe.setVisibility(VISIBLE);
                                    tv_img_btn_subscribe.setVisibility(VISIBLE);
                                    tv_img_btn_subscribe.setText("Subscribed");
                                    tv_img_btn_subscribe.setTextColor(Color.parseColor("#123288"));
                                }
                                else if (!dataSnapshot.exists()) {
                                    Drawable sub = getResources().getDrawable(R.drawable.ic_subscription_button);
                                    btn_subscribe.setBackgroundDrawable(sub);
                                    btn_subscribe.setVisibility(VISIBLE);
                                    tv_img_btn_subscribe.setVisibility(VISIBLE);
                                    tv_img_btn_subscribe.setText("Subscribe");
                                }
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        };
                        query.addListenerForSingleValueEvent(valueEventListener);

                        //Check if video already saved or not

                        final DatabaseReference myRef_for_saved_videos = FirebaseDatabase.getInstance().getReference().child("saved").child(user_name);
                        Query query_for_saved = myRef_for_saved_videos.orderByChild("video_title").equalTo(var_video_title);

                        ValueEventListener valueEventListener_for_saved = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                if (dataSnapshot.exists())
                                {
                                    Drawable save = getResources().getDrawable(R.drawable.ic_save_blue);
                                    btn_save.setBackgroundDrawable(save);
                                    btn_save.setVisibility(VISIBLE);
                                    tv_img_btn_save.setVisibility(VISIBLE);
                                    tv_img_btn_save.setText("Saved");
                                    tv_img_btn_save.setTextColor(Color.parseColor("#123288"));
                                }

                                else if (!dataSnapshot.exists())
                                {
                                    Drawable save = getResources().getDrawable(R.drawable.ic_save_video);
                                    btn_save.setBackgroundDrawable(save);
                                    btn_save.setVisibility(VISIBLE);
                                    tv_img_btn_save.setVisibility(VISIBLE);
                                    tv_img_btn_save.setText("Save");
                                    tv_img_btn_save.setTextColor(Color.parseColor("#838383"));
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError)
                            {

                            }
                        };
                        query_for_saved.addListenerForSingleValueEvent(valueEventListener_for_saved);

                    }

                }
                else if (up_cr_name.getVisibility() == View.VISIBLE && vid_descrip.getVisibility()== VISIBLE && date_time.getVisibility()== VISIBLE)
                {
                    Drawable replacer = getResources().getDrawable(R.drawable.show_more_24dp);
                    img_btn.setBackgroundDrawable(replacer);
                    up_cr_name.setVisibility(View.GONE);
                    vid_descrip.setVisibility(View.GONE);
                    date_time.setVisibility(View.GONE);
                    btn_save.setVisibility(View.GONE);
                    btn_subscribe.setVisibility(GONE);
                    tv_img_btn_subscribe.setVisibility(GONE);
                    tv_img_btn_save.setVisibility(GONE);
                }
            }
        });
        //tv_desc.setText(vid_desc);
        //tv_name.setText(up_na + " - " + cr_na);
        //date_time.setText(dt);

    //    if (s.isEmpty())
      //  {
            FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("videos"), Post.class)
                    .build();

            adapter = new SuggestionAdapter(options);
            recyclerView.setAdapter(adapter);
            //  }
        //if (s.equals("SaveAdapter"))
        //{
        //    GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        //    String user_name = googleSignInAccount.getDisplayName();
        //    FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>()
        //            .setQuery(FirebaseDatabase.getInstance().getReference().child("saved").child(user_name), Post.class)
        //            .build();
        //    adapter = new SuggestionAdapter(options);
        //   recyclerView.setAdapter(adapter);
        // }



        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.R_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setTitle("Home");
        }
        BottomNavigationView navigationView = findViewById(R.id.navbtn);
        navigationView.setSelectedItemId(R.id.homepage);


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.scrpage) {
                    Intent intent = new Intent(Video_activity.this, Subscription.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.toppage) {
                    Intent intent = new Intent(Video_activity.this, Top.class);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (id == R.id.homepage)
                {
                    //                drawer.closeDrawers();
                    Intent intent = new Intent(Video_activity.this, After_login.class);
                    return true;
                } else if (id == R.id.savebtn) {
                    Intent intent = new Intent(Video_activity.this, Save.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

      /*  if(videoView.isPlaying())
        {
            savedInstanceState.putInt("pos" , videoView.getCurrentPosition());
        }
*/
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
          /*  int pos =0 ;
            if (savedInstanceState!=null)
            {
                pos = savedInstanceState.getInt("pos");
            }*/
            vid_title.setVisibility(GONE);
            up_cr_name.setVisibility(GONE);
            vid_descrip.setVisibility(GONE);
            date_time.setVisibility(GONE);
            btn_subscribe.setVisibility(GONE);
            btn_save.setVisibility(GONE);
            tv_img_btn_subscribe.setVisibility(GONE);
            tv_img_btn_save.setVisibility(GONE);
           // toolbar.setVisibility(GONE);
            img_btn.setVisibility(GONE);
            navigationView.setVisibility(GONE);
            recyclerView.setVisibility(GONE);
            img_btn.setVisibility(GONE);

           /* DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) videoView.getLayoutParams();
            params.width = metrics.widthPixels;
            params.height = metrics.heightPixels;
            params.leftMargin = 0;
            videoView.setLayoutParams(params);*/
            videoView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        }

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
