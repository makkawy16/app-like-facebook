package com.example.facebook.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.facebook.Model.UserModel;
import com.example.facebook.R;
import com.example.facebook.databinding.ActivityWelcomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class welcome extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //getSupportActionBar().hide();
        mAuth=FirebaseAuth.getInstance();

        UserModel user = (UserModel) getIntent().getExtras().getSerializable("user");
        Log.d("dddddd", "onCreate: " + user);
        Log.d("dddddddd", "onCreate: " + user.getName());
        binding.profileName.setText(user.getName());
        Picasso.get().load(user.getImgUrl()).into(binding.profilePicHome);

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