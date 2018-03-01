package com.example.humungus.safaricare.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.humungus.safaricare.R;
import com.example.humungus.safaricare.adapters.reportsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class tweetFragment extends Fragment {

    private RecyclerView recyclerView;
    private reportsAdapter adapter;

    public tweetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweet,container,false);
        recyclerView = view.findViewById(R.id.recycleradapter);

        return view;
    }

}
