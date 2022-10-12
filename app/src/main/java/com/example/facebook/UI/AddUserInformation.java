package com.example.facebook.UI;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.facebook.Model.UserModel;
import com.example.facebook.R;
import com.example.facebook.databinding.ActivityAddUserInformationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddUserInformation extends AppCompatActivity {

    String name, age, gender, phone, email, password, imgurl;
    Uri imguri;
    public final String dataBaseName = "users";

    ActivityAddUserInformationBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserInformationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initUi();
        //   Log.d("dddddddd" ,""+email);
        //   Log.d("dddddddd" ,""+password);


        binding.continueBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserToDatabase();
            }
        });


        binding.profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhoto();
            }
        });


    }

    private void initUi() {
        name = binding.username.getText().toString();
        age = binding.age.getText().toString();
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        phone = binding.phoneNumber.getText().toString();
        gender = checkGender();
        userId = mAuth.getUid();
    }

    private void addUserToDatabase() {
        initUi();
        //  checkGender();
        UserModel userModel = new UserModel(userId, name, email, password, phone, imgurl, gender, age);
        databaseReference.child(dataBaseName).child(userId + " " +name).setValue(userModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(AddUserInformation.this, "Welcome " + name, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddUserInformation.this,welcome.class));
                            finish();
                        }
                        else
                            alertDialog("Error " + task.getException().getLocalizedMessage());
                    }
                });


    }

    private void addPhotoToStorage(Uri imguri) {

        final StorageReference file = storageReference.child(userId + "  " + name);

        file.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imgurl = uri.toString();
                        Toast.makeText(AddUserInformation.this, "upload success", Toast.LENGTH_SHORT).show();
                        Log.d("ddddddddd", "" + imgurl);

                    }
                });
            }
        });


    }


    private ActivityResultLauncher<String> imageLancher = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    binding.profilePicture.setImageURI(result);
                    imguri = result;
                    addPhotoToStorage(imguri);
                }
            });


    private ActivityResultLauncher<String> requestPermissionLancher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    getImage();
                } else {
                    Toast.makeText(this, "need permission to access gallery ", Toast.LENGTH_SHORT).show();
                }
            });


    private void getImage() {
        imageLancher.launch("image/*");
    }

    private void addPhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            imageLancher.launch("image/*");
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            alertDialog("need this Permission to add your photo");
        } else {
            requestPermissionLancher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }


    private String checkGender() {
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (binding.genderMale.isChecked() || binding.genderFemale.isChecked()) {
                    switch (checkedId) {

                        case R.id.gender_male: {
                            Toast.makeText(AddUserInformation.this, "male", Toast.LENGTH_SHORT).show();
                            gender = "Male";

                        }
                        break;
                        case R.id.gender_female: {
                            Toast.makeText(AddUserInformation.this, "female", Toast.LENGTH_SHORT).show();
                            gender = "Female";
                        }

                        break;
                        default:
                            Toast.makeText(AddUserInformation.this, "Select your Gender", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddUserInformation.this, "Select your Gender", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return gender;
    }

    private void alertDialog(String msg) {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(msg);
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissionLancher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                dialog.dismiss();
            }
        });
        dialog.show();


    }


}