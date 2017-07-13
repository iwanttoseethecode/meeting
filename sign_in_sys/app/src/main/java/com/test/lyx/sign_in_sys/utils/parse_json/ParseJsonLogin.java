package com.test.lyx.sign_in_sys.utils.parse_json;

import com.test.lyx.sign_in_sys.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class ParseJsonLogin {


    private static String json_result;

    public static String parse(String jsonString, User user) {

        try {

            JSONObject jsonObject = new JSONObject(jsonString);
            json_result = jsonObject.getString("result");


            if (!jsonObject.getString("data").isEmpty()) {

                String json_data = jsonObject.getString("data");

                user.setData(json_data);

            }


        } catch (JSONException e) {


            e.getMessage();

        }

        return String.valueOf(json_result);
    }



}
