package com.test.lyx.sign_in_sys.utils;

import android.content.Context;
import android.util.Log;

import com.ping.greendao.gen.Local_PeopleDao;
import com.test.lyx.sign_in_sys.entity.Local_People;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/** 操作Local_People数据库的工具
 * Created by Administrator on 2017/5/24 0024.
 */


public class LocalUserDaoUtils {
    private DaoManager mManager;
    private static final String TAG = LocalUserDaoUtils.class.getSimpleName();

    public LocalUserDaoUtils(Context context) {
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成local_people记录的插入，如果表未创建，先创建local_people表
     */
    public boolean insertLocalUser(Local_People people) {
        boolean flag = false;
        flag = mManager.getDaoSession().getLocal_PeopleDao().insert(people) == -1 ? false : true;
        Log.i(TAG, "insert People :" + flag + "-->" + people.toString());
        return flag;
    }


    //通过会议id和物理卡号查询list
    public List queryByCon(String mid,String cardno){

        try {

            return mManager.getDaoSession().getLocal_PeopleDao().queryBuilder().where(Local_PeopleDao.Properties.PeopleId.eq(mid),
                    Local_PeopleDao.Properties.C_num.eq(cardno)).list();

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;

    }


    //插入多条数据，在子线程操作
    public boolean insertPeopleList(final List<Local_People> peopleList) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (Local_People people : peopleList) {
                        mManager.getDaoSession().insertOrReplace(people);
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
    public boolean updatePeople(Local_People people) {
        boolean flag = false;
        try {
            mManager.getDaoSession().update(people);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //删除一条数据
    public boolean deletePeople(Local_People people) {
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().delete(people);
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
            mManager.getDaoSession().deleteAll(Local_People.class);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //查询所有记录
    public List<Local_People> queryAllPeople() {
        return mManager.getDaoSession().loadAll(Local_People.class);
    }

    //根据主键ID查询记录
    public Local_People queryPeopleById(long key) {
        return mManager.getDaoSession().load(Local_People.class, key);
    }

    //使用native sql进行查询操作
    public List<Local_People> queryPeopleByNativeSql(String sql, String[] conditions) {
        return mManager.getDaoSession().queryRaw(Local_People.class, sql, conditions);
    }


    //使用queryBuilder进行查询
    public List<Local_People> queryPeopleByQueryBuilder(long id){
        QueryBuilder<Local_People> queryBuilder = mManager.getDaoSession().queryBuilder(Local_People.class);
        return queryBuilder.where(Local_PeopleDao.Properties._id.eq(id)).list();
    }


    public void closeCon(){
        mManager.closeConnection();
    }


}
