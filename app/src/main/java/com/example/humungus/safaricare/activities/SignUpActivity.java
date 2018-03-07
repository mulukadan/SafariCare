package com.example.humungus.safaricare.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.humungus.safaricare.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText emailET, passwordET, usernameET;
    private Button createAccBtn;

    private FirebaseAuth mAuth;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        emailET = (EditText)findViewById(R.id.signupemail);
        passwordET = (EditText)findViewById(R.id.signuppassword);
        usernameET = (EditText)findViewById(R.id.signupusername);

        pd = new ProgressDialog(this);

        createAccBtn = (Button) findViewById(R.id.signupbtn);

        createAccBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signupbtn: //Create Account
                pd.setMessage("Creating Account...");
                pd.setCancelable(false);
                registerUser();
                break;
        }
    }

    public void registerUser(){
        final String Email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        final String username = usernameET.getText().toString().trim();

        if(username.isEmpty()){
            usernameET.setError("Username is required");
            usernameET.requestFocus();
            return;
        }
        if(Email.isEmpty()){
            emailET.setError("Email is required");
            emailET.requestFocus();

            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            emailET.setError("Please enter a valid Email");
            emailET.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passwordET.setError("Password is required");
            passwordET.requestFocus();
            return;
        }
        if(password.length()<6){
            passwordET.setError("Password should be atleast 6 characters");
            passwordET.requestFocus();
            return;
        }


        pd.show();
        mAuth.createUserWithEmailAndPassword(Email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            pd.hide();
                            finish();
                            Toast.makeText(getApplicationContext(),"Account Created Successfuly", Toast.LENGTH_SHORT ).show();
                            //Setting User Display name
                            FirebaseUser user = mAuth.getCurrentUser();

                            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .build();
                            user.updateProfile(profile)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getApplicationContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });


                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            pd.hide();
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(SignUpActivity.this, Email+": is already registered!!",
                                        Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}
