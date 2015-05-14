package mrhs.jamaapp.inr;

import static gcm.Config.SENDER_ID;
import gcm.Config;
import gcm.ServerUtilities;

import com.google.android.gcm.GCMRegistrar;

import mrhs.jamaapp.inr.R;
import mrhs.jamaapp.inr.aboutj.AboutJamaatMainActivity;
import mrhs.jamaapp.inr.announces.AnnounceMainActivity;
import mrhs.jamaapp.inr.archive.ArchiveMainActivity;
import mrhs.jamaapp.inr.articles.ArticleMainActivity;
import mrhs.jamaapp.inr.downloader.DownloaderService;
import mrhs.jamaapp.inr.interviews.InterviewMainActivity;
import mrhs.jamaapp.inr.news.NewsMainActivity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	private static final boolean LOCAL_SHOW_LOG = true;
	
	// Asyntask for GCM
	 	AsyncTask<Void, Void, Void> mRegisterTask;
	
	Button newsButton,announceButton,interviewButton,articleButton,aboutJButton,aboutUsButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		buttonInitializer();
		setUpGCM();
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
				overridePendingTransition(R.anim.pull_in_left, R.anim.push_main_out);
			}
		});		
		
		articleButton = (Button)findViewById(R.id.article_button);
		articleButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,ArticleMainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.pull_in_left, R.anim.push_main_out);
			}
		});
		
		interviewButton = (Button) findViewById(R.id.interview_button);
		interviewButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,InterviewMainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.pull_in_left, R.anim.push_main_out);
				
			}
		});
		
		announceButton = (Button) findViewById(R.id.announcement_button);
		announceButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,AnnounceMainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.pull_in_left, R.anim.push_main_out);
				
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
		
		aboutUsButton = (Button) findViewById(R.id.about_us_button);
		aboutUsButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,AboutUsActivity.class);
				startActivity(intent);
				
			}
		});
		//aboutUsButton.setEnabled(false);
		
		Typeface adobeFont = Typeface.createFromAsset(getAssets(),"fonts/adobe_arabic_bold.otf");
		newsButton.setTypeface(adobeFont);
		articleButton.setTypeface(adobeFont);
		announceButton.setTypeface(adobeFont);
		interviewButton.setTypeface(adobeFont);
		aboutJButton.setTypeface(adobeFont);
		aboutUsButton.setTypeface(adobeFont);
		
		newsButton.setTextSize(26);
		articleButton.setTextSize(26);
		announceButton.setTextSize(26);
		interviewButton.setTextSize(26);
		aboutJButton.setTextSize(26);
		aboutUsButton.setTextSize(26);
	}	
	
	public void setUpGCM(){
		// GCM Related parts		
		if (!isConnectingToInternet()) {
			
			// stop executing code by return
			return;
		}
		// Make sure the device has the proper dependencies.
		//GCMRegistrar.checkDevice(this);
		
		
		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		GCMRegistrar.checkManifest(this);
			
		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);
		
		// Check if regid already presents
		if (regId.equals("")) {
			// Registration is not present, register now with GCM			
			GCMRegistrar.register(this, SENDER_ID);
		} else {
			// Device is already registered on GCM
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.				
				//Toast.makeText(getApplicationContext(), "Already registered with GCM", Toast.LENGTH_LONG).show();
			} else {
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
								// hence the use of AsyncTask instead of a raw thread.
				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {
					
					@Override
					protected Void doInBackground(Void... params) {
						// Register on our server
						// On server creates a new user
						ServerUtilities.register(context, Config.APP_NAME, regId);
						return null;
					}
					
					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}
					
				};
				mRegisterTask.execute(null, null, null);
			}
		}
	}
	
	public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null)
                  for (int i = 0; i < info.length; i++)
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false;
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.action_archive:			
			Intent intent = new Intent(MainActivity.this,ArchiveMainActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.pull_in_down, R.anim.push_main_out);
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
		//stopService(new Intent(this,DownloaderService.class));
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
