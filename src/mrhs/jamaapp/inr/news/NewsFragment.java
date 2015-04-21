package mrhs.jamaapp.inr.news;

import java.util.ArrayList;

import mrhs.jamaapp.inr.R;
import mrhs.jamaapp.inr.database.DatabaseHandler;
import mrhs.jamaapp.inr.main.Commons;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class NewsFragment extends Fragment {
	private static final boolean LOCAL_SHOW_LOG = true;
	
	public String type;
	public ArrayList<Integer> newsIdList;
	
	public ListView listView;
	public NewsListArrayAdapter adapter; 
	
	public DatabaseHandler db;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		db = new DatabaseHandler(getActivity()).open(); 
		newsIdList = getNewsIdList();
	}
	
	@Override
	public void setArguments(Bundle args) {
		// TODO Auto-generated method stub
		super.setArguments(args);
		this.type = args.getString("type");
	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        
        listView = (ListView)rootView.findViewById(R.id.news_list);
		adapter = new NewsListArrayAdapter(getActivity(), newsIdList, this);
		listView.setAdapter(adapter);
		listView.setDividerHeight(2); 
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,int position, long id){
				// TODO Auto-generated method stub
				
			}
		}); 
        return rootView;
    }
	public ArrayList<Integer> getNewsIdList(){
		Cursor cursor = db.newsHandler.getAllByType(type);
		ArrayList<Integer> list = new ArrayList<Integer>();
		if (cursor.moveToFirst()){
			list.add(cursor.getInt(0));
			for(int i=0;i<Commons.NEWS_ENTRY_COUNT-1;i++){
				if(cursor.moveToNext())
					list.add(cursor.getInt(0));
				else
					break;
			}
		}
		return list;		
		
	}
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}
}
