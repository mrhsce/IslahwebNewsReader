package mrhs.jamaapp.inr;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TextManager {
	Context context;
	
	public TextManager(Context ctx) {
		// TODO Auto-generated constructor stub
		context = ctx;
	}
	
	public void shareShortText(String type,String title,String source,String date,String link) throws UnsupportedEncodingException{
		String text = "برنامه خبرخوان اصلاح وب\n"+type+"\n"+title+"\n"+source+" "+date+"\n"+URLDecoder.decode(link, "UTF-8");
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        //sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(sharingIntent, "به اشتراک گذاری از طریق"));
	}
	
	public void copyCompleteTextToClipboard(String type,String title,String source,String date,String link,String mainText) throws UnsupportedEncodingException{
		String text = "برنامه خبرخوان اصلاح وب\n"+type+"\n"+title+"\n"+mainText+"\n"+source+" "+date+"\n"+URLDecoder.decode(link, "UTF-8");
		ClipboardManager clipboard = (ClipboardManager)   context.getSystemService(Context.CLIPBOARD_SERVICE);
	    ClipData clip = ClipData.newPlainText("INR", text);
	    clipboard.setPrimaryClip(clip);
	    Toast.makeText(context, "متن مطلب با موفقیت کپی شد", Toast.LENGTH_SHORT).show();
	}
}
