package mrhs.jamaapp.inr.downloader;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownloaderService extends Service {

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
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
