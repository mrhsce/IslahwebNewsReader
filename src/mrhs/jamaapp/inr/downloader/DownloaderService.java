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
	private static final boolean LOCAL_SHOW_LOG = false;
	
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
	
	Integer finishedInitialDownloadCount;
	
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
		
		finishedInitialDownloadCount = 0;
		
		db = new DatabaseHandler(this).open();
		db.cleanExtras();
		newsScraper = new NewsScraper();
		articleScraper = new ArticleScraper();		
		interviewScraper = new InterviewScraper();
		announcementScraper = new AnnouncementScraper();
	}
	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		log("Service started");
		
		primaryDownload();		
		
		return super.onStartCommand(intent, flags, startId);
	}	
	
	public void primaryDownload(){
		log("Primary download started");
		finishedInitialDownloadCount = 0;
		new NewsAsynckTask(1).execute();
		new ArticleAsynckTask(1).execute();
		new AnnounceAsynckTask(1).execute();
		new InterviewAsynckTask(1).execute();
	}
	
	public void secondaryDownload(){
		log("Secondary download started");
		new SecondaryDownloadAsynckTask().execute();
	}
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}
	
	private class NewsAsynckTask extends AsyncTask<Void, Void, Void>{

		Integer run;
		
		public NewsAsynckTask(Integer runInitialDownload) {
			// TODO Auto-generated constructor stub
			run = runInitialDownload;
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			
			if(run==0 || run==1){
				newsScraper.initialInsert(db);
			}
			log("Initial download finished");
			if(run==0 || run == 2){
				Cursor cursor = db.newsHandler.getThoseWithoutMainText();
				int i = 0;
				while(cursor.moveToPosition(i)){
					newsScraper.secondaryInsert(db, cursor.getString(1), cursor.getInt(0));
					log("Another news main text successfully inserted");
					i++;
				}
			}
			return null;
		}
		
		@Override
	    protected void onPostExecute(Void result) {
			super.onPostExecute(result);
	        
	        if(run==1){
	        	finishedInitialDownloadCount++;
	        }
	        if(finishedInitialDownloadCount==4){
	        	finishedInitialDownloadCount=0;
	        	secondaryDownload();
	        }	

	        //this method will be running on UI thread
	    }
	}
	
	private class ArticleAsynckTask extends AsyncTask<Void, Void, Void>{

		Integer run;
		
		public ArticleAsynckTask(Integer runInitialDownload) {
			// TODO Auto-generated constructor stub
			run = runInitialDownload;
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			
			if(run==0 || run==1){
				articleScraper.initialInsert(db);
			}
			log("Article Initial download finished");
			if(run==0 || run == 2){	
				Cursor cursor = db.articleHandler.getThoseWithoutMainText();
				int i = 0;
				while(cursor.moveToPosition(i)){
					articleScraper.secondaryInsert(db, cursor.getString(1), cursor.getInt(0));
					log("Another article main text successfully inserted");
					i++;
				}	
			}
			return null;
		}
		
		@Override
	    protected void onPostExecute(Void result) {
			super.onPostExecute(result);
	        
	        if(run==1){
	        	finishedInitialDownloadCount++;
	        }
	        if(finishedInitialDownloadCount==4){
	        	finishedInitialDownloadCount=0;
	        	secondaryDownload();
	        }	

	        //this method will be running on UI thread
	    }
	}
	
	private class InterviewAsynckTask extends AsyncTask<Void, Void, Void>{

		Integer run;
		
		public InterviewAsynckTask(Integer runInitialDownload) {
			// TODO Auto-generated constructor stub
			run = runInitialDownload;
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			
			if(run==0 || run==1){
				interviewScraper.initialInsert(db);
			}
			log("Interview Initial download finished");
			if(run==0 || run == 2){
				Cursor cursor = db.interviewHandler.getThoseWithoutMainText();
				int i = 0;
				while(cursor.moveToPosition(i)){
					interviewScraper.secondaryInsert(db, cursor.getString(1), cursor.getInt(0));
					log("Another interview main text successfully inserted");
					i++;
				}
			}
			return null;
		}
		
		@Override
	    protected void onPostExecute(Void result) {
	        super.onPostExecute(result);
	        
	        if(run==1){
	        	finishedInitialDownloadCount++;
	        }
	        if(finishedInitialDownloadCount==4){
	        	finishedInitialDownloadCount=0;
	        	secondaryDownload();
	        }	        
	        
	        //this method will be running on UI thread
	    }
	}
	
	private class AnnounceAsynckTask extends AsyncTask<Void, Void, Void>{

		Integer run;
		
		public AnnounceAsynckTask(Integer runInitialDownload) {
			// TODO Auto-generated constructor stub
			run = runInitialDownload;
		}
		
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			
			if(run==0 || run==1){
				announcementScraper.initialInsert(db);
			}
			log("Announce Initial download finished");
			if(run==0 || run == 2){
				Cursor cursor = db.anouncementHandler.getThoseWithoutMainText();
				int i = 0;
				while(cursor.moveToPosition(i)){
					announcementScraper.secondaryInsert(db, cursor.getString(1), cursor.getInt(0));
					log("Another announce main text successfully inserted");
					i++;
				}		
			}
			return null;
		}
		
		@Override
	    protected void onPostExecute(Void result) {
			super.onPostExecute(result);
	        
	        if(run==1){
	        	finishedInitialDownloadCount++;
	        }
	        if(finishedInitialDownloadCount==4){
	        	finishedInitialDownloadCount=0;
	        	secondaryDownload();
	        }	

	        //this method will be running on UI thread
	    }
	}
	
	private class SecondaryDownloadAsynckTask extends AsyncTask<Void, Void, Void>{

		Boolean remains = false;		
		
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			
			Cursor cursor = db.newsHandler.getThoseWithoutMainText();
			if(cursor.moveToFirst()){
				remains = true;
				newsScraper.secondaryInsert(db, cursor.getString(1), cursor.getInt(0));
				log("Another news main text successfully inserted");
			}
			cursor = db.articleHandler.getThoseWithoutMainText();
			if(cursor.moveToFirst()){
				remains = true;
				articleScraper.secondaryInsert(db, cursor.getString(1), cursor.getInt(0));
				log("Another article main text successfully inserted");
			}
			cursor = db.interviewHandler.getThoseWithoutMainText();
			if(cursor.moveToFirst()){
				remains = true;
				interviewScraper.secondaryInsert(db, cursor.getString(1), cursor.getInt(0));
				log("Another interview main text successfully inserted");
			}
			cursor = db.anouncementHandler.getThoseWithoutMainText();
			if(cursor.moveToFirst()){
				remains = true;
				announcementScraper.secondaryInsert(db, cursor.getString(1), cursor.getInt(0));
				log("Another announce main text successfully inserted");
			}
				
			return null;
		}
		
		@Override
	    protected void onPostExecute(Void result) {
			super.onPostExecute(result);
	        
	        if(remains){
	        	new SecondaryDownloadAsynckTask().execute();
	        }
	        log("One secondary download round completed");
	        //this method will be running on UI thread
	    }
	}

}
