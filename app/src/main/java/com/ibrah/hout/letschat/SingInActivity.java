package com.ibrah.hout.letschat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SingInActivity extends AppCompatActivity {

    TextInputLayout mEmail;
    TextInputLayout mPassword;
    Button mSignInBtn;
    FirebaseAuth mFirebaseAuth;
    RelativeLayout relativeLayout;
    ProgressDialog progressDialog;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);



        mFirebaseAuth = FirebaseAuth.getInstance();

        relativeLayout = findViewById(R.id.sign_in_layout);
        toolbar = findViewById(R.id.singin_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mEmail = findViewById(R.id.login_email);
        mPassword = findViewById(R.id.login_password);
        mSignInBtn = findViewById(R.id.singin_button);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("Please wait a second");
        progressDialog.setCanceledOnTouchOutside(false);
        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();

                hideKeyboard();

                if ((!TextUtils.isEmpty(email)) && (!TextUtils.isEmpty(password))){

                    progressDialog.show();
                    loginUser(email,password);


                }else {
                    Snackbar snackbar = Snackbar.make(view,"Make sure you filled the spaces",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        });





    }
    private void loginUser(String email, String password){

        mFirebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    progressDialog.setMessage("You're ready to go!");
                    progressDialog.dismiss();
                    Intent main = new Intent(SingInActivity.this,MainActivity.class);
                    main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(main);
                    finish();

                }else{

                    progressDialog.dismiss();

                    Snackbar snackbar = Snackbar.make(relativeLayout,"Something went Wrong, can't log in",Snackbar.LENGTH_LONG);
                    snackbar.show();

                }
            }
        });



    }

    public void hideKeyboard(){

        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
