package mrhs.jamaapp.inr.downloader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import mrhs.jamaapp.inr.database.DatabaseHandler;
import mrhs.jamaapp.inr.main.Commons;
import mrhs.jamaapp.inr.main.SdCardHandler;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageDownloader {
	private static final boolean LOCAL_SHOW_LOG = true;
	
	public boolean downloadImage(Integer id,String link,String type,DatabaseHandler db,SdCardHandler sd){
				
		try {
	        URL url = new URL(link);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        if(input!=null)
	        	log("The input stream is not null");
	        Bitmap img = BitmapFactory.decodeStream(input);
	        if(img!=null)
	        	log("The bitmap is not null");

	        db.open();
			if(type.equals(Commons.NEWS)){
				if(sd.storeImage(Commons.APP_ROOT_DIRECTORY+File.separator+Commons.NEWS+File.separator+
						Integer.toString(id)+".jpg", img))
					db.newsHandler.imgUpdate(id, Commons.APP_ROOT_DIRECTORY+File.separator+Commons.NEWS+
							File.separator+Integer.toString(id)+".jpg",
							Commons.INDEX_IMAGE);
			}
			if(type.equals(Commons.ARTICLE)){
				if(sd.storeImage(Commons.APP_ROOT_DIRECTORY+File.separator+Commons.ARTICLE+File.separator+
						Integer.toString(id)+".jpg", img))
					db.articleHandler.imgUpdate(id, Commons.APP_ROOT_DIRECTORY+File.separator+Commons.ARTICLE+
							File.separator+Integer.toString(id)+".jpg",
							Commons.INDEX_IMAGE);
				
			}
			if(type.equals(Commons.INTERVIEW)){
				if(sd.storeImage(Commons.APP_ROOT_DIRECTORY+File.separator+Commons.INTERVIEW+File.separator+
						Integer.toString(id)+".jpg", img))
					db.interviewHandler.imgUpdate(id, Commons.APP_ROOT_DIRECTORY+File.separator+Commons.INTERVIEW+
							File.separator+Integer.toString(id)+".jpg",
							Commons.INDEX_IMAGE);
			}			
			db.close();
			return true;

	    } catch (IOException e) {
	        e.printStackTrace();
	        Log.e("getBmpFromUrl error: ", e.getMessage().toString());
	        return false;
	    }
	}
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}
}
