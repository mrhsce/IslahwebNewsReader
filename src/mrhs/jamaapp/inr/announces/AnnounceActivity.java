package mrhs.jamaapp.inr.announces;

import java.io.UnsupportedEncodingException;

import mrhs.jamaapp.inr.Commons;
import mrhs.jamaapp.inr.R;
import mrhs.jamaapp.inr.TextManager;
import mrhs.jamaapp.inr.database.DatabaseHandler;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AnnounceActivity extends Activity {
	private static final boolean LOCAL_SHOW_LOG = true;
	
	String title,jDate,pageLink,text;
	TextView dateView,titleView,mainTextView;
	Button pageLinkButton,ShareButton,copyButton;
	boolean archived;
	Integer id;
	
	DatabaseHandler db;
	TextManager txtMgr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_announce);
		
		RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
		TranslateAnimation slide = new TranslateAnimation(500, 0, 0, 0);    	    
		slide.setDuration(1000);
		slide.setInterpolator(this,android.R.anim.decelerate_interpolator);
	    slide.setFillAfter(true);   
	    mainLayout.startAnimation(slide); 
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	    
		settingUpAttributes();
		db = new DatabaseHandler(this).open();
		txtMgr = new TextManager(this);
		pageLinkButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent browserIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(pageLink));
				startActivity(browserIntent);
			}
		});
		
		copyButton = (Button) findViewById(R.id.copyButton);
		copyButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					txtMgr.copyCompleteTextToClipboard("خبر",title,"خبرگزاری اصلاح",jDate,pageLink,text);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		ShareButton = (Button) findViewById(R.id.shareButton);
		ShareButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					txtMgr.shareShortText("بیانیه",title,"خبرگزاری اصلاح",jDate,pageLink);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
						
	}
	
	private void settingUpAttributes(){
		
		title = getIntent().getExtras().getString("title");
		jDate = getIntent().getExtras().getString("jDate");
		pageLink = getIntent().getExtras().getString("pageLink");
		text = getIntent().getExtras().getString("text");
		archived = getIntent().getExtras().getBoolean("archived");
		id = getIntent().getExtras().getInt("id");
		
		dateView = (TextView)findViewById(R.id.dateTxtView);
		dateView.setText(jDate);
		titleView = (TextView)findViewById(R.id.titleTxtView);
		titleView.setText(title);
		mainTextView = (TextView)findViewById(R.id.maintextTxtView);
		mainTextView.setText(text);
		
		Typeface mitraFont = Typeface.createFromAsset(getAssets(),"fonts/mitra.ttf");
		Typeface titrFont = Typeface.createFromAsset(getAssets(),"fonts/TitrBold.ttf");
		titleView.setTypeface(titrFont);
		mainTextView.setTypeface(mitraFont);
		
		pageLinkButton = (Button)findViewById(R.id.pageLinkButton);		
		
	}

	public void archiveSwitch(MenuItem item){
		if(!archived){
			item.setIcon(R.drawable.crescent_true);
			archived = true;
			db.anouncementHandler.setArchived(id, archived);
			Toast.makeText(this, "این مطلب به آرشیو اضافه شد", Toast.LENGTH_SHORT).show();
		}
		else{
			item.setIcon(R.drawable.crescent_false);
			archived = false;
			db.anouncementHandler.setArchived(id, archived);
			Toast.makeText(this, "این مطلب از آرشیو حذف شد", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		log("onCreateOptionMenu");
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.announce, menu);
		
		if(archived){
			menu.findItem(R.id.action_archive).setIcon(R.drawable.crescent_true);
		}
		else{
			menu.findItem(R.id.action_archive).setIcon(R.drawable.crescent_false);
		}
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.action_archive:
			archiveSwitch(item);
			
			break;
		case android.R.id.home:
			onBackPressed();
			
			break;
		}
		
		return true;
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		db.close();
		overridePendingTransition(R.anim.push_main_in,R.anim.pull_out_right);
	}
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}

}
