package mrhs.jamaapp.inr.news;

import java.util.ArrayList;
import mrhs.jamaapp.inr.R;
import mrhs.jamaapp.inr.main.Commons;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsListArrayAdapter extends ArrayAdapter<Integer> {
	private static final boolean LOCAL_SHOW_LOG = true;
	
	private String newsType;
	private Context context;
	private NewsFragment parent;
	
	private String[] tabs = { "اخبار جماعت", "اخبار اصلاح", "اخبار ورزشی" };
	
	private ArrayList<String> titleList,sourceList,dateList,typeList;
	
	private boolean showType;
	private boolean inArchive;
	
	public NewsListArrayAdapter(Context ctx,ArrayList<Integer> newsIdList,boolean showtype,boolean inArchive,NewsFragment parent) {
		// TODO Auto-generated constructor stub
		super(ctx, R.layout.news_list_item, newsIdList);
		this.parent = parent;
		this.newsType = parent.type;
		context = ctx;
		showType = showtype;
		this.inArchive = inArchive;
		initializeLists();		
	}
	
	
	
	public void initializeLists(){
		titleList = new ArrayList<String>();
		sourceList = new ArrayList<String>();
		dateList = new ArrayList<String>();
		if(showType)
			typeList = new ArrayList<String>();
		
		Cursor cursor;
		if(!inArchive){
			if(!showType)
				cursor = parent.db.newsHandler.getAllByType(newsType);
			else
				cursor = parent.db.newsHandler.getAll();
		}
		else{
			cursor = parent.db.newsHandler.getAllArchived();
		}		
		
		if (cursor.moveToFirst()){
			titleList.add(cursor.getString(1));
			sourceList.add(cursor.getString(4));
			dateList.add(cursor.getString(2));
			if(showType)
				typeList.add(cursor.getString(5));
			
			if(!inArchive){
				for(int i=0;i<Commons.NEWS_ENTRY_COUNT-1;i++){
					if(cursor.moveToNext()){
						titleList.add(cursor.getString(1));
						sourceList.add(cursor.getString(4));
						dateList.add(cursor.getString(2));
						if(showType)
							typeList.add(cursor.getString(5));
					}
					else
						break;
				}
			}
			else{
				while(cursor.moveToNext()){
					titleList.add(cursor.getString(1));
					sourceList.add(cursor.getString(4));
					dateList.add(cursor.getString(2));
					if(showType)
						typeList.add(cursor.getString(5));
				}
			}
			
		}
	}
	
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		initializeLists();
		super.notifyDataSetChanged();
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
		TextView typeView = (TextView) convertView.findViewById(R.id.labelType);
		
		ImageView indexImgView = (ImageView) convertView.findViewById(R.id.indexImgView);
		
		if(showType)
			typeView.setText(tabs[Integer.parseInt(typeList.get(position))]);			
		else
			typeView.setVisibility(View.GONE);
		
		titleView.setText(titleList.get(position));
		dateView.setText(dateList.get(position));
		sourceView.setText(sourceList.get(position));
		
		
		return convertView;
	}
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}

}
