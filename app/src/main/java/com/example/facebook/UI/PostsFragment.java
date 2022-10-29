package com.example.facebook.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.facebook.R;
import com.example.facebook.UI.Adapter.PostsAdapter;
import com.example.facebook.data.Model.PostModel;
import com.example.facebook.databinding.FragmentPostsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PostsFragment extends Fragment {

    FragmentPostsBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    PostsAdapter postsAdapter;
    List<PostModel> postsList = new ArrayList<>();
    String name, profilePicUrl, postimgUrl, postTitle;
    String userId = FirebaseAuth.getInstance().getUid();

    public PostsFragment() {
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
        return inflater.inflate(R.layout.fragment_posts, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentPostsBinding.bind(view);
        if (isInternetConnected())
            Toast.makeText(getActivity(), "internet Wroking", Toast.LENGTH_SHORT).show();
        else
            alertDialog("Error", "No Internet Connection");
        initRecycler();
        getPosts();

        binding.addPostLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView())
                        .navigate(R.id.action_postsFragment2_to_addNewPostFragment2);
            }
        });
    }

    private void initRecycler() {
        postsAdapter = new PostsAdapter();
        binding.postsRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.postsRecycler.setAdapter(postsAdapter);
    }

    private void getPosts() {


        databaseReference.child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postsList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    postsList.add(snapshot1.getValue(PostModel.class));
                }
                postsAdapter.addPost(postsList, getContext());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean isInternetConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            if (networkInfo.isConnected())
                return true;
            else
                return false;
        } else
            return false;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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
}