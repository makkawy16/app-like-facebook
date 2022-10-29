package com.example.facebook.UI;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.facebook.data.Model.PostModel;
import com.example.facebook.R;
import com.example.facebook.databinding.FragmentAddNewPostBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddNewPostFragment extends Fragment {

    private FragmentAddNewPostBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    String userId = FirebaseAuth.getInstance().getUid();
    private String name, profilePicURL, postTitle;
    private String imgUrl = "";
    private Uri imguri;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public AddNewPostFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentAddNewPostBinding.bind(view);
        getUserInfo(userId);

        binding.addPhotoNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addPhoto();
            }
        });

        binding.addPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPost(userId);
                Navigation.findNavController(getView())
                        .navigate(R.id.action_addNewPostFragment2_to_postsFragment2);
            }
        });

        binding.backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView())
                        .navigate(R.id.action_addNewPostFragment2_to_postsFragment2);
            }
        });


    }

    private void getUserInfo(String userId) {

        databaseReference.child(AddUserInformation.dataBaseName).child(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        name = snapshot.child("name").getValue(String.class);
                        profilePicURL = snapshot.child("imgUrl").getValue(String.class);
                        Log.d("ddddddddddd", "onDataChange: name :" + name);
                        Log.d("ddddddddddd", "onDataChange: url :" + profilePicURL);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }


    private void addPost(String userId) {
        postTitle = binding.postTitleAdd.getText().toString();
        PostModel postModel = new PostModel(userId, name, profilePicURL, postTitle, imgUrl);
        databaseReference.child("posts").child(String.valueOf(System.currentTimeMillis())).setValue(postModel);

    }

    ActivityResultLauncher<String> imageLancher = registerForActivityResult(new ActivityResultContracts.GetContent()
            , new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {
                    if (result != null) {

                        imguri = result;
                        binding.photoSelected.setImageURI(imguri);
                        addPhotoToStorage(imguri);
                    }
                }
            });

    ActivityResultLauncher<String> requestPermissionLancher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    imageLancher.launch("image/*");
                } else {
                    Toast.makeText(getActivity(), "need permission to access gallery ", Toast.LENGTH_SHORT).show();
                }
            });

    private void getImage() {
        imageLancher.launch("image/*");
    }

    private void addPhoto() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            imageLancher.launch("image/*");
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            alertDialog("Explain", "need this Permission to add your photo");
        } else {
            requestPermissionLancher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }


    private void addPhotoToStorage(Uri imguri) {
        binding.loading.setVisibility(View.VISIBLE);
        final StorageReference file = storageReference.child(String.valueOf(System.currentTimeMillis()));

        file.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imgUrl = uri.toString();
                        Toast.makeText(getActivity(), "upload success", Toast.LENGTH_SHORT).show();
                        Log.d("ddddddddd", "" + imgUrl);
                        binding.loading.setVisibility(View.GONE);

                    }
                });
            }
        });


    }


    private void alertDialog(String title, String msg) {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setMessage(msg);
        dialog.setTitle(title);
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}