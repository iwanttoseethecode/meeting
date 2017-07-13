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

public class ParseJsonMeeting {




    private static String json_result;
    private static List<User.MeetingList> meetingLists;

    public static String parse(String jsonString, User user) {

        try {

            JSONObject jsonObject = new JSONObject(jsonString);
            json_result = jsonObject.getString("result");
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            meetingLists = new ArrayList<>();
            for (int i = 0 ;i<jsonArray.length();i++){

                User.MeetingList meeting = user.new MeetingList();

                JSONObject meeting_object = (JSONObject)jsonArray.get(i);

                meeting.setMeeting_id(meeting_object.getString("meeting_id"));
                meeting.setMeeting_name(meeting_object.getString("meeting_name"));
                meeting.setMeeting_place(meeting_object.getString("meeting_place"));
                meeting.setMeeting_start(meeting_object.getString("meeting_start"));
                meeting.setMeeting_end(meeting_object.getString("meeting_end"));


                meetingLists.add(meeting);


            }

            user.setMeetingLists(meetingLists);

        } catch (JSONException e) {


            e.getMessage();

        }

        return String.valueOf(json_result);
    }





}
