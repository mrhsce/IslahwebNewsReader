package mrhs.jamaapp.inr.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import mrhs.jamaapp.inr.main.Commons;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class DbBookHandler {
	private static final boolean LOCAL_SHOW_LOG = true;
	
	private DatabaseHandler parent;
	 	
	
	public DbBookHandler(DatabaseHandler parent){
		this.parent = parent;
	}
	
	public boolean initialInsert(String title,String details,String indexImgAddr,String summary,String pageLink){
		
		String gdate = "";
		gdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());		
		
		ContentValues values=new ContentValues();
		values.put("title",title);
		values.put("gdate",gdate);
		values.put("details",details);
		values.put("pageLink", pageLink);
		try{
			return parent.db.insert(DatabaseHandler.TABLE_BOOKS, null, values)>0;
		}catch(Exception e){
			e.printStackTrace();
			log("Error inserting values to the "+DatabaseHandler.TABLE_BOOKS+" table");
			return false;
		}
	}
	
	public boolean secondInsert(Integer id,String mainText,String jDate){
		String gdate = "";
		try {
			gdate = new SimpleDateFormat("yyyy-MM-dd").
						format(new SimpleDateFormat("yyyy/MM/dd",Locale.getDefault()).
						parse(parent.dateConvertor.PersianToGregorian(jDate)));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		ContentValues values=new ContentValues();
		values.put("mainText",mainText);
		values.put("jdate",jDate);
		values.put("gdate",gdate);
		
		return parent.db.update(DatabaseHandler.TABLE_BOOKS, values, "id = "+id, null)>0;
	}
	
	public boolean imgUpdate(Integer id,String address){
		ContentValues values=new ContentValues();
		values.put("indexImg",address);
		return parent.db.update(DatabaseHandler.TABLE_BOOKS, values, "id = "+id, null)>0;
	}
	
	public boolean exists(String title){
			Cursor cursor = parent.db.query(DatabaseHandler.TABLE_BOOKS, new String[]{"title","jdate"},
					"title='"+title+"'",null, null, null, null);
			if(cursor != null){
				return cursor.moveToFirst();
				}
			return false;
	}
	
	public Cursor getAll(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_BOOKS, new String[]{
				"id","title","jdate","details","summary","indexImg","pageLink","mainText"},null,null, null, null, null);
		return cursor;
	}
		
	public Cursor getById(Integer id){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_BOOKS, new String[]{
				"id","title","jdate","details","summary","indexImg","pageLink","mainText"},"id="+id,null, null, null, "gdate desc");
		return cursor;
	}
	
	public Cursor getThoseWithoutMainText(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_BOOKS, new String[]{
				"id","title","jdate","details","summary","indexImg","pageLink","mainText"},"mainText="+null,null, null, null, "gdate desc");
		return cursor;
	}
	
	public Cursor getThoseWithoutIndexImage(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_BOOKS, new String[]{
				"id","title","jdate","details","summary","indexImg","pageLink","mainText"},"indexImg like 'http://%'"+null,null, null, null, "gdate desc");
		return cursor;
	}
	
	public int deleteEntry(Integer id){
		return parent.db.delete(DatabaseHandler.TABLE_BOOKS, "id = "+id, null);
	}
	
	public int deleteEntry(String title){
		return parent.db.delete(DatabaseHandler.TABLE_BOOKS, "title='"+title+"'", null);
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
