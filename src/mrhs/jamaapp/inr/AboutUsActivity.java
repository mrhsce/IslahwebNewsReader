package mrhs.jamaapp.inr;

import mrhs.jamaapp.inr.R;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.Menu;
import android.widget.TextView;

public class AboutUsActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		Typeface titr = Typeface.createFromAsset(getAssets(),"fonts/TitrBold.ttf");
		((TextView)findViewById(R.id.txtView2)).setTypeface(titr);
		((TextView)findViewById(R.id.txtView3)).setTypeface(titr);
		((TextView)findViewById(R.id.txtView4)).setTypeface(titr);
		((TextView)findViewById(R.id.txtView5)).setTypeface(titr);
		
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about_us, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

}
