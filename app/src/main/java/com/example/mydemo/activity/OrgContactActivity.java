package com.example.mydemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.mydemo.R;
import com.example.mydemo.adapter.OrgContactAdapter;
import com.example.mydemo.entity.AllVo;
import com.example.mydemo.entity.EmpUserVo;
import com.example.mydemo.entity.OrgVo;
import com.example.mydemo.entity.ResultVo;
import com.example.mydemo.utils.LogUtils;
import com.example.mydemo.view.RecyclerViewItemDecoration;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by jack on 2017/5/21.
 */

public class OrgContactActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener, OrgContactAdapter.OnSubordinateClickListener {

    private HorizontalScrollView horizontalScrollView;
    private LinearLayout ll_shortcut;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TextView tv_select;
    private Button btn_ok;
    private OrgContactAdapter adapter;
    private List<MultiItemEntity> list = new ArrayList<>();
    private List<OrgVo> orgList = new ArrayList<>();
    private List<EmpUserVo> empList = new ArrayList<>();
    private List<AllVo> selectAllList = new ArrayList<>();
    private List<OrgVo> selectOrgList = new ArrayList<>();
    private List<EmpUserVo> selectEmpList = new ArrayList<>();
    private AllVo curAllVo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_org_contact);
        horizontalScrollView = (HorizontalScrollView)findViewById(R.id.horizontalScrollView);
        ll_shortcut = (LinearLayout) findViewById(R.id.ll_shortcut);
        tv_select = (TextView) findViewById(R.id.tv_select);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
                .color("#eeeeee")
                .thickness(1)
                .lastLineVisible(true)
                .create());
        adapter = new OrgContactAdapter(list, selectAllList, selectOrgList, selectEmpList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnSubordinateClickListener(this);
        getOrgContacts("");
    }

    private void addView2HorizontalScrollView(OrgVo orgVo){
        LinearLayout linearLayout = new LinearLayout(this);
        LayoutParams ll_layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(ll_layoutParams);
        int count = ll_shortcut.getChildCount();
        if(count>0) {
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(R.drawable.common_arrow);
            layoutParams.gravity = Gravity.CENTER_VERTICAL;
            imageView.setLayoutParams(layoutParams);
            linearLayout.addView(imageView);
        }
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(this);
        textView.setText(orgVo.getName());
        textView.setTextColor(Color.BLACK);
        if(count>0) {
            LinearLayout linearLayout1 = (LinearLayout) ll_shortcut.getChildAt(count-1);
            TextView textView1 = (TextView) linearLayout1.getChildAt(linearLayout1.getChildCount()-1);
            textView1.setTextColor(Color.BLUE);
        }
        textView.setLayoutParams(layoutParams);
        textView.setTag(orgVo);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTextView((TextView) v);
            }
        });
        linearLayout.addView(textView);
        ll_shortcut.addView(linearLayout);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                horizontalScrollView.fullScroll(View.FOCUS_RIGHT);
            }
        });
    }

    private void clickTextView(TextView tv){
        OrgVo orgVo = (OrgVo) tv.getTag();
        tv.setTextColor(Color.BLACK);
        int index = ll_shortcut.indexOfChild((View) tv.getParent());
        int count  = ll_shortcut.getChildCount();
        ll_shortcut.removeViews(index+1,count-(index+1));
        getOrgContacts(orgVo.getId());
    }

    private void getOrgContacts(String code){
        list.clear();
        orgList.clear();
        empList.clear();
        String url = "http://101.201.108.172:8280/masCustomer/service/UserInfoService/getEmployeeOrg";
        OkGo.post(url)
                .tag(this)
	            .params("servId", "")
	            .params("orgCode", code)
	            .params("servId", "")
	            .params("loginId", "1213")
	            .params("token", "311f4f508d776b115827e76bd11ae724")
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        LogUtils.e(e.toString());
                    }

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        ResultVo resultVo = gson.fromJson(s, ResultVo.class);
                        String json = gson.toJson(resultVo.getMsg());
                        OrgVo orgVo = gson.fromJson(json, OrgVo.class);
                        if(orgVo!=null) {
                            if(ll_shortcut.getChildCount()==0) {
                                addView2HorizontalScrollView(orgVo);
                            }
                            handleData(orgVo);
                        }
                    }
                });
    }

    private void handleData(OrgVo orgVo) {
        curAllVo = new AllVo();
        curAllVo.setOrgId(orgVo.getId());
        list.add(curAllVo);
        orgList.addAll(orgVo.getOrgVOs());
        list.addAll(orgList);
        empList.addAll(orgVo.getUsers());
        list.addAll(empList);
        adapter.setNewData(list);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MultiItemEntity entity = (MultiItemEntity) adapter.getData().get(position);
        CheckBox checkbox = (CheckBox) view.findViewById(R.id.checkbox);
        checkbox.setChecked(!checkbox.isChecked());
        switch (entity.getItemType()){
            case OrgContactAdapter.ALL:
                AllVo allVo = (AllVo) entity;
                if(checkbox.isChecked()){
                    selectAllList.add(allVo);

                    for(OrgVo orgVo : orgList){
                        if(!selectOrgList.contains(orgVo)){
                            selectOrgList.add(orgVo);
                            selectAllUserByOrg(orgVo);
                        }
                    }

                    for(EmpUserVo empUserVo : empList){
                        if(!selectEmpList.contains(empUserVo)){
                            selectEmpList.add(empUserVo);
                        }
                    }
                }else{
                    selectAllList.remove(allVo);

                    for(OrgVo orgVo : orgList){
                        if(selectOrgList.contains(orgVo)){
                            selectOrgList.remove(orgVo);
                            unSelectAllUserByOrg(orgVo);
                        }
                    }

                    for(EmpUserVo empUserVo : empList){
                        if(selectEmpList.contains(empUserVo)){
                            selectEmpList.remove(empUserVo);
                        }
                    }
                }
                refreshSelectText();
                break;

            case OrgContactAdapter.ORG:
                OrgVo orgVo = (OrgVo) entity;
                if(checkbox.isChecked()){
                    if(!selectOrgList.contains(orgVo)) {
                        selectOrgList.add(orgVo);
                        selectAllUserByOrg(orgVo);
                    }
                }else{
                    if(selectOrgList.contains(orgVo)) {
                        selectOrgList.remove(orgVo);
                        unSelectAllUserByOrg(orgVo);
                    }
                    if(selectAllList.contains(curAllVo)) {
                        selectAllList.remove(curAllVo);
                    }
                }
                refreshSelectText();
                break;

            case OrgContactAdapter.EMP:
                EmpUserVo empUserVo = (EmpUserVo) entity;
                if(checkbox.isChecked()){
                    if(!selectEmpList.contains(empUserVo)) {
                        selectEmpList.add(empUserVo);
                    }
                }else{
                    if(selectEmpList.contains(empUserVo)) {
                        selectEmpList.remove(empUserVo);
                    }
                    if(selectAllList.contains(curAllVo)) {
                        selectAllList.remove(curAllVo);
                    }
                }
                refreshSelectText();
                break;
        }
    }

    private void refreshSelectText() {
        String str = "已选择" + selectOrgList.size()+ "个部门" + selectEmpList.size()+ "个人";
        tv_select.setText(str);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSubordinateClick(BaseViewHolder helper, View v, int position) {
        OrgVo orgVo = (OrgVo) adapter.getData().get(position);
        addView2HorizontalScrollView(orgVo);
        getOrgContacts(orgVo.getId());
    }

    private void selectAllUserByOrg(OrgVo orgVo){
        ArrayList<OrgVo> orgList = orgVo.getOrgVOs();
        ArrayList<EmpUserVo> userList = orgVo.getUsers();
        for(EmpUserVo empUserVo : userList){
            if(!selectEmpList.contains(empUserVo)) {
                selectEmpList.add(empUserVo);
            }
        }
        for(OrgVo vo : orgList){
            selectAllUserByOrg(vo);
        }
    }

    private void unSelectAllUserByOrg(OrgVo orgVo){
        ArrayList<OrgVo> orgList = orgVo.getOrgVOs();
        ArrayList<EmpUserVo> userList = orgVo.getUsers();
        for(EmpUserVo empUserVo : userList){
            if(selectEmpList.contains(empUserVo)) {
                selectEmpList.remove(empUserVo);
            }
        }
        for(OrgVo vo : orgList){
            unSelectAllUserByOrg(vo);
            if(selectOrgList.contains(vo)) {
                selectOrgList.remove(vo);
            }
        }
    }
}
