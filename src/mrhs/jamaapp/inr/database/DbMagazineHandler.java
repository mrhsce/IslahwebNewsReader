package mrhs.jamaapp.inr.database;

import mrhs.jamaapp.inr.main.Commons;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class DbMagazineHandler {
	
	private static final boolean LOCAL_SHOW_LOG = true;
	
	private DatabaseHandler parent;
	 	
	
	public DbMagazineHandler(DatabaseHandler parent){
		this.parent = parent;
	}
	
	public boolean initialInsert(String title,String indexImgAddr,String pageLink){
				
		ContentValues values=new ContentValues();
		values.put("title",title);
		values.put("indexImg", indexImgAddr);
		values.put("pageLink", pageLink);
		try{
			return parent.db.insert(DatabaseHandler.TABLE_MAG, null, values)>0;
		}catch(Exception e){
			e.printStackTrace();
			log("Error inserting values to the "+DatabaseHandler.TABLE_MAG+" table");
			return false;
		}
	}
	
	public boolean secondInsert(Integer id,String pdfAddr){
		ContentValues values=new ContentValues();
		values.put("pdfFile",pdfAddr);
		return parent.db.update(DatabaseHandler.TABLE_MAG, values, "id = "+id, null)>0;
	}
	
	public boolean imgUpdate(Integer id,String address){
		ContentValues values=new ContentValues();
		values.put("indexImg",address);
		return parent.db.update(DatabaseHandler.TABLE_MAG, values, "id = "+id, null)>0;
	}
	
	public boolean exists(String title){
			Cursor cursor = parent.db.query(DatabaseHandler.TABLE_MAG, new String[]{"title"},
					"title='"+title+"'",null, null, null, null);
			if(cursor != null){
				return cursor.moveToFirst();
				}
			return false;
	}
	
	public Cursor getAll(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_MAG, new String[]{
				"id","title","indexImg","pageLink","pdfFile"},null,null, null, null, null);
		return cursor;
	}	
	
	public Cursor getById(Integer id){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_MAG, new String[]{
				"id","title","indexImg","pageLink","pdfFile"},"id="+id,null, null, null, null);
		return cursor;
	}
	
	public Cursor getThoseWithoutIndexImage(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_MAG, new String[]{
				"id","title","indexImg","pageLink","pdfFile"},"indexImg like 'http://%'"+null,null, null, null, null);
		return cursor;
	}
	
	public Cursor getThoseWithoutPdf(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_MAG, new String[]{
				"id","title","indexImg","pageLink","pdfFile"},"pdfFile = null"+null,null, null, null, null);
		return cursor;
	}
	
	public int deleteEntry(Integer id){
		return parent.db.delete(DatabaseHandler.TABLE_MAG, "id = "+id, null);
	}
	
	public int deleteEntry(String title){
		return parent.db.delete(DatabaseHandler.TABLE_MAG, "title='"+title+"'", null);
	}	
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}
}
