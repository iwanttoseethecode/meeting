package com.test.lyx.sign_in_sys.utils;

import android.content.Context;
import android.util.Log;

import com.ping.greendao.gen.Local_MeetingDao;
import com.test.lyx.sign_in_sys.entity.Local_Meeting;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class LocalMeetingDaoUtils {
    private DaoManager mManager;
    private static final String TAG = LocalMeetingDaoUtils.class.getSimpleName();

    public LocalMeetingDaoUtils(Context context) {
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }


    /**
     * 完成local_meeting记录的插入，如果表未创建，先创建local_meeting表
     */
    public boolean insertLocalMeeting(Local_Meeting meeting) {
        boolean flag = false;
        flag = mManager.getDaoSession().getLocal_MeetingDao().insert(meeting) == -1 ? false : true;
        Log.i(TAG, "insert meeting :" + flag + "-->" + meeting.toString());
        return flag;
    }


    //插入多条数据，在子线程操作
    public boolean insertMeetingList(final List<Local_Meeting> meetingList) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (Local_Meeting meeting : meetingList) {
                        mManager.getDaoSession().insertOrReplace(meeting);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    //修改一条数据
    public boolean updateMeeting(Local_Meeting meeting) {
        boolean flag = false;
        try {
            mManager.getDaoSession().update(meeting);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //删除一条数据
    public boolean deleteMeeting(Local_Meeting meeting) {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(meeting);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //删除所有记录
    public boolean deleteAll() {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(Local_Meeting.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //查询所有记录
    public List<Local_Meeting> queryAllMeeting() {
        return mManager.getDaoSession().loadAll(Local_Meeting.class);
    }

    //根据主键ID查询记录
    public Local_Meeting queryMeetingById(long key) {
        return mManager.getDaoSession().load(Local_Meeting.class, key);
    }

    //使用native sql进行查询操作
    public List<Local_Meeting> queryMeetingByNativeSql(String sql, String[] conditions) {
        return mManager.getDaoSession().queryRaw(Local_Meeting.class, sql, conditions);
    }


    //使用queryBuilder进行查询
    public List<Local_Meeting> queryMeetingByQueryBuilder(long id){
        QueryBuilder<Local_Meeting> queryBuilder = mManager.getDaoSession().queryBuilder(Local_Meeting.class);
        return queryBuilder.where(Local_MeetingDao.Properties._id.eq(id)).list();
    }


    public void closeCon(){

        mManager.closeConnection();
    }







}
