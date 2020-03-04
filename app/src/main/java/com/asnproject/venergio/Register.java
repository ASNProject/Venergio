package com.asnproject.venergio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etUsername, etEmail, etPassowrd;
    private ProgressDialog loading;
    private static final String TAG = "venergio";
    private DatabaseReference database;
    private String sPid, sPnama, sPemail, sPdesk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.editText6);
        etPassowrd = findViewById(R.id.editText5);
        etUsername = findViewById(R.id.editText4);

        sPnama = getIntent().getStringExtra("title");
        sPemail = getIntent().getStringExtra("email");
        sPdesk = getIntent().getStringExtra("desk");

        etUsername.setText(sPnama);
        etEmail.setText(sPemail);
        etPassowrd.setText(sPdesk);

        database = FirebaseDatabase.getInstance().getReference();

        Button btnRegister = findViewById(R.id.buttonregister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etEmail.getText().toString();
                String password = etPassowrd.getText().toString();
                final String username = etUsername.getText().toString();

                if  (email.equals("")){
                    etEmail.setError("Email is required");
                    etEmail.requestFocus();
                    return;
                }
                else if (password.equals("")){
                    etPassowrd.setError("Email is required");
                    etPassowrd.requestFocus();
                    return;
                }
                else if (username.isEmpty()){
                    etUsername.setError("Username is requires");
                    etUsername.requestFocus();
                    return;
                }

                else{


                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                 if (task.isSuccessful()){
                                     FirebaseUser user = mAuth.getCurrentUser();

                                     Toast.makeText(Register.this, "Register Successfull",
                                             Toast.LENGTH_SHORT).show();

                                     Intent i = new Intent(Register.this, MainActivity.class);
                                     startActivity(i);
                                     finish();
                                     if (user !=null ){
                                         UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                                 .setDisplayName(username)
                                                 .build();

                                         user.updateProfile(profile)
                                                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                     @Override
                                                     public void onComplete(@NonNull Task<Void> task) {
                                                         if (task.isSuccessful()){
                                                             Toast.makeText(Register.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                                         }
                                                     }
                                                 });
                                     }
                                 }
                                 else {
                                     if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                         Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                                     }
                                     else{
                                         Toast.makeText(Register.this, "Register Failed",
                                                 Toast.LENGTH_SHORT).show();
                                     }
                                 }


                                }
                            });
                    loading = ProgressDialog.show(Register.this,
                            null,
                            "Please wait...",
                            true,
                            false);

                    submitUser(new Requests(
                            username.toLowerCase(),
                            email.toLowerCase(),
                            password.toLowerCase()));




                }

            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent goLogin = new Intent(Register.this, MainActivity.class);
        startActivity(goLogin);
        finish();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void submitUser(Requests requests) {
        String username = etUsername.getText().toString();
        database.child("Data User")
                .child(username)
                .setValue(requests)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        loading.dismiss();

                        etUsername.setText("");
                        etEmail.setText("");
                        etPassowrd.setText("");

                        Toast.makeText(Register.this,
                                "Data Berhasil ditambahkan",
                                Toast.LENGTH_SHORT).show();

                    }

                });
    }
}
