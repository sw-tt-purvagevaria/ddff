package com.pg.helloworld;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by test on 22/8/17.
 */

public class OneFragment extends Fragment {

    TextView tvFragment;
    private ArrayList<String> arrayList;
    private int position = 0;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(
                R.layout.fragment_one, container, false);
        Bundle args = getArguments();
        arrayList = args.getStringArrayList("ARRAY_LIST");
        position = args.getInt("position int");
        tvFragment = (TextView) rootView.findViewById(R.id.tvFragment);

        tvFragment.setText(args.getString("position") + "\n" + arrayList.get(position));
        return rootView;


    }


}
