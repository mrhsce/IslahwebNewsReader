package mrhs.jamaapp.inr.database;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import mrhs.jamaapp.inr.main.Commons;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class DbGalleryHandler {
	private static final boolean LOCAL_SHOW_LOG = true;
	
	private DatabaseHandler parent;
	 	
	
	public DbGalleryHandler(DatabaseHandler parent){
		this.parent = parent;
	}
	
	public boolean initialInsert(String title,String jDate,String indexImgAddr,String type,String pageLink){
		
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
		values.put("title",title);
		values.put("jdate",jDate);
		values.put("gdate",gdate);
		values.put("indexImg", indexImgAddr);
		values.put("type",type);
		values.put("pageLink", pageLink);
		try{
			return parent.db.insert(DatabaseHandler.TABLE_GALLERY, null, values)>0;
		}catch(Exception e){
			e.printStackTrace();
			log("Error inserting values to the "+DatabaseHandler.TABLE_GALLERY+" table");
			return false;
		}
	}
	
	public boolean secondInsert(Integer id,String aboutText,String imgAddresses){
		ContentValues values=new ContentValues();
		values.put("aboutText",aboutText);
		values.put("imageAddresses",imgAddresses);
		return parent.db.update(DatabaseHandler.TABLE_GALLERY, values, "id = "+id, null)>0;
	}
	
	public boolean imgUpdate(Integer id,String address,boolean pictureType){
		ContentValues values=new ContentValues();
		if(pictureType)
			values.put("indexImg",address);
		else
			values.put("bigImg",address);
		return parent.db.update(DatabaseHandler.TABLE_GALLERY, values, "id = "+id, null)>0;
	}
	
	public boolean exists(String title,String jdate){
			Cursor cursor = parent.db.query(DatabaseHandler.TABLE_GALLERY, new String[]{"title","jdate"},
					"title='"+title+"' and jdate='"+jdate+"'",null, null, null, null);
			if(cursor != null){
				return cursor.moveToFirst();
				}
			return false;
	}
	
	public Cursor getAll(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_GALLERY, new String[]{
				"id","title","jdate","indexImg","type","pageLink","imageAddresses","aboutText"},null,null, null, null, null);
		return cursor;
	}
	
	public Cursor getAllTypes(){
		Cursor cursor = parent.db.query(true,DatabaseHandler.TABLE_GALLERY, new String[]{"type"},null,null,null, null, null, null);
		return cursor;
	}
	
	public Cursor getAllByType(String type){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_GALLERY, new String[]{
				"id","title","jdate","indexImg","type","pageLink","imageAddresses","aboutText"},"type='"+type+"'",null, null, null, "gdate desc");
		return cursor;
	}
	
	public Cursor getById(Integer id){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_GALLERY, new String[]{
				"id","title","jdate","indexImg","type","pageLink","imageAddresses","aboutText"},"id="+id,null, null, null, "gdate desc");
		return cursor;
	}
	
	public Cursor getThoseWithoutIndexImage(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_GALLERY, new String[]{
				"id","title","jdate","indexImg","type","pageLink","imageAddresses","aboutText"},"indexImg like 'http://%'"+null,null, null, null, "gdate desc");
		return cursor;
	}
	
	public Cursor getThoseWithoutMainText(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_GALLERY, new String[]{
				"id","title","jdate","indexImg","type","pageLink","imageAddresses","aboutText"},"aboutText=null",null, null, null, "gdate desc");
		return cursor;
	}
	
	public Cursor getThoseWithoutBigImage(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_GALLERY, new String[]{
				"id","title","jdate","indexImg","type","pageLink","imageAddresses","aboutText"},"imageAddresses like 'http://%'"+null,null, null, null, "gdate desc");
		return cursor;
	}
	
	public int deleteEntry(Integer id){
		return parent.db.delete(DatabaseHandler.TABLE_GALLERY, "id = "+id, null);
	}
	
	public int deleteEntry(String title,String jdate){
		return parent.db.delete(DatabaseHandler.TABLE_GALLERY, "title='"+title+"' and jdate='"+jdate+"'", null);
	}
	
	public int deleteEntry(String type){
		return parent.db.delete(DatabaseHandler.TABLE_GALLERY, "type='"+type+"'", null);
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
