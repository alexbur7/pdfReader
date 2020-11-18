package com.example.pdfreader.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.pdfreader.R;
import com.example.pdfreader.controller.Activity.HistoryActivity;
import com.example.pdfreader.model.DBCreater;
import com.example.pdfreader.model.Pdf;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.util.List;

public class BookFragment extends Fragment {

    private static final String KEY_TO_URI = "key_to_uri";
    private static final int REQUEST_PERMISSION = 1;
    private boolean mPermission;
    private Uri mUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.book_fragment, container, false);
        PDFView pdfView = v.findViewById(R.id.pdf_view);
        mUri =getArguments().getParcelable(KEY_TO_URI);
        permission_fn(mUri);
        //String provider = "com.android.providers.files.DocumentProvider";
        //getActivity().grantUriPermission(provider,getActivity().getIntent().setAction(Intent.ACTION_OPEN_DOCUMENT).getData(),Intent.FLAG_GRANT_READ_URI_PERMISSION);
        setUpPdfView(pdfView, mUri);
        setHasOptionsMenu(true);
        return v;
    }

    private void permission_fn(Uri uri){
        if ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){
            if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE))){}
            else {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_PERMISSION);
            }
        }
        else {
            mPermission =true;
            writeToBase(uri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==REQUEST_PERMISSION){
            if (grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                mPermission = true;
                writeToBase(mUri);
            }
        }
        else {

        }
    }

    private void writeToBase(Uri uri){
        String name = uri.getLastPathSegment();
        List<Pdf> pdfList=DBCreater.getInstance(getActivity()).getPDFs();
        for (Pdf pdf:pdfList){
            if (pdf.getUri().equals(uri)) return;
        }
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    name = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }

        DBCreater.getInstance(getActivity()).insertBook(new Pdf(uri,name));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.pdf_toolbar_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.switch_fragment){
            Intent intent=new Intent(getActivity(), HistoryActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpPdfView(PDFView pdfView, Uri uri) {
        pdfView.fromUri(uri)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .enableAnnotationRendering(false)
                .enableAntialiasing(true)
                .autoSpacing(false)
                .pageFitPolicy(FitPolicy.BOTH)
                .pageSnap(true)
                .autoSpacing(true)
                .pageFling(true)
                .load();
    }

    public static Fragment newInstance(Uri uri){
        BookFragment fragment=new BookFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable(KEY_TO_URI, uri);
        fragment.setArguments(bundle);
        return fragment;
    }

}
