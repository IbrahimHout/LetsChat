package com.ibrah.hout.letschat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Edite_field_Activity extends AppCompatActivity {

    Toolbar toolbar;
    EditText editText;
    Button ok_btn;
    Button cancle_btn;
    String title;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_field_);

        Intent intent = getIntent();
        int requesttype = intent.getIntExtra("request",0);

        title ="Enter";
        if (requesttype ==1){title="name";}else if (requesttype==2){title= "status";};
        Toast.makeText(this, title, Toast.LENGTH_SHORT).show();



        toolbar = findViewById(R.id.toolbar_filed);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit your "+title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressDialog = new ProgressDialog(this);


        editText = findViewById(R.id.field);
        editText.setHint(title);

        String old_data = intent.getStringExtra("old_"+title);
        if (old_data!=null){
            editText.setText(old_data);

            editText.setSelection(0,old_data.length());
        }


        ok_btn = findViewById(R.id.ok_tv_btn);
        cancle_btn = findViewById(R.id.cancel_tv_btn);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                progressDialog.setTitle("Editing your"+title);
                progressDialog.setMessage("Loading your new "+title);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();



                String name = editText.getText().toString();
                if (TextUtils.isEmpty(name)){
                    Snackbar snackbar= Snackbar.make(view,"You should Enter a value",Snackbar.LENGTH_LONG);
                    snackbar.show();
                    progressDialog.hide();
                }else {

                    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
                    String user_ID = user.getUid();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(user_ID).child(title);
                    reference.setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                finish();
                            }else {
                               Snackbar.make(view,"Something wrong happend, try again",Snackbar.LENGTH_LONG).show();
                                progressDialog.hide();

                            }
                        }
                    });

                }
            }
        });


        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

}
