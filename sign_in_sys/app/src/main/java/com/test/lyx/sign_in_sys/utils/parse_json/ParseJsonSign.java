package com.test.lyx.sign_in_sys.utils.parse_json;

import com.test.lyx.sign_in_sys.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class ParseJsonSign {


    private static String json_result;
    private static List<User.SignList> signLists;
    private static final String TAG = "Json_signTest";


    public static String parse(String jsonString, User user) {

        try{

            JSONObject jsonObject = new JSONObject(jsonString);
            json_result = jsonObject.getString("result");
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            signLists = new ArrayList<>();

            for (int i=0;i<jsonArray.length();i++){
                User.SignList sign = user.new SignList();
                JSONObject sign_object =  (JSONObject) jsonArray.get(i);

                sign.setCname(sign_object.getString("cname"));
                sign.setCnumber(sign_object.getString("cnumber"));
                sign.setDname(sign_object.getString("dname"));
                sign.setSign_time(sign_object.getString("sign_time"));
                sign.setState(sign_object.getString("state"));


                signLists.add(sign);



            }

            user.setSignLists(signLists);


        }catch (JSONException e){


            e.getMessage();

        }

        return String.valueOf(json_result);
    }





}
