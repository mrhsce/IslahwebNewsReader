package mrhs.jamaapp.inr.archive;

import java.util.ArrayList;

import mrhs.jamaapp.inr.Commons;
import mrhs.jamaapp.inr.R;
import mrhs.jamaapp.inr.announces.AnnounceActivity;
import mrhs.jamaapp.inr.announces.AnnounceArrayAdaptor;
import mrhs.jamaapp.inr.database.DatabaseHandler;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AnnounceFragment extends Fragment {
private static final boolean LOCAL_SHOW_LOG = true;
	
	public boolean inArchive;
	public ArrayList<Integer> announceIdList;
	
	public ListView listView;
	public TextView emptyHint;
	public AnnounceArrayAdaptor adapter; 
	
	public DatabaseHandler db;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		if(savedInstanceState != null){
			inArchive = savedInstanceState.getBoolean("inArchive");
		}
		db = new DatabaseHandler(getActivity()).open(); 		
		setUpList();
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
						bundle.putBoolean("inArchive", inArchive);
						
					}
					Intent intent = new Intent(getActivity(), AnnounceActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
					getActivity().overridePendingTransition(R.anim.pull_in_left, R.anim.push_main_out);
					db.anouncementHandler.setSeen(announceIdList.get(position));
				}
				else
					Toast.makeText(getActivity(), "متن خبر هنوز دانلود نشده است", Toast.LENGTH_SHORT).show();
			}
		}); 
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
		outState.putBoolean("inArchive", inArchive);
	}

	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setUpList();
	}
	
	
	private void setUpList(){
		announceIdList = getannounceIdList();
		if(announceIdList.size()>0){
			emptyHint.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
			log("The size of the list is "+announceIdList.size());
			adapter = new AnnounceArrayAdaptor(getActivity(), announceIdList,inArchive);
			listView.setAdapter(adapter);
		}
		else{
			emptyHint.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void setArguments(Bundle args) {
		// TODO Auto-generated method stub
		super.setArguments(args);
		this.inArchive = args.getBoolean("inArchive");
	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_announce, container, false);        
        listView = (ListView)rootView.findViewById(R.id.announce_list);  
        emptyHint = (TextView) rootView.findViewById(R.id.emptyHintTextView); 
        return rootView;
    }
	public ArrayList<Integer> getannounceIdList(){
		Cursor cursor;
		if(!inArchive){
			cursor = db.anouncementHandler.getAll();
		}
		else{
			cursor = db.anouncementHandler.getAllArchived();
		}
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		if (cursor.moveToFirst()){
			list.add(cursor.getInt(0));
			
			if(!inArchive){
				for(int i=0;i<Commons.ANNOUNCE_ENTRY_COUNT-1;i++){
					if(cursor.moveToNext())
						list.add(cursor.getInt(0));
					else
						break;
				}
			}
			else{
				while(cursor.moveToNext())
					list.add(cursor.getInt(0));
			}
			
		}
		return list;		
		
	}
	
	@Override
	public void onDestroyView() {
			// TODO Auto-generated method stub
		db.close();
			super.onDestroyView();
	}
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}
}
