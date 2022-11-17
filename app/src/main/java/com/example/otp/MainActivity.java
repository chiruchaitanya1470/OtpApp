package com.example.otp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText number;
    FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;

    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         button=(Button) findViewById(R.id.login);
         number=(EditText) findViewById(R.id.number);
         mAuth=FirebaseAuth.getInstance();
        buttonAction();
        pd=new ProgressDialog(getApplicationContext());
        pd.setTitle("please wait");
        pd.setCanceledOnTouchOutside(false);
        mCallback=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Intent intent=new Intent(getApplicationContext(),otp_page.class);
                intent.putExtra("verification",s);
                startActivity(intent);

            }
        } ;
    }
    void buttonAction(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(number.getText().toString().trim().isEmpty()){
                    Toast.makeText(MainActivity.this, "please the number", Toast.LENGTH_SHORT).show();
                }
                else if((number.getText().toString().trim()).length()!=10){
                    Toast.makeText(MainActivity.this, "please enter valid number", Toast.LENGTH_SHORT).show();


                }else{
                    String n1=number.getText().toString().trim();
                    startphoneNumberVerification(n1);
                }


            }
        });
    }

    private void startphoneNumberVerification(String number) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallback)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
}