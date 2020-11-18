package com.example.pdfreader.controller.Activity;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import com.example.pdfreader.R;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment getFragment();

    @LayoutRes
    protected abstract int getLayoutRes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container)==null){
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,getFragment()).commit();
        }
        else  getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,getFragment()).commit();
    }
}