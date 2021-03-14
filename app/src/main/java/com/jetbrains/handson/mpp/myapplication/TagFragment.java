package com.jetbrains.handson.mpp.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class TagFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_layout, null);
        ((TextView)v.findViewById(R.id.fragment_title)).setText(getFragmentTag());
        ((Button)v.findViewById(R.id.next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNext();
            }
        });
        ((Button)v.findViewById(R.id.previous)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPrevious();
            }
        });


        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onPrevious(){
        ((OnFragmentChangeListener)getActivity()).onPrevious(getFragmentTag());
    }

    public void onNext(){
        ((OnFragmentChangeListener)getActivity()).onNext(getFragmentTag());
    }

    abstract String getFragmentTag();
}
