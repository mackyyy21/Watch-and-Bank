package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class Login extends AppCompatActivity {
    Button Lsignupbtn,Lloginbtn;

    EditText Lemail,Lpassword;

    TextView forgotP;

    FirebaseAuth auth;
    ProgressBar LprogressBar;

    private static  String TAG  = "Login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Lloginbtn = findViewById(R.id.Lloginbtn);
        Lsignupbtn =  findViewById(R.id.Lsignupbtn);
        Lemail = findViewById(R.id.Lemail);
        Lpassword = findViewById(R.id.Lpassword);
        forgotP = findViewById(R.id.forgotP);
        auth = FirebaseAuth.getInstance();
        LprogressBar = findViewById(R.id.Lprogressbar);

        Lsignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
        Lloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailtxt  = Lemail.getText().toString();
                String passwordtxt = Lpassword.getText().toString();

                if (TextUtils.isEmpty(emailtxt)){
                    Toast.makeText(Login.this, "Please Enter Your Email.", Toast.LENGTH_SHORT).show();
                    Lemail.setError("Email is Required.");
                    Lemail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailtxt).matches()){
                    Toast.makeText(Login.this, "Please re-enter Your Email.", Toast.LENGTH_SHORT).show();
                    Lemail.setError("Valid Email is Required.");
                    Lemail.requestFocus();
                } else if (TextUtils.isEmpty(passwordtxt)){
                    Toast.makeText(Login.this, "Please Enter Your Password.", Toast.LENGTH_SHORT).show();
                    Lpassword.setError("Password is Required.");
                    Lpassword.requestFocus();
                    Lpassword.clearComposingText();
                } else{
                    LprogressBar.setVisibility(View.VISIBLE);
                    loginUser(emailtxt,passwordtxt);
                }
            }
        });
        forgotP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRecoverPasswordDialog();
            }
        });


    }

    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Password");

        LinearLayout linearLayout = new LinearLayout(this);

        EditText emailEt = new EditText(this);
        emailEt.setHint("Email");
        emailEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        emailEt.setMinEms(16);

        linearLayout.addView(emailEt);
        linearLayout.setPadding(10,10,10,10);

        builder.setView(linearLayout);

        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String  email = emailEt.getText().toString().trim();
                beginRecovery(email);



            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        builder.create().show();
    }

    private void beginRecovery(String email) {
        LprogressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    LprogressBar.setVisibility(View.GONE);
                    Toast.makeText(Login.this,"Email Sent",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(Login.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void loginUser(String emailtxt, String passwordtxt) {
        auth.signInWithEmailAndPassword(emailtxt,passwordtxt).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    LprogressBar.setVisibility(View.GONE);
                    Toast.makeText(Login.this,"You Successfully Login.",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this, DashBoard.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e){
                        Lemail.setError("User does not exist or no longer Valid. Pleas Register again.");
                        Lemail.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        Lemail.setError("Invalid Email.Kindly check and re-enter");
                        Lemail.requestFocus();
                    } catch (Exception e){
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                LprogressBar.setVisibility(View.GONE);
            }
        });
    }
}