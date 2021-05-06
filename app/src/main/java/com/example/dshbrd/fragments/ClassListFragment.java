    package com.example.dshbrd.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dshbrd.AdapterClassList;
import com.example.dshbrd.ClassInfo;
import com.example.dshbrd.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

    /**
 * A simple {@link Fragment} subclass.
*/
public class ClassListFragment extends Fragment {

    private RecyclerView Rv;

    private FirebaseAuth firebaseAuth;

    private ArrayList<ClassInfo> classInfos;
    private AdapterClassList adapterClassList;

    public ClassListFragment() {
}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_class_list, container, false);

        Rv = view.findViewById(R.id.classRv);

        firebaseAuth = FirebaseAuth.getInstance();

        loadClassList();

        return view;
    }

    private void loadClassList() {
        classInfos = new ArrayList<ClassInfo>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("clase");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                classInfos.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    if ( ds.child("Participants").child(firebaseAuth.getUid()).exists()) {
                        ClassInfo model = ds.getValue(ClassInfo.class);
                        classInfos.add(model);
                    }
                }
                adapterClassList = new AdapterClassList(getActivity(), classInfos);
                Rv.setAdapter(adapterClassList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}