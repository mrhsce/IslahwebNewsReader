package mrhs.jamaapp.inr.main;

import mrhs.jamaapp.inr.R;
import mrhs.jamaapp.inr.aboutj.AboutJamaatMainActivity;
import mrhs.jamaapp.inr.announces.AnnounceMainActivity;
import mrhs.jamaapp.inr.archive.ArchiveMainActivity;
import mrhs.jamaapp.inr.articles.ArticleMainActivity;
import mrhs.jamaapp.inr.downloader.DownloaderService;
import mrhs.jamaapp.inr.interviews.InterviewMainActivity;
import mrhs.jamaapp.inr.news.NewsMainActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	private static final boolean LOCAL_SHOW_LOG = true;
	
	Button newsButton,announceButton,interviewButton,articleButton,contactButton,aboutJButton,aboutUsButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		buttonInitializer();
		Intent intent = new Intent(this,DownloaderService.class);
		startService(intent);
	}
	
	private void buttonInitializer(){
		newsButton = (Button)findViewById(R.id.news_button);
		newsButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,NewsMainActivity.class);
				startActivity(intent);
			}
		});
		
		articleButton = (Button)findViewById(R.id.article_button);
		articleButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,ArticleMainActivity.class);
				startActivity(intent);
			}
		});
		
		interviewButton = (Button) findViewById(R.id.interview_button);
		interviewButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,InterviewMainActivity.class);
				startActivity(intent);
				
			}
		});
		
		announceButton = (Button) findViewById(R.id.announcement_button);
		announceButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,AnnounceMainActivity.class);
				startActivity(intent);
				
			}
		});
		
		aboutJButton = (Button) findViewById(R.id.about_jamaat_button);
		aboutJButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,AboutJamaatMainActivity.class);
				startActivity(intent);
				
			}
		});
		
		contactButton = (Button) findViewById(R.id.contact_jamaat_button);
		contactButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,AboutJamaatMainActivity.class);
				startActivity(intent);
				
			}
		});
		
		aboutJButton.setEnabled(false);
		contactButton.setEnabled(false);
		
		aboutUsButton = (Button) findViewById(R.id.about_us_button);
		aboutUsButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,AboutUsActivity.class);
				startActivity(intent);
				
			}
		});
	}	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.action_archive:			
			Intent intent = new Intent(MainActivity.this,ArchiveMainActivity.class);
			startActivity(intent);
			break;
//		case R.id.action_refresh:
//			refresh
//			
//			break;
		}
		
		return true;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		stopService(new Intent(this,DownloaderService.class));
		super.onDestroy();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}

}
