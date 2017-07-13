package com.test.lyx.sign_in_sys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.test.lyx.sign_in_sys.entity.ConstantPool;
import com.test.lyx.sign_in_sys.entity.User;
import com.test.lyx.sign_in_sys.utils.Request_Task;
import com.test.lyx.sign_in_sys.utils.ToastUtil;
import com.test.lyx.sign_in_sys.utils.parse_json.ParseJsonLogin;

import java.util.LinkedHashMap;

public class LoginActivity extends BasicActivity {
    private EditText user;
    private EditText pass;
    private String u_text, p_text;
    private LinkedHashMap<String, String> paramsValue;
    private User u;
    private long exitTime = 0;
    private static final String TAG = "LoginActivityTest";
    private TextView login_local;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText) findViewById(R.id.user_name);
        pass = (EditText) findViewById(R.id.user_password);
        login_local = (TextView)findViewById(R.id.login_local);
        login_local.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );


        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("正在登陆");
        dialog.setTitle("正在登陆会议列表界面...");
        dialog.setCanceledOnTouchOutside(false);


        u = new User();
        login_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_local.setTextColor(getResources().getColor(R.color.blue_link));
                startActivity(new Intent(LoginActivity.this,LocalMeetingList.class));
            }
        });
    }

    public void putMapData() {

        paramsValue = new LinkedHashMap<>();
        paramsValue.put(ConstantPool.PARAM_NAME, "Login");
        paramsValue.put("cnumber", u_text);
        paramsValue.put("pwd", p_text);

    }


    public void login(View view) {

        u_text = user.getText().toString();
        p_text = pass.getText().toString();
        u = new User();

        if (isNetworkConnected()) {



            if (u_text.equals("") || p_text.equals("")) {

                ToastUtil.showToast(getApplicationContext(), getString(R.string.account_pass_empty));

            } else {

                dialog.show();
                putMapData();
                new Request_Task(paramsValue, new Request_Task.CallBack() {
                    @Override
                    public void doResult(String data) {

                        dialog.dismiss();

                        Log.i(TAG, "doResult: "+data);

                        if (ParseJsonLogin.parse(data, u).equals(ConstantPool.RESULT_OK)) {

                            startActivity(new Intent(getApplication(), MeetingListsActivity.class));
                            LoginActivity.this.finish();

                        } else {


                            ToastUtil.showToast(getApplicationContext(), getString(R.string.account_pass_error));

                        }

                    }

                }).execute(ConstantPool.URL);

            }

        } else {

            ToastUtil.showToast(getApplicationContext(), getString(R.string.no_network_tip));

        }

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
