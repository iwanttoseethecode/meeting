package com.test.lyx.sign_in_sys.utils.parse_json;

import com.test.lyx.sign_in_sys.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class ParseJsonInfo {

    private static String json_result;

    public static String parse(String jsonString, User user) {

        try{

            JSONObject jsonObject = new JSONObject(jsonString);
            json_result = jsonObject.getString("result");
            user.setSign_num(jsonObject.getString("sign_num"));

            JSONObject object_data = jsonObject.getJSONObject("data");

            //解析CardInfo

            user.setCard_num(object_data.getString("card_num"));
            user.setCard_name(object_data.getString("card_name"));
            user.setCard_pic(object_data.getString("card_pic"));
            user.setCard_dname(object_data.getString("card_dname"));
            user.setCard_id(object_data.getString("card_id"));




        }catch (JSONException e){


            e.getMessage();

        }

        return String.valueOf(json_result);
    }




}
