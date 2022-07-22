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

public class SendMoney extends AppCompatActivity {
    String SMusername;
    TextView SMdisplayUserName,SMcurrentbalance;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_money);

        SMdisplayUserName = findViewById(R.id.SMdisplayUserName);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        SMcurrentbalance = findViewById(R.id.SMcurrentbalance);

        if (firebaseUser == null){

        }else
        {
            ShowMainScreen(firebaseUser);

        }

    }
    private void ShowMainScreen(FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
        referenceProfile.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelUsers modelUsers = snapshot.getValue(ModelUsers.class);
                if (modelUsers != null) {
                    SMusername = modelUsers.usersName;
                   

                    SMdisplayUserName.setText(SMusername);


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SendMoney.this, "Something went Wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}