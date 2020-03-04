package com.asnproject.venergio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Halaman1 extends AppCompatActivity {
    private TextView dataisi, isiandata;
    private FirebaseAuth mAuth;
    private DatabaseReference getDatabase;
    private DatabaseReference getReference;
    private String KEY_NAME = "username";
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman1);
        mAuth = FirebaseAuth.getInstance();
        getDatabase = FirebaseDatabase.getInstance().getReference().child("Data User");
        loadUserInformation();


    }


    private void loadUserInformation(){
        final FirebaseUser user = mAuth.getCurrentUser();
         dataisi = findViewById(R.id.isi);

        if(user != null){
            if (user.getDisplayName() != null){
               dataisi.setText(user.getDisplayName());
               final String data = dataisi.getText().toString();

               getDatabase.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                       //Membaca database
                    if (dataSnapshot.exists()){
                       // String db = dataSnapshot.child(data).child("email").getValue().toString();
                        //isiandata.setText(db);
                        String gambar1 = dataSnapshot.child("Gambar").child("Gambar1").getValue().toString();
                        String gambar2 = dataSnapshot.child("Gambar").child("Gambar2").getValue().toString();
                        String gambar3 = dataSnapshot.child("Gambar").child("Gambar3").getValue().toString();
                        String gambar4 = dataSnapshot.child("Gambar").child("Gambar4").getValue().toString();


                        ImageSlider imageSlider = findViewById(R.id.slider);

                        List<SlideModel> slideModels=new ArrayList<>();
                        slideModels.add(new SlideModel(gambar1));
                        slideModels.add(new SlideModel(gambar2));
                        slideModels.add(new SlideModel(gambar3));
                        slideModels.add(new SlideModel(gambar4));
                        imageSlider.setImageList(slideModels,true);

                    }

                       //menulis data di firebase
                       //String data = dataisi.getText().toString();
                       //getReference.child("Data User").child(data).child("isi").setValue("111");
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });
            }
        }
    }
}
