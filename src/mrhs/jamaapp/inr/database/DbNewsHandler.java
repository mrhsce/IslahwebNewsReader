package mrhs.jamaapp.inr.database;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import mrhs.jamaapp.inr.main.Commons;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class DbNewsHandler {
	private static final boolean LOCAL_SHOW_LOG = true;
	
	private DatabaseHandler parent;
	 	
	
	public DbNewsHandler(DatabaseHandler parent){
		this.parent = parent;
	}
	String CREATE_NEWS="CREATE TABLE IF NOT EXISTS "+ 
			" (id INTEGER PRIMARY KEY,title text not null,date DATE not null," +
			" jdate text not null,indexImg text not null,source text not null," +
			"type text not null,bigImg text,pageLink text not null,mainText text)";	
	public boolean initialInsert(String title,String jDate,String indexImgAddr,String source,String type,String pageLink){
		
		String gdate = "";
		try {
			gdate = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).
					format(new SimpleDateFormat("yyyy/MM/dd",Locale.getDefault()).
					parse(parent.dateConvertor.PersianToGregorian(jDate)));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ContentValues values=new ContentValues();
		values.put("title",title);
		values.put("gdate",gdate);
		values.put("jdate",jDate);
		values.put("indexImg", indexImgAddr);
		values.put("source",source);
		values.put("type",type);
		values.put("pageLink", pageLink);
		try{
			return parent.db.insert(DatabaseHandler.TABLE_NEWS, null, values)>0;
		}catch(Exception e){
			e.printStackTrace();
			log("Error inserting values to the "+DatabaseHandler.TABLE_NEWS+" table");
			return false;
		}
	}
	
	public boolean secondInsert(Integer id,String mainText,String imgAddress){
		ContentValues values=new ContentValues();
		values.put("mainText",mainText);
		values.put("bigImg",imgAddress);
		return parent.db.update(DatabaseHandler.TABLE_NEWS, values, "id = "+id, null)>0;
	}
	
	public boolean imgUpdate(Integer id,String address,boolean pictureType){
		ContentValues values=new ContentValues();
		if(pictureType)
			values.put("indexImg",address);
		else
			values.put("bigImg",address);
		return parent.db.update(DatabaseHandler.TABLE_NEWS, values, "id = "+id, null)>0;
	}
	
	public boolean exists(String title,String jdate){
		
	}
	
	public Cursor getAll(){
		
	}
	
	public Cursor getAllByType(){
		
	}
	
	public Cursor getById(){
		
	}
	
	public Cursor getThoseWithoutIndexImage(){
		
	}
	
	public Cursor getgetThoseWithoutMainText(){
		
	}
	
	public Cursor getgetThoseWithoutBigImage(){
		
	}
	
	
	private static void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d("DbNewsHandler",message);
	}

}
