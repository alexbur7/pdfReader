package com.example.pdfreader.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pdfreader.R;
import com.example.pdfreader.controller.RecyclerAdapter;
import com.example.pdfreader.model.DBCreater;
import com.example.pdfreader.model.Pdf;
import com.example.pdfreader.model.RoomBase;

public class HistoryListFragment extends Fragment {

    private RecyclerAdapter mRecyclerAdapter;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //mRecyclerAdapter=new RecyclerAdapter(getContext());
        View v=inflater.inflate(R.layout.history_list_fragment,container,false);
        mRecyclerAdapter=new RecyclerAdapter(getActivity(), DBCreater.getInstance(getActivity()).getPDFs());
        mRecyclerView=v.findViewById(R.id.rec_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mRecyclerAdapter);
        setHasOptionsMenu(true);
        return v;
    }
}
