package test.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExerciseDbHelper {
	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_BODY_PART = "bodypart";
    public static final String KEY_CATEGORY = "category";
    
	private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    
    private static final String DATABASE_NAME = "exercise.db";
    private static final int DATABASE_VERSION = 1;
    
    private static final String EXERCISE_TABLE = "exercise";

    private static final String CREATE_SET_TABLE = "create table "+ EXERCISE_TABLE + " (" +
    												KEY_ID +" integer primary key autoincrement, " +
    												KEY_NAME +" text not null, " + 
    												KEY_BODY_PART +" text not null, " +
    												KEY_CATEGORY +" text not null" +
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
            db.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE);
            onCreate(db);
        }
    }
    
    public void Reset() { 
    	mDbHelper.onUpgrade(this.mDb, 1, 1); 
    }
    
    public ExerciseDbHelper(Context ctx) {
        mCtx = ctx;
        mDbHelper = new DatabaseHelper(mCtx);
    }
    
    public ExerciseDbHelper open() throws SQLException {
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
    
    public void close() { 
    	mDbHelper.close(); 
	}

    public void createExerciseEntry(Exercise exercise) {
    	ContentValues cv = new ContentValues();
    	cv.put(KEY_ID, exercise.getId()); 
    	cv.put(KEY_NAME, exercise.getName());
    	cv.put(KEY_BODY_PART, exercise.getBodyPart());
    	cv.put(KEY_CATEGORY, exercise.getCategory()); 
        mDb.insert(EXERCISE_TABLE, null, cv);
    }
    
    public Exercise getFirstExerciseFromDB() throws SQLException {
        Cursor cur = mDb.query(true, EXERCISE_TABLE, 
                               new String[] {KEY_ID, KEY_NAME, KEY_BODY_PART, KEY_CATEGORY},
                               null, null, null, null, null, null);
        
//        Cursor cursor = mDb.query(TABLE_NAME, new String[] {"_id", "title", "title_raw"}, 
//                "title_raw like " + "'%Smith%'", null, null, null, null);

        if(cur.moveToFirst()) {
        	int _id = cur.getInt(cur.getColumnIndex(KEY_ID));
        	String name = cur.getString(cur.getColumnIndex(KEY_NAME));
        	String bodyPart = cur.getString(cur.getColumnIndex(KEY_BODY_PART));
        	String category = cur.getString(cur.getColumnIndex(KEY_CATEGORY));
        	cur.close();
            return new Exercise(_id, name, bodyPart, category);
        } else {
        	cur.close();
        	return null;
        }
    }    
}