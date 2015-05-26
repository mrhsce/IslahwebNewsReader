package mrhs.jamaapp.inr.downloader;


import mrhs.jamaapp.inr.Commons;
import mrhs.jamaapp.inr.SdCardHandler;
import mrhs.jamaapp.inr.database.DatabaseHandler;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

public class DownloaderService extends Service {
	private static final boolean LOCAL_SHOW_LOG = true;
	
	public boolean secondaryRemains = true;
	public boolean imageRemains = true;
	
	ArticleScraper articleScraper;
	AnnouncementScraper announcementScraper;
	ImageDownloader imageDownloader;
	InterviewScraper interviewScraper;
	NewsScraper newsScraper;
	
	SdCardHandler sdHandler;
	
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
		
		sdHandler = new SdCardHandler();
		sdHandler.prepareSdCard(this);
		
		db.cleanExtras();
		newsScraper = new NewsScraper();
		articleScraper = new ArticleScraper();		
		interviewScraper = new InterviewScraper();
		announcementScraper = new AnnouncementScraper();
		imageDownloader = new ImageDownloader();
	}
	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		log("Service started");
		
		primaryDownload();
		//tritieryDownload();
		
		return Service.START_NOT_STICKY;
	}	
	
	public void primaryDownload(){
		log("Primary download started");
		if(isConnectingToInternet() ){
			new NewsAsynckTask(1).execute();
			new ArticleAsynckTask(1).execute();
			new AnnounceAsynckTask(1).execute();
			new InterviewAsynckTask(1).execute();
		}
	}
	
	public void secondaryDownload(){
		log("Secondary download started");
		new SecondaryDownloadAsynckTask().execute();
		tritieryDownload();
	}
	public void tritieryDownload(){
		log("Tritiery download started");
		new ImageDownloadAsynckTask().execute();		
	}
	
	public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null)
                  for (int i = 0; i < info.length; i++)
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false;
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
	        
	        if(remains && isConnectingToInternet()){	        	
	        	new SecondaryDownloadAsynckTask().execute();
	        }
	        else{
	        	secondaryRemains = false;
	        	if(!imageRemains)
	        		DownloaderService.this.stopSelf();
	        }
	        log("One secondary download round completed");
	        //this method will be running on UI thread
	    }
	}
	
	private class ImageDownloadAsynckTask extends AsyncTask<Void, Void, Void>{

		Boolean remains = false;		
		
		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			
			Cursor cursor = db.newsHandler.getThoseWithoutIndexImage();
			log("The image downloader started");
			if(cursor.moveToFirst()){
				log("The news address is "+cursor.getString(1));
				remains = true;
				imageDownloader.downloadImage(cursor.getInt(0), cursor.getString(1), Commons.NEWS, db, sdHandler);
				log("Another news image successfully inserted");
			}
			
			cursor = db.articleHandler.getThoseWithoutIndexImage();
			if(cursor.moveToFirst()){
				log("The article address is "+cursor.getString(1));
				remains = true;
				imageDownloader.downloadImage(cursor.getInt(0), cursor.getString(1), Commons.ARTICLE, db, sdHandler);
				log("Another article image successfully inserted");
			}
			cursor = db.interviewHandler.getThoseWithoutIndexImage();
			if(cursor.moveToFirst()){
				log("The interview address is "+cursor.getString(1));
				remains = true;
				imageDownloader.downloadImage(cursor.getInt(0), cursor.getString(1), Commons.INTERVIEW, db, sdHandler);
				log("Another interview image successfully inserted");
			}
				
			return null;
		}
		
		@Override
	    protected void onPostExecute(Void result) {
			super.onPostExecute(result);
	        
	        if(remains && isConnectingToInternet()){
	        	new ImageDownloadAsynckTask().execute();
	        }
	        else{
	        	imageRemains = false;
	        	if(!secondaryRemains)
	        		DownloaderService.this.stopSelf();
	        }
	        	
	        log("One tritiery download round completed");
	        //this method will be running on UI thread
	    }
	}
	
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}

}
