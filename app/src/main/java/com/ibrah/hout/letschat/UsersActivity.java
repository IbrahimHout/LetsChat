package com.ibrah.hout.letschat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ibrah.hout.letschat.Intities.User;
import com.ibrah.hout.letschat.Intities.UserViewHolder;

public class UsersActivity extends AppCompatActivity {


    Toolbar toolbar;
    RecyclerView recyclerView;
    private DatabaseReference mUsersDatabase;
//    FirebaseRecyclerAdapter<User,UserViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);


        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        toolbar = findViewById(R.id.users_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Find a User");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.usersrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


    }

    @Override
    protected void onStart() {
        super.onStart();

//        Query query = mUsersDatabase;
//        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>().setQuery(query,User.class).build();
//        adapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull User model) {
//
//                holder.getUserName().setText(model.getName());
//
//
//            }
//
//            @Override
//            public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(UsersActivity.this).inflate(R.layout.single_user_row,null,false);
//                UserViewHolder userViewHolder = new UserViewHolder(view);
//
//                return userViewHolder;
//            }
//        };

//        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        adapter.stopListening();
    }
}
