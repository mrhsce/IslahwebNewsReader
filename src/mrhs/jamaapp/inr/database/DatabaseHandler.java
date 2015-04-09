package mrhs.jamaapp.inr.database;


import mrhs.jamaapp.inr.Commons;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler {
	private static final boolean LOCAL_SHOW_LOG = true;
	
	private static final String TABLE_NEWS = "newsTable";
	private static final String TABLE_INTERVIEW = "interviewTable";
	private static final String TABLE_SELECTED = "selectedTable";
	private static final String TABLE_ANNOUNCE = "announcementTable";
	private static final String TABLE_BOOKS = "booksTable";
	private static final String TABLE_MAG = "magazineTable";
	private static final String TABLE_ARTICLE = "articleTable";
	private static final String TABLE_FEQHI = "feqhiTable";
	private static final String TABLE_GALLERY = "galleryTable";
	
	private DbHelper dbHelper;
	private  SQLiteDatabase db;
	
	// Necessary functions (DDL)
	public DatabaseHandler(Context ctx){
		dbHelper=new DbHelper(ctx);
	}
	
	public DatabaseHandler open() throws SQLException{
		db=dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		dbHelper.close();
	}	
	
	private static class DbHelper extends SQLiteOpenHelper{
		
		public DbHelper(Context context){
			super(context, Commons.DATABASE_NAME, null, Commons.DATABASE_VERSION);
			log("The database has been initialized");
		}
	
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			String CREATE_NEWS="CREATE TABLE IF NOT EXISTS "+TABLE_NEWS+ 
					" (id INTEGER PRIMARY KEY,title text not null,gdate DATE not null," +
					" jdate text not null,indexImg text not null,source text not null," +
					"type text not null,bigImg text,pageLink text not null,mainText text)";					
			db.execSQL(CREATE_NEWS);
			
			String CREATE_INTERVIEW="CREATE TABLE IF NOT EXISTS "+TABLE_INTERVIEW+ 
					" (id INTEGER PRIMARY KEY,title text not null,gdate DATE not null,jdate text not null," +
					"indexText text not null,indexImg text not null,writer text not null," +
					"bigImg text,pageLink text not null,mainText text)";					
			db.execSQL(CREATE_INTERVIEW);
			
			String CREATE_SELECTED="CREATE TABLE IF NOT EXISTS "+TABLE_SELECTED+ 
					" (id INTEGER PRIMARY KEY,title text not null,gdate DATE not null,jdate text," +
					"author text not null,pageLink text not null,mainText text)";					
			db.execSQL(CREATE_SELECTED);
			
			String CREATE_ANNOUNCE="CREATE TABLE IF NOT EXISTS "+TABLE_ANNOUNCE+ 
					" (id INTEGER PRIMARY KEY,title text not null,gdate DATE not null,jdate text not null," +
					"pageLink text not null,mainText text)";					
			db.execSQL(CREATE_ANNOUNCE);
			
			String CREATE_BOOKS="CREATE TABLE IF NOT EXISTS "+TABLE_BOOKS+ 
					" (id INTEGER PRIMARY KEY,title text not null,gdate DATE not null,jdate text," +
					"indexImg text not null,details text not null,summary text not null,mainText text)";					
			db.execSQL(CREATE_BOOKS);
			
			String CREATE_MAG="CREATE TABLE IF NOT EXISTS "+TABLE_MAG+ 
					" (id INTEGER PRIMARY KEY,title text not null,indexImg text not null," +
					"pageLink text not null,pdfFile text)";					
			db.execSQL(CREATE_MAG);
			
			String CREATE_ARTICLE="CREATE TABLE IF NOT EXISTS "+TABLE_ARTICLE+ 
					" (id INTEGER PRIMARY KEY,title text not null,gdate DATE not null,jdate text not null," +
					"indexText text not null,indexImg text not null,writer text not null,type text not null," +
					"pageLink text not null,bigImg text,mainText text)";					
			db.execSQL(CREATE_ARTICLE);
			
			String CREATE_FEQHI="CREATE TABLE IF NOT EXISTS "+TABLE_FEQHI+ 
					" (id INTEGER PRIMARY KEY,questionText text not null,gdate DATE not null,jdate text not null," +
					"type text not null,answerText text not null)";					
			db.execSQL(CREATE_FEQHI);
			
			String CREATE_GALLERY="CREATE TABLE IF NOT EXISTS "+TABLE_GALLERY+ 
					" (id INTEGER PRIMARY KEY,title text not null,gdate DATE not null,jdate text not null," +
					"type text not null,indexImg text not null,aboutText text not null,pageLink text not null," +
					"morePhotosFolder text not null)";					
			db.execSQL(CREATE_GALLERY);
			
			log("All the table schematics has been created");
		}
	
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS "+TABLE_NEWS);
			db.execSQL("DROP TABLE IF EXISTS "+TABLE_INTERVIEW);
			db.execSQL("DROP TABLE IF EXISTS "+TABLE_SELECTED);
			db.execSQL("DROP TABLE IF EXISTS "+TABLE_ANNOUNCE);
			db.execSQL("DROP TABLE IF EXISTS "+TABLE_BOOKS);
			db.execSQL("DROP TABLE IF EXISTS "+TABLE_MAG);
			db.execSQL("DROP TABLE IF EXISTS "+TABLE_ARTICLE);
			db.execSQL("DROP TABLE IF EXISTS "+TABLE_FEQHI);
			db.execSQL("DROP TABLE IF EXISTS "+TABLE_GALLERY);
			onCreate(db);
		}	
		
	}
	private static void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d("DatabaseHandler",message);
	}
}
