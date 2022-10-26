package com.example.facebook.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.facebook.R;
import com.example.facebook.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private String email, password;
    private FirebaseAuth mAuth;
    private ProgressDialog mloadingBar;
    private FirebaseUser currentUser;
    private ActivityMainBinding binding;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().hide();

        initUi();

        binding.forgettxt.setMovementMethod(LinkMovementMethod.getInstance());

        currentUser = mAuth.getCurrentUser();

        if (currentUser != null)
            startActivity(new Intent(MainActivity.this, HomePage.class));
        else
            Toast.makeText(this, "must make a Login", Toast.LENGTH_SHORT).show();


        binding.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, CreateNewAccount.class));
                finish();
            }
        });
        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.email.getText().toString().isEmpty())
                    binding.email.setError("Please Enter Email or Phone Number");
                else if (binding.passText.getText().toString().isEmpty())
                    binding.passText.setError("PLease Enter Password");
                else
                    signin();

            }
        });


    }




    void signin() {
        initUi();
        waitnig();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(MainActivity.this, HomePage.class));
                            mloadingBar.dismiss();
                            finish();

                        } else {
                            Toast.makeText(MainActivity.this, "wrong email or password  " + task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            mloadingBar.dismiss();

                        }

                    }
                });


    }

    private void initUi() {
        email = binding.email.getText().toString();
        password = binding.passText.getText().toString();
        mAuth = FirebaseAuth.getInstance();
        mloadingBar = new ProgressDialog(MainActivity.this);
        login = findViewById(R.id.loginbtn);
    }

    private void waitnig() {
        mloadingBar.setTitle("Signin");
        mloadingBar.setMessage("Please Wait");
        mloadingBar.setCanceledOnTouchOutside(false);
        mloadingBar.show();
    }

}