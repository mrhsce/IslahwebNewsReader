package mrhs.jamaapp.inr.downloader;


import mrhs.jamaapp.inr.main.Commons;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import mrhs.jamaapp.inr.database.DatabaseHandler;
import android.util.Log;

public class NewsScraper {
	private static final boolean LOCAL_SHOW_LOG = true;
	private static Integer JAMMA_PAGE_NUM = 0;
	private static Integer ISLAH_PAGE_NUM = 0;
	private static Integer SPORT_PAGE_NUM = 0;
	
	DownloaderService parent;
	
	public NewsScraper(DownloaderService parent) {
		// TODO Auto-generated constructor stub
		this.parent = parent;
	}
	
	public void resetPageNumber(){
		JAMMA_PAGE_NUM = 0;
		ISLAH_PAGE_NUM = 0;
		SPORT_PAGE_NUM = 0;
	}
	
	public void initialInsertJamaNews(final DatabaseHandler db){
		log("Started initial insert");	
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				log("Trying");
				Document doc;
				String url="http://m.islahweb.org/jamaat_news?page=";
				String html=parent.getHtml(url+JAMMA_PAGE_NUM);
				
				doc=Jsoup.parse(html);
				Elements links=doc.select("tbody tr");
				try{for (Element link : links){
					String title = link.select("td a").get(1).text();
					String jDate = link.select("td").get(2).text();
					
					//if(db.newsHandler.exists(title, jdate)) continue;
					String pageLink = "http://m.islahweb.org"+link.select("td a").get(1).attr("href");
					String indexImgAddr = link.select("img").get(0).attr("src").replace("40x40crop", "200x200");
					String imgAddress = indexImgAddr.replace("40x40", "700x700");
					link.select("td a").get(1).remove();
					String source = link.select("td").get(1).text();					
					db.newsHandler.initialInsert(title, jDate, indexImgAddr, imgAddress, source, Commons.NEWS_TYPE_JAMAAT, pageLink);
				
					}
				}catch (IndexOutOfBoundsException e) {Log.i("realmadridparser", "Problem in webpage");}
			}
		});
		thread.start();			
		JAMMA_PAGE_NUM++;
	}
	
	public void secondaryInsertJamaNews(DatabaseHandler db){
		
	}
	
	public void initialInsertIslahNews(DatabaseHandler db){
		
		ISLAH_PAGE_NUM++;
	}
	
	public void secondaryInsertIslahNews(DatabaseHandler db){
		
	}
	
	public void initialInsertSportNews(DatabaseHandler db){
		
		SPORT_PAGE_NUM++;
	}
	
	public void secondaryInsertSportNews(DatabaseHandler db){
		
	}	
	
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}
}
