package mrhs.jamaapp.inr.news;

import java.util.ArrayList;
import mrhs.jamaapp.inr.R;
import mrhs.jamaapp.inr.main.Commons;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NewsListArrayAdapter extends ArrayAdapter<Integer> {
	
	private String newsType;
	private ArrayList<Integer> newsIdList;
	private Context context;
	private NewsFragment parent;
	
	private ArrayList<String> titleList,sourceList,dateList;
	
	public NewsListArrayAdapter(Context ctx,ArrayList<Integer> newsIdList,NewsFragment parent) {
		// TODO Auto-generated constructor stub
		super(ctx, R.layout.news_list_item, newsIdList);
		this.newsIdList = newsIdList;
		this.parent = parent;
		this.newsType = parent.type;
		context = ctx;
		
	}
	
	public void initializeLists(){
		titleList = new ArrayList<String>();
		sourceList = new ArrayList<String>();
		dateList = new ArrayList<String>();
		
		Cursor cursor = parent.db.newsHandler.getAllByType(newsType);
		if (cursor.moveToFirst()){
			titleList.add(cursor.getString(1));
			sourceList.add(cursor.getString(4));
			dateList.add(cursor.getString(2));
			for(int i=0;i<Commons.NEWS_ENTRY_COUNT-1;i++){
				if(cursor.moveToNext())
				{
					titleList.add(cursor.getString(1));
					sourceList.add(cursor.getString(4));
					dateList.add(cursor.getString(2));
				}
				else
					break;
			}
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null){
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.news_list_item, parent, false);
		}		
		TextView titleView = (TextView) convertView.findViewById(R.id.labelTitle);
		TextView dateView = (TextView) convertView.findViewById(R.id.labelDate);
		TextView sourceView = (TextView) convertView.findViewById(R.id.labelSource);
		
		titleView.setText(titleList.get(position));
		dateView.setText(dateList.get(position));
		sourceView.setText(sourceList.get(position));
		
		
		return convertView;
	}

}
