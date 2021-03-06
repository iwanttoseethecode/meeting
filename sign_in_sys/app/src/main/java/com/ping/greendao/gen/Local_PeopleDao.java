package com.ping.greendao.gen;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import com.test.lyx.sign_in_sys.entity.Local_Meeting;

import com.test.lyx.sign_in_sys.entity.Local_People;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LOCAL__PEOPLE".
*/
public class Local_PeopleDao extends AbstractDao<Local_People, Long> {

    public static final String TABLENAME = "LOCAL__PEOPLE";

    /**
     * Properties of entity Local_People.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property _id = new Property(0, Long.class, "_id", true, "_id");
        public final static Property Cid = new Property(1, int.class, "cid", false, "CID");
        public final static Property C_name = new Property(2, String.class, "c_name", false, "C_NAME");
        public final static Property C_num = new Property(3, String.class, "c_num", false, "C_NUM");
        public final static Property D_name = new Property(4, String.class, "d_name", false, "D_NAME");
        public final static Property S_date = new Property(5, String.class, "s_date", false, "S_DATE");
        public final static Property S_state = new Property(6, String.class, "s_state", false, "S_STATE");
        public final static Property PeopleId = new Property(7, Long.class, "peopleId", false, "PEOPLE_ID");
    }

    private DaoSession daoSession;

    private Query<Local_People> local_Meeting_PeoplesQuery;

    public Local_PeopleDao(DaoConfig config) {
        super(config);
    }
    
    public Local_PeopleDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOCAL__PEOPLE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: _id
                "\"CID\" INTEGER NOT NULL ," + // 1: cid
                "\"C_NAME\" TEXT," + // 2: c_name
                "\"C_NUM\" TEXT," + // 3: c_num
                "\"D_NAME\" TEXT," + // 4: d_name
                "\"S_DATE\" TEXT," + // 5: s_date
                "\"S_STATE\" TEXT," + // 6: s_state
                "\"PEOPLE_ID\" INTEGER);"); // 7: peopleId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOCAL__PEOPLE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Local_People entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
        stmt.bindLong(2, entity.getCid());
 
        String c_name = entity.getC_name();
        if (c_name != null) {
            stmt.bindString(3, c_name);
        }
 
        String c_num = entity.getC_num();
        if (c_num != null) {
            stmt.bindString(4, c_num);
        }
 
        String d_name = entity.getD_name();
        if (d_name != null) {
            stmt.bindString(5, d_name);
        }
 
        String s_date = entity.getS_date();
        if (s_date != null) {
            stmt.bindString(6, s_date);
        }
 
        String s_state = entity.getS_state();
        if (s_state != null) {
            stmt.bindString(7, s_state);
        }
 
        Long peopleId = entity.getPeopleId();
        if (peopleId != null) {
            stmt.bindLong(8, peopleId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Local_People entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
        stmt.bindLong(2, entity.getCid());
 
        String c_name = entity.getC_name();
        if (c_name != null) {
            stmt.bindString(3, c_name);
        }
 
        String c_num = entity.getC_num();
        if (c_num != null) {
            stmt.bindString(4, c_num);
        }
 
        String d_name = entity.getD_name();
        if (d_name != null) {
            stmt.bindString(5, d_name);
        }
 
        String s_date = entity.getS_date();
        if (s_date != null) {
            stmt.bindString(6, s_date);
        }
 
        String s_state = entity.getS_state();
        if (s_state != null) {
            stmt.bindString(7, s_state);
        }
 
        Long peopleId = entity.getPeopleId();
        if (peopleId != null) {
            stmt.bindLong(8, peopleId);
        }
    }

    @Override
    protected final void attachEntity(Local_People entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Local_People readEntity(Cursor cursor, int offset) {
        Local_People entity = new Local_People( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // _id
            cursor.getInt(offset + 1), // cid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // c_name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // c_num
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // d_name
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // s_date
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // s_state
            cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7) // peopleId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Local_People entity, int offset) {
        entity.set_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCid(cursor.getInt(offset + 1));
        entity.setC_name(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setC_num(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setD_name(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setS_date(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setS_state(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setPeopleId(cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Local_People entity, long rowId) {
        entity.set_id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Local_People entity) {
        if(entity != null) {
            return entity.get_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Local_People entity) {
        return entity.get_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "peoples" to-many relationship of Local_Meeting. */
    public List<Local_People> _queryLocal_Meeting_Peoples(Long peopleId) {
        synchronized (this) {
            if (local_Meeting_PeoplesQuery == null) {
                QueryBuilder<Local_People> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.PeopleId.eq(null));
                local_Meeting_PeoplesQuery = queryBuilder.build();
            }
        }
        Query<Local_People> query = local_Meeting_PeoplesQuery.forCurrentThread();
        query.setParameter(0, peopleId);
        return query.list();
    }

    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getLocal_MeetingDao().getAllColumns());
            builder.append(" FROM LOCAL__PEOPLE T");
            builder.append(" LEFT JOIN LOCAL__MEETING T0 ON T.\"PEOPLE_ID\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected Local_People loadCurrentDeep(Cursor cursor, boolean lock) {
        Local_People entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        Local_Meeting localMeeting = loadCurrentOther(daoSession.getLocal_MeetingDao(), cursor, offset);
        entity.setLocalMeeting(localMeeting);

        return entity;    
    }

    public Local_People loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<Local_People> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<Local_People> list = new ArrayList<Local_People>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<Local_People> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<Local_People> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
