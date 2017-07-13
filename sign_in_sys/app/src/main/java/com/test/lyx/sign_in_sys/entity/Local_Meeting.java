package com.test.lyx.sign_in_sys.entity;

import com.ping.greendao.gen.DaoSession;
import com.ping.greendao.gen.Local_MeetingDao;
import com.ping.greendao.gen.Local_PeopleDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

/**
 * Created by Administrator on 2017/5/24 0024.
 */
@Entity
public class Local_Meeting {

    @Id(autoincrement = true)
    private Long _id;
    @NotNull
    private int mid;//会议id

    @ToMany(referencedJoinProperty = "peopleId")
    private List<Local_People> peoples;


    private String mStart;//会议开始时间
    private String mEnd;//会议结束时间
    private String mName;//会议名&主题
    private String mPlace;//会议地点
    private String p_num;//会议应参与人数
    private String p_tNum;//会议实际参与人数
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 911994625)
    private transient Local_MeetingDao myDao;
    @Generated(hash = 495962265)
    public Local_Meeting(Long _id, int mid, String mStart, String mEnd,
            String mName, String mPlace, String p_num, String p_tNum) {
        this._id = _id;
        this.mid = mid;
        this.mStart = mStart;
        this.mEnd = mEnd;
        this.mName = mName;
        this.mPlace = mPlace;
        this.p_num = p_num;
        this.p_tNum = p_tNum;
    }
    @Generated(hash = 1998942741)
    public Local_Meeting() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public int getMid() {
        return this.mid;
    }
    public void setMid(int mid) {
        this.mid = mid;
    }
    public String getMStart() {
        return this.mStart;
    }
    public void setMStart(String mStart) {
        this.mStart = mStart;
    }
    public String getMEnd() {
        return this.mEnd;
    }
    public void setMEnd(String mEnd) {
        this.mEnd = mEnd;
    }
    public String getMName() {
        return this.mName;
    }
    public void setMName(String mName) {
        this.mName = mName;
    }
    public String getMPlace() {
        return this.mPlace;
    }
    public void setMPlace(String mPlace) {
        this.mPlace = mPlace;
    }
    public String getP_num() {
        return this.p_num;
    }
    public void setP_num(String p_num) {
        this.p_num = p_num;
    }
    public String getP_tNum() {
        return this.p_tNum;
    }
    public void setP_tNum(String p_tNum) {
        this.p_tNum = p_tNum;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 887703716)
    public List<Local_People> getPeoples() {
        if (peoples == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            Local_PeopleDao targetDao = daoSession.getLocal_PeopleDao();
            List<Local_People> peoplesNew = targetDao
                    ._queryLocal_Meeting_Peoples(_id);
            synchronized (this) {
                if (peoples == null) {
                    peoples = peoplesNew;
                }
            }
        }
        return peoples;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1540104093)
    public synchronized void resetPeoples() {
        peoples = null;
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
    @Generated(hash = 1715720794)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getLocal_MeetingDao() : null;
    }





}
