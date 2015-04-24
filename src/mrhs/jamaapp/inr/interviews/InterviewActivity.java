package mrhs.jamaapp.inr.interviews;

import mrhs.jamaapp.inr.R;
import mrhs.jamaapp.inr.R.layout;
import mrhs.jamaapp.inr.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class InterviewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interview);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.interview, menu);
		return true;
	}

}
