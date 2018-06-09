package com.sushinamu.dell.googleauthenticantion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {

    FirebaseAuth auth;
    ImageView img;
    TextView username,email;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        img = (ImageView)findViewById(R.id.img);
        username = (TextView)findViewById(R.id.username);
        email = (TextView)findViewById(R.id.email);

        auth = FirebaseAuth.getInstance();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //FirebaseAuth.getInstance().signOut();
                finish();
                //auth.signOut();
            }
        });

        System.out.println("User information = " +FirebaseAuth.getInstance().getCurrentUser().getEmail());

        username.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        //img.setImageURI(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl());

        Picasso.with(getBaseContext())
                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl())
                .into(img);

    }
}
