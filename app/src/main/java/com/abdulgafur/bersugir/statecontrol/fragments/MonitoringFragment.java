package com.abdulgafur.bersugir.statecontrol.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.abdulgafur.bersugir.statecontrol.Adapters.MonitoringAdapter;
import com.abdulgafur.bersugir.statecontrol.Products.MonitoringProduct;
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


public class MonitoringFragment extends Fragment {


    public MonitoringFragment() {
        // Required empty public constructor
    }

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ListView listView;
    MonitoringAdapter adapter;
    ArrayList<MonitoringProduct> list;
    String token;

    private ArrayList<String> mKeys = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_monitoring, container,false);

        findId(v);
        if (user != null) {
            mDatabase.child("Users").child(user.getUid()).child("token").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    token = dataSnapshot.getValue(String.class);
                    mDatabase.child("Control").child(token).child("Monitoring").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            list.add(new MonitoringProduct(dataSnapshot.child("name").getValue(String.class), String.valueOf(dataSnapshot.child("Moisture/" + "value").getValue()), dataSnapshot.child("Moisture/" + "State").getValue(String.class), String.valueOf(dataSnapshot.child("Temperature/" + "value").getValue()), dataSnapshot.child("Temperature/" + "State").getValue(String.class), String.valueOf(dataSnapshot.child("Humidity/" + "value").getValue()), dataSnapshot.child("Humidity/" + "State").getValue(String.class)));
                            adapter.notifyDataSetChanged();
                            String key = dataSnapshot.getKey();
                            mKeys.add(key);
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                            String key = dataSnapshot.getKey();
                            int index = mKeys.indexOf(key);
                            list.set(index, new MonitoringProduct(dataSnapshot.child("name").getValue(String.class), String.valueOf(dataSnapshot.child("Moisture/" + "value").getValue()), dataSnapshot.child("Moisture/" + "State").getValue(String.class), String.valueOf(dataSnapshot.child("Temperature/" + "value").getValue()), dataSnapshot.child("Temperature/" + "State").getValue(String.class), String.valueOf(dataSnapshot.child("Humidity/" + "value").getValue()), dataSnapshot.child("Humidity/" + "State").getValue(String.class)));
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                            String key = dataSnapshot.getKey();
                            int index = mKeys.indexOf(key);
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
        }

        return v;
    }

    public void findId(View v){
        listView = v.findViewById(R.id.listViewMonitoring);
        list = new ArrayList<>();
        adapter = new MonitoringAdapter(getActivity(), list);
        listView.setAdapter(adapter);
    }
}
