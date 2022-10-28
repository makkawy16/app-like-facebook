package com.example.facebook.UI;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.facebook.Model.PostModel;
import com.example.facebook.R;
import com.example.facebook.databinding.FragmentAddNewPostBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

        binding.addPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPost(userId);
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





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}