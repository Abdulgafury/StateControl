package com.abdulgafur.bersugir.statecontrol.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abdulgafur.bersugir.statecontrol.R;

public class SendFragment extends Fragment {


    public SendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_send, container,false);

        Button sendButton = v.findViewById(R.id.sendButton);
        final EditText subjectTextField = v.findViewById(R.id.subjectTextField);
        final EditText messageTextField = v.findViewById(R.id.messageTextField);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"bersugur.abdulgafur@mail.ru"});
                i.putExtra(Intent.EXTRA_SUBJECT, subjectTextField.getText().toString());
                i.putExtra(Intent.EXTRA_TEXT   , messageTextField.getText().toString());
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }
}
