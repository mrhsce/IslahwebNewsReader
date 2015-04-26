package mrhs.jamaapp.inr.interviews;

import mrhs.jamaapp.inr.R;
import mrhs.jamaapp.inr.database.DatabaseHandler;
import mrhs.jamaapp.inr.main.Commons;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InterviewActivity extends Activity {

	private static final boolean LOCAL_SHOW_LOG = true;
	
	String title,jDate,writer,pageLink,text,indexImgAddr,bigImgAddr;
	TextView dateView,writerView,titleView,mainTextView;
	Button pageLinkButton;
	ImageView indexImgView,archivedImgView;
	boolean archived;
	Integer id;
	
	DatabaseHandler db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interview);
		
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
		
		indexImgView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		archivedImgView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
	
	private void settingUpAttributes(){
		
		title = getIntent().getExtras().getString("title");
		jDate = getIntent().getExtras().getString("jDate");
		writer = getIntent().getExtras().getString("writer");
		pageLink = getIntent().getExtras().getString("pageLink");
		text = getIntent().getExtras().getString("text");
		indexImgAddr = getIntent().getExtras().getString("indexImgAddr");
		bigImgAddr = getIntent().getExtras().getString("bigImgAddr");
		archived = getIntent().getExtras().getBoolean("archived");
		id = getIntent().getExtras().getInt("id");
		
		dateView = (TextView)findViewById(R.id.dateTxtView);
		dateView.setText(jDate);
		writerView = (TextView)findViewById(R.id.writerTxtView);
		writerView.setText(writer);
		titleView = (TextView)findViewById(R.id.titleTxtView);
		titleView.setText(title);
		mainTextView = (TextView)findViewById(R.id.maintextTxtView);		
		mainTextView.setText(Html.fromHtml(text));
		
		pageLinkButton = (Button)findViewById(R.id.pageLinkButton);
		
		indexImgView = (ImageView)findViewById(R.id.indexImgView);
		//indexImgView.setImageDrawable(drawable);
		//If image is not downloaded set it to invisible
		archivedImgView = (ImageView)findViewById(R.id.archivedImgView);
//		if(archived)
//			archivedImgView.setImageDrawable(drawable);
//		else
//			archivedImgView.setImageDrawable(drawable);
	}

	public void archiveSwitch(MenuItem item){
		if(!archived){
			item.setIcon(R.drawable.crescent_true);
			archived = true;
			db.interviewHandler.setArchived(id, archived);
			Toast.makeText(this, "این مطلب به آرشیو اضافه شد", Toast.LENGTH_SHORT).show();
		}
		else{
			item.setIcon(R.drawable.crescent_false);
			archived = false;
			db.interviewHandler.setArchived(id, archived);
			Toast.makeText(this, "این مطلب از آرشیو حذف شد", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		log("onCreateOptionMenu");
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.interview, menu);
		
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
