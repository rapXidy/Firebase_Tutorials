package com.example.firebasetutorial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
private EditText etName, etEmail, etPhone, etPass, etConfirm;
private Button btnSignUp;

private String name, email, phone, password, comfirm;
private FirebaseAuth mAuth;
private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressDialog = new ProgressDialog(RegistrationActivity.this);
        mAuth = FirebaseAuth.getInstance();
        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPass = (EditText) findViewById(R.id.etPass);
        etConfirm = findViewById(R.id.etConfirm);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });



    }

    private void validate() {
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        email = etEmail.getText().toString().trim();
        password = etPass.getText().toString().trim();
        comfirm = etConfirm.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            etEmail.setError("pls input email");
            return;
        }
        if (TextUtils.isEmpty(password) || !password.matches(comfirm)){
            etPass.setError("invalid passsword");
        }else {
            registerOnFirebase();
        }
    }

    private void registerOnFirebase() {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    progressDialog.dismiss();
                }

                else {
                    progressDialog.dismiss();
                    String m = task.getException().toString();
                    Toast.makeText(RegistrationActivity.this, m, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
