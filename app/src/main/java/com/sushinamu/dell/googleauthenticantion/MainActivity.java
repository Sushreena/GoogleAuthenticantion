package com.sushinamu.dell.googleauthenticantion;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    Button sign;
    private int requestcode = 1000;
    FirebaseAuth auth;

    GoogleSignInClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sign = (Button)findViewById(R.id.btnsign);
        auth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this,gso);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signin = client.getSignInIntent();
                startActivityForResult(signin,requestcode);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = auth.getCurrentUser();
        if (currentuser != null)
        {
            Intent profile = new Intent(MainActivity.this,Profile.class);
            startActivity(profile);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestcode)
        {
            System.out.println("I am under if condition");
            Task<GoogleSignInAccount> task =GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                System.out.println("I am in try block");
                GoogleSignInAccount account = task.getResult(ApiException.class);
                FireBaseSighin(account);
            }catch (ApiException e){
                Toast.makeText(this,"Could Not Sign in",Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void FireBaseSighin(GoogleSignInAccount account){
        System.out.println("ma yaa ni aae");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            System.out.println("task is successful");
                            FirebaseUser user = auth.getCurrentUser();
                            Intent profile = new Intent(MainActivity.this,Profile.class);
                            startActivity(profile);
                            Toast.makeText(MainActivity.this,"SignIn Done",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            System.out.println("not successfull");
                        }
                    }
                });
    }


}
