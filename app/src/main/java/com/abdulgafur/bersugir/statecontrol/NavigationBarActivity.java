package com.abdulgafur.bersugir.statecontrol;


import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abdulgafur.bersugir.statecontrol.fragments.AboutFragment;
import com.abdulgafur.bersugir.statecontrol.fragments.ManageFragment;
import com.abdulgafur.bersugir.statecontrol.fragments.MonitoringFragment;
import com.abdulgafur.bersugir.statecontrol.fragments.SendFragment;
import com.abdulgafur.bersugir.statecontrol.fragments.SettingsFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class NavigationBarActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ManageFragment fmanage;
    MonitoringFragment fmonitoring;
    SettingsFragment fsettings;
    AboutFragment fabout;
    SendFragment fsend;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    StorageReference mStorage = FirebaseStorage.getInstance().getReference();

    @SuppressLint("Assert")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fmonitoring = new MonitoringFragment();
        fmanage = new ManageFragment();
        fsettings = new SettingsFragment();
        fabout = new AboutFragment();
        fsend = new SendFragment();



        View headerNavBar = navigationView.getHeaderView(0);
        TextView navBarUser = headerNavBar.findViewById(R.id.NavBarUsernameField);
        TextView navBarEmail = headerNavBar.findViewById(R.id.NavBarEmailField);
        final ImageView navBarImageView = headerNavBar.findViewById(R.id.navBarImageView);

        FragmentTransaction ftrans = getFragmentManager().beginTransaction();
        ftrans.replace(R.id.container, fmonitoring);
        ftrans.commit();


        if (user != null) {

            // Set username and Email
            navBarEmail.setText(user.getEmail());
            if (user.getDisplayName() != null) {
                navBarUser.setText(user.getDisplayName());
            }


            mStorage.child("UserPhotos/" + user.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    // Got the download URL for 'users/me/profile.png'
                    Picasso.with(NavigationBarActivity.this)
                            .load(uri)
                            .transform(new CircleTransform())
                            .resize(175, 175)
                            .centerCrop()
                            .into(navBarImageView);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

            // https://www.youtube.com/user/akshayejh

            // http://square.github.io/picasso/

            View parentLayout = findViewById(android.R.id.content);

            navBarEmail.setText(user.getEmail());

            Snackbar.make(parentLayout, "Connected!", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                    .show();
        } else {
            Intent intentToLogin = new Intent(NavigationBarActivity.this, Login.class);
            startActivity(intentToLogin);
        }




    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        final int id = item.getItemId();

        android.app.FragmentTransaction ftrans = getFragmentManager().beginTransaction();

        if (id == R.id.nav_monitoring) {
            ftrans.replace(R.id.container, fmonitoring);
        } else if (id == R.id.nav_manage) {
            ftrans.replace(R.id.container, fmanage);
        } else if (id == R.id.nav_settings) {
            ftrans.replace(R.id.container, fsettings);
        } else if (id == R.id.nav_about) {
            ftrans.replace(R.id.container, fabout);
        } else if (id == R.id.nav_send) {
            ftrans.replace(R.id.container, fsend);
        }

        ftrans.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
