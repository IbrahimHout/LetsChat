package com.ibrah.hout.letschat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileSettingsActivity extends AppCompatActivity {


    Toolbar toolbar;
    private DatabaseReference mUserDatabaseReference;
    private FirebaseUser mCurrentUser;
    private TextView displayName_tv;
    private TextView status_tv;
    private CircleImageView imgView;
    private ImageButton edit_name_imgbtn;
    private ImageButton edit_status_imgbtn;
    private ImageButton edit_display_img;
    private String img_uri;
    private static final  int IMAGE_REQUEST= 1;
    private StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        toolbar = findViewById(R.id.profile_settings_toolbar);
        displayName_tv = findViewById(R.id.display_name_settings);
        status_tv = findViewById(R.id.status_tv_settings);
        imgView = findViewById(R.id.profile_img);

        edit_name_imgbtn = findViewById(R.id.edit_display_name_imgbtn);
        edit_status_imgbtn = findViewById(R.id.edit_status_imgbtn);
        edit_display_img = findViewById(R.id.edit_display_imgbtn);

        // Firebase Storage Reference declaration
        storageReference = FirebaseStorage.getInstance().getReference();



        edit_name_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileSettingsActivity.this,Edite_field_Activity.class);
                intent.putExtra("request",1);
                intent.putExtra("old_name",displayName_tv.getText().toString());
                startActivity(intent);
            }
        });
        edit_status_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileSettingsActivity.this,Edite_field_Activity.class);
                intent.putExtra("request",2);
                intent.putExtra("old_status",status_tv.getText().toString());
                startActivity(intent);
            }
        });

        edit_display_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent galaryIntent = new Intent();
//                galaryIntent.setAction(Intent.ACTION_GET_CONTENT);
//                galaryIntent.setType("image/*");
////                galaryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivityForResult(galaryIntent,IMAGE_REQUEST);
//
////                startActivityForResult( Intent.createChooser(galaryIntent,"Select Image"),IMAGE_REQUEST);

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(ProfileSettingsActivity.this);


            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.profile_settings));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


       refreshData();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                final ProgressDialog progressDialog = new ProgressDialog(ProfileSettingsActivity.this);
                        progressDialog.setTitle("Uploading");
                        progressDialog.setMessage("Just a second, We're uplodaing your photo");
                        progressDialog.show();


                Uri resultUri = result.getUri();
                img_uri = resultUri.toString();
                Toast.makeText(ProfileSettingsActivity.this, img_uri+"", Toast.LENGTH_SHORT).show();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uID = null;
                if (user != null) {
                    uID = user.getUid();
                }
                StorageReference profile_pic_path = storageReference.child("profile_images").child(uID).child("profile_image.jpg");

                profile_pic_path.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()){
                            String downloadLinkl = task.getResult().getDownloadUrl().toString();

                            mUserDatabaseReference.child("image").setValue(downloadLinkl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Toast.makeText(ProfileSettingsActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();

                                }
                            });





                        }else {

                            Toast.makeText(ProfileSettingsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                        }
                    }
                });


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }


    private void refreshData(){

        mCurrentUser=FirebaseAuth.getInstance().getCurrentUser();
        String user_ID = mCurrentUser.getUid();

        mUserDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(user_ID);

        mUserDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String img_url = dataSnapshot.child("image").getValue().toString();
                String img_thump_url = dataSnapshot.child("thump_image").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();

                displayName_tv.setText(name);
                status_tv.setText(status);
                Picasso.with(ProfileSettingsActivity.this)
                        .load(img_url)
                        .placeholder(R.drawable.defaultpic)
                        .error(R.drawable.defaultpic)
                        .into(imgView);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
