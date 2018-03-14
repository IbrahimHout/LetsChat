package com.ibrah.hout.letschat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG ="Firebase" ;
    private TextInputLayout mDisplay;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private Button mCreate;
    private android.support.v7.widget.Toolbar toolbar;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    private FirebaseDatabase mFirebaseDatabase ;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=mFirebaseDatabase.getReference();


        toolbar = findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Account");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDisplay =(TextInputLayout) findViewById(R.id.display_name_reg);
        mEmail = (TextInputLayout) findViewById(R.id.email_reg);
        mPassword = (TextInputLayout) findViewById(R.id.password_reg);
        mCreate = (Button) findViewById(R.id.create_reg);
        progressDialog = new ProgressDialog(this);

        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String display_name = mDisplay.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String password= mPassword.getEditText().getText().toString();

                //// Hide keyboard ///////

                hideKeyboard();

                ///////////
                if ( !(TextUtils.isEmpty(display_name)) && !(TextUtils.isEmpty(email))&& !(TextUtils.isEmpty(password))){
                    progressDialog.setTitle("Registering user");
                    progressDialog.setMessage("Please waite a second");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    register_user(display_name,email,password);



                }else{

                    View view1= LayoutInflater.from(RegisterActivity.this).inflate(R.layout.activity_register,null);
                    Snackbar snackbar = Snackbar.make(view
                            ,"Please Make sure you filled all spaces",Snackbar.LENGTH_LONG);
                    snackbar.show();


                }
                //////////

            }

        });
    }

    private void register_user(final String display_name, final String email, final String password) {

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){


                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    String userID = firebaseUser.getUid();


                    DatabaseReference users_Reference = databaseReference.child("users").child(userID);

                    HashMap<String, String> user_info_map = new HashMap<>();
                    user_info_map.put("name",display_name);
                    user_info_map.put("status","Hi there, I Started using Let's chat app \n Say hi!");
                    user_info_map.put("image","default");
                    user_info_map.put("thump_image","default");
                    user_info_map.put("email",email);
                    user_info_map.put("password",password);

                    users_Reference.setValue(user_info_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){

                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "Registration success", Toast.LENGTH_SHORT).show();
                                Intent loggetIntent = new Intent(RegisterActivity.this,MainActivity.class);
                                loggetIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(loggetIntent);
                                finish();

                           }else {

                            }
                        }
                    });




                }else{
                    progressDialog.hide();
                    Toast.makeText(RegisterActivity.this, "Some Error happend", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void hideKeyboard(){

        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}

