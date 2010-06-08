package test.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PreferenceDbHelper {
	public static final String KEY_USER_ID = "user_id";
	public static final String KEY_USERNAME = "username";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_EMAIL = "email";
    
	private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    
    private static final String DATABASE_NAME = "exercise.db";
    private static final int DATABASE_VERSION = 1;
    
    private static final String PREFERENCES_TABLE = "preference";

    private static final String CREATE_SET_TABLE = "create table "+ PREFERENCES_TABLE + " (" +
    												KEY_USER_ID +" integer unique primary key, " +
    												KEY_USERNAME+" varchar not null, " + 
    												KEY_PASSWORD +" varchar not null, " + 
    												KEY_EMAIL +" varchar not null " + 
    												");";
                                              
    private Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) { 
            super(context, DATABASE_NAME, null, DATABASE_VERSION); 
        }
    
        public void onCreate(SQLiteDatabase db) { 
            db.execSQL(CREATE_SET_TABLE);
        }
    
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + PREFERENCES_TABLE);
            onCreate(db);
        }
    }
    
    public void Reset() { 
    	mDbHelper.onUpgrade(this.mDb, 1, 1); 
    }
    
    public PreferenceDbHelper(Context ctx) {
        mCtx = ctx;
        mDbHelper = new DatabaseHelper(mCtx);
    }
    
    public PreferenceDbHelper open() throws SQLException {
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
    
    public void close() { 
    	mDbHelper.close(); 
	}

    public void createUserEntry(UserData userData) {
    	ContentValues cv = new ContentValues();
    	cv.put(KEY_USER_ID, userData.getUserId()); 
    	cv.put(KEY_USERNAME, userData.getUsername());
    	cv.put(KEY_PASSWORD, userData.getPassword());
    	cv.put(KEY_EMAIL, userData.getEmail()); 
        mDb.insert(PREFERENCES_TABLE, null, cv);
    }
    
    public UserData getFirstExerciseFromDB() throws SQLException {
        Cursor cur = mDb.query(true, PREFERENCES_TABLE, 
                               new String[] {KEY_USER_ID, KEY_USERNAME, KEY_PASSWORD, KEY_EMAIL},
                               null, null, null, null, null, null);
        
//        Cursor cursor = mDb.query(TABLE_NAME, new String[] {"_id", "title", "title_raw"}, 
//                "title_raw like " + "'%Smith%'", null, null, null, null);

        if(cur.moveToFirst()) {
        	int user_id = cur.getInt(cur.getColumnIndex(KEY_USER_ID));
        	String username = cur.getString(cur.getColumnIndex(KEY_USERNAME));
        	String password = cur.getString(cur.getColumnIndex(KEY_PASSWORD));
        	String email = cur.getString(cur.getColumnIndex(KEY_EMAIL));
        	cur.close();
            return new UserData(user_id, username, password, email);
        } else {
        	cur.close();
        	return null;
        }
    }    
}