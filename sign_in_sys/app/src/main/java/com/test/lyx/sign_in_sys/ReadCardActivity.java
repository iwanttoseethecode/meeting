package com.test.lyx.sign_in_sys;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.test.lyx.sign_in_sys.entity.ConstantPool;
import com.test.lyx.sign_in_sys.entity.User;
import com.test.lyx.sign_in_sys.utils.Request_Task;
import com.test.lyx.sign_in_sys.utils.ToastUtil;
import com.test.lyx.sign_in_sys.utils.ToolbarSet;
import com.test.lyx.sign_in_sys.utils.parse_json.ParseJsonInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import cn.pda.rfid.hf.Error;
import cn.pda.rfid.hf.HfConmmand;
import cn.pda.rfid.hf.HfError;
import cn.pda.rfid.hf.HfReader;
import cn.pda.serialport.SerialPort;
import cn.pda.serialport.Tools;

public class ReadCardActivity extends BasicActivity {

    private TextView card_name, card_dname, card_num, sign_number;
    private String mid;
    private LinkedHashMap<String, String> paramsValue;
    private LinkedHashMap<String, String> paramsValue_send;
    private String p_number;//模拟物理卡号
    private Toolbar toolbar;
    private ImageView card_pic, read_card_back;
    private User user;
    private String cid;
    private String json_order;
    private LinearLayout layout_sign_num;
    private Button queryMlist;


    private SerialPort mSerialPort;
    private InputStream is;
    private OutputStream os;
    private HfConmmand hf;
    private byte[] uid;
    public SoundPool nsp;
    public HashMap<Integer, Integer> nsuondMap;
    private Handler handler;
    private boolean isRead = false;
    private static final String TAG = "ReadCardTest";
    private long s;
    private String mName;
    private Message message;
    private String end_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_card);
        initDataAndView();
        initSound();
        initModule();
        setHandler();
        Message message = handler.obtainMessage(1);
        handler.sendMessageDelayed(message, 1000);

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

    //初始化数据和View
    private void initDataAndView() {
        mid = getIntent().getStringExtra("mid");
        mName = getIntent().getStringExtra("mName");
        end_time = getIntent().getStringExtra("endTime");

        toolbar = (Toolbar) findViewById(R.id.card_toolbar);
        queryMlist = (Button) findViewById(R.id.query);
        card_name = (TextView) findViewById(R.id.card_name);
        card_dname = (TextView) findViewById(R.id.card_dname);
        card_num = (TextView) findViewById(R.id.card_num);
        card_pic = (ImageView) findViewById(R.id.card_pic);
        read_card_back = (ImageView) findViewById(R.id.read_card_back);
        sign_number = (TextView) findViewById(R.id.sign_number);
        layout_sign_num = (LinearLayout) findViewById(R.id.layout_sign_num);
        queryMlist.setText(mName + "(签到列表)");

        ToolbarSet.setToolbar(this, toolbar, "读卡界面");


    }

    //设置循环
    private void setHandler() {
        handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        //不论如何先读卡
                        Read();

                        //有网络连接才获取卡片信息
                        if (isNetworkConnected()) {

                            getCardInfo();

                        }

                        //无论有无网络都不停止handler循环
                        message = handler.obtainMessage(1);
                        handler.sendMessageDelayed(message, 1000);


                }

            }
        };

    }

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
            Toast.makeText(ReadCardActivity.this, "打开读卡器失败", Toast.LENGTH_LONG).show();
        }
    }

    //获取当前时间
    public String getCurrTime() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);

    }


    //查询签到表
    public void querySignList(View view) {
        Intent intent = new Intent(this, SignListsActivity.class);
        intent.putExtra("sendMid", mid);
        startActivity(intent);
    }


    //发送签到信息
    public void sendInfo() {

        putMapData_Send();

        if (isNetworkConnected()) {

            if (!cid.equals("")) {


                new Request_Task(paramsValue_send, new Request_Task.CallBack() {
                    @Override
                    public void doResult(String data) {

                        JSONObject jsonObject = null;
                        //不论结果如何只要有网络任务就把是否已经读卡置为false
                        isRead = false;

                        try {

                            jsonObject = new JSONObject(data);

                            String json_result = jsonObject.getString("result");

                            if (json_result.equals(ConstantPool.RESULT_OK)) {

                                json_order = jsonObject.getString("sign_order");


                            }


                            if (json_result.equals(ConstantPool.RESULT_OK)) {

                                ToastUtil.showToast(getApplicationContext(), "签到成功！您是第" + json_order + "个签到的参会者。");
                                playSounds(1);

                            } else if (json_result.equals(String.valueOf(2))) {

                                ToastUtil.showToast(getApplicationContext(), "无需重复签到！");
                                playSounds(4);

                            } else {

                                show(false);
                                ToastUtil.showToast(getApplicationContext(), "签到失败！");
                                playSounds(2);


                            }


                        } catch (JSONException e) {

                            e.printStackTrace();

                        }


                    }
                }).execute(ConstantPool.URL);


            }


        } else {

            ToastUtil.showToast(getApplicationContext(), getString(R.string.no_network_tip));

        }


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

            if (isSignAllow(end_time, getCurrTime())) {
                isRead = true;


            } else {
                isRead = false;
                ToastUtil.showToast(getApplicationContext(), "会议已结束！");

            }


            //每次读卡，无网络连接就提示
            if (!isNetworkConnected()) {

                ToastUtil.showToast(getApplicationContext(), getString(R.string.no_network_tip));

            }

        }else {

            Log.i(TAG, "Read: 未读取到卡信息");


        }


    }


    //注入参数
    private void putMapData() {
        paramsValue = new LinkedHashMap<>();
        paramsValue.put(ConstantPool.PARAM_NAME, "GetCardInfo");
        paramsValue.put("card_no", p_number);
        paramsValue.put("mid", mid);


    }

    private void putMapData_Send() {

        paramsValue_send = new LinkedHashMap<>();
        paramsValue_send.put(ConstantPool.PARAM_NAME, "AddSignInfo");
        paramsValue_send.put("cid", cid);
        paramsValue_send.put("mid", mid);
        paramsValue_send.put("sign_time", getCurrTime());


    }

    //显示&隐藏 控件
    private void show(Boolean b) {
        if (b) {

            card_name.setVisibility(View.VISIBLE);
            card_dname.setVisibility(View.VISIBLE);
            card_num.setVisibility(View.VISIBLE);
            sign_number.setVisibility(View.VISIBLE);
            layout_sign_num.setVisibility(View.VISIBLE);
            card_pic.setVisibility(View.VISIBLE);
            read_card_back.setVisibility(View.GONE);


        } else {

            card_name.setVisibility(View.GONE);
            card_dname.setVisibility(View.GONE);
            card_num.setVisibility(View.GONE);
            sign_number.setVisibility(View.GONE);
            layout_sign_num.setVisibility(View.GONE);
            card_pic.setVisibility(View.GONE);
            read_card_back.setVisibility(View.VISIBLE);

        }


    }

    //发送物理卡号通过卡号接收人员详细信息
    private void getCardInfo() {

        //如果已经读卡才执行发送信息
        if (isRead) {


            p_number = String.valueOf(s);
            Log.i(TAG, "物理卡号: " + s);
            putMapData();
            new Request_Task(paramsValue, new Request_Task.CallBack() {
                @Override
                public void doResult(String data) {

                    user = new User();

                    String resVal = ParseJsonInfo.parse(data, user);

                    //不论结果如何只要有网络任务就把是否已经读卡置为false
                    isRead = false;
                    if (!data.equals(ConstantPool.RESULT_FAILED)) {

                        Log.i(TAG, "result=" + resVal);
                        Log.i(TAG, "data = " + data);
                        if (user.getCard_num() != null) {
                            show(true);

                            cid = user.getCard_id();
                            card_name.setText("姓名：" + user.getCard_name());
                            card_dname.setText("部门：" + user.getCard_dname());
                            card_num.setText("编号：" + user.getCard_num());
                            sign_number.setText("已签到人数：" + user.getSign_num());
                            card_pic.setImageDrawable(getResources().getDrawable(R.mipmap.avatar));
                            sendInfo();


                        } else {

                            playSounds(3);
                            ToastUtil.showToast(getApplicationContext(), getString(R.string.no_card_info));

                        }


                    } else {
                        ToastUtil.showToast(getApplicationContext(), getString(R.string.connect_failed_tips));

                    }

                }
            }).execute(ConstantPool.URL);


        }
    }

    //初始化声音
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

    //退出程序则关闭模块
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");

        if (mSerialPort != null) {
            mSerialPort.rfid_poweroff();
            Log.i(TAG, "PowerOff");

            try {
                is.close();
                os.close();
                Log.i(TAG, "streamClosed");

            } catch (IOException e) {
                e.printStackTrace();
            }
            mSerialPort.close(14);
            Log.i(TAG, "mSerialPort.close()");
        }
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
}
