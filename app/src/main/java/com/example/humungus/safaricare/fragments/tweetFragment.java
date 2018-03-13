package com.example.humungus.safaricare.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.example.humungus.safaricare.R;
import com.example.humungus.safaricare.adapters.reportsAdapter;
import com.example.humungus.safaricare.models.reportsModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class tweetFragment extends Fragment {

    private RecyclerView recyclerView;
    private reportsAdapter adapter;
    private Button SearchBtn;
    private EditText SearchTextET;
    private List<reportsModel> reports = new ArrayList<reportsModel>();

    public tweetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweet,container,false);
        SearchBtn = (Button)view.findViewById(R.id.searchbtn);
        SearchTextET = (EditText)view.findViewById(R.id.searchbox);

        recyclerView = view.findViewById(R.id.recycleradapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.smoothScrollToPosition(0);

        displayReports();
        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchReports();
            }
        });

        return view;
    }

    private void SearchReports() {
        String searchTxt = SearchTextET.getText().toString().trim();
        setAdapter(searchTxt);
    }

    public void setAdapter(final  String searchedString){
        reports.clear();
        DatabaseReference ReportsRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("reports");
        ReportsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String NoPlate = snapshot.child("noPlate").getValue(String.class);
                    String matName = snapshot.child("matName").getValue(String.class);
                    String sacco = snapshot.child("sacco").getValue(String.class);
                    String tweets = snapshot.child("tweets").getValue(String.class);

                    if (NoPlate.toLowerCase().contains(searchedString.toLowerCase())){
                        reports.add(snapshot.getValue(reportsModel.class));
                    }
                    if (matName.toLowerCase().contains(searchedString.toLowerCase())){
                        reports.add(snapshot.getValue(reportsModel.class));
                    }
                    if (sacco.toLowerCase().contains(searchedString.toLowerCase())){
                        reports.add(snapshot.getValue(reportsModel.class));
                    }
                    if (tweets.toLowerCase().contains(searchedString.toLowerCase())){
                        reports.add(snapshot.getValue(reportsModel.class));
                    }
                }

                adapter = new reportsAdapter(getContext(),reports);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
