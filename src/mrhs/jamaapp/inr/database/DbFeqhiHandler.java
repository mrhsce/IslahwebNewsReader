package mrhs.jamaapp.inr.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import mrhs.jamaapp.inr.main.Commons;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class DbFeqhiHandler {
	private static final boolean LOCAL_SHOW_LOG = true;
	
	private DatabaseHandler parent;
	 	
	
	public DbFeqhiHandler(DatabaseHandler parent){
		this.parent = parent;
	}
	
	public boolean initialInsert(String title,String jdate,String type,String answerText){
		
		String gdate = "";
		try {
			gdate = new SimpleDateFormat("yyyy-MM-dd").
						format(new SimpleDateFormat("yyyy/MM/dd",Locale.getDefault()).
						parse(parent.dateConvertor.PersianToGregorian(jdate)));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}			
		
		ContentValues values=new ContentValues();
		values.put("title",title);
		values.put("jdate",jdate);
		values.put("gdate",gdate);
		values.put("type", type);
		values.put("answerText", answerText);
		try{
			return parent.db.insert(DatabaseHandler.TABLE_FEQHI, null, values)>0;
		}catch(Exception e){
			e.printStackTrace();
			log("Error inserting values to the "+DatabaseHandler.TABLE_FEQHI+" table");
			return false;
		}
	}
		
	public boolean exists(String title,String jdate){
			Cursor cursor = parent.db.query(DatabaseHandler.TABLE_FEQHI, new String[]{"title","jdate"},
					"title='"+title+"' and jdate='"+jdate+"'",null, null, null, null);
			if(cursor != null){
				return cursor.moveToFirst();
				}
			return false;
	}
	
	public Cursor getAll(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_FEQHI, new String[]{
				"id","title","jdate","type","answerText"},null,null, null, null, null);
		return cursor;
	}
	
	public Cursor getAllByType(String type){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_FEQHI, new String[]{
				"id","title","jdate","type","answerText"},"type='"+type+"'",null, null, null, "gdate desc");
		return cursor;
	}
		
	public Cursor getById(Integer id){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_FEQHI, new String[]{
				"id","title","jdate","type","answerText"},"id="+id,null, null, null, "gdate desc");
		return cursor;
	}
	
	public int deleteEntry(Integer id){
		return parent.db.delete(DatabaseHandler.TABLE_FEQHI, "id = "+id, null);
	}
	
	public int deleteEntry(String type){
		return parent.db.delete(DatabaseHandler.TABLE_FEQHI, "type='"+type+"'", null);
	}
	
	public int deleteEntry(String title,String jdate){
		return parent.db.delete(DatabaseHandler.TABLE_FEQHI, "title='"+title+"' and jdate='"+jdate+"'", null);
	}	
	
	public void deleteEntryBefore(Date date){
		String jdate = parent.dateConvertor.GregorianToPersian(new SimpleDateFormat("yyyy/MM/dd").format(date));
		Cursor cursor = getAll();
		do{
			if(cursor.moveToFirst()){
				if(cursor.getString(2) != null){
					if(parent.dateConvertor.CalculateDaysBetween(jdate,cursor.getString(2))==0){
						deleteEntry(cursor.getInt(0));
					}
				}
			}
		}while(cursor.moveToNext());
	}	
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}
}
