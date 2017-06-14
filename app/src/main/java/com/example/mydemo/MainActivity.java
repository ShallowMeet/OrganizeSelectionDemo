package com.example.mydemo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.mydemo.activity.OrgContactActivity;
import com.example.mydemo.adapter.MainItemAdapter;
import com.example.mydemo.adapter.OrgContactAdapter;
import com.example.mydemo.view.RecyclerViewItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MainItemAdapter adapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
                //.color(Color.RED)
                .color("#eeeeee")
//                .dashWidth(8)
//                .dashGap(5)
                .thickness(1)
                //.drawableID(R.drawable.diver)
                //.paddingStart(20)
                //.paddingEnd(10)
                //.firstLineVisible(true)
                .lastLineVisible(true)
                .create());

        adapter = new MainItemAdapter();
        recyclerView.setAdapter(adapter);
        initData();
        adapter.setOnItemClickListener(this);
    }

    private void initData() {
        list.add("demo1");
        list.add("demo2");
        list.add("demo3");
        adapter.setNewData(list);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        switch (position){
            case 0:
                Intent intent = new Intent(MainActivity.this, OrgContactActivity.class);
                startActivity(intent);
                break;
        }
    }
}
