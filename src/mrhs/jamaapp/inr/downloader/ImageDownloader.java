package mrhs.jamaapp.inr.downloader;

import mrhs.jamaapp.inr.main.Commons;
import android.util.Log;

public class ImageDownloader {
	private static final boolean LOCAL_SHOW_LOG = true;	
	
	public String getImage(String address){
		
		return address;
	}
	
	public String getMultipleImage(String address){

		return address;
	}
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}
}
