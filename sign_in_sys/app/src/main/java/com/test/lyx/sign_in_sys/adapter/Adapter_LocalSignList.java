package com.test.lyx.sign_in_sys.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.test.lyx.sign_in_sys.R;
import com.test.lyx.sign_in_sys.entity.Local_People;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class Adapter_LocalSignList extends BaseAdapter{

    private Context context;
    private List<Local_People> list;

    public Adapter_LocalSignList(Context context, List<Local_People> list) {
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
        Adapter_LocalSignList.ViewHolder vh;

        if (convertView == null) {
            vh = new Adapter_LocalSignList.ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_local_sign_list, null);

            vh.item_local_sign_name = (TextView)convertView.findViewById(R.id.item_local_sign_name);
            vh.item_local_sign_part = (TextView) convertView.findViewById(R.id.item_local_sign_part);
            vh.item_local_sign_state = (TextView) convertView.findViewById(R.id.item_local_sign_state);
            vh.item_local_sign_time = (TextView) convertView.findViewById(R.id.item_local_sign_time);
            convertView.setTag(vh);

        } else {

            vh = (Adapter_LocalSignList.ViewHolder) convertView.getTag();

        }




        Local_People people = list.get(position);

        vh.item_local_sign_name.setText(people.getC_name());
        vh.item_local_sign_part.setText("部门： "+people.getD_name());
        vh.item_local_sign_time.setText("签到时间： "+people.getS_date());
        vh.item_local_sign_state.setText("签到状态： "+people.getS_state());


        return convertView;

    }


    private class ViewHolder {

        TextView  item_local_sign_name, item_local_sign_part, item_local_sign_state,item_local_sign_time;


    }




}
