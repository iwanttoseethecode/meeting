package com.test.lyx.sign_in_sys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.test.lyx.sign_in_sys.adapter.Adapter_LocalMeeting;
import com.test.lyx.sign_in_sys.entity.ConstantPool;
import com.test.lyx.sign_in_sys.entity.Local_Meeting;
import com.test.lyx.sign_in_sys.utils.DateString;
import com.test.lyx.sign_in_sys.utils.LocalMeetingDaoUtils;
import com.test.lyx.sign_in_sys.utils.Request_Task;
import com.test.lyx.sign_in_sys.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class LocalMeetingList extends BasicActivity {
    private Toolbar toolbar;
    private ImageView local_back;
    private TextView local_xq, local_date;
    private LocalMeetingDaoUtils meetingUtil;
    private ListView local_list;
    private static final String TAG = "LocalMeetingTest";
    private Adapter_LocalMeeting adapter_localMeeting;
    private ProgressDialog dialog;


    public LinkedHashMap<String, String> putMapData() {

        LinkedHashMap<String, String> paramsValue = new LinkedHashMap<>();
        paramsValue.put(ConstantPool.PARAM_NAME, "GetAollowMeeting");
        return paramsValue;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_meeting_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        local_back = (ImageView) findViewById(R.id.local_back);
        local_xq = (TextView) findViewById(R.id.local_xq);
        local_date = (TextView) findViewById(R.id.local_date);
        local_list = (ListView) findViewById(R.id.local_list);
        dialog = new ProgressDialog(LocalMeetingList.this);
        dialog.setMessage("正在从网络端更新本地数据");
        dialog.setTitle("正在更新");
        dialog.setCanceledOnTouchOutside(false);

        //设置时间和星期
        local_xq.setText(DateString.StringXq());
        local_date.setText(DateString.StringDate());
        setCompletePic(local_back, R.mipmap.time_background);
        meetingUtil = new LocalMeetingDaoUtils(getApplicationContext());
        adapter_localMeeting = new Adapter_LocalMeeting(getApplicationContext(), meetingUtil.queryAllMeeting());
        local_list.setAdapter(adapter_localMeeting);
        local_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent in = new Intent(getApplicationContext(), LocalReadCardActivity.class);
                in.putExtra("local_mid", meetingUtil.queryAllMeeting().get(position).getMid());
                in.putExtra("local_mName", meetingUtil.queryAllMeeting().get(position).getMName());
                in.putExtra("local_end", meetingUtil.queryAllMeeting().get(position).getMEnd());

                Log.i(TAG, "mid =" + meetingUtil.queryAllMeeting().get(position).getMid());
                startActivity(in);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.menu_edit) {


            dialog.show();
            queryMeetingList();

            return true;

        } else if (id == R.id.menu_delete) {

            meetingUtil.deleteAll();
            local_list.setAdapter(new Adapter_LocalMeeting(getApplicationContext(), meetingUtil.queryAllMeeting()));


        }

        return super.onOptionsItemSelected(item);
    }

    public void setCompletePic(View view, int res) {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res);
        int bwidth = bitmap.getWidth();
        int bHeight = bitmap.getHeight();
        //获取手机屏幕宽度
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = width * bHeight / bwidth;
        ViewGroup.LayoutParams para = view.getLayoutParams();
        //高度设置为图片自身高度
        para.height = height;
        view.setLayoutParams(para);
    }

    public void parse(String jsonString, List<Local_Meeting> list) {

        try {

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject meeting_object = (JSONObject) jsonArray.get(i);
                Local_Meeting localMeeting = new Local_Meeting(null, Integer.parseInt(meeting_object.getString("meeting_id")),
                        meeting_object.getString("meeting_start"), meeting_object.getString("meeting_end"), meeting_object.getString("meeting_name"),
                        meeting_object.getString("meeting_place"), "0", "0");
                list.add(localMeeting);
            }

        } catch (JSONException e) {

            Log.i(TAG, "JSONException: " + e.getMessage());

        }

    }


    private void queryMeetingList() {

        new Request_Task(putMapData(), new Request_Task.CallBack() {
            @Override
            public void doResult(String data) {

                dialog.dismiss();
                if (!data.equals(ConstantPool.RESULT_FAILED)) {

                    List<Local_Meeting> list = new ArrayList<>();
                    parse(data, list);
                    meetingUtil.deleteAll();
                    meetingUtil.insertMeetingList(list);
                    local_list.setAdapter(new Adapter_LocalMeeting(getApplicationContext(), meetingUtil.queryAllMeeting()));
                    local_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent in = new Intent(getApplicationContext(), LocalReadCardActivity.class);
                            in.putExtra("local_mid", meetingUtil.queryAllMeeting().get(position).getMid());
                            in.putExtra("local_mName", meetingUtil.queryAllMeeting().get(position).getMName());
                            in.putExtra("local_start", meetingUtil.queryAllMeeting().get(position).getMStart());
                            in.putExtra("local_end", meetingUtil.queryAllMeeting().get(position).getMEnd());

                            startActivity(in);


                        }
                    });


                } else {

                    ToastUtil.showToast(getApplicationContext(), getString(R.string.connect_failed_tips));

                }


            }
        }).execute(ConstantPool.URL);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        meetingUtil.closeCon();


    }
}
