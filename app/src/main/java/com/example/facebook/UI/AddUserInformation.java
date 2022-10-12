package com.example.facebook.UI;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.facebook.R;
import com.example.facebook.databinding.ActivityAddUserInformationBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddUserInformation extends AppCompatActivity {

    String name, age, gender, phone, email, password, imgurl;
    Uri imguri;

    ActivityAddUserInformationBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserInformationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //checkGender();
        initUi();
        Log.d("dddddddd" ,""+email);
        Log.d("dddddddd" ,""+password);


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

    }

    private void addUserToDatabase() {
        initUi();
        checkGender();



    }

    private void addPhotoToStorage(Uri imguri) {

        final StorageReference file = storageReference.child("test img");

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


    private void checkGender() {
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