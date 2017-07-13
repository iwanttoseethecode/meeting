package com.test.lyx.sign_in_sys;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.test.lyx.sign_in_sys.adapter.Adapter_LocalSignList;
import com.test.lyx.sign_in_sys.entity.ConstantPool;
import com.test.lyx.sign_in_sys.entity.Local_People;
import com.test.lyx.sign_in_sys.utils.LocalUserDaoUtils;
import com.test.lyx.sign_in_sys.utils.Request_Task;
import com.test.lyx.sign_in_sys.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;

public class LocalSignListActivity extends BasicActivity {

    private ListView localSignList;
    private LocalUserDaoUtils peopleUtil;
    private Toolbar toolbar;
    private LinkedHashMap<String, String> localMap;
    private String upJson, mid;
    private ProgressDialog dialog;
    private static final String TAG = "LocalSignListTest";

    private void putMapData() {

        localMap = new LinkedHashMap<>();
        localMap.put(ConstantPool.PARAM_NAME, "UploadSignInfo");
        localMap.put("mid", mid);
        localMap.put("signstr", upJson);


    }

    private String listToJson(List<Local_People> peopleList) {
        try {
            JSONArray jsonArray = new JSONArray();

            JSONObject tmpObj;

            int count = peopleList.size();
            for (int i = 0; i < count; i++) {
                tmpObj = new JSONObject();
                tmpObj.put("cid", peopleList.get(i).getCid());
                tmpObj.put("signtime", peopleList.get(i).getS_date());
                jsonArray.put(tmpObj);
            }

            return jsonArray.toString();


        } catch (JSONException e) {

            e.printStackTrace();
        }
        return "";

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_sign_list);
        toolbar = (Toolbar) findViewById(R.id.local_signList_toolbar);
        setSupportActionBar(toolbar);
        localSignList = (ListView) findViewById(R.id.localSignList);
        mid = String.valueOf(getIntent().getLongExtra("local_read_mid", -1));
        dialog = new ProgressDialog(this);
        dialog.setMessage("正在提交本地列表...");
        dialog.setTitle("正在提交");
        dialog.setCanceledOnTouchOutside(false);
        peopleUtil = new LocalUserDaoUtils(getApplicationContext());
        List<Local_People> list = peopleUtil.queryAllPeople();
        upJson = listToJson(list);
        Log.i(TAG, "mid = " + mid + "\nupJson = " + upJson);

        if (list.size() != 0) {

            Adapter_LocalSignList adapter = new Adapter_LocalSignList(this, list);
            localSignList.setAdapter(adapter);


        } else {

            ToastUtil.showToast(getApplicationContext(), "没有人员数据！");

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;

    }

    private String parseJson(String jsonString) {

        try {
            JSONObject object = new JSONObject(jsonString);
            return object.getString("result");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_uploadList) {
            putMapData();
            dialog.show();
            new Request_Task(localMap, new Request_Task.CallBack() {
                @Override
                public void doResult(String data) {
                    dialog.dismiss();

                    if (!data.equals(ConstantPool.RESULT_FAILED)) {

                        if (parseJson(data).equals(String.valueOf(0))) {

                            ToastUtil.showToast(getApplicationContext(), "提交本地签到表成功！");

                        } else {

                            ToastUtil.showToast(getApplicationContext(), "提交失败!");

                        }


                    } else {

                        ToastUtil.showToast(getApplicationContext(), getString(R.string.connect_failed_tips));

                    }


                }
            }).execute(ConstantPool.URL);


        }


        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        peopleUtil.closeCon();


    }



}
