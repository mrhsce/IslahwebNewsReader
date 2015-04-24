package mrhs.jamaapp.inr.downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import mrhs.jamaapp.inr.database.DatabaseHandler;
import mrhs.jamaapp.inr.main.Commons;
import android.util.Log;

public class ArticleScraper {
	private static final boolean LOCAL_SHOW_LOG = true;
	
	public void initialInsert(final DatabaseHandler db){
		log("Trying Article initial insert");
		Document doc;
		String[] addrList = new String[]{"religion","andisheh","ahlesonnat","culture",
				"politics","society","history","literature"};
		String[] typeList = new String[]{Commons.ARTICLE_TYPE_DINODAVAT,Commons.ARTICLE_TYPE_ANDISHE,
				Commons.ARTICLE_TYPE_AHLESONNAT,Commons.ARTICLE_TYPE_FARHANG,Commons.ARTICLE_TYPE_SIASI,
				Commons.ARTICLE_TYPE_EJTEMAEI,Commons.ARTICLE_TYPE_TARIKH,Commons.ARTICLE_TYPE_ADABOHONAR};
		for(int j=0;j<addrList.length;j++){
			String html=getHtml("http://m.islahweb.org/"+addrList[j]);
			log("Type "+typeList[j]+" scraper has started");
			doc=Jsoup.parse(html);
			Elements links=doc.select("tbody tr");
			try{for (int i=0;i<Commons.ARTICLE_ENTRY_COUNT;i++){
				String title = links.get(i).select("td a").get(1).text();
				log("The title is: "+title);
				String jDate = "";
				if(j==1)
					jDate = links.get(i).select("td").get(3).text();
				else
					jDate = links.get(i).select("td").get(2).text();
				
				log("The jDate is: "+jDate);
				
				if(db.articleHandler.exists(title, jDate)) continue;
				String pageLink = "http://m.islahweb.org"+links.get(i).select("td a").get(1).attr("href");
				log("The pageLink is: "+pageLink);
				String indexImgAddr = links.get(i).select("img").get(0).attr("src").replace("40x40crop", "200x200");
				log("The indexImgAddr is: "+indexImgAddr);
				String imgAddress = indexImgAddr.replace("200x200", "700x700");	
				
				links.get(i).select("td a").get(1).remove();
				String writer = links.get(i).select("td").get(1).text();	
				log("The writer is: "+writer);
				String indexTxt = "";
				if(j==0)
					indexTxt = doc.select("div"+links.get(i).select("tr").get(0).attr("rel")+" span").get(0).text();
				else
					indexTxt = "";
				log("The indexTxt is: "+indexTxt);
				if(db.articleHandler.initialInsert(title, jDate, indexTxt, indexImgAddr, imgAddress, writer, typeList[j], pageLink))
					log("Article Initial insert finished successfully");
				else
					log("Article was not successfully inserted");
				}
			}catch (IndexOutOfBoundsException e) {log("Problem in Article initial insert");}
		}
	}
	
	public void secondaryInsert(final DatabaseHandler db,final String url,final Integer id){
		log("Trying article secondary insert");
		Document doc;
		String html=getHtml(url);
		
		doc=Jsoup.parse(html);
		Elements links=doc.select("div[class=inner] p");
		String mainText = "";
		try{for(Element link:links){					
			mainText += link.text();
			db.articleHandler.secondInsert(id, mainText);
			log("Secondary insert finished successfully");
		}
		}catch (IndexOutOfBoundsException e) {log("Problem in article Secondary insert");}
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
