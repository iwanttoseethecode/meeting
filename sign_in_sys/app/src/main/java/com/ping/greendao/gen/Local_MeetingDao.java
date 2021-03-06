package com.ping.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.test.lyx.sign_in_sys.entity.Local_Meeting;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LOCAL__MEETING".
*/
public class Local_MeetingDao extends AbstractDao<Local_Meeting, Long> {

    public static final String TABLENAME = "LOCAL__MEETING";

    /**
     * Properties of entity Local_Meeting.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property _id = new Property(0, Long.class, "_id", true, "_id");
        public final static Property Mid = new Property(1, int.class, "mid", false, "MID");
        public final static Property MStart = new Property(2, String.class, "mStart", false, "M_START");
        public final static Property MEnd = new Property(3, String.class, "mEnd", false, "M_END");
        public final static Property MName = new Property(4, String.class, "mName", false, "M_NAME");
        public final static Property MPlace = new Property(5, String.class, "mPlace", false, "M_PLACE");
        public final static Property P_num = new Property(6, String.class, "p_num", false, "P_NUM");
        public final static Property P_tNum = new Property(7, String.class, "p_tNum", false, "P_T_NUM");
    }

    private DaoSession daoSession;


    public Local_MeetingDao(DaoConfig config) {
        super(config);
    }
    
    public Local_MeetingDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOCAL__MEETING\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: _id
                "\"MID\" INTEGER NOT NULL ," + // 1: mid
                "\"M_START\" TEXT," + // 2: mStart
                "\"M_END\" TEXT," + // 3: mEnd
                "\"M_NAME\" TEXT," + // 4: mName
                "\"M_PLACE\" TEXT," + // 5: mPlace
                "\"P_NUM\" TEXT," + // 6: p_num
                "\"P_T_NUM\" TEXT);"); // 7: p_tNum
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOCAL__MEETING\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Local_Meeting entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
        stmt.bindLong(2, entity.getMid());
 
        String mStart = entity.getMStart();
        if (mStart != null) {
            stmt.bindString(3, mStart);
        }
 
        String mEnd = entity.getMEnd();
        if (mEnd != null) {
            stmt.bindString(4, mEnd);
        }
 
        String mName = entity.getMName();
        if (mName != null) {
            stmt.bindString(5, mName);
        }
 
        String mPlace = entity.getMPlace();
        if (mPlace != null) {
            stmt.bindString(6, mPlace);
        }
 
        String p_num = entity.getP_num();
        if (p_num != null) {
            stmt.bindString(7, p_num);
        }
 
        String p_tNum = entity.getP_tNum();
        if (p_tNum != null) {
            stmt.bindString(8, p_tNum);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Local_Meeting entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
        stmt.bindLong(2, entity.getMid());
 
        String mStart = entity.getMStart();
        if (mStart != null) {
            stmt.bindString(3, mStart);
        }
 
        String mEnd = entity.getMEnd();
        if (mEnd != null) {
            stmt.bindString(4, mEnd);
        }
 
        String mName = entity.getMName();
        if (mName != null) {
            stmt.bindString(5, mName);
        }
 
        String mPlace = entity.getMPlace();
        if (mPlace != null) {
            stmt.bindString(6, mPlace);
        }
 
        String p_num = entity.getP_num();
        if (p_num != null) {
            stmt.bindString(7, p_num);
        }
 
        String p_tNum = entity.getP_tNum();
        if (p_tNum != null) {
            stmt.bindString(8, p_tNum);
        }
    }

    @Override
    protected final void attachEntity(Local_Meeting entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Local_Meeting readEntity(Cursor cursor, int offset) {
        Local_Meeting entity = new Local_Meeting( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // _id
            cursor.getInt(offset + 1), // mid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // mStart
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // mEnd
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // mName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // mPlace
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // p_num
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // p_tNum
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Local_Meeting entity, int offset) {
        entity.set_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMid(cursor.getInt(offset + 1));
        entity.setMStart(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMEnd(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setMName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setMPlace(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setP_num(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setP_tNum(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Local_Meeting entity, long rowId) {
        entity.set_id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Local_Meeting entity) {
        if(entity != null) {
            return entity.get_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Local_Meeting entity) {
        return entity.get_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
