package ca.utoronto.ece1778.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	//Declare some useful variables
	private final static String DATABASE_NAME = "mesurment_result.db";
	private final static int DATABASE_VERSION = 1;
	private final static String TABLE_NAME = "Measurment_table";
	public final static String FIELD_id = "event_id";
	public final static String FIELD_Result = "event_result";
	
	
	public DBOpenHelper(Context context) {
		//When created, will be saved under database/
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Applied when the database first created
		String sql = "CREATE TABLE " + TABLE_NAME + " (" + FIELD_id
				+ " INTEGER primary key autoincrement, " + " " + FIELD_Result
				+ " INTEGER, time_int INTEGER, which_leg String )";
		db.execSQL(sql);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int OldVersion, int NewVersion) {
		// Applied when the version number changes
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}

}
