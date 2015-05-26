package mrhs.jamaapp.inr.database;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import mrhs.jamaapp.inr.Commons;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class DbNewsHandler {
	private static final boolean LOCAL_SHOW_LOG = false;
	
	private DatabaseHandler parent;
	 	
	
	public DbNewsHandler(DatabaseHandler parent){
		this.parent = parent;
	}
	
	public boolean initialInsert(String title,String jDate,String indexImgAddr,String imgAddress,
			String source,String type,String pageLink){
		log("Start inserting");
		while(reachesLimitation(type)){
				log("one deletion");
				deleteOldestNonArchived(type);
			}
			log("before gdate");
			String gdate = "";
			try {
				gdate = new SimpleDateFormat("yyyy-MM-dd").
							format(new SimpleDateFormat("yyyy/MM/dd",Locale.getDefault()).
							parse(parent.dateConvertor.PersianToGregorian(jDate)));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
	
			log("gdate is "+gdate);
			
			ContentValues values=new ContentValues();
			values.put("title",title);
			values.put("jdate",jDate);
			values.put("gdate",gdate);
			values.put("indexImg", indexImgAddr);
			values.put("bigImg",imgAddress);
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
	
	public boolean secondInsert(Integer id,String mainText){
		ContentValues values=new ContentValues();
		values.put("mainText",mainText);
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
	
	public boolean setSeen(Integer id){
		ContentValues values=new ContentValues();
		values.put("seen", 1);
		try{
			return parent.db.update(DatabaseHandler.TABLE_NEWS, values, "id = "+id, null)>0;
		}catch(Exception e){
			e.printStackTrace();
			log("Error inserting values to the "+DatabaseHandler.TABLE_NEWS+" table");
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
			return parent.db.update(DatabaseHandler.TABLE_NEWS, values, "id = "+id, null)>0;
		}catch(Exception e){
			e.printStackTrace();
			log("Error inserting values to the "+DatabaseHandler.TABLE_NEWS+" table");
			return false;
		}
	}
	
	public boolean exists(String title,String jdate){
			Cursor cursor = parent.db.query(DatabaseHandler.TABLE_NEWS, new String[]{"title","jdate"},
					"title='"+title+"' and jdate='"+jdate+"'",null, null, null, null);
			if(cursor != null){
				return cursor.moveToFirst();
				}
			return false;
	}	
	
	
	public boolean chkSecondInsert(Integer id){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_NEWS, new String[]{"mainText","seen"},"id="+id,null, null, null, "gdate desc");
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
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_NEWS, new String[]{
				"id","title","jdate","indexImg","source","type","pageLink","bigImg","mainText","seen","archived"},null,null, null, null, "gdate desc");
		return cursor;
	}
	
	public Cursor getAllArchived(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_NEWS, new String[]{
				"id","title","jdate","indexImg","source","type","pageLink","bigImg","mainText","seen","archived"},"archived = 1",null, null, null, "gdate desc");
		return cursor;
	}
	
	public Cursor getAllNonArchived(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_NEWS, new String[]{
				"id","title","jdate","indexImg","source","type","pageLink","bigImg","mainText","seen","archived"},"archived = 0",null, null, null, "gdate desc");
		return cursor;
	}
	
	public Cursor getAllByType(String type){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_NEWS, new String[]{
				"id","title","jdate","indexImg","source","type","pageLink","bigImg","mainText","seen","archived"},"type='"+type+"'",null, null, null, "gdate desc");
		return cursor;
	}
	
	public Cursor getById(Integer id){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_NEWS, new String[]{
				"id","title","jdate","indexImg","source","type","pageLink","bigImg","mainText","seen","archived"},"id="+id,null, null, null, "gdate desc");
		return cursor;
	}	
	
	public Cursor getThoseWithoutIndexImage(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_NEWS, new String[]{
				"id","indexImg"},"indexImg like 'http://%'",null, null, null, "gdate desc");
		return cursor;
	}
	
	public Cursor getThoseWithoutMainText(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_NEWS, new String[]{
				"id","pageLink"},"mainText is null or mainText=''",null, null, null, "gdate desc");
		return cursor;
	}
	
	public Cursor getThoseWithoutBigImage(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_NEWS, new String[]{
				"id","bigImg"},"bigImg like 'http://%'",null, null, null, "gdate desc");
		return cursor;
	}
	
	public void cleanExtras(){
		String[] list = new String[]{Commons.NEWS_TYPE_ISLAHWEB,Commons.NEWS_TYPE_JAMAAT,Commons.NEWS_TYPE_SPORT};
		for(String type:list){
			while(exceedsLimitation(type))
				deleteOldestNonArchived(type);
		}
		
	}
	
	public boolean reachesLimitation(String type){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_NEWS, new String[]{"count(id)"},"type='"+type+"'",null, null, null, null);
		if(cursor.moveToFirst())
			return cursor.getInt(0)>=Commons.NEWS_ENTRY_COUNT;
		return false;
	}
	
	public boolean exceedsLimitation(String type){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_NEWS, new String[]{"count(id)"},"type='"+type+"'",null, null, null, null);
		if(cursor.moveToFirst())
			return cursor.getInt(0)>Commons.NEWS_ENTRY_COUNT;
		return false;
	}
	
	public void deleteOldestNonArchived(String type){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_NEWS, new String[]{"id"},"archived = 0 and type='"+type+"'",null, null, null, "gdate asc");
		if(cursor.moveToFirst()){
			log("deleting "+cursor.getInt(0));
			deleteEntry(cursor.getInt(0));
		}
	}
	
	public int deleteEntry(Integer id){
		log("Delete entry started");
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_NEWS,
				new String[]{"bigImg","indexImg"},"id ="+id,null, null, null, null);
		if(cursor.moveToFirst()){			
			log("Deleting image at "+cursor.getString(0));
			parent.sdHandler.deleteImage(cursor.getString(0));
			log("Deleting image at "+cursor.getString(1));
			parent.sdHandler.deleteImage(cursor.getString(1));
			log("Images deleted");
		}		
		return parent.db.delete(DatabaseHandler.TABLE_NEWS, "id = "+id, null);
	}
	
	public int deleteEntry(String title,String jdate){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_NEWS,
				new String[]{"bigImg","indexImg"},"title='"+title+"' and jdate='"+jdate+"'",null, null, null, null);
		if(cursor.moveToFirst()){	
			parent.sdHandler.deleteImage(cursor.getString(0));
			parent.sdHandler.deleteImage(cursor.getString(1));
		}
		return parent.db.delete(DatabaseHandler.TABLE_NEWS, "title='"+title+"' and jdate='"+jdate+"'", null);
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
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_NEWS, new String[]{
				"id"},null,null, null, null, null);
		return cursor.moveToFirst();
	}
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}

}
