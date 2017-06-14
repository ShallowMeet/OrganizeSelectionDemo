package com.example.mydemo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.mydemo.R;

/**
 * Created by jack on 2017/5/20.
 */

public class MainItemAdapter extends BaseQuickAdapter<String, BaseViewHolder>{

    public MainItemAdapter(){
        super(R.layout.list_item_main);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.name, item);
    }
}
