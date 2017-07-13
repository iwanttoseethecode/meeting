package com.test.lyx.sign_in_sys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.test.lyx.sign_in_sys.R;
import com.test.lyx.sign_in_sys.entity.User;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class Adapter_sign  extends BaseAdapter{

    private Context context;
    private List<User.SignList> list;

    public Adapter_sign(Context context, List<User.SignList> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Adapter_sign.ViewHolder vh;

        if (convertView == null) {
            vh = new Adapter_sign.ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_sign_list, null);

            vh.item_sign_num = (TextView)convertView.findViewById(R.id.item_sign_num);
            vh.item_sign_name = (TextView) convertView.findViewById(R.id.item_sign_name);
            vh.item_sign_part = (TextView) convertView.findViewById(R.id.item_sign_part);
            vh.item_sign_time = (TextView) convertView.findViewById(R.id.item_sign_time);
            vh.item_sign_state = (TextView)convertView.findViewById(R.id.item_sign_state);
            convertView.setTag(vh);

        } else {

            vh = (Adapter_sign.ViewHolder) convertView.getTag();

        }

        User.SignList signList = list.get(position);

        vh.item_sign_name.setText(signList.getCname());
        vh.item_sign_num.setText("编号： "+signList.getCnumber());
        vh.item_sign_part.setText("部门： "+signList.getDname());
        vh.item_sign_time.setText("签到时间： "+signList.getSign_time());
        vh.item_sign_state.setText("签到状态： "+signList.getState());


        return convertView;

    }


    private class ViewHolder {

        TextView  item_sign_name, item_sign_part, item_sign_time,item_sign_num,item_sign_state;


    }




}
