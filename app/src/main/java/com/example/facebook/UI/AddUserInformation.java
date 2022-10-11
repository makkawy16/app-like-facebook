package com.example.facebook.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.facebook.R;
import com.example.facebook.databinding.ActivityAddUserInformationBinding;

public class AddUserInformation extends AppCompatActivity {


    ActivityAddUserInformationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserInformationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        checkGender();



    }


    private void checkGender(){
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (binding.genderMale.isChecked() || binding.genderFemale.isChecked()){
                    switch(checkedId){

                        case R.id.gender_male:
                            Toast.makeText(AddUserInformation.this, "male", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.gender_female:
                            Toast.makeText(AddUserInformation.this, "female", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(AddUserInformation.this, "Select your Gender", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(AddUserInformation.this, "Select your Gender", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}