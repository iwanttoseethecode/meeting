package com.test.lyx.sign_in_sys.entity;

import com.ping.greendao.gen.DaoSession;
import com.ping.greendao.gen.Local_MeetingDao;
import com.ping.greendao.gen.Local_PeopleDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;

/**
 * Created by Administrator on 2017/5/24 0024.
 */




@Entity
public class Local_People {

    @Id(autoincrement = true)
    private Long _id;
    @NotNull
    private int cid;//人员cid

    private String c_name;//姓名
    private String c_num;//物理卡号
    private String d_name;//部门
    private String s_date;//签到时间
    private String s_state; //签到状态

    private Long peopleId;//关联（这里用mid代替）

    @ToOne(joinProperty = "peopleId")
    private Local_Meeting localMeeting;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 808196119)
    private transient Local_PeopleDao myDao;
    @Generated(hash = 1895273973)
    private transient Long localMeeting__resolvedKey;

    @Generated(hash = 2119930837)
    public Local_People(Long _id, int cid, String c_name, String c_num, String d_name,
            String s_date, String s_state, Long peopleId) {
        this._id = _id;
        this.cid = cid;
        this.c_name = c_name;
        this.c_num = c_num;
        this.d_name = d_name;
        this.s_date = s_date;
        this.s_state = s_state;
        this.peopleId = peopleId;
    }
    @Generated(hash = 574281031)
    public Local_People() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public int getCid() {
        return this.cid;
    }
    public void setCid(int cid) {
        this.cid = cid;
    }
    public String getC_name() {
        return this.c_name;
    }
    public void setC_name(String c_name) {
        this.c_name = c_name;
    }
    public String getC_num() {
        return this.c_num;
    }
    public void setC_num(String c_num) {
        this.c_num = c_num;
    }
    public String getD_name() {
        return this.d_name;
    }
    public void setD_name(String d_name) {
        this.d_name = d_name;
    }
    public String getS_date() {
        return this.s_date;
    }
    public void setS_date(String s_date) {
        this.s_date = s_date;
    }
    public Long getPeopleId() {
        return this.peopleId;
    }
    public void setPeopleId(Long peopleId) {
        this.peopleId = peopleId;
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 384693499)
    public Local_Meeting getLocalMeeting() {
        Long __key = this.peopleId;
        if (localMeeting__resolvedKey == null
                || !localMeeting__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            Local_MeetingDao targetDao = daoSession.getLocal_MeetingDao();
            Local_Meeting localMeetingNew = targetDao.load(__key);
            synchronized (this) {
                localMeeting = localMeetingNew;
                localMeeting__resolvedKey = __key;
            }
        }
        return localMeeting;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1783079459)
    public void setLocalMeeting(Local_Meeting localMeeting) {
        synchronized (this) {
            this.localMeeting = localMeeting;
            peopleId = localMeeting == null ? null : localMeeting.get_id();
            localMeeting__resolvedKey = peopleId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1081883073)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getLocal_PeopleDao() : null;
    }
    public String getS_state() {
        return this.s_state;
    }
    public void setS_state(String s_state) {
        this.s_state = s_state;
    }
  
}

