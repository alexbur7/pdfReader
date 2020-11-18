package com.example.pdfreader.controller.Activity;

import androidx.fragment.app.Fragment;

import com.example.pdfreader.R;
import com.example.pdfreader.view.HistoryListFragment;

public class HistoryActivity extends SingleFragmentActivity {
    @Override
    protected Fragment getFragment() {
        return new HistoryListFragment();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.acitivity_fragment;
    }
}
