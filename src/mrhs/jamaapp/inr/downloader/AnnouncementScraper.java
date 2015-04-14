package mrhs.jamaapp.inr.downloader;

import mrhs.jamaapp.inr.database.DatabaseHandler;
import mrhs.jamaapp.inr.main.Commons;
import android.util.Log;

public class AnnouncementScraper {
	private static final boolean LOCAL_SHOW_LOG = true;
	private static Integer PAGE_NUM = 0;
	
	public void resetPageNumber(){
		PAGE_NUM = 0;
	}
	
	public void initialInsert(DatabaseHandler db){
		
		PAGE_NUM++;
	}
	
	public void secondaryInsert(DatabaseHandler db){
		
	}
	
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}
}
