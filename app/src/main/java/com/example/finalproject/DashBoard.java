package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashBoard extends AppCompatActivity {
    Button Homebtn,Historybtn,Usersbtn;
    String DBusername;
    Button  Accountbtn,SendMoneybtn;
    TextView currentbalance,displayUserName;
    AlertDialog.Builder mydialog;

    FloatingActionButton addbalancebtn;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        firebaseAuth = FirebaseAuth.getInstance();
        Usersbtn = findViewById(R.id.Userbtn);
        Accountbtn =  findViewById(R.id.Accountbtn);
        addbalancebtn = findViewById(R.id.addbalancebtn);
        currentbalance = findViewById(R.id.currentbalance);
        displayUserName =  findViewById(R.id.displayUserName);
        SendMoneybtn = findViewById(R.id.SendMoneybtn);

        getSupportActionBar().setTitle("DashBoard");

        SendMoneybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(DashBoard.this,SendMoney.class);
                startActivity(intent);
            }
        });


        Usersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(DashBoard.this,UsersActivity.class);
                startActivity(intent);
            }
        });
        Accountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(DashBoard.this,AccountActivity.class);
                startActivity(intent);
            }
        });
        addbalancebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mydialog = new AlertDialog.Builder(DashBoard.this);
                mydialog.setTitle("Enter Balance");

                EditText balanceInput = new EditText(DashBoard.this);
                balanceInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                mydialog.setView(balanceInput);

                mydialog.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        currentbalance.setText(new StringBuilder().append("₱ ").append(balanceInput.getText()).append(".00").toString());
                        dialogInterface.dismiss();

                    }
                });
                mydialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                mydialog.create().show();
            }
        });

    }







    private void checkUserstatus(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null){

        }
        else{
            startActivity(new Intent(DashBoard.this,Login.class));
            finish();
        }
    }


    public void onStart() {
        checkUserstatus();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout){
            firebaseAuth.signOut();
            checkUserstatus();

        }
        return super.onOptionsItemSelected(item);
    }
}