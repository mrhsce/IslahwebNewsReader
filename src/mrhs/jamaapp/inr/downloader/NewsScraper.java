package mrhs.jamaapp.inr.downloader;


import mrhs.jamaapp.inr.main.Commons;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import mrhs.jamaapp.inr.database.DatabaseHandler;
import android.util.Log;
import android.webkit.WebView.FindListener;

public class NewsScraper {
	private static final boolean LOCAL_SHOW_LOG = true;
	
	DownloaderService parent;
	
	public NewsScraper(DownloaderService parent) {
		// TODO Auto-generated constructor stub
		this.parent = parent;
	}	
	
	public void initialInsertJamaNews(final DatabaseHandler db){		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				log("Trying JamaNews initial insert");
				Document doc;
				String html=parent.getHtml("http://m.islahweb.org/jamaat_news");
				
				doc=Jsoup.parse(html);
				Elements links=doc.select("tbody tr");
				try{for (int i=0;i<Commons.NEWS_ENTRY_COUNT;i++){
					String title = links.get(i).select("td a").get(1).text();
					String jDate = links.get(i).select("td").get(2).text();
					
					//if(db.newsHandler.exists(title, jdate)) continue;
					String pageLink = "http://m.islahweb.org"+links.get(i).select("td a").get(1).attr("href");
					String indexImgAddr = links.get(i).select("img").get(0).attr("src").replace("40x40crop", "200x200");
					String imgAddress = indexImgAddr.replace("40x40", "700x700");
					links.get(i).select("td a").get(1).remove();
					String source = links.get(i).select("td").get(1).text();					
					db.newsHandler.initialInsert(title, jDate, indexImgAddr, imgAddress, source, Commons.NEWS_TYPE_JAMAAT, pageLink);
					log("JamaNews Initial insert finished successfully");
					}
				}catch (IndexOutOfBoundsException e) {log("Problem in JamaNews initial insert");}
			}
		});
		thread.start();	
	}
	
	public void secondaryInsertJamaNews(final DatabaseHandler db,final String url,final Integer id){
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				log("Trying JamaNews secondary insert");
				Document doc;
				String html=parent.getHtml(url);
				
				doc=Jsoup.parse(html);
				Elements links=doc.select("div.inner p");
				String mainText = "";
				try{for(Element link:links){					
					mainText += link.text();
					db.newsHandler.secondInsert(id, mainText);
					log("JamaNews Secondary insert finished successfully");
				}
				}catch (IndexOutOfBoundsException e) {log("Problem in JamaNews Secondary insert");}
			}
		});
		thread.start();	
	}
	
	public void initialInsertIslahNews(final DatabaseHandler db){
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				log("Trying IslahNews initial insert");
				Document doc;
				String html=parent.getHtml("http://m.islahweb.org/jamaat_news");
				
				doc=Jsoup.parse(html);
				Elements links=doc.select("tbody tr");
				try{for (int i=0;i<Commons.NEWS_ENTRY_COUNT;i++){
					String title = links.get(i).select("td a").get(0).text();
					String jDate = links.get(i).select("td").get(1).text();
					
					//if(db.newsHandler.exists(title, jdate)) continue;
					String pageLink = "http://m.islahweb.org"+links.get(i).select("td a").get(0).attr("href");
					String indexImgAddr = links.get(i).select("img").get(0).attr("src").replace("75x75crop", "200x200");
					String imgAddress = indexImgAddr.replace("40x40", "700x700");
					links.get(i).select("td a").get(0).remove();
					String source = links.get(i).select("td").get(0).text();					
					db.newsHandler.initialInsert(title, jDate, indexImgAddr, imgAddress, source, Commons.NEWS_TYPE_JAMAAT, pageLink);
					log("JamaNews Initial insert finished successfully");
					}
				}catch (IndexOutOfBoundsException e) {log("Problem in JamaNews initial insert");}
			}
		});
		thread.start();	
	}
	
	public void secondaryInsertIslahNews(final DatabaseHandler db,final String url,final Integer id){
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				log("Trying IslahNews secondary insert");
				Document doc;
				String html=parent.getHtml(url);
				
				doc=Jsoup.parse(html);
				Elements links=doc.select("div.inner p");
				String mainText = "";
				try{for(Element link:links){					
					mainText += link.text();
					db.newsHandler.secondInsert(id, mainText);
					log("IslahNews Secondary insert finished successfully");
				}
				}catch (IndexOutOfBoundsException e) {log("Problem in Islahnews Secondary insert");}
			}
		});
		thread.start();	
	}
	public void initialInsertSportNews(final DatabaseHandler db){
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				log("Trying SportNews initial insert");
				Document doc;
				String html=parent.getHtml("http://m.islahweb.org/sports");
				
				doc=Jsoup.parse(html);
				Elements links=doc.select("tbody tr");
				try{for (int i=0;i<Commons.NEWS_ENTRY_COUNT;i++){
					String title = links.get(i).select("td a").get(1).text();
					String jDate = links.get(i).select("td").get(2).text();
					
					//if(db.newsHandler.exists(title, jdate)) continue;
					String pageLink = "http://m.islahweb.org"+links.get(i).select("td a").get(1).attr("href");
					String indexImgAddr = links.get(i).select("img").get(0).attr("src").replace("40x40crop", "200x200");
					String imgAddress = indexImgAddr.replace("40x40", "700x700");
					links.get(i).select("td a").get(1).remove();
					String source = links.get(i).select("td").get(1).text();					
					db.newsHandler.initialInsert(title, jDate, indexImgAddr, imgAddress, source, Commons.NEWS_TYPE_JAMAAT, pageLink);
					log("JamaNews Initial insert finished successfully");
					}
				}catch (IndexOutOfBoundsException e) {log("Problem in JamaNews initial insert");}
			}
		});
		thread.start();	
	}
	
	public void secondaryInsertSportNews(final DatabaseHandler db,final String url,final Integer id){
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				log("Trying SportNews secondary insert");
				Document doc;
				String html=parent.getHtml(url);
				
				doc=Jsoup.parse(html);
				Elements links=doc.select("div.inner p");
				String mainText = "";
				try{for(Element link:links){					
					mainText += link.text();
					db.newsHandler.secondInsert(id, mainText);
					log("SportNews Secondary insert finished successfully");
				}
				}catch (IndexOutOfBoundsException e) {log("Problem in SportNews Secondary insert");}
			}
		});
		thread.start();	
	}	
	
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}
}
