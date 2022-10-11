package com.example.facebook.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.facebook.R;
import com.google.firebase.auth.FirebaseAuth;

public class welcome extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //getSupportActionBar().hide();
        mAuth=FirebaseAuth.getInstance();

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menuu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout:
                mAuth.signOut();
                finish();
                startActivity(new Intent(welcome.this, MainActivity.class));
              //  finish();
            default:
                return super.onOptionsItemSelected(item);
        }


    }




}