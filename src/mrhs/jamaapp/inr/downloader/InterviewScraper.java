package mrhs.jamaapp.inr.downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import mrhs.jamaapp.inr.Commons;
import mrhs.jamaapp.inr.database.DatabaseHandler;
import android.text.Html;
import android.util.Log;

public class InterviewScraper {
	private static final boolean LOCAL_SHOW_LOG = true;
	
	public void initialInsert(final DatabaseHandler db){
		log("Trying Interview initial insert");
		Document doc;
		String[] addrList = new String[]{"0","1"};
		boolean downloaded = false;
		
		for(int j=0;j<addrList.length;j++){
			if(downloaded)
				break;
			String html=getHtml("http://m.islahweb.org/topic/category/مصاحبه?page="+addrList[j]);
			log("Interview page "+addrList[j]+" scraper has started");
			doc=Jsoup.parse(html);
			Elements links=doc.select("div.content-content div.inner");
			try{for (int i=0;i<5;i++){
				String title = links.get(i).select("h2 a").get(0).text();
				log("The title is: "+title);
				String jDate =  links.get(i).select("div.meta span").get(0).text();
				log("The jDate is: "+jDate);
				
				if(db.interviewHandler.exists(title, jDate)) {downloaded = true;break;}
				String pageLink = "http://m.islahweb.org"+links.get(i).select("h2 a").get(0).attr("href");
				//log("The pageLink is: "+pageLink);
				String indexImgAddr = links.get(i).select("img").get(0).attr("src").replace("/imagecache/"+"150x150crop", "");
				log("The indexImgAddr is: "+indexImgAddr);
				String imgAddress = indexImgAddr;	
				
				String writer = links.get(i).select("div[class=content clearfix] a").text().replace(")", "").replace("(", "");
				//log("The writer is: "+writer);
				String indexTxt = links.get(i).select("div[class=content clearfix] p").text();
				//log("The indexTxt is: "+indexTxt);
				if(db.interviewHandler.initialInsert(title, jDate, indexTxt, indexImgAddr, imgAddress, writer, pageLink))
					log("Interview Initial insert finished successfully");
				else
					log("Interview was not successfully inserted");
				}
			}catch (IndexOutOfBoundsException e) {log("Problem in interview initial insert");}
			catch (NumberFormatException e) {log("Problem in interview initial insert");}
		}
	}
	
	public void secondaryInsert(final DatabaseHandler db,final String url,final Integer id){
		log("Trying interview secondary insert");
		Document doc;
		String html=getHtml(url);
		
		doc=Jsoup.parse(html);
		
		String mainText = "";
		try{
			Elements elements = doc.select("div[class=inner] p");
			for(Element element : elements){
				mainText += Html.fromHtml(element.select("p").text())+"\n";
			}
			db.interviewHandler.secondInsert(id, mainText);
			log("Secondary insert finished successfully");		
		}catch (IndexOutOfBoundsException e) {log("Problem in interview Secondary insert");}
		
	}
	
	public String getHtml(String url){
		try {			
			String response = "";
			DefaultHttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse execute = client.execute(httpGet);
            InputStream content = execute.getEntity().getContent();

            BufferedReader buffer = new BufferedReader(
                    new InputStreamReader(content));
            String s = "";
            while ((s = buffer.readLine()) != null) {
                response += s;
            }
            content.close();
	        log("Page recieved");
	        return response;
		} catch(IOException e)
		{log("unable to get page");return "";}
	}
		
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}
}
