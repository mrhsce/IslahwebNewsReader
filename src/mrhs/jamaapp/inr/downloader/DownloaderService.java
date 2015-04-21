package mrhs.jamaapp.inr.downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import mrhs.jamaapp.inr.database.DatabaseHandler;
import mrhs.jamaapp.inr.main.Commons;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Service;
import android.content.Intent;
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
		newsScraper = new NewsScraper(this);
	}
	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		log("Service started");
		newsScraper.initialInsertIslahNews(db);
		newsScraper.initialInsertJamaNews(db);
		newsScraper.initialInsertSportNews(db);
		log("service finished");
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	public String getHtml(String url){
		try {			
			HttpClient httpclient = new DefaultHttpClient(); // Create HTTP Client
	        HttpGet httpget = new HttpGet(url); // Set the action you want to do
	        HttpResponse response = httpclient.execute(httpget); // Executeit
	        HttpEntity entity = response.getEntity(); 
	        InputStream is = entity.getContent(); // Create an InputStream with the response
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = reader.readLine()) != null)
	            sb.append(line);        			        
	        is.close();
	        log("Page recieved");
	        return sb.toString();
		} catch(IOException e)
		{log("unable to get page");return "";}
	}
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}

}
