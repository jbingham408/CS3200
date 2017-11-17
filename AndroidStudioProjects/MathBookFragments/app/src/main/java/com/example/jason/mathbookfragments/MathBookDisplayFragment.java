package com.example.jason.mathbookfragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jason on 11/12/15.
 */
public class MathBookDisplayFragment extends Fragment {

    int bookSelection;
    public static MathBookDisplayFragment newInstance(int index){
        MathBookDisplayFragment displayFragment = new MathBookDisplayFragment();
        Bundle args = new Bundle();

        displayFragment.setArguments(args);

        return displayFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragments, container, false);

        switch(bookSelection){
            case 0:
                break;
        }

        return view;
    }
}
