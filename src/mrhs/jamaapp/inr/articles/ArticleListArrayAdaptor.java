package mrhs.jamaapp.inr.articles;

import java.util.ArrayList;

import mrhs.jamaapp.inr.R;
import mrhs.jamaapp.inr.database.DatabaseHandler;
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

public class ArticleListArrayAdaptor extends ArrayAdapter<Integer> {

private static final boolean LOCAL_SHOW_LOG = true;
	
	private String articleType;
	private Context context;
	private ArticleFragment parent;
	
	private ArrayList<String> titleList,writerList,dateList,textList;
	
	public ArticleListArrayAdaptor(Context ctx,ArrayList<Integer> articleIdList,ArticleFragment parent) {
		// TODO Auto-generated constructor stub
		super(ctx, R.layout.article_list_item, articleIdList);
		this.parent = parent;
		this.articleType = parent.type;
		context = ctx;
		initializeLists();		
	}
	
	public void initializeLists(){
		titleList = new ArrayList<String>();
		writerList = new ArrayList<String>();
		dateList = new ArrayList<String>();
		textList = new ArrayList<String>();
		
		Cursor cursor = parent.db.articleHandler.getAllByType(articleType);
		log("article type is: "+articleType);
		if (cursor.moveToFirst()){
			titleList.add(cursor.getString(1));
			writerList.add(cursor.getString(5));
			textList.add(cursor.getString(3));
			dateList.add(cursor.getString(2));
			for(int i=0;i<Commons.ARTICLE_ENTRY_COUNT-1;i++){
				if(cursor.moveToNext())
				{
					titleList.add(cursor.getString(1));
					writerList.add(cursor.getString(5));
					textList.add(cursor.getString(3));
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
			convertView = inflater.inflate(R.layout.article_list_item, parent, false);
		}
		
		TextView titleView = (TextView) convertView.findViewById(R.id.labelTitle);
		TextView dateView = (TextView) convertView.findViewById(R.id.labelDate);
		TextView sourceView = (TextView) convertView.findViewById(R.id.labelSource);
		TextView textView = (TextView) convertView.findViewById(R.id.labelText);
		
		ImageView indexImgView = (ImageView) convertView.findViewById(R.id.indexImgView);
		
		titleView.setText(titleList.get(position));
		dateView.setText(dateList.get(position));
		sourceView.setText(writerList.get(position));
		if(!textList.get(position).equals(""))
			textView.setText(textList.get(position));
		else
			textView.setVisibility(View.GONE);
		
		
		return convertView;
	}
	
	private static void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d("DatabaseHandler",message);
	}

}
