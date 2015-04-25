package mrhs.jamaapp.inr.announces;

import mrhs.jamaapp.inr.R;
import mrhs.jamaapp.inr.R.layout;
import mrhs.jamaapp.inr.R.menu;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AnnounceActivity extends Activity {

	String title,jDate,pageLink,text;
	TextView dateView,titleView,mainTextView;
	Button pageLinkButton;
	ImageView archivedImgView;
	boolean archived;
	Integer id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_announce);
		
		settingUpAttributes();
		pageLinkButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent browserIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(pageLink));
				startActivity(browserIntent);
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
		
		pageLinkButton = (Button)findViewById(R.id.pageLinkButton);
		
		archivedImgView = (ImageView)findViewById(R.id.archivedImgView);
//		if(archived)
//			archivedImgView.setImageDrawable(drawable);
//		else
//			archivedImgView.setImageDrawable(drawable);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.news, menu);
		return true;
	}

}
