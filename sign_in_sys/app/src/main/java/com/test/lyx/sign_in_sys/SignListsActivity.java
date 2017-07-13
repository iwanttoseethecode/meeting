package com.test.lyx.sign_in_sys;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.test.lyx.sign_in_sys.adapter.Adapter_sign;
import com.test.lyx.sign_in_sys.entity.ConstantPool;
import com.test.lyx.sign_in_sys.entity.User;
import com.test.lyx.sign_in_sys.utils.Request_Task;
import com.test.lyx.sign_in_sys.utils.ToastUtil;
import com.test.lyx.sign_in_sys.utils.ToolbarSet;
import com.test.lyx.sign_in_sys.utils.parse_json.ParseJsonSign;

import java.util.LinkedHashMap;

public class SignListsActivity extends BasicActivity {
    private Toolbar sList_toolbar;
    private ListView sListView;
    private Adapter_sign adapter_sign;
    private LinkedHashMap<String, String> paramsValue;
    private User user;

    private void putMapData() {
        paramsValue = new LinkedHashMap<>();
        paramsValue.put(ConstantPool.PARAM_NAME, "GetSignDetail");
        paramsValue.put("mid", getIntent().getStringExtra("sendMid"));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_lists);
        sList_toolbar = (Toolbar) findViewById(R.id.sList_toolbar);
        ToolbarSet.setToolbar(this, sList_toolbar, "签到列表");
        sListView = (ListView) findViewById(R.id.sListView);


        if (isNetworkConnected()) {

            putMapData();

            new Request_Task(paramsValue, new Request_Task.CallBack() {
                @Override
                public void doResult(String data) {

                    user = new User();
                    if (!data.equals(ConstantPool.RESULT_FAILED)) {

                        ParseJsonSign.parse(data, user);

                        if (user.getSignLists()!=null) {

                            adapter_sign = new Adapter_sign(getApplicationContext(), user.getSignLists());
                            sListView.setAdapter(adapter_sign);


                        } else {
                            ToastUtil.showToast(getApplicationContext(), "暂无数据");
                        }
                    } else {

                        ToastUtil.showToast(getApplicationContext(), getString(R.string.connect_failed_tips));

                    }


                }
            }).execute(ConstantPool.URL);


        } else {

            ToastUtil.showToast(getApplicationContext(), "没有网络连接！");
        }


    }


}
