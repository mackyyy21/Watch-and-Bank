package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    FirebaseAuth auth;
    DatePickerDialog picker;

    private EditText Sfullname,Semail,Sage,Sphonenum,Saddress,Sbirthdate,Spassword,Sconpassword;

    private CountryCodePicker ccp;



    private AlertDialog.Builder builder;

    private CheckBox checkBox;

    private Button Ssignupbtn,Scancelbtn;

    private ProgressBar SprogressBar;

    private static  String TAG  = "SignUp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Create Account");

        Toast.makeText(Register.this, "You can Register NOW!", Toast.LENGTH_LONG).show();

        Sfullname = findViewById(R.id.Sfullname);
        Semail = findViewById(R.id.Lemail);
        Sage = findViewById(R.id.Sage);
        Sphonenum = findViewById(R.id.Sphonenum);
        Saddress = findViewById(R.id.Saddress);
        Sbirthdate = findViewById(R.id.Sbirthdate);
        Spassword = findViewById(R.id.Spassword);
        Sconpassword = findViewById(R.id.Sconpassword);
        auth = FirebaseAuth.getInstance();



        ccp = findViewById(R.id.ccp);
        Ssignupbtn = findViewById(R.id.Ssignupbtn);
        Scancelbtn = findViewById(R.id.Scancelbtn);
        checkBox =  findViewById(R.id.check_id);
        builder = new AlertDialog.Builder(this);
        SprogressBar = findViewById(R.id.Sprogressbar);

        Sbirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                picker = new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                        Sbirthdate.setText((month + 1 )+ "/" + dayOfMonth + "/" + year);
                    }
                }, year,month,day);
                picker.show();
            }
        });


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    builder.setTitle("Terms and Conditions")
                            .setMessage("Terms & Conditions\n" +
                                    "By downloading or using the app, these terms will automatically apply to you – you should make sure therefore that you read them carefully before using the app. You’re not allowed to copy or modify the app, any part of the app, or our trademarks in any way. You’re not allowed to attempt to extract the source code of the app, and you also shouldn’t try to translate the app into other languages or make derivative versions. The app itself, and all the trademarks, copyright, database rights, and other intellectual property rights related to it, still belong to Mark.\n" +
                                    "\n" +
                                    "Mark is committed to ensuring that the app is as useful and efficient as possible. For that reason, we reserve the right to make changes to the app or to charge for its services, at any time and for any reason. We will never charge you for the app or its services without making it very clear to you exactly what you’re paying for.\n" +
                                    "\n" +
                                    "The Watch And Bank App app stores and processes personal data that you have provided to us, to provide my Service. It’s your responsibility to keep your phone and access to the app secure. We therefore recommend that you do not jailbreak or root your phone, which is the process of removing software restrictions and limitations imposed by the official operating system of your device. It could make your phone vulnerable to malware/viruses/malicious programs, compromise your phone’s security features and it could mean that the Watch And Bank App app won’t work properly or at all." +
                                    "\n" +
                                    "You" +
                                    " should be aware that there are certain things that Mark will not take responsibility for. Certain functions of the app will require the app to have an active internet connection. The connection can be Wi-Fi or provided by your mobile network provider, but Mark cannot take responsibility for the app not working at full functionality if you don’t have access to Wi-Fi, and you don’t have any of your data allowance left.\n" +
                                    "\n" +
                                    "If you’re using the app outside of an area with Wi-Fi, you should remember that the terms of the agreement with your mobile network provider will still apply. As a result, you may be charged by your mobile provider for the cost of data for the duration of the connection while accessing the app, or other third-party charges. In using the app, you’re accepting responsibility for any such charges, including roaming data charges if you use the app outside of your home territory (i.e. region or country) without turning off data roaming. If you are not the bill payer for the device on which you’re using the app, please be aware that we assume that you have received permission from the bill payer for using the app.\n" +
                                    "\n" +
                                    "Along the same lines, Mark cannot always take responsibility for the way you use the app i.e. You need to make sure that your device stays charged – if it runs out of battery and you can’t turn it on to avail the Service, Mark cannot accept responsibility.\n" +
                                    "\n" +
                                    "With respect to Mark’s responsibility for your use of the app, when you’re using the app, it’s important to bear in mind that although we endeavor to ensure that it is updated and correct at all times, we do rely on third parties to provide information to us so that we can make it available to you. Mark accepts no liability for any loss, direct or indirect, you experience as a result of relying wholly on this functionality of the app.\n" +
                                    "\n" +
                                    "At some point, we may wish to update the app. The app is currently available on Android – the requirements for the system(and for any additional systems we decide to extend the availability of the app to) may change, and you’ll need to download the updates if you want to keep using the app. Mark does not promise that it will always update the app so that it is relevant to you and/or works with the Android version that you have installed on your device. However, you promise to always accept updates to the application when offered to you, We may also wish to stop providing the app, and may terminate use of it at any time without giving notice of termination to you. Unless we tell you otherwise, upon any termination, (a) the rights and licenses granted to you in these terms will end; (b) you must stop using the app, and (if needed) delete it from your device.\n" +
                                    "\n" +
                                    "Changes to This Terms and Conditions\n" +
                                    "\n" +
                                    "I may update our Terms and Conditions from time to time. Thus, you are advised to review this page periodically for any changes. I will notify you of any changes by posting the new Terms and Conditions on this page.\n" +
                                    "\n" +
                                    "These terms and conditions are effective as of 2023-06-18\n" +
                                    "\n" +
                                    "Contact Us\n" +
                                    "\n" +
                                    "If you have any questions or suggestions about my Terms and Conditions, do not hesitate to contact me at marknavarez125@gmail.com.\n" +
                                    "\n" +
                                    "This Terms and Conditions page was generated by App Privacy Policy Generator")
                            .setPositiveButton("I Accept", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Ssignupbtn.setEnabled(true);
                                    dialogInterface.dismiss();
                                }
                            })
                            .setNegativeButton("Decline", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    checkBox.setChecked(false);
                                }
                            });
                    builder.show();

                }
                else{
                    Ssignupbtn.setEnabled(false);
                }
            }
        });

        Ssignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usersName = Sfullname.getText().toString();
                String usersEmail = Semail.getText().toString();
                String usersAge = Sage.getText().toString();
                String usersPhoneNumber = Sphonenum.getText().toString();
                String usersAddress = Saddress.getText().toString();
                String usersBirthdate = Sbirthdate.getText().toString();
                String usersPassword = Spassword.getText().toString();
                String usersConPassword = Sconpassword.getText().toString();
                String userId = usersName;

                String mobileregex = "[6-9][0-9]{9}";
                Matcher mobilematcher;
                Pattern mobilePattern  = Pattern.compile(mobileregex);
                mobilematcher = mobilePattern.matcher(usersPhoneNumber);

                if(TextUtils.isEmpty(usersName)){
                    Toast.makeText(Register.this, "Please Enter Your Full Name.", Toast.LENGTH_SHORT).show();
                    Sfullname.setError("Full Name is Required.");
                    Sfullname.requestFocus();
                }else if (TextUtils.isEmpty(usersEmail)){
                    Toast.makeText(Register.this, "Please Enter Your Email.", Toast.LENGTH_SHORT).show();
                    Semail.setError("Email is Required.");
                    Semail.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(usersEmail).matches()){
                    Toast.makeText(Register.this, "Please Re-Enter Your Full Name.", Toast.LENGTH_SHORT).show();
                    Semail.setError("Valid Email is Required.");
                    Semail.requestFocus();
                }else if (TextUtils.isEmpty(usersPhoneNumber)){
                    Toast.makeText(Register.this, "Please Enter Your Phone Number.", Toast.LENGTH_SHORT).show();
                    Sphonenum.setError("Phone Number is Required.");
                    Sphonenum.requestFocus();
                }else if (usersPhoneNumber.length() !=10){
                    Toast.makeText(Register.this, "Please Re-Enter Your Phone Number.", Toast.LENGTH_SHORT).show();
                    Sphonenum.setError("Phone Number should be 10 digits.");
                    Sphonenum.requestFocus();
                }else if (!mobilematcher.find()){
                    Toast.makeText(Register.this, "Please Re-Enter Your Phone Number.", Toast.LENGTH_SHORT).show();
                    Sphonenum.setError("Invalid Phone Number.");
                    Sphonenum.requestFocus();
                }else if (TextUtils.isEmpty(usersBirthdate)){
                    Toast.makeText(Register.this, "Please Enter Your Birth Date.", Toast.LENGTH_SHORT).show();
                    Sbirthdate.setError("Birth Date is Required.");
                    Sbirthdate.requestFocus();
                }else if (TextUtils.isEmpty(usersAge)){
                    Toast.makeText(Register.this, "Please Enter Your Age.", Toast.LENGTH_SHORT).show();
                    Sage.setError("Age is Required.");
                    Sage.requestFocus();
                }else if (TextUtils.isEmpty(usersAddress)) {
                    Toast.makeText(Register.this, "Please Enter Your Address.", Toast.LENGTH_SHORT).show();
                    Saddress.setError("Address is Required.");
                    Saddress.requestFocus();
                }else if (TextUtils.isEmpty(usersPassword)){
                    Toast.makeText(Register.this, "Please Enter Your Password.", Toast.LENGTH_SHORT).show();
                    Spassword.setError("Password is Required");
                    Spassword.requestFocus();
                }else if (usersPassword.length() < 6){
                    Toast.makeText(Register.this, "Password Should at least 6 digits.", Toast.LENGTH_SHORT).show();
                    Spassword.setError("Password too weak.");
                    Spassword.requestFocus();
                }else if (TextUtils.isEmpty(usersConPassword)){
                    Toast.makeText(Register.this, "Please Confirm Your Password.", Toast.LENGTH_SHORT).show();
                    Sconpassword.setError("Password Confirmation is Required.");
                    Sconpassword.requestFocus();
                }else if (!usersPassword.equals(usersConPassword)){
                    Toast.makeText(Register.this, "Password is not matched.", Toast.LENGTH_SHORT).show();
                    Sconpassword.setError("Password is not matched.");
                    Sconpassword.requestFocus();
                    Spassword.clearComposingText();
                    Sconpassword.clearComposingText();
                }else{
                    SprogressBar.setVisibility(View.VISIBLE);
                    registerUser(usersName,usersEmail,usersAge,usersPhoneNumber,usersAddress,usersBirthdate,usersPassword,userId);
                }
            }
        });

    }

    private void registerUser(String usersName, String usersEmail, String usersAge, String usersPhoneNumber, String usersAddress, String usersBirthdate, String userPassword,String userId) {
        auth.createUserWithEmailAndPassword(usersEmail,  userPassword).addOnCompleteListener(Register.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();


                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("Users");
                            ModelUsers modelUsers = new ModelUsers(usersName,usersEmail,usersAge,usersPhoneNumber,usersAddress,usersBirthdate,userId);

                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    reference.child(userId).setValue(modelUsers);

                                    firebaseUser.sendEmailVerification();



                                    Toast.makeText(Register.this, "User Registered Successfully.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Register.this, DashBoard.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                Spassword.setError("Your Password is too weak. Kindly use mix alphabet and numbers.");
                                Spassword.requestFocus();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Semail.setError("Your email is invalid or already use. Kindly re-enter.");
                                Semail.requestFocus();
                            } catch (FirebaseAuthUserCollisionException e) {
                                Semail.setError("Email is  already use to other account. Use another Email");
                                Semail.requestFocus();
                            } catch (Exception e) {
                                Log.e(TAG, e.getMessage());
                                Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                SprogressBar.setVisibility(View.GONE);
                            }
                        }

                    }
                });
    }

}
