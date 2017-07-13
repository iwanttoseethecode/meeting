package com.test.lyx.sign_in_sys.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class User {
    private String card_num;
    private String card_name;
    private String card_pic;
    private String card_dname;

    private String data;
    private List<MeetingList> meetingLists;
    private List<SignList> signLists;
    private String sign_num;
    private String sign_order;

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    private String card_id;

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    public String getCard_name() {
        return card_name;
    }

    public void setCard_name(String card_name) {
        this.card_name = card_name;
    }

    public String getCard_pic() {
        return card_pic;
    }

    public void setCard_pic(String card_pic) {
        this.card_pic = card_pic;
    }

    public String getCard_dname() {
        return card_dname;
    }

    public void setCard_dname(String card_dname) {
        this.card_dname = card_dname;
    }

    public String getSign_order() {
        return sign_order;
    }

    public void setSign_order(String sign_order) {
        this.sign_order = sign_order;
    }

    public String getSign_num() {
        return sign_num;
    }

    public void setSign_num(String sign_num) {
        this.sign_num = sign_num;
    }

    public List<SignList> getSignLists() {
        return signLists;
    }

    public void setSignLists(List<SignList> signLists) {
        this.signLists = signLists;
    }

    public List<MeetingList> getMeetingLists() {
        return meetingLists;
    }
    public void setMeetingLists(List<MeetingList> meetingLists) {
        this.meetingLists = meetingLists;
    }

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public class MeetingList{
        private String meeting_id;
        private String meeting_name;
        private String meeting_place;
        private String meeting_start;
        private String meeting_end;

        public String getMeeting_end() {
            return meeting_end;
        }

        public void setMeeting_end(String meeting_end) {
            this.meeting_end = meeting_end;
        }


        public String getMeeting_id() {
            return meeting_id;
        }

        public void setMeeting_id(String meeting_id) {
            this.meeting_id = meeting_id;
        }

        public String getMeeting_name() {
            return meeting_name;
        }

        public void setMeeting_name(String meeting_name) {
            this.meeting_name = meeting_name;
        }

        public String getMeeting_place() {
            return meeting_place;
        }

        public void setMeeting_place(String meeting_place) {
            this.meeting_place = meeting_place;
        }

        public String getMeeting_start() {
            return meeting_start;
        }

        public void setMeeting_start(String meeting_start) {
            this.meeting_start = meeting_start;
        }
    }

    public class SignList{

        private String cname;
        private String cnumber;
        private String dname;
        private String sign_time;
        private String state;

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }


        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getCnumber() {
            return cnumber;
        }

        public void setCnumber(String cnumber) {
            this.cnumber = cnumber;
        }

        public String getDname() {
            return dname;
        }

        public void setDname(String dname) {
            this.dname = dname;
        }

        public String getSign_time() {
            return sign_time;
        }
        public void setSign_time(String sign_time) {
            this.sign_time = sign_time;
        }



    }



}
