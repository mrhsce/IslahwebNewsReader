package mrhs.jamaapp.inr.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import mrhs.jamaapp.inr.Commons;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class DbAnnouncementHandler {
	private static final boolean LOCAL_SHOW_LOG = true;
	
	private DatabaseHandler parent;
	 	
	
	public DbAnnouncementHandler(DatabaseHandler parent){
		this.parent = parent;
	}
	public boolean initialInsert(String title,String jdate,String pageLink){
		
		while(reachesLimitation()){
			deleteOldestNonArchived();
		}
		
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
		values.put("pageLink", pageLink);
		try{
			return parent.db.insert(DatabaseHandler.TABLE_ANNOUNCE, null, values)>0;
		}catch(Exception e){
			e.printStackTrace();
			log("Error inserting values to the "+DatabaseHandler.TABLE_ANNOUNCE+" table");
			return false;
		}
	}
	
	public boolean secondInsert(Integer id,String mainText){
		
		ContentValues values=new ContentValues();
		values.put("mainText",mainText);
		
		return parent.db.update(DatabaseHandler.TABLE_ANNOUNCE, values, "id = "+id, null)>0;
	}
	
	public boolean setSeen(Integer id){
		ContentValues values=new ContentValues();
		values.put("seen", 1);
		try{
			return parent.db.update(DatabaseHandler.TABLE_ANNOUNCE, values, "id = "+id, null)>0;
		}catch(Exception e){
			e.printStackTrace();
			log("Error inserting values to the "+DatabaseHandler.TABLE_ANNOUNCE+" table");
			return false;
		}
	}
	
	public boolean setArchived(Integer id,boolean archived){
		ContentValues values=new ContentValues();
		if(archived)
			values.put("archived", 1);
		else
			values.put("archived", 0);
		try{
			return parent.db.update(DatabaseHandler.TABLE_ANNOUNCE, values, "id = "+id, null)>0;
		}catch(Exception e){
			e.printStackTrace();
			log("Error inserting values to the "+DatabaseHandler.TABLE_ANNOUNCE+" table");
			return false;
		}
	}
	
	public boolean exists(String title,String jdate){
			Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ANNOUNCE, new String[]{"title","jdate"},
					"title='"+title+"' and jdate='"+jdate+"'",null, null, null, null);
			if(cursor != null){
				return cursor.moveToFirst();
				}
			return false;
	}
	
	public boolean chkSecondInsert(Integer id){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ANNOUNCE, new String[]{"mainText","seen"},"id="+id,null, null, null, "gdate desc");
		if(cursor.moveToFirst())
			if(cursor.getString(0)!=null){
				if(!cursor.getString(0).equals(""))
					return true;
				else
					return false;
			}
				
			else
				return false;
		else
			return false;
	}
	
	public Cursor getAll(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ANNOUNCE, new String[]{
				"id","title","jdate","pageLink","mainText","seen","archived"},null,null, null, null, "gdate desc");
		return cursor;
	}
	
	public Cursor getAllArchived(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ANNOUNCE, new String[]{
				"id","title","jdate","pageLink","mainText","seen","archived"},"archived = 1",null, null, null, "gdate desc");
		return cursor;
	}	
		
	public Cursor getById(Integer id){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ANNOUNCE, new String[]{
				"id","title","jdate","pageLink","mainText","seen","archived"},"id="+id,null, null, null, "gdate desc");
		return cursor;
	}
	
	public Cursor getThoseWithoutMainText(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ANNOUNCE, new String[]{
				"id","pageLink"},"mainText is null or mainText=''",null, null, null, "gdate desc");
		return cursor;
	}
	
	public void cleanExtras(){
		while(exceedsLimitation())
				deleteOldestNonArchived();		
	}
	
	public boolean reachesLimitation(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ANNOUNCE, new String[]{"count(id)"},null,null, null, null, null);
		if(cursor.moveToFirst())
			return cursor.getInt(0)>=Commons.ANNOUNCE_ENTRY_COUNT;
		return false;
	}
	
	public boolean exceedsLimitation(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ANNOUNCE, new String[]{"count(id)"},null,null, null, null, null);
		if(cursor.moveToFirst())
			return cursor.getInt(0)>Commons.ANNOUNCE_ENTRY_COUNT;
		return false;
	}
	
	public void deleteOldestNonArchived(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ANNOUNCE, new String[]{"id"},"archived = 0",null, null, null, "gdate asc");
		if(cursor.moveToFirst())
			deleteEntry(cursor.getInt(0));
	}
	
	public int deleteEntry(Integer id){
		return parent.db.delete(DatabaseHandler.TABLE_ANNOUNCE, "id = "+id, null);
	}
	
	public int deleteEntry(String title,String jdate){
		return parent.db.delete(DatabaseHandler.TABLE_ANNOUNCE, "title='"+title+"' and jdate='"+jdate+"'", null);
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
	
	public boolean isEmpty(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ANNOUNCE, new String[]{
				"id"},null,null, null, null, null);
		return cursor.moveToFirst();
	}
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}
}
