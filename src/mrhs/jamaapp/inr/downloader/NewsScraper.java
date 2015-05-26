package mrhs.jamaapp.inr.downloader;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import mrhs.jamaapp.inr.Commons;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import mrhs.jamaapp.inr.database.DatabaseHandler;
import android.text.Html;
import android.util.Log;

public class NewsScraper {
	private static final boolean LOCAL_SHOW_LOG = true;
	
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
				log("The page link is: "+pageLink);
				String indexImgAddr = links.get(i).select("img").get(0).attr("src").replace("/imagecache/"+imgDimList[type], "");
				String imgAddress = indexImgAddr;
				//log("The img addr is: "+imgAddress);
				// This is for handling the f$^&ng big image!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				if(indexImgAddr.equals("http://m.islahweb.org/sites/default/files/img_2686.jpg")){
					indexImgAddr = "Fuck";
					imgAddress = "Fuck";
				}	
				//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				
				links.get(i).select("td a").get(indexList[type]).remove();
				String source = links.get(i).select("td").get(indexList[type]).text().replace(")", "").replace("(", "");
				//log("The source is: "+source);
				db.newsHandler.initialInsert(title, jDate, indexImgAddr, imgAddress, source, typeList[type], pageLink);
				//log("News "+typeList[type]+" Initial insert finished successfully");
				}
			}catch (IndexOutOfBoundsException e) {log("Problem in News News "+typeList[type]+" initial insert");}
			catch (NumberFormatException e) {log("Problem in news initial insert");}
		}
	}
	
	public void secondaryInsert(final DatabaseHandler db,final String url,final Integer id){
		log("Trying secondary insert");
		Document doc;
		String html=getHtml(url);		
		doc=Jsoup.parse(html);
		
		String mainText = "";
		try{					
			Elements elements = doc.select("div[class=inner] p");
			for(Element element : elements){
				mainText += Html.fromHtml(element.select("p").text())+"\n";
			}		
			db.newsHandler.secondInsert(id, mainText);
			log("Secondary insert finished successfully");
		}catch (IndexOutOfBoundsException e) {log("Problem in Islahnews Secondary insert");}
		
	}
	
	public String getHtml(String url){
		try {
			String response = "";
			DefaultHttpClient client = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            log("1");
            HttpResponse execute = client.execute(httpGet);
            log("2");
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
		{  e.printStackTrace();
			log("unable to get page");return "";}
	}
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}
}
