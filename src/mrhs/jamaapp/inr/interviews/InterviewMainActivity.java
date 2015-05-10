package mrhs.jamaapp.inr.interviews;

import java.util.ArrayList;

import mrhs.jamaapp.inr.Commons;
import mrhs.jamaapp.inr.R;
import mrhs.jamaapp.inr.SdCardHandler;
import mrhs.jamaapp.inr.database.DatabaseHandler;
import mrhs.jamaapp.inr.news.NewsMainActivity;
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

public class InterviewMainActivity extends Activity {
	private static final boolean LOCAL_SHOW_LOG = true;
	
	public ArrayList<Integer> interviewIdList;
	
	public ListView listView;
	public InterviewArrayAdaptor adapter; 
	
	public DatabaseHandler db;
	SdCardHandler sd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interview_main);
		getActionBar().setDisplayHomeAsUpEnabled(true);	
		db = new DatabaseHandler(this).open(); 
		sd = new SdCardHandler();
		setUpList();
		listView.setDividerHeight(8);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,int position, long id){
				// TODO Auto-generated method stub
				if(db.interviewHandler.chkSecondInsert(interviewIdList.get(position))){
					Cursor cursor = db.interviewHandler.getById(interviewIdList.get(position));
					Bundle bundle = new Bundle();
					if(cursor.moveToFirst()){
						
						bundle.putString("title",cursor.getString(1));
						bundle.putString("jDate", cursor.getString(2));
						bundle.putString("writer",cursor.getString(5));
						bundle.putString("pageLink",cursor.getString(6));
						bundle.putString("text",cursor.getString(8));
						bundle.putString("indexImgAddr", cursor.getString(4));
						bundle.putString("bigImgAddr",cursor.getString(7));
						if(cursor.getInt(9)==0)
							bundle.putBoolean("archived",false);
						else
							bundle.putBoolean("archived",true);
						bundle.putInt("id",cursor.getInt(0));
						
					}
					Intent intent = new Intent(InterviewMainActivity.this, InterviewActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
					overridePendingTransition(R.anim.pull_in_left, R.anim.push_main_out);
					db.interviewHandler.setSeen(interviewIdList.get(position));
				}
				else
					Toast.makeText(InterviewMainActivity.this, "متن مصاحبه هنوز دانلود نشده است", Toast.LENGTH_SHORT).show();
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
		interviewIdList = getinterviewIdList();
		log("The size of the list is "+interviewIdList.size());
		
		listView = (ListView) findViewById(R.id.interview_list);
		adapter = new InterviewArrayAdaptor(this, interviewIdList,false,sd,db);
		listView.setAdapter(adapter);
	}
	
	public ArrayList<Integer> getinterviewIdList(){
		Cursor cursor = db.interviewHandler.getAll();
		ArrayList<Integer> list = new ArrayList<Integer>();
		if (cursor.moveToFirst()){
			list.add(cursor.getInt(0));
			for(int i=0;i<Commons.INTERVIEW_ENTRY_COUNT-1;i++){
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
