package com.example.otp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class otp_page extends AppCompatActivity {
    public static  String pres_name="my prefs";
    Button proceed;
    EditText code;
    String verificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_page);
        verificationId=getIntent().getStringExtra("verification");
        proceed=(Button) findViewById(R.id.proceed);
        code=(EditText) findViewById(R.id.digit1);
        proceedAction();
    }
    void proceedAction(){
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(code.getText().toString().trim().isEmpty()){
                    Toast.makeText(otp_page.this, "please enter the otp", Toast.LENGTH_SHORT).show();
                }else if((code.getText().toString().trim()).length()==6){
                    if(verificationId!=null){
                        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(
                                verificationId,code.getText().toString().trim()
                        );
                        FirebaseAuth.getInstance().signInWithCredential(credential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            SharedPreferences sharedPreferences=getSharedPreferences(otp_page.pres_name,0);
                                            SharedPreferences.Editor editor=sharedPreferences.edit();

                                            editor.putBoolean("haslogged",true);
                                            editor.commit();

                                            Intent intent=new Intent(getApplicationContext(),Home.class);
                                            startActivity(intent);

                                        }
                                    }
                                });


                    }

                }else{
                    Toast.makeText(otp_page.this, "please enter the valid otp", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}