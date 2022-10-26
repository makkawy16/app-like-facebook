package com.example.facebook.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.facebook.R;
import com.example.facebook.databinding.FragmentAddNewPostBinding;

public class AddNewPostFragment extends Fragment {

    FragmentAddNewPostBinding binding;

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

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}