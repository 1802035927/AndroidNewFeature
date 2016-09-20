package com.example.admin.androidnewfeature;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.example.admin.androidnewfeature.databinding.ActivityDatabindingBinding;
import com.example.admin.model.Items;

/**
 * Created by admin on 2016/9/21.
 */
public class DataBindingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDatabindingBinding databindingBinding = DataBindingUtil.setContentView(this, R.layout.activity_databinding);
        Items items = new Items();
        items.setTitle("title");
        items.setContent("content");
        databindingBinding.setItems(items);
    }
}
