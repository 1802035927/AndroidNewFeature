package com.example.admin.androidnewfeature;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.admin.adapter.ItemsAdapter;
import com.example.admin.model.Items;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView main_recycler;
    private ItemsAdapter itemsAdapter;
    private List<Items> itemsList;
    private Button btn_linear, btn_grid, btn_stagged;
    private SwipeRefreshLayout refreshLayout;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 301:
                    Items items = new Items();
                    items.setTitle("" + itemsList.size());
                    items.setContent("add.Items" + itemsList.size());
                    itemsList.add(items);
                    itemsAdapter.notifyItemInserted(itemsList.size());
                    if (refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }
                    Toast.makeText(MainActivity.this, "下拉刷新加载完成", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initRecyclerView();
    }

    private void initView() {
        btn_grid = (Button) findViewById(R.id.btn_grid);
        btn_linear = (Button) findViewById(R.id.btn_linear);
        btn_stagged = (Button) findViewById(R.id.btn_stagged);
        btn_grid.setOnClickListener(this);
        btn_linear.setOnClickListener(this);
        btn_stagged.setOnClickListener(this);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_refresh);
        refreshLayout.setProgressBackgroundColorSchemeColor(Color.parseColor("#ff0000"));
        refreshLayout.setSize(SwipeRefreshLayout.LARGE);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(301);
                    }
                }).start();
            }
        });
    }

    private void initData() {
        itemsList = new ArrayList<>();
        for (int i = 'A'; i < 'Z'; i++) {
            Items items = new Items();
            items.setTitle("子母——" + (char) i);
            items.setContent("数字——" + i);
            itemsList.add(items);
        }
    }

    public void initRecyclerView() {
        itemsAdapter = new ItemsAdapter(this, itemsList);
        main_recycler = (RecyclerView) findViewById(R.id.main_recycler);
        main_recycler.setLayoutManager(new LinearLayoutManager(this));
        main_recycler.setItemAnimator(new DefaultItemAnimator());
        main_recycler.setAdapter(itemsAdapter);
        itemsAdapter.setItemsOnclickListener(new ItemsAdapter.ItemsOnclickListener() {
            @Override
            public void onclick(View view, int position) {
                Toast.makeText(MainActivity.this, "view.getId--" + view.getId() + "position--" + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void setOnLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "items.content--" + itemsList.get(position).getContent(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_linear:
                main_recycler.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.btn_grid:
                main_recycler.setLayoutManager(new GridLayoutManager(this, 3));
                break;
            case R.id.btn_stagged:
                main_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                break;
        }
    }
}
