package com.example.jobportalcorporate;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    EditText mailid,pwd,pwd1;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mailid= findViewById(R.id.et_signup_email);
        pwd= findViewById(R.id.et_signup_pwd);
        pwd1= findViewById(R.id.et_signup_pwd1);

        mAuth=FirebaseAuth.getInstance();
        findViewById(R.id.btn_signup).setOnClickListener(this);
    }
    private void registerUser(){
        String email = mailid.getText().toString().trim();
        String password = pwd.getText().toString().trim();
        String password1 = pwd1.getText().toString().trim();


        if (email.isEmpty()){
            mailid.setError("Email is required");
            mailid.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mailid.setError("Please enter valid email");
            mailid.requestFocus();
            return;
        }

        if (password.isEmpty()){
            pwd.setError("Password is required");
            pwd.requestFocus();
            return;
        }
        if (password1.isEmpty()){
            pwd1.setError("Password is required");
            pwd1.requestFocus();
            return;
        }

        if (password.length()<6){
            pwd.setError("Minimum length should be 6");
            pwd.requestFocus();
            return;
        }

        if (!password1.equals(password)){
            pwd1.setError("Password don't match");
            pwd1.requestFocus();
            return;
        }





        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if (task.isSuccessful()){

                Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent=new Intent(SignUpActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }else {
                if (task.getException() instanceof FirebaseAuthUserCollisionException){
                    Toast.makeText(SignUpActivity.this, "Email is already registered", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SignUpActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        // Replacing switch with if-else statement
        if (v.getId() == R.id.btn_signup) {
            registerUser();
        }
    }

}
