package com.test.lyx.sign_in_sys;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.test.lyx.sign_in_sys.entity.ConstantPool;
import com.test.lyx.sign_in_sys.entity.Local_People;
import com.test.lyx.sign_in_sys.utils.LocalUserDaoUtils;
import com.test.lyx.sign_in_sys.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import cn.pda.rfid.hf.Error;
import cn.pda.rfid.hf.HfConmmand;
import cn.pda.rfid.hf.HfError;
import cn.pda.rfid.hf.HfReader;
import cn.pda.serialport.SerialPort;
import cn.pda.serialport.Tools;

public class LocalReadCardActivity extends BasicActivity {
    //    private ProgressDialog dialog;
    private TextView card_name, card_dname;
    private LinearLayout layout_sign_num;

    private LinkedHashMap<String, String> map;
    private String local_mName, local_end;
    private Long local_mid;
    private LocalUserDaoUtils peopleUtil;
    private Toolbar toolbar;
    private static final String TAG = "LocalReadCardTest";
    private SerialPort mSerialPort;
    private InputStream is;
    private OutputStream os;
    private HfConmmand hf;
    private byte[] uid;
    private Handler handler;
    private Message message;
    private long s;
    public SoundPool nsp;
    public HashMap<Integer, Integer> nsuondMap;
    private String testJson;
    private ImageView card_pic, read_card_back;
    private String local_start;


    //初始化读卡模块
    private void initModule() {
        try {

//            mSerialPort = new SerialPort(13, 115200, 0); //旧型号手持机端口号和波特率

            mSerialPort = new SerialPort(14, 115200, 0);   // 新型号手持机的端口号和波特率
            mSerialPort.rfid_poweron();
            is = mSerialPort.getInputStream();
            os = mSerialPort.getOutputStream();
            hf = new HfReader(is, os);
            Log.i(TAG, "执行到这一步");

        } catch (IOException e) {
            Toast.makeText(LocalReadCardActivity.this, "打开读卡器失败", Toast.LENGTH_LONG).show();
        }
    }

    private void setHandler() {
        handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        //不论如何先读卡
                        Read();


                        //有网络连接才获取卡片信息
                        if (isNetworkConnected()) {


                        }

                        //无论有无网络都不停止handler循环
                        message = handler.obtainMessage(1);
                        handler.sendMessageDelayed(message, 1000);


                }

            }
        };

    }


    //读卡方法
    private void Read() {
        Error error = new HfError();
        error.getErrorCode();
        uid = hf.findCard14443A(error);
        if (uid != null) {

            StringBuffer str = new StringBuffer(Tools.Bytes2HexString(uid, uid.length));
            sort(str);
            s = Long.parseLong(String.valueOf(sort(str)), 16);
            Log.i(TAG, "物理卡号：" + s);
            Log.i(TAG, "原卡号" + str);
            playSounds(5);

            //已读卡置为True

            if (isSignAllow(local_end, getCurrTime())) {

                List<Local_People> list = peopleUtil.queryByCon(String.valueOf(local_mid),String.valueOf(s));

                if (list.size()!=0){

                    if (list.get(0).getS_state().equals("未签到")){

                        show(true);
                        ToastUtil.showToast(getApplicationContext(), "签到成功！");
                        playSounds(1);

                        String cid = String.valueOf(list.get(0).getCid());
                        card_name.setText("姓名：" + list.get(0).getC_name());
                        card_dname.setText("部门：" + list.get(0).getD_name());
                        card_pic.setImageDrawable(getResources().getDrawable(R.mipmap.avatar));
                        if (isSignAllow(local_start, getCurrTime())){

                            list.get(0).setS_state("正常签到");


                        }else {


                            list.get(0).setS_state("迟到");

                        }

                        list.get(0).setS_date(getCurrTime());
                        peopleUtil.updatePeople(list.get(0));


                    }else {

                        show(true);
                        ToastUtil.showToast(getApplicationContext(), "无需重复签到！");
                        playSounds(4);

                    }


                }else {

                    playSounds(3);
                    ToastUtil.showToast(getApplicationContext(), getString(R.string.no_card_info));

                }




            } else {
                ToastUtil.showToast(getApplicationContext(), "会议已结束！");

            }


        } else {

            Log.i(TAG, "Read: 未读取到卡信息");


        }


    }


    public String getCurrTime() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);

    }


    //两位一组，逆向排序
    private StringBuffer sort(StringBuffer str) {

        StringBuffer temp = new StringBuffer();

        for (int i = 0; i < str.length(); i += 2) {
            if (str.length() - i >= 2) {

                temp.append(str.substring(str.length() - i - 2, str.length() - i));

            } else if (str.length() - i == 1) {

                temp.append(str.substring(0, 1));
            }
        }

        return temp;
    }


    //t1 = 会议结束时间 t2 = 当前时间
    private boolean isSignAllow(String t1, String t2) {

        java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        java.util.Calendar c1 = java.util.Calendar.getInstance();
        java.util.Calendar c2 = java.util.Calendar.getInstance();
        try {
            c1.setTime(df.parse(t1));
            c2.setTime(df.parse(t2));
        } catch (java.text.ParseException e) {
            System.err.println("格式不正确");
        }

        int result = c1.compareTo(c2);
        if (result == 0)
            return true;
        else if (result < 0)
            return false;
        else
            return true;


    }


    public void initSound() {

        nsuondMap = new HashMap();
        nsp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        nsuondMap.put(1, nsp.load(this, R.raw.sign_success, 1));
        nsuondMap.put(2, nsp.load(this, R.raw.sign_fail, 1));
        nsuondMap.put(3, nsp.load(this, R.raw.sign_null_new, 1));
        nsuondMap.put(4, nsp.load(this, R.raw.sign_repeat, 1));
        nsuondMap.put(5, nsp.load(this, R.raw.msg, 1));


    }


    //播放声音
    public void playSounds(int count) {
        AudioManager am = (AudioManager) this
                .getSystemService(this.AUDIO_SERVICE);
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float audioCurrentVolumn = am
                .getStreamVolume(AudioManager.STREAM_MUSIC);
        float volumnRatio = audioCurrentVolumn / audioMaxVolumn;
        nsp.play(nsuondMap.get(count), audioMaxVolumn, audioMaxVolumn, 1, 0, (float) 1.0);
    }


    //显示&隐藏 控件
    private void show(Boolean b) {
        if (b) {

            card_name.setVisibility(View.VISIBLE);
            card_dname.setVisibility(View.VISIBLE);
            layout_sign_num.setVisibility(View.GONE);
            card_pic.setVisibility(View.VISIBLE);
            read_card_back.setVisibility(View.GONE);


        } else {

            card_name.setVisibility(View.GONE);
            card_dname.setVisibility(View.GONE);
            layout_sign_num.setVisibility(View.GONE);
            card_pic.setVisibility(View.GONE);
            read_card_back.setVisibility(View.VISIBLE);

        }


    }


    private void putMapData() {
        map = new LinkedHashMap<>();
        map.put(ConstantPool.PARAM_NAME, "GetMeetingPerson");
        map.put("mid", getIntent().getStringExtra("local_mid"));

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_read_card);
        toolbar = (Toolbar) findViewById(R.id.local_read_toolbar);
        setSupportActionBar(toolbar);
        card_name = (TextView) findViewById(R.id.local_card_name);
        card_dname = (TextView) findViewById(R.id.local_card_dname);
        card_pic = (ImageView) findViewById(R.id.local_card_pic);
        read_card_back = (ImageView) findViewById(R.id.local_read_card_back);
        layout_sign_num = (LinearLayout) findViewById(R.id.local_layout_sign_num);

        local_end = getIntent().getStringExtra("local_end");
        local_mid = (long) getIntent().getIntExtra("local_mid", -1);
        Log.i(TAG, "LocalReadActivity:mid =  "+local_mid);

        local_mName = getIntent().getStringExtra("local_mName");
        local_start = getIntent().getStringExtra("local_start");
        initSound();
        initModule();
        setHandler();
        peopleUtil = new LocalUserDaoUtils(getApplicationContext());
        Message message = handler.obtainMessage(1);
        handler.sendMessageDelayed(message, 1000);


        Log.i(TAG, "local_end = " + local_end + "\nlocal_mid = " + local_mid + "\nlocal_mName = " + local_mName);
//
//        dialog = new ProgressDialog(LocalReadCardActivity.this);
//        dialog.setMessage("正在更新本地参会人员信息数");
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setTitle("正在更新...");
        testJson = "{\n" +
                "       \"result\": 0,\n" +
                "       \"total\":3,\n" +
                "       \"data\": [\n" +
                "                {   \n" +
                "                    \"cid\": \"7\",\n" +
                "                    \"cardno\": \"3615770091\",\n" +
                "                    \"cname\": \"300000\",\n" +
                "                    \"dname\": \"金牛区网点\"\n" +
                "                },\n" +
                "                {   \n" +
                "                    \"cid\": \"8\",\n" +
                "                    \"cardno\": \"3615590587\",\n" +
                "                    \"cname\": \"400000\",\n" +
                "                    \"dname\": \"锦江区网点\"\n" +
                "                },\n" +
                "                {   \n" +
                "                    \"cid\": \"5\",\n" +
                "                    \"cardno\": \"3615712283\",\n" +
                "                    \"cname\": \"100000\",\n" +
                "                    \"dname\": \"一环主城区\"\n" +
                "                }\n" +
                "\n" +
                "            ]\n" +
                "}";


    }

    private void praseJson(String json, List<Local_People> list) {

        try {

            JSONObject object = new JSONObject(json);
            object.getString("total");
            JSONArray jsonArray = object.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject local = (JSONObject) jsonArray.get(i);
                Local_People people = new Local_People(null, Integer.parseInt(local.getString("cid")), local.getString("cname"),
                        local.getString("cardno"), local.getString("dname"), "", "未签到", local_mid);

                list.add(people);

            }

        } catch (JSONException e) {

            Log.i(TAG, e.getMessage());

        }

    }

    private void updatePeople() {

        putMapData();
//        new Request_Task(map, new Request_Task.CallBack() {
//            @Override
//            public void doResult(String data) {

//                dialog.dismiss();
        List<Local_People> list = new ArrayList<>();
        praseJson(testJson, list);
        peopleUtil.deleteAll();
        peopleUtil.insertPeopleList(list);
        ToastUtil.showToast(getApplicationContext(), "保存成功");

//
//            }
//        }).execute(ConstantPool.URL);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_read, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.menu_load) {         //下载并存储参会人员信息
//            dialog.show();

            if (isNetworkConnected()) {

                updatePeople();
            } else {

                ToastUtil.showToast(getApplicationContext(), "请检查网络");

            }

            return true;

        } else if (id == R.id.menu_deletePerson){

            //清除所有人员信息
            peopleUtil.deleteAll();

        }

        return super.onOptionsItemSelected(item);
    }


    public void querySignList(View view) {      //查询本地已签到信息
        Intent in = new Intent(this, LocalSignListActivity.class);
        in.putExtra("local_read_mid", local_mid);
        startActivity(in);


    }


    //进入其他页面暂时关闭循环

    @Override
    protected void onStop() {
        super.onStop();
        if (handler != null) {

            handler.removeMessages(1);


        }

    }

    //退回本页面则打开循环读卡
    @Override
    protected void onResume() {
        super.onResume();
        if (handler != null && message != null) {

            setHandler();
            Message message = handler.obtainMessage(1);
            handler.sendMessageDelayed(message, 1000);


        }

    }


    //退出关闭数据库
    @Override
    protected void onDestroy() {
        super.onDestroy();
        peopleUtil.closeCon();

    }
}
