package com.example.academy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UploaderActivity extends AppCompatActivity
{
    public String current_id = "";
    //Slider
    LinearLayout linearLayout;
    ImageView imageView;
    String nameeee;

    RelativeLayout relay;
    LinearLayout linlay;
    ImageView image_nav_head;

    Button upload_file , select_file;
    TextView text_upload, txt_fileDetails;
    EditText et_video_description , et_video_title , et_course_name;
    Intent myFileIntent;
    String empty;
    String video_description;
    String video_title;
    String course;
    String file_details;
    String link;
    private static final int PICK_IMAGE_REQUEST = 234;
    private Uri filePath;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase databaseReference;
    DatabaseReference myRef;
    public String uploader_name;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploader);


        txt_fileDetails = (TextView)findViewById(R.id.txt_file_details);
        text_upload = (TextView)findViewById(R.id.txt_upload) ;
        upload_file = (Button)  findViewById(R.id.btn_upload);
        select_file = (Button) findViewById(R.id.btn_select_file);
        et_video_title = (EditText) findViewById(R.id.et_video_name);
        et_video_description = (EditText)findViewById(R.id.et_video_description);
        et_course_name = (EditText)findViewById(R.id.et_course_name);

        //Slider
        linearLayout = (LinearLayout) findViewById(R.id.ll_linearLayout);
        imageView = (ImageView) findViewById(R.id.img_imageView);

        image_nav_head = (ImageView)findViewById(R.id.image_nav_head);
        linlay = (LinearLayout) findViewById(R.id.linlay);
        relay  = (RelativeLayout)findViewById(R.id.relay);


        firebaseAuth = FirebaseAuth.getInstance();
        current_id = firebaseAuth.getCurrentUser().getUid();
//        txt_fileDetails.setText(current_id);
        myRef = FirebaseDatabase.getInstance().getReference().child("registered_users").child(current_id);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                uploader_name = dataSnapshot.child("user_name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
                course = et_course_name.getText().toString();
                file_details = txt_fileDetails.getText().toString();

                if(video_description.matches(empty) || video_title.matches(empty) || course.matches(empty))
                {
                    Toast.makeText(UploaderActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if (file_details.matches(empty))
                    {
                        Toast.makeText(UploaderActivity.this, "Please insert a video file ", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        reference = FirebaseDatabase.getInstance().getReference().child("videos");
                        String current_date = new SimpleDateFormat("dd-MM-yyyy" , Locale.getDefault()).format(new Date());
                        String current_time = new SimpleDateFormat("HH:mm:ss" , Locale.getDefault()).format(new Date());
                        String date_time = current_date + " "+current_time;
                        String link = "https:\\www.google.com";
                        //UploaderActivityDetails uploaderActivityDetails = new UploaderActivityDetails();
                        //uploaderActivityDetails.setCourse_name(course);
                        //uploaderActivityDetails.setData_time(date_time);
                        //uploaderActivityDetails.setVideo_description(video_description);
                        //uploaderActivityDetails.setUploader_name(uploader_name);
                        //uploaderActivityDetails.setVideo_title(video_title);
                        //uploaderActivityDetails.setVideo_link(link);
                        //VideoDetails videoDetails = new VideoDetails();
                        //String details = "details";
                        //videoDetails.setCourse(course);
                        //videoDetails.setVideo_title(video_title);
                        //videoDetails.setVideo_description(video_description);
                        //videoDetails.setDate_time(date_time);
                        //videoDetails.setVideo_link(link);
                        //reference.child(uploader_name).child(course);
                        //reference.child(uploader_name).child(course).setValue(videoDetails);
                        //reference.child(current_id).setValue(uploaderActivityDetails);
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
                    }
                }
            }
        });
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
        StorageReference fileFolder= FirebaseStorage.getInstance().getReference().child(uploader_name).child(course);
        if (filePath!=null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading your video");
            progressDialog.show();
            final StorageReference fileName = fileFolder.child(filePath.getLastPathSegment());
            fileName.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {

                        fileName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url=String.valueOf(uri);
                                storeLink(url);
                            }
                        });
                            progressDialog.dismiss();

                            //Toast.makeText(UploaderActivity.this, "File Uploaded Successfully", Toast.LENGTH_LONG).show();
                            //et_course_name.setText(empty);
                            //et_video_description.setText(empty);
                            //et_video_title.setText(empty);
                            //txt_fileDetails.setText(empty);
                            //Toast.makeText(UploaderActivity.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
                            //txt_fileDetails.setText(download.toString());
                            // Get a URL to the uploaded content
                            //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception)
                        {
                            progressDialog.dismiss();
                            Toast.makeText(UploaderActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                            // Handle unsuccessful uploads
                            // ...
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot)
                {
                    double prog = (100.0 * taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage( ((int) prog) + "% Uploaded, Please wait"  );
                }
            });
        }

    }
    private void storeLink(String url)
    {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("videos");
        HashMap<String,Object> hashMap=new HashMap<String, Object>();
        hashMap.put("video_link" , url);
        //databaseReference.updateChildren(hashMap);
        String current_date = new SimpleDateFormat("dd-MM-yyyy" , Locale.getDefault()).format(new Date());
        String current_time = new SimpleDateFormat("HH:mm:ss" , Locale.getDefault()).format(new Date());
        String date_time = current_date + " "+current_time;
        //String link = "https:\\www.google.com";
        UploaderActivityDetails uploaderActivityDetails = new UploaderActivityDetails();
        uploaderActivityDetails.setCourse_name(course);
        uploaderActivityDetails.setData_time(date_time);
        uploaderActivityDetails.setVideo_description(video_description);
        uploaderActivityDetails.setUploader_name(uploader_name);
        uploaderActivityDetails.setVideo_title(video_title);
        uploaderActivityDetails.setVideo_link(url);
        databaseReference.push().setValue(uploaderActivityDetails);
        Toast.makeText(this, "Successfully uploaded", Toast.LENGTH_SHORT).show();
        et_course_name.setText(empty);
        et_video_description.setText(empty);
        et_video_title.setText(empty);
        txt_fileDetails.setText(empty);
        //databaseReference.setValue();
        // Map<String , Object> update_link = new HashMap<String, Object>();
        //update_link.put("video_link" , hashMap);
        // hashMap.put("video_link",url);
         //databaseReference.child("videos").child(uploader_name).child(course).setValue(hashMap);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        firebaseAuth.signOut();
        Intent inte = new Intent(UploaderActivity.this , Login.class);
        startActivity(inte);
        finish();
    }
}
