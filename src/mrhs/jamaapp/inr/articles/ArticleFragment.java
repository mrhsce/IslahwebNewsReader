package mrhs.jamaapp.inr.articles;

import java.util.ArrayList;

import mrhs.jamaapp.inr.R;
import mrhs.jamaapp.inr.database.DatabaseHandler;
import mrhs.jamaapp.inr.main.Commons;
import mrhs.jamaapp.inr.news.NewsListArrayAdapter;
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

public class ArticleFragment extends Fragment {
private static final boolean LOCAL_SHOW_LOG = true;
	
	public String type;
	public boolean inArchive;
	
	public ArrayList<Integer> articleIdList;
	
	public ListView listView;
	public TextView emptyHint;
	public ArticleListArrayAdaptor adapter; 
	
	public DatabaseHandler db;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		db = new DatabaseHandler(getActivity()).open(); 
		setUpList();
		listView.setDividerHeight(8);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View view,int position, long id){
				// TODO Auto-generated method stub
				if(db.articleHandler.chkSecondInsert(articleIdList.get(position))){
					Cursor cursor = db.articleHandler.getById(articleIdList.get(position));
					Bundle bundle = new Bundle();
					if(cursor.moveToFirst()){
						
						bundle.putString("title",cursor.getString(1));
						bundle.putString("jDate", cursor.getString(2));
						bundle.putString("writer",cursor.getString(5));
						bundle.putString("type",cursor.getString(6));
						bundle.putString("pageLink",cursor.getString(7));
						bundle.putString("text",cursor.getString(9));
						bundle.putString("indexImgAddr", cursor.getString(4));
						bundle.putString("bigImgAddr",cursor.getString(8));
												
						if(cursor.getInt(10)==0)
							bundle.putBoolean("archived",false);
						else
							bundle.putBoolean("archived",true);
						bundle.putInt("id",cursor.getInt(0));
						bundle.putBoolean("inArchive", inArchive);
						
					}
					Intent intent = new Intent(getActivity(), ArticleActivity.class);
					intent.putExtras(bundle);
					startActivity(intent);
					db.articleHandler.setSeen(articleIdList.get(position));
				}
				else
					Toast.makeText(getActivity(), "متن مقاله هنوز دانلود نشده است", Toast.LENGTH_SHORT).show();
			}
		}); 
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setUpList();
	}
	
	private void setUpList(){
		articleIdList = getarticleIdList();
		if(articleIdList.size()>0){
			emptyHint.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
			log("The size of the list is "+articleIdList.size());
			if(type.equals(""))
				adapter = new ArticleListArrayAdaptor(getActivity(), articleIdList,true,inArchive, this);
			else
				adapter = new ArticleListArrayAdaptor(getActivity(), articleIdList,false,inArchive, this);
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
		this.type = args.getString("type");
		this.inArchive =args.getBoolean("inArchive");
	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_article, container, false);        
        listView = (ListView)rootView.findViewById(R.id.article_list);      
        emptyHint = (TextView) rootView.findViewById(R.id.emptyHintTextView); 
        return rootView;
    }
	public ArrayList<Integer> getarticleIdList(){
		Cursor cursor;
		if(!inArchive){
			if(!type.equals(""))
				cursor = db.articleHandler.getAllByType(type);
			else
				cursor = db.articleHandler.getAll();
		}
		else
			cursor = db.articleHandler.getAllArchived();
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		if (cursor.moveToFirst()){
			list.add(cursor.getInt(0));
			
			if(!inArchive){
				for(int i=0;i<Commons.ARTICLE_ENTRY_COUNT-1;i++){
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
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}
}
