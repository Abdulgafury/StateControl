package com.abdulgafur.bersugir.statecontrol.fragments;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ListView;
import android.widget.TextView;

import com.abdulgafur.bersugir.statecontrol.Adapters.ManageAdapter;
import com.abdulgafur.bersugir.statecontrol.Products.ManageProduct;
import com.abdulgafur.bersugir.statecontrol.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ManageFragment extends Fragment {


    public ManageFragment() {
        // Required empty public constructor
    }

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ListView listView;
    ManageAdapter adapter;
    ArrayList<ManageProduct> list;
    String token;

    private ArrayList<String> mKeys = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_manage, container,false);
        findId(v);


        mDatabase.child("Users").child(user.getUid()).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                token = dataSnapshot.getValue(String.class);
                mDatabase.child("Control").child(token).child("Manage").addChildEventListener(new ChildEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        list.add(new ManageProduct(1,dataSnapshot.child("name").getValue(String.class), dataSnapshot.child("Status").getValue(String.class),dataSnapshot.child("State").getValue(String.class), dataSnapshot.child("button1").getValue(String.class),dataSnapshot.child("button2").getValue(String.class),token,dataSnapshot.child("id").getValue(String.class)));

                        adapter.notifyDataSetChanged();

                        @SuppressLint("PrivateResource") LayoutAnimationController lac = new LayoutAnimationController(AnimationUtils.loadAnimation(getActivity(), R.anim.abc_fade_in), 0.5f); //0.5f == time between appearance of listview items.
                        listView.setLayoutAnimation(lac);

                        String key = dataSnapshot.getKey();
                        mKeys.add(key);

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        String key = dataSnapshot.getKey();
                        int index = mKeys.indexOf(key);
                        list.set(index, new ManageProduct(1,dataSnapshot.child("name").getValue(String.class), dataSnapshot.child("Status").getValue(String.class),dataSnapshot.child("State").getValue(String.class), dataSnapshot.child("button1").getValue(String.class),dataSnapshot.child("button2").getValue(String.class),token,dataSnapshot.child("id").getValue(String.class)));
                        adapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        String key = dataSnapshot.getKey();
                        final int index = mKeys.indexOf(key);

                        final Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);
                        // listView.startAnimation(animation);
                        listView.getChildAt(index).setAnimation(animation);


                        list.remove(index);




                        adapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }

    public void findId(View v){
        listView = v.findViewById(R.id.listViewManage);
        list = new ArrayList<>();
        adapter = new ManageAdapter(getActivity(), list);
        listView.setAdapter(adapter);
    }
}
