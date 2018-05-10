package com.abdulgafur.bersugir.statecontrol;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    Button SignInButton, SignUpButton;
    EditText SignInEmailField, SignInPassField;
    EditText SignUpNameField, SignUpEmailField, SignUpPassField, SignUpPassConfirmField;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TabHost host = findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Sign In");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Sign Up");
        host.addTab(spec);

        host.setOnTabChangedListener(new AnimatedTabHostListener(host));

        SignInButton = findViewById(R.id.SignInButton);
        SignUpButton = findViewById(R.id.SignUpButton);

        SignInEmailField = findViewById(R.id.SignInEmailField);
        SignInPassField = findViewById(R.id.SignInPassField);

        SignUpNameField = findViewById(R.id.SignUpNameField);
        SignUpEmailField = findViewById(R.id.SignUpEmailField);
        SignUpPassField = findViewById(R.id.SignUpPassField);
        SignUpPassConfirmField = findViewById(R.id.SignUpPassConfirmField);


        mAuth = FirebaseAuth.getInstance();

        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn(view);
            }
        });
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp(view);
            }
        });
    }

    // Sign In
    public void SignIn(final View view) {
        if (isEmpty(SignInEmailField) || isEmpty(SignInPassField)) {
            Toast.makeText(Login.this, "Please enter empty fields!", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.signInWithEmailAndPassword(SignInEmailField.getText().toString(), SignInPassField.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(Login.this, "Successfully!", Toast.LENGTH_SHORT).show();

                        Intent intentToMain = new Intent(Login.this, NavigationBarActivity.class);
                        startActivity(intentToMain);

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(Login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        //updateUI(null);
                    }
                }
            });
        }
    }

    // Sign Up
    public void SignUp(final View view) {
        if (isEmpty(SignUpEmailField) || isEmpty(SignUpNameField) || isEmpty(SignUpPassField) || isEmpty(SignUpPassConfirmField)) {
            Toast.makeText(Login.this, "Please enter empty fields!", Toast.LENGTH_SHORT).show();
        } else {
            if (SignUpPassField.getText().toString().equals(SignUpPassConfirmField.getText().toString())) {
                mAuth.createUserWithEmailAndPassword(SignUpEmailField.getText().toString(), SignUpPassField.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Login.this, "Account has been created!", Toast.LENGTH_SHORT).show();

                            mDatabase = FirebaseDatabase.getInstance().getReference();

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(SignUpNameField.getText().toString())
                                    .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
                                    .build();
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                //
                                            }
                                        }
                                    });


                            String token = mDatabase.push().getKey();

                            assert user != null;
                            writeNewUser(user.getUid(), SignUpNameField.getText().toString(), SignUpEmailField.getText().toString(), token);

                            Intent intentToMain = new Intent(Login.this, NavigationBarActivity.class);
                            startActivity(intentToMain);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(Login.this, "Passwords are not same", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Check is empty?
    public boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void writeNewUser(String userId, String name, String email, String token) {
        User user = new User(token);

        mDatabase.child("Users").child(userId).setValue(user);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        // FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

}