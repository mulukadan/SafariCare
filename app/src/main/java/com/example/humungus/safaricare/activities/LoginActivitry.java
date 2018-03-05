package com.example.humungus.safaricare.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.humungus.safaricare.R;
import com.example.humungus.safaricare.SlideViewActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivitry extends AppCompatActivity implements View.OnClickListener {

    private EditText emailET, passwordET;
    private Button loginBtn, joinBtn, learnMoreBtn;

    private FirebaseAuth mAuth;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activitry);

        mAuth = FirebaseAuth.getInstance();

        emailET = (EditText)findViewById(R.id.email);
        passwordET = (EditText)findViewById(R.id.password);

        pd = new ProgressDialog(this);

        loginBtn = (Button) findViewById(R.id.loginbtn);
        joinBtn = (Button) findViewById(R.id.joinbtn);
        learnMoreBtn = (Button) findViewById(R.id.learnmorebtn);

        loginBtn.setOnClickListener(this);
        joinBtn.setOnClickListener(this);
        learnMoreBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginbtn: //Login
                pd.setMessage("Logging in...");
                pd.setCancelable(false);
                logInUser();
                break;
            case R.id.joinbtn: //To Create Account Page
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.learnmorebtn: //To learn More
                Intent i = new Intent(getApplicationContext(), SlideViewActivity.class);
                startActivity(i);
                break;
        }
    }

    public void logInUser(){
        final String Email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
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


        mAuth.signInWithEmailAndPassword(Email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            pd.hide();
                            finish();
                            Toast.makeText(getApplicationContext(),"Login successful", Toast.LENGTH_SHORT ).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            pd.hide();
                            Toast.makeText(getApplicationContext(),"Wrong Email or Password!!", Toast.LENGTH_SHORT ).show();
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
            Toast.makeText(getApplicationContext(), "Welcome back: " + currentUser.getEmail(), Toast.LENGTH_LONG).show();
            finish();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}
