package com.example.pdfreader.controller.Activity;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.pdfreader.R;
import com.example.pdfreader.view.BookFragment;

public class BookActivity extends SingleFragmentActivity {


    @Override
    protected Fragment getFragment() {
        Intent intent = getIntent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        return BookFragment.newInstance(intent.getData());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.acitivity_fragment;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
