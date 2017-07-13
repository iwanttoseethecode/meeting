package com.test.lyx.sign_in_sys;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.test.lyx.sign_in_sys.adapter.Adapter_meeting;
import com.test.lyx.sign_in_sys.entity.ConstantPool;
import com.test.lyx.sign_in_sys.entity.User;
import com.test.lyx.sign_in_sys.utils.DateString;
import com.test.lyx.sign_in_sys.utils.Request_Task;
import com.test.lyx.sign_in_sys.utils.ToastUtil;
import com.test.lyx.sign_in_sys.utils.parse_json.ParseJsonMeeting;

import java.util.LinkedHashMap;


public class MeetingListsActivity extends BasicActivity {
    private ListView mLists_listView;
    private LinkedHashMap<String, String> paramsValue;
    private User user;
    private long exitTime = 0;


    public void putMapData() {
        paramsValue = new LinkedHashMap<>();
        paramsValue.put(ConstantPool.PARAM_NAME, "GetAollowMeeting");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_lists);
        mLists_listView = (ListView) findViewById(R.id.mLists_listView);
        ImageView time_back = (ImageView) findViewById(R.id.time_back);
        TextView meeting_xq = (TextView) findViewById(R.id.meeting_xq);
        TextView meeting_date = (TextView) findViewById(R.id.meeting_date);
        setCompletePic(time_back, R.mipmap.time_background);
        meeting_xq.setText(DateString.StringXq());
        meeting_date.setText(DateString.StringDate());
        if (isNetworkConnected()) {

            queryMeetingList();

        } else {
            ToastUtil.showToast(this, getString(R.string.no_network_tip));
        }

    }

    private void queryMeetingList() {
        putMapData();

        new Request_Task(paramsValue, new Request_Task.CallBack() {
            @Override
            public void doResult(String data) {
                user = new User();

                if (!data.equals(ConstantPool.RESULT_FAILED)) {

                    ParseJsonMeeting.parse(data, user);

                    if (user.getMeetingLists()!=null) {

                        Adapter_meeting adapter_meeting = new Adapter_meeting(getApplicationContext(), user.getMeetingLists());
                        mLists_listView.setAdapter(adapter_meeting);
                        mLists_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                Intent intent = new Intent(getApplicationContext(), ReadCardActivity.class);
                                intent.putExtra("mid", user.getMeetingLists().get(i).getMeeting_id());
                                intent.putExtra("mName", user.getMeetingLists().get(i).getMeeting_name());
                                intent.putExtra("endTime",user.getMeetingLists().get(i).getMeeting_end());
                                startActivity(intent);
                            }
                        });
                    } else {

                        ToastUtil.showToast(MeetingListsActivity.this, getString(R.string.no_data_tips));

                    }


                } else {
                    ToastUtil.showToast(getApplicationContext(), getString(R.string.connect_failed_tips));


                }


            }
        }).execute(ConstantPool.URL);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {

                this.finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
