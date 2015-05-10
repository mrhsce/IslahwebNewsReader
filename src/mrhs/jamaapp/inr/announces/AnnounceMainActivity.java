package mrhs.jamaapp.inr.announces;

import java.util.ArrayList;

import mrhs.jamaapp.inr.Commons;
import mrhs.jamaapp.inr.R;
import mrhs.jamaapp.inr.database.DatabaseHandler;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class AnnounceMainActivity extends Activity {

private static final boolean LOCAL_SHOW_LOG = true;
	
	public ArrayList<Integer> announceIdList;
	
	public ListView listView;
	public AnnounceArrayAdaptor adapter; 
	
	public DatabaseHandler db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_announce_main);
		getActionBar().setDisplayHomeAsUpEnabled(true);	
		db = new DatabaseHandler(this).open(); 
		setUpList();
		listView.setAdapter(adapter);
		listView.setDividerHeight(8);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,int position, long id){
				// TODO Auto-generated method stub
				if(db.anouncementHandler.chkSecondInsert(announceIdList.get(position))){
					Cursor cursor = db.anouncementHandler.getById(announceIdList.get(position));
					Bundle bundle = new Bundle();
					if(cursor.moveToFirst()){
						
						bundle.putString("title",cursor.getString(1));
						bundle.putString("jDate", cursor.getString(2));
						bundle.putString("pageLink",cursor.getString(3));
						bundle.putString("text",cursor.getString(4));
						if(cursor.getInt(6)==0)
							bundle.putBoolean("archived",false);
						else
							bundle.putBoolean("archived",true);
						bundle.putInt("id",cursor.getInt(0));
						
					}
					Intent intent = new Intent(AnnounceMainActivity.this, AnnounceActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
					overridePendingTransition(R.anim.pull_in_left, R.anim.push_main_out);
					db.anouncementHandler.setSeen(announceIdList.get(position));
				}
				else
					Toast.makeText(AnnounceMainActivity.this, "متن بیانیه هنوز دانلود نشده است", Toast.LENGTH_SHORT).show();
			}
		}); 
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setUpList();
	}
	
	public void setUpList(){
		announceIdList = getannounceIdList();
		log("The size of the list is "+announceIdList.size());
		
		listView = (ListView) findViewById(R.id.announce_list);
		adapter = new AnnounceArrayAdaptor(this, announceIdList,false);
		listView.setAdapter(adapter);
	}
	
	public ArrayList<Integer> getannounceIdList(){
		Cursor cursor = db.anouncementHandler.getAll();
		ArrayList<Integer> list = new ArrayList<Integer>();
		if (cursor.moveToFirst()){
			list.add(cursor.getInt(0));
			for(int i=0;i<Commons.ANNOUNCE_ENTRY_COUNT-1;i++){
				if(cursor.moveToNext())
					list.add(cursor.getInt(0));
				else
					break;
			}
		}
		return list;		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.interview_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
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
		overridePendingTransition(R.anim.push_main_in,R.anim.pull_out_right);
	}
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}

}
