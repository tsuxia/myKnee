package ca.utoronto.ece1778.services;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class ResultService {
	
	private final static String TABLE_NAME = "Measurment_table";
	public final static String FIELD_id = "event_id";
	public final static String FIELD_RESULT = "event_result";
	
	private DBOpenHelper dbOpenHelper;
	
	public ResultService (Context context){
		
		this.dbOpenHelper = new DBOpenHelper(context);
	}
	
	//Add a new item into database
	public void save (Result result){
		
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("insert into " + TABLE_NAME + "(event_result, time_int, which_leg) values (?,?,?)", new Object[] {result.getEvent_result(),
			    result.getTime_int(), result.getWhich_leg()});
	}
	//delete an item from database
    public void delete (int event_id){
		
    	SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
    	db.execSQL("delete from " + TABLE_NAME + " where event_id=?",new Object[] {event_id});
	}
    //edit an item from database
    public void update (Result result){
		
    	SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
    	db.execSQL("update "+TABLE_NAME+" set event_result=?, time_int=?, which_leg=?",new Object[] {result.getEvent_result(),
				 result.getTime_int(), result.getWhich_leg()});
	}
    //find a item from the database
    public Result find (Integer id){
	  
    	SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
    	Cursor cursor = db.rawQuery("select * from "+TABLE_NAME+" where event_id=?", 
    			new String[]{id.toString()} );
    	if (cursor.moveToFirst()){
    		int event_id = cursor.getInt(cursor.getColumnIndex("event_id"));
    		int event_result = cursor.getInt(cursor.getColumnIndex("event_result"));
    		int time_int = cursor.getInt(cursor.getColumnIndex("time_int"));
    		String which_leg = cursor.getString(cursor.getColumnIndex("which_leg"));
    		return new Result(event_id, event_result, time_int, which_leg);
    	}
    	cursor.close();
    	return null;
    }

    public List<Result> getScrollData (int offset, int maxResult){
    	
    	List<Result> results = new ArrayList<Result>();
    	SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
    	Cursor cursor = db.rawQuery("select * from Measurment_table order by event_id " +
    			"asc limit ?, ?", new String [] {String.valueOf(offset), String.valueOf(maxResult)});
    	while (cursor.moveToNext()){
    		
    		int id = cursor.getInt(cursor.getColumnIndex("event_id"));
    		int result = cursor.getInt(cursor.getColumnIndex("event_result"));
    		int time_int = cursor.getInt(cursor.getColumnIndex("time_int"));
    		String which_leg = cursor.getString(cursor.getColumnIndex("which_leg"));
    		results.add(new Result(id, result, time_int, which_leg));
    	}
    	
    	cursor.close();
    	return results;
    }
    
    //for show1();
    public Cursor getCursorScrollData (int offset, int maxResult){
    	SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
    	Cursor cursor = db.rawQuery("select event_id as _id, event_result, date, time_int, which_leg  from Measurment_table order by _id " +
    			"asc limit ?, ?", new String [] {String.valueOf(offset), String.valueOf(maxResult)});
    	return cursor;
    }
    
    public int getCount (){
    	
    	SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
    	Cursor cursor = db.rawQuery("select count(*) from Measurment_table", null );
    	cursor.moveToFirst();
    	int result_count = (int) cursor.getLong(0);
    	cursor.close();
    	
    	return result_count;
    }
	
	public int getfirsttime (){
		List<Result> results = new ArrayList<Result>();
    	SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
    	Cursor cursor = db.rawQuery("select * from Measurment_table order by ?", new String [] {"event_id"});
    	int firsttime = 0;
    	if (cursor.moveToFirst()){
    		firsttime = cursor.getInt(cursor.getColumnIndex("time_int"));
    	}
    	
		return firsttime;
	}
	
	public void deleteall(){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		
		String sq2 = "CREATE TABLE " + TABLE_NAME + " (" + FIELD_id
				+ " INTEGER primary key autoincrement, " + " " + FIELD_RESULT
				+ " INTEGER, time_int INTEGER, which_leg String)";
		db.execSQL(sq2);
	}

}
