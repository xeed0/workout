package test.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SetDBHelper {
    public static final String KEY_ID = "_id";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_EXERCISE_ID = "exercise_id";
    public static final String KEY_ITERATIONS = "iterations";
    public static final String KEY_SET_NUMBER = "number";
    public static final String KEY_WEIGHT = "weight";
    
	private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    
    private static final String DATABASE_NAME = "exercise.db";
    private static final int DATABASE_VERSION = 1;
    
    private static final String SET_TABLE = "set";

    private static final String CREATE_SET_TABLE = "create table "+ SET_TABLE + " (" +
    												KEY_ID +" integer primary key autoincrement, " +
    												KEY_USER_ID +" integer not null, " + 
    												KEY_EXERCISE_ID +" integer not null, " +
    												KEY_WEIGHT +" integer not null ," +
													KEY_ITERATIONS +" integer not null, " +
    												KEY_SET_NUMBER +" integer not null " +
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
            db.execSQL("DROP TABLE IF EXISTS " + SET_TABLE);
            onCreate(db);
        }
    }
    
    public void Reset() { mDbHelper.onUpgrade(this.mDb, 1, 1); }
    
    public SetDBHelper(Context ctx) {
        mCtx = ctx;
        mDbHelper = new DatabaseHelper(mCtx);
    }
    
    public SetDBHelper open() throws SQLException {
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }
    
    public void close() { 
    	mDbHelper.close(); 
	}

    public void createWorkoutSetEntry(WorkoutSet workoutSet) {
    	ContentValues cv = new ContentValues();
    	cv.put(KEY_USER_ID, workoutSet.getUserId()); 
    	cv.put(KEY_EXERCISE_ID, workoutSet.getExerciseId());
    	cv.put(KEY_ITERATIONS, workoutSet.getIterations());
    	cv.put(KEY_SET_NUMBER, workoutSet.getSetNumber()); 
		cv.put(KEY_WEIGHT, workoutSet.getWeight());
        mDb.insert(SET_TABLE, null, cv);
    }
    
    public WorkoutSet getFirstWorkoutSetFromDB() throws SQLException {
        Cursor cur = mDb.query(true, SET_TABLE, 
                               new String[] {KEY_ID, KEY_USER_ID, KEY_EXERCISE_ID, KEY_WEIGHT, 
        									KEY_ITERATIONS, KEY_SET_NUMBER},
                               null, null, null, null, null, null);
        
//        Cursor cursor = mDb.query(TABLE_NAME, new String[] {"_id", "title", "title_raw"}, 
//                "title_raw like " + "'%Smith%'", null, null, null, null);

        if(cur.moveToFirst()) {
        	int _id = cur.getInt(cur.getColumnIndex(KEY_ID));
        	int user_id = cur.getInt(cur.getColumnIndex(KEY_USER_ID));
        	int exercise_id = cur.getInt(cur.getColumnIndex(KEY_EXERCISE_ID));
        	int weight = cur.getInt(cur.getColumnIndex(KEY_WEIGHT));
            int iterations = cur.getInt(cur.getColumnIndex(KEY_ITERATIONS));
        	int setNumber = cur.getInt(cur.getColumnIndex(KEY_SET_NUMBER));
        	cur.close();
            return new WorkoutSet(user_id, exercise_id, weight, iterations, setNumber);
        } else {
        	cur.close();
        	return null;
        }
    }    
}