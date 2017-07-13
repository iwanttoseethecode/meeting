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

public class Adapter_meeting extends BaseAdapter{

    private Context context;
    private List<User.MeetingList> list;

    public Adapter_meeting(Context context, List<User.MeetingList> list) {
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
        Adapter_meeting.ViewHolder vh;

        if (convertView == null) {
            vh = new Adapter_meeting.ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_meeting_list, null);

            vh.item_meeting_end = (TextView)convertView.findViewById(R.id.item_meeting_end);
            vh.item_meeting_name = (TextView) convertView.findViewById(R.id.item_meeting_name);
            vh.item_meeting_place = (TextView) convertView.findViewById(R.id.item_meeting_place);
            vh.item_meeting_time = (TextView) convertView.findViewById(R.id.item_meeting_time);

            convertView.setTag(vh);

        } else {

            vh = (Adapter_meeting.ViewHolder) convertView.getTag();

        }

        User.MeetingList meetingList = list.get(position);


        vh.item_meeting_name.setText(meetingList.getMeeting_name());
        vh.item_meeting_place.setText("会议地点："+meetingList.getMeeting_place());
        vh.item_meeting_time.setText("开始时间： "+meetingList.getMeeting_start());
        vh.item_meeting_end.setText("结束时间： "+meetingList.getMeeting_end());

        return convertView;
    }


    private class ViewHolder {

        TextView  item_meeting_name, item_meeting_place, item_meeting_time,item_meeting_end;

    }




}
