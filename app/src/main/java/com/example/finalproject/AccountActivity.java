package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountActivity extends AppCompatActivity {

    TextView showfullname,showEmail,showAge,showPhonenum,showAddress,showBirthdate;
    String userFullname,userEmail,userAge,userPhoneNum,userAddress,userBirthdate;
    FirebaseAuth authprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        getSupportActionBar().setTitle("Account");

        showfullname =  findViewById(R.id.showfullname);
        showEmail =  findViewById(R.id.showEmail);
        showAge =  findViewById(R.id.showAge);
        showPhonenum =  findViewById(R.id.showPhonenum);
        showAddress =  findViewById(R.id.showAddress);
        showBirthdate = findViewById(R.id.showBirthdate);

        authprofile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authprofile.getCurrentUser();

        if (firebaseUser == null){
            Toast.makeText(AccountActivity.this, "User's Details are not Available at the moment.", Toast.LENGTH_SHORT).show();
        }else
        {
            showUserProfile(firebaseUser);

        }

    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
        referenceProfile.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelUsers modelUsers = snapshot.getValue(ModelUsers.class);
                if (modelUsers != null){
                    userFullname = modelUsers.usersName;
                    userEmail = firebaseUser.getEmail();
                    userAge = modelUsers.usersAge;
                    userPhoneNum = modelUsers.usersPhoneNumber;
                    userAddress = modelUsers.usersAddress;
                    userBirthdate = modelUsers.usersBirthdate;

                    showfullname.setText(userFullname);
                    showEmail.setText(userEmail);
                    showAge.setText(userAge);
                    showPhonenum.setText(userPhoneNum);
                    showAddress.setText(userAddress);
                    showBirthdate.setText(userBirthdate);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AccountActivity.this, "Something went Wrong.", Toast.LENGTH_SHORT).show();


            }
        });
    }
}