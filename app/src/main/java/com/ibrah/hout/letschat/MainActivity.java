package com.ibrah.hout.letschat;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ibrah.hout.letschat.Adapters.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Toolbar mToolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mToolbar =  findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Let's Chat!");
        mToolbar.setTitle("Let's Chat!");

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.main_tabs);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);



    }


    @Override
    public void onStart() {
        super.onStart();


        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();

        if (currentUser==null){
            singOutAndUpdateUI();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        int selection = item.getItemId();
        switch (selection){

            case R.id.main_menu_logout_button:
                singOutAndUpdateUI();
                break;

            case R.id.main_menu_account_settings:
                Intent goToSettings = new Intent(MainActivity.this,ProfileSettingsActivity.class);
                startActivity(goToSettings);

                break;

            case R.id.main_menu_all_users:
                Intent goToUsers = new Intent(MainActivity.this,UsersActivity.class);
                startActivity(goToUsers);

                break;


            default:
                break;

        }

        return true;
    }

    public void singOutAndUpdateUI(){

            FirebaseAuth.getInstance().signOut();
            Intent startIntent = new Intent();
            startIntent.setClass(MainActivity.this,StartActivity.class);
            startActivity(startIntent);
            finish();


    }

}