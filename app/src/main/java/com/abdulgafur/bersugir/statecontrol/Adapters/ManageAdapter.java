package com.abdulgafur.bersugir.statecontrol.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.abdulgafur.bersugir.statecontrol.Products.ManageProduct;
import com.abdulgafur.bersugir.statecontrol.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ManageAdapter extends BaseAdapter {
    private ArrayList<ManageProduct> list;
    private Context context;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    public ManageAdapter(Context context, ArrayList<ManageProduct> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        @SuppressLint("ViewHolder") View v = View.inflate(context, R.layout.custom_list_manage, null);
        TextView name = v.findViewById(R.id.device_name);
        final TextView status = v.findViewById(R.id.device_status);
        TextView state = v.findViewById(R.id.device_state);
        Button button1 = v.findViewById(R.id.device_button1);
        Button button2 = v.findViewById(R.id.device_button2);

        button1.setText(list.get(i).getButton1());
        button2.setText(list.get(i).getButton2());
        name.setText(list.get(i).getName());
        status.setText("Status: " + list.get(i).getStatus());
        state.setText("State: " + list.get(i).getState());

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("Control").child(list.get(i).getToken()).child("Manage").child(list.get(i).getUid()).child("Status").setValue(list.get(i).getButton1()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) status.setText("Status: " + list.get(i).getButton1());
                    }
                });
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("Control").child(list.get(i).getToken()).child("Manage").child(list.get(i).getUid()).child("Status").setValue(list.get(i).getButton2()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) status.setText("Status: " + list.get(i).getButton2());
                    }
                });
            }
        });
        v.setTag(list.get(i).getId());
        return v;
    }
}
