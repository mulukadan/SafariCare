package com.example.humungus.safaricare.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.humungus.safaricare.R;
import com.example.humungus.safaricare.adapters.reportsAdapter;
import com.example.humungus.safaricare.models.reportsModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.smoothScrollToPosition(0);

        displayReports();

        return view;
    }

   public void displayReports(){
       // Initialize message ListView and its adapter
       final List<reportsModel> reports = new ArrayList<>();
       adapter = new reportsAdapter(getContext(),reports);
       recyclerView.setAdapter(adapter);

       DatabaseReference ReportsRef = FirebaseDatabase.getInstance()
               .getReference()
               .child("reports");

       ReportsRef.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            reportsModel rpt = dataSnapshot.getValue(reportsModel.class);
            reports.add(rpt);
            adapter.notifyDataSetChanged();
           }

           @Override
           public void onChildChanged(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onChildRemoved(DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
    }
}
