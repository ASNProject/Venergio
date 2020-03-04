package com.asnproject.venergio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText username, password;
    SessionSharePreference session;
    private String KEY_NAME = "Username";
    private ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();


        session = new SessionSharePreference(MainActivity.this.getApplicationContext());

        username = findViewById(R.id.editText4);
        password = findViewById(R.id.editText5);
        String Lusername = session.getUsername();
        String Lpassword = session.getPassword();
        username.setText(Lusername);
        password.setText(Lpassword);
        String tusername = username.getText().toString();

        if ((username.equals(Lusername)) && (password.equals(Lpassword))){
            Intent intent = new Intent(MainActivity.this, Halaman1.class);
            startActivity(intent);
            finish();


        }


            Button btnLogin = findViewById(R.id.buttonlogin);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String setusername = username.getText().toString();
                    String setpasswaord = password.getText().toString();
                    final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);

                    /////autologin
                    if (checkBox.isChecked()){
                        if (setusername.equals("")){
                            Toast.makeText(MainActivity.this, "Please Input Username", Toast.LENGTH_SHORT).show();
                        }
                        else if (setpasswaord.equals("")){
                            Toast.makeText(MainActivity.this, "Please Input Password", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            mAuth.signInWithEmailAndPassword(setusername, setpasswaord)
                                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()){
                                                FirebaseUser user = mAuth.getCurrentUser();

                                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(MainActivity.this, Halaman1.class);
                                                startActivity(intent);
                                                String Susername = String.valueOf(username.getText());
                                                String Spassword = String.valueOf(password.getText());
                                                session.setUsername(Susername);
                                                session.setPassword(Spassword);
                                                finish();
                                                username.setText("");
                                                password.setText("");

                                                loading = ProgressDialog.show(MainActivity.this,
                                                        null,
                                                        "Please wait...",
                                                        true,
                                                        false);


                                            }
                                            else {
                                                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                                username.setText("");
                                                password.setText("");
                                            }
                                        }
                                    });

                        }////akhir

                    }
                    ///tanpa auto login
                    else {
                        if (setusername.equals("")){
                            Toast.makeText(MainActivity.this, "Please Input Username", Toast.LENGTH_SHORT).show();
                        }
                        else if (setpasswaord.equals("")){
                            Toast.makeText(MainActivity.this, "Please Input Password", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            mAuth.signInWithEmailAndPassword(setusername, setpasswaord)
                                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()){
                                                FirebaseUser user = mAuth.getCurrentUser();

                                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(MainActivity.this, Halaman1.class);
                                                startActivity(intent);
                                                finish();
                                                username.setText("");
                                                password.setText("");
                                               // loading = ProgressDialog.show(MainActivity.this,
                                                 //       null,
                                                   //     "Please wait...",
                                                     //   true, false);
                                            }
                                            else {
                                                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                                username.setText("");
                                                password.setText("");
                                            }
                                        }
                                    });

                        }////akhir
                    }
                }
            });



        TextView btnregister = findViewById(R.id.text1);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goregister = new Intent(MainActivity.this, Register.class);
                startActivity(goregister);
                finish();
            }
        });





    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

}
