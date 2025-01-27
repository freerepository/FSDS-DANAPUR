package com.sedulous.fsdsdanapur.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sedulous.fsdsdanapur.Utils.O;
import com.sedulous.fsdsdanapur.R;
public class NextTrainScreenFragment extends Fragment {

    private AppCompatButton bt_next;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_next_train_screen, container, false);
        bt_next = view.findViewById(R.id.bt_next);
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment homeFragment = new HomeFragment();
                O.loadFragments(homeFragment, requireActivity().getSupportFragmentManager(), R.id.content_frame);
            }
        });
        return view;
    }
}