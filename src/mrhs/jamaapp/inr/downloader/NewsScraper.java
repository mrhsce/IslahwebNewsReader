package mrhs.jamaapp.inr.downloader;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import mrhs.jamaapp.inr.main.Commons;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import mrhs.jamaapp.inr.database.DatabaseHandler;
import android.util.Log;

public class NewsScraper {
	private static final boolean LOCAL_SHOW_LOG = false;
	
	public void initialInsert(final DatabaseHandler db){
		log("Trying news initial insert");
		String[] typeList = new String[]{Commons.NEWS_TYPE_JAMAAT,Commons.NEWS_TYPE_ISLAHWEB,Commons.NEWS_TYPE_SPORT};
		String[] addrList = new String[]{"http://m.islahweb.org/jamaat_news","http://m.islahweb.org/islahweb-news",
				"http://m.islahweb.org/sports"};
		Integer[] indexList = new Integer[]{1,0,1};
		String[] imgDimList = new String[]{"40x40crop","75x75crop","40x40crop"};

		Document doc;
		for(int type=0;type<typeList.length;type++){
			String html=getHtml(addrList[type]);
			doc=Jsoup.parse(html);
			Elements links=doc.select("tbody tr");
			try{for (int i=0;i<Commons.NEWS_ENTRY_COUNT;i++){
				String title = links.get(i).select("td a").get(indexList[type]).text();
				log("The title is: "+title);
				String jDate = links.get(i).select("td").get(indexList[type]+1).text();
				log("The jDate is: "+jDate);
				if(db.newsHandler.exists(title, jDate)) break;
				String pageLink = "http://m.islahweb.org"+links.get(i).select("td a").get(indexList[type]).attr("href");
				//log("The page link is: "+pageLink);
				String indexImgAddr = links.get(i).select("img").get(0).attr("src").replace(imgDimList[type], "200x200");
				String imgAddress = indexImgAddr.replace("200x200", "700x700");
				//log("The imgAddr is: "+imgAddress);
				links.get(i).select("td a").get(indexList[type]).remove();
				String source = links.get(i).select("td").get(indexList[type]).text();					
				//log("The source is: "+source);
				db.newsHandler.initialInsert(title, jDate, indexImgAddr, imgAddress, source, typeList[type], pageLink);
				log("News "+typeList[type]+" Initial insert finished successfully");
				}
			}catch (IndexOutOfBoundsException e) {log("Problem in News News "+typeList[type]+" initial insert");}
		}
	}
	
	public void secondaryInsert(final DatabaseHandler db,final String url,final Integer id){
		log("Trying secondary insert");
		Document doc;
		String html=getHtml(url);		
		doc=Jsoup.parse(html);
		
		String mainText = "";
		try{					
			mainText = doc.select("div[class=inner] p").text();		
			db.newsHandler.secondInsert(id, mainText);
			log("Secondary insert finished successfully");
		}catch (IndexOutOfBoundsException e) {log("Problem in Islahnews Secondary insert");}
		
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
