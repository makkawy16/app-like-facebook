package com.example.facebook.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facebook.R;
import com.example.facebook.databinding.ActivityCreateNewAccountBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewAccount extends AppCompatActivity {

        private String email,password;
     private    FirebaseAuth mAuth;
    private ProgressDialog mloadingBar;
    ActivityCreateNewAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCreateNewAccountBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getSupportActionBar().hide();
        initUI();

       binding.createAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateUser();
            }
        });

        binding.backTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateNewAccount.this,MainActivity.class));
                finish();
            }
        });


    }


    private void initUI(){


        email=binding.email.getText().toString();
        password=binding.passText.getText().toString();
        mAuth=FirebaseAuth.getInstance();
    }
    private void CreateUser(){
        //loading
        mloadingBar = new ProgressDialog(CreateNewAccount.this);
        mloadingBar.setTitle("Signup");
        mloadingBar.setMessage("Please Wait");
        mloadingBar.setCanceledOnTouchOutside(false);
        mloadingBar.show();

        //assign edit text value to the string
        email=binding.email.getText().toString();
        password=binding.passText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(CreateNewAccount.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            mloadingBar.dismiss();
                            Intent intent = new Intent(CreateNewAccount.this,welcome.class);
                            intent.putExtra("email",email);
                            intent.putExtra("password",password);
                            startActivity(intent);

                            finish();
                        }
                        else{

                            Toast.makeText(CreateNewAccount.this , "can not create" + task.getException().getLocalizedMessage(),Toast.LENGTH_LONG).show();
                            Log.d("dddddddddd" , ""+task.getException().getLocalizedMessage());
                            mloadingBar.dismiss();
                        }

                    }
                });
    }
}

//test comment