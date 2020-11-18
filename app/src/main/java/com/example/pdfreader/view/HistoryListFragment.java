package com.example.pdfreader.view;

import android.content.Intent;
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
import com.example.pdfreader.controller.Activity.BookActivity;
import com.example.pdfreader.controller.RecyclerAdapter;
import com.example.pdfreader.model.DBCreater;
import com.example.pdfreader.model.Pdf;
import com.example.pdfreader.model.RoomBase;

import static android.app.Activity.RESULT_OK;

public class HistoryListFragment extends Fragment {

    private RecyclerAdapter mRecyclerAdapter;
    private RecyclerView mRecyclerView;
    private static final int PICK_FILE_RESULT_CODE=1;

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

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerAdapter.setPdfList(DBCreater.getInstance(getActivity()).getPDFs());
        mRecyclerAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==PICK_FILE_RESULT_CODE){
                if (data!=null) {
                    DBCreater.getInstance(getActivity()).writeToBase(data.getData());
                    Intent intent = new Intent(getActivity(), BookActivity.class);
                    intent.setData(data.getData());
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.new_pdf, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.new_pdf){
            newPdf();
        }
        return super.onOptionsItemSelected(item);
    }

    private void newPdf() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent,PICK_FILE_RESULT_CODE);
    }
}
