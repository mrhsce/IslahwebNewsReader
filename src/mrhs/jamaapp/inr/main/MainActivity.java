package mrhs.jamaapp.inr.main;

import mrhs.jamaapp.inr.R;
import mrhs.jamaapp.inr.downloader.DownloaderService;
import mrhs.jamaapp.inr.news.NewsMainActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
