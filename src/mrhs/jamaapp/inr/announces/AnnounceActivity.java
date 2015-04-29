package mrhs.jamaapp.inr.announces;

import mrhs.jamaapp.inr.R;
import mrhs.jamaapp.inr.R.layout;
import mrhs.jamaapp.inr.R.menu;
import mrhs.jamaapp.inr.database.DatabaseHandler;
import mrhs.jamaapp.inr.main.Commons;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AnnounceActivity extends Activity {
	private static final boolean LOCAL_SHOW_LOG = true;
	
	String title,jDate,pageLink,text;
	TextView dateView,titleView,mainTextView;
	Button pageLinkButton;
	boolean archived;
	Integer id;
	
	DatabaseHandler db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_announce);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	    
		settingUpAttributes();
		db = new DatabaseHandler(this).open();
		pageLinkButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent browserIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(pageLink));
				startActivity(browserIntent);
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
		mainTextView.setText(Html.fromHtml(text));
		
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
			this.finish();
			
			break;
		}
		
		return true;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		db.close();
		super.onDestroy();
	}
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}

}
