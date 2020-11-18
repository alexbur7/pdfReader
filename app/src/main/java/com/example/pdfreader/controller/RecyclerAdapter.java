package com.example.pdfreader.controller;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pdfreader.R;
import com.example.pdfreader.controller.Activity.BookActivity;
import com.example.pdfreader.model.Pdf;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PDFHolder> {

    private Context mContext;
    private List<Pdf> mPdfList;

    public RecyclerAdapter(Context context, List<Pdf> list){
        this.mContext=context;
        this.mPdfList=list;
    }

    public List<Pdf> getPdfList() {
        return mPdfList;
    }

    public void setPdfList(List<Pdf> pdfList) {
        mPdfList = pdfList;
    }

    @NonNull
    @Override
    public PDFHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PDFHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PDFHolder holder, int position) {
        holder.onBind(mPdfList.get(position).getUri(),mPdfList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return mPdfList.size();
    }

    public class PDFHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mPdfNameText;
        private String mUri;
        private String mName;

        public PDFHolder(@NonNull View itemView) {
            super(itemView);
            mPdfNameText=itemView.findViewById(R.id.pdf_name);
            itemView.setOnClickListener(this);
        }

        public void onBind(String uri,String name){
            this.mUri=uri;
            this.mName=name;
            mPdfNameText.setText(name);
        }

        @Override
        public void onClick(View v) {
                Intent intent = new Intent(mContext, BookActivity.class);
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                Log.e("URI", mUri);
                intent.setData(Uri.parse(mUri));
                mContext.startActivity(intent);
        }
    }
}
