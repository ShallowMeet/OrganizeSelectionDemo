package com.example.mydemo.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.mydemo.R;
import com.example.mydemo.entity.AllVo;
import com.example.mydemo.entity.EmpUserVo;
import com.example.mydemo.entity.OrgVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jack on 2017/5/21.
 */

public class OrgContactAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int ALL = 1;
    public static final int ORG = 2;
    public static final int EMP = 3;

    private List<OrgVo> selectOrgList;
    private List<EmpUserVo> selectEmpList;
    private List<AllVo> selectAllList;

    private List<EmpUserVo> alluserList = new ArrayList<>();

    public OrgContactAdapter(List data, List<AllVo> selectAllList, List<OrgVo> selectOrgList, List<EmpUserVo> selectEmpList){
        super(data);
        this.selectAllList = selectAllList;
        this.selectOrgList = selectOrgList;
        this.selectEmpList = selectEmpList;
        addItemType(ALL, R.layout.list_item_all);
        addItemType(ORG, R.layout.list_item_org);
        addItemType(EMP, R.layout.list_item_emp);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()){
            case ALL:
                AllVo allVo = (AllVo) item;
                helper.setText(R.id.tv_name, "全选");
                CheckBox checkBox_all = helper.getView(R.id.checkbox);
                if(selectAllList.contains(allVo)){
                    checkBox_all.setChecked(true);
                }else{
                    checkBox_all.setChecked(false);
                }
                break;
            case ORG:
                OrgVo orgVo = (OrgVo) item;
                helper.setText(R.id.tv_name, ((OrgVo) item).getName());
                int selectCount = getSelectUserCountByOrg(orgVo);
                if(selectCount==0) {
                    helper.setText(R.id.tv_count, "(" + ((OrgVo) item).getCount() + ")");
                }else{
                    helper.setText(R.id.tv_count, "(" + selectCount + "/" + ((OrgVo) item).getCount() + ")");
                }
                CheckBox checkBox_org = helper.getView(R.id.checkbox);
                LinearLayout ll_subordinate = helper.getView(R.id.ll_subordinate);
                TextView tv_subordinate = helper.getView(R.id.tv_subordinate);
                if(selectOrgList.contains(orgVo)){
                    checkBox_org.setChecked(true);
                    tv_subordinate.setTextColor(Color.BLACK);
                    ll_subordinate.setEnabled(false);
                }else{
                    checkBox_org.setChecked(false);
                    tv_subordinate.setTextColor(Color.BLUE);
                    ll_subordinate.setEnabled(true);
                }
                ll_subordinate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onSubordinateClickListener!=null){
                            onSubordinateClickListener.onSubordinateClick(helper, v, helper.getAdapterPosition());
                        }
                    }
                });
                break;
            case EMP:
                EmpUserVo empUserVo = (EmpUserVo) item;
                helper.setText(R.id.tv_name, ((EmpUserVo) item).getName());
                CheckBox checkBox_emp = helper.getView(R.id.checkbox);
                if(selectEmpList.contains(empUserVo)){
                    checkBox_emp.setChecked(true);
                }else{
                    checkBox_emp.setChecked(false);
                }
                break;
        }
    }

    private int getSelectUserCountByOrg(OrgVo orgVo){
        int count = 0;
        alluserList.clear();
        getAllUserByOrg(orgVo);
        for(EmpUserVo empUserVo : alluserList){
            if(selectEmpList.contains(empUserVo)){
                count++;
            }
        }
        return count;
    }

    private void getAllUserByOrg(OrgVo orgVo){
        ArrayList<OrgVo> orgList = orgVo.getOrgVOs();
        ArrayList<EmpUserVo> userList = orgVo.getUsers();
        alluserList.addAll(userList);
        for(OrgVo vo : orgList){
            getAllUserByOrg(vo);
        }
    }

    OnSubordinateClickListener onSubordinateClickListener;
    public interface OnSubordinateClickListener{
        void onSubordinateClick(BaseViewHolder helper,View v,int position);
    }
    public void setOnSubordinateClickListener(OnSubordinateClickListener onSubordinateClickListener) {
        this.onSubordinateClickListener = onSubordinateClickListener;
    }
}
