package mrhs.jamaapp.inr.downloader;


import mrhs.jamaapp.inr.database.DatabaseHandler;
import mrhs.jamaapp.inr.main.Commons;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

public class DownloaderService extends Service {
	private static final boolean LOCAL_SHOW_LOG = true;
	ArticleScraper articleScraper;
	AnnouncementScraper announcementScraper;
	BooksScraper booksScraper;
	FeqhiScraper feqhiScraper;
	GalleryScraper galleryScraper;
	ImageDownloader imageDownloader;
	InterviewScraper interviewScraper;
	MagazineScraper magazineScraper;
	NewsScraper newsScraper;
	SelectedScraper selectedScraper;
	
	DatabaseHandler db;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		log("Service created");
		db = new DatabaseHandler(this).open();
		newsScraper = new NewsScraper();
		articleScraper = new ArticleScraper();
	}
	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		log("Service started");
		new NewsAsynckTask(true).execute();
		new ArticleAsynckTask(true).execute();
		
		log("service finished");
		
		return super.onStartCommand(intent, flags, startId);
	}	
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}
	
	private class NewsAsynckTask extends AsyncTask<Void, Void, Void>{

		boolean run;
		
		public NewsAsynckTask(boolean runInitialDownload) {
			// TODO Auto-generated constructor stub
			run = runInitialDownload;
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			
			if(run){
				newsScraper.initialInsertIslahNews(db);
				newsScraper.initialInsertJamaNews(db);
				newsScraper.initialInsertSportNews(db);
			}
			log("Initial download finished");
			Cursor cursor = db.newsHandler.getThoseWithoutMainText();
			int i = 0;
			while(cursor.moveToPosition(i)){
				newsScraper.secondaryInsert(db, cursor.getString(1), cursor.getInt(0));
				log("Another news main text successfully inserted");
				i++;
			}
			
			return null;
		}
		
		@Override
	    protected void onPostExecute(Void result) {
	        super.onPostExecute(result);

	        //this method will be running on UI thread
	    }
	}
	
	private class ArticleAsynckTask extends AsyncTask<Void, Void, Void>{

		boolean run;
		
		public ArticleAsynckTask(boolean runInitialDownload) {
			// TODO Auto-generated constructor stub
			run = runInitialDownload;
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			
			if(run){
				articleScraper.initialInsert(db);
			}
			log("Article Initial download finished");
			Cursor cursor = db.articleHandler.getThoseWithoutMainText();
			int i = 0;
			while(cursor.moveToPosition(i)){
				articleScraper.secondaryInsert(db, cursor.getString(1), cursor.getInt(0));
				log("Another article main text successfully inserted");
				i++;
			}			
			return null;
		}
		
		@Override
	    protected void onPostExecute(Void result) {
	        super.onPostExecute(result);

	        //this method will be running on UI thread
	    }
	}

}
