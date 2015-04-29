package mrhs.jamaapp.inr.database;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import mrhs.jamaapp.inr.main.Commons;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class DbArticleHandler {
private static final boolean LOCAL_SHOW_LOG = true;
	
	private DatabaseHandler parent;
	 	
	
	public DbArticleHandler(DatabaseHandler parent){
		this.parent = parent;
	}
	
	public boolean initialInsert(String title,String jDate,String indexTxt,String indexImgAddr,String imgAddress,
			String writer,String type,String pageLink){
		while(reachesLimitation(type)){
			log("deleted");
				deleteOldestNonArchived(type);
			}
			String gDate = "";
			try {
				gDate = new SimpleDateFormat("yyyy-MM-dd").
							format(new SimpleDateFormat("yyyy/MM/dd",Locale.getDefault()).
							parse(parent.dateConvertor.PersianToGregorian(jDate)));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
	
			log("gdate is "+gDate);
			
			ContentValues values=new ContentValues();
			values.put("title",title);
			values.put("jdate",jDate);
			values.put("gdate",gDate);
			values.put("indexImg", indexImgAddr);
			values.put("indexText", indexTxt);
			values.put("bigImg",imgAddress);
			values.put("writer",writer);
			values.put("type",type);
			values.put("pageLink", pageLink);
			try{
				return parent.db.insert(DatabaseHandler.TABLE_ARTICLE, null, values)>0;
			}catch(Exception e){
				e.printStackTrace();
				log("Error inserting values to the "+DatabaseHandler.TABLE_ARTICLE+" table");
				return false;
			}
		
	}
	
	public boolean secondInsert(Integer id,String mainText){
		ContentValues values=new ContentValues();
		values.put("mainText",mainText);
		return parent.db.update(DatabaseHandler.TABLE_ARTICLE, values, "id = "+id, null)>0;
	}
	
	public boolean imgUpdate(Integer id,String address,boolean pictureType){
		ContentValues values=new ContentValues();
		if(pictureType)
			values.put("indexImg",address);
		else
			values.put("bigImg",address);
		return parent.db.update(DatabaseHandler.TABLE_ARTICLE, values, "id = "+id, null)>0;
	}
	
	public boolean setSeen(Integer id){
		ContentValues values=new ContentValues();
		values.put("seen", 1);
		try{
			return parent.db.update(DatabaseHandler.TABLE_ARTICLE, values, "id = "+id, null)>0;
		}catch(Exception e){
			e.printStackTrace();
			log("Error inserting values to the "+DatabaseHandler.TABLE_ARTICLE+" table");
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
			return parent.db.update(DatabaseHandler.TABLE_ARTICLE, values, "id = "+id, null)>0;
		}catch(Exception e){
			e.printStackTrace();
			log("Error inserting values to the "+DatabaseHandler.TABLE_ARTICLE+" table");
			return false;
		}
	}
	
	public boolean exists(String title,String jDate){
			Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ARTICLE, new String[]{"title","jdate"},
					"title='"+title+"' and jdate='"+jDate+"'",null, null, null, null);
			if(cursor != null){
				return cursor.moveToFirst();
				}
			return false;
	}	
	
	
	public boolean chkSecondInsert(Integer id){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ARTICLE, new String[]{"mainText","seen"},"id="+id,null, null, null, "gdate desc");
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
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ARTICLE, new String[]{
				"id","title","jdate","indexText","indexImg","writer","type","pageLink","bigImg","mainText","seen","archived"},null,null, null, null, "gdate desc");
		return cursor;
	}
	
	public Cursor getAllArchived(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ARTICLE, new String[]{
				"id","title","jdate","indexText","indexImg","writer","type","pageLink","bigImg","mainText","seen","archived"},"archived = 1",null, null, null, "gdate desc");
		return cursor;
	}
	
	public Cursor getAllNonArchived(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ARTICLE, new String[]{
				"id","title","jdate","indexText","indexImg","writer","type","pageLink","bigImg","mainText","seen","archived"},"archived = 0",null, null, null, "gdate desc");
		return cursor;
	}
	
	public Cursor getAllByType(String type){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ARTICLE, new String[]{
				"id","title","jdate","indexText","indexImg","writer","type","pageLink","bigImg","mainText","seen","archived"},"type='"+type+"'",null, null, null, "gdate desc");
		return cursor;
	}
	
	public Cursor getById(Integer id){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ARTICLE, new String[]{
				"id","title","jdate","indexText","indexImg","writer","type","pageLink","bigImg","mainText","seen","archived"},"id="+id,null, null, null, "gdate desc");
		return cursor;
	}	
	
	public Cursor getThoseWithoutIndexImage(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ARTICLE, new String[]{
				"id","indexImg"},"indexImg like 'http://%'",null, null, null, "gdate desc");
		return cursor;
	}
	
	public Cursor getThoseWithoutMainText(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ARTICLE, new String[]{
				"id","pageLink"},"mainText is null or mainText=''",null, null, null, "gdate desc");
		return cursor;
	}
	
	public Cursor getThoseWithoutBigImage(){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ARTICLE, new String[]{
				"id","bigImg"},"bigImg like 'http://%'",null, null, null, "gdate desc");
		return cursor;
	}
	
	public void cleanExtras(){
		String[] list = new String[]{Commons.ARTICLE_TYPE_ADABOHONAR,Commons.ARTICLE_TYPE_AHLESONNAT,Commons.ARTICLE_TYPE_ANDISHE,
				Commons.ARTICLE_TYPE_DINODAVAT,Commons.ARTICLE_TYPE_EJTEMAEI,Commons.ARTICLE_TYPE_FARHANG,
				Commons.ARTICLE_TYPE_SIASI,Commons.ARTICLE_TYPE_TARIKH};
		for(String type:list){
			while(exceedsLimitation(type))
				deleteOldestNonArchived(type);
		}
		
	}
	
	public boolean reachesLimitation(String type){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ARTICLE, new String[]{"count(id)"},"type='"+type+"'",null, null, null, null);
		if(cursor.moveToFirst())
			return cursor.getInt(0)>=Commons.ARTICLE_ENTRY_COUNT;
		return false;
	}
	
	public boolean exceedsLimitation(String type){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ARTICLE, new String[]{"count(id)"},"type='"+type+"'",null, null, null, null);
		if(cursor.moveToFirst())
			return cursor.getInt(0)>Commons.ARTICLE_ENTRY_COUNT;
		return false;
	}
	
	public void deleteOldestNonArchived(String type){
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ARTICLE, new String[]{"id"},"archived = 0 and type='"+type+"'",null, null, null, "gdate asc");
		if(cursor.moveToFirst())
			deleteEntry(cursor.getInt(0));
	}
	
	public int deleteEntry(Integer id){
		return parent.db.delete(DatabaseHandler.TABLE_ARTICLE, "id = "+id, null);
	}
	
	public int deleteEntry(String title,String jdate){
		return parent.db.delete(DatabaseHandler.TABLE_ARTICLE, "title='"+title+"' and jdate='"+jdate+"'", null);
	}
	
	public int deleteEntry(String type){
		return parent.db.delete(DatabaseHandler.TABLE_ARTICLE, "type='"+type+"'", null);
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
		Cursor cursor = parent.db.query(DatabaseHandler.TABLE_ARTICLE, new String[]{
				"id"},null,null, null, null, null);
		return cursor.moveToFirst();
	}	
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}
}
