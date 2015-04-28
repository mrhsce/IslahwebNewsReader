package mrhs.jamaapp.inr.interviews;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InterviewArrayAdaptor extends ArrayAdapter<Integer>{

	private static final boolean LOCAL_SHOW_LOG = true;
	
	private Context parent;
	
	DatabaseHandler db;
	boolean inArchive;
	
	private ArrayList<String> titleList,writerList,dateList,textList;
	private ArrayList<Integer> seenList,favoriteList;
	
	public InterviewArrayAdaptor(Context ctx,ArrayList<Integer> articleIdList,boolean inArchive) {
		// TODO Auto-generated constructor stub
		super(ctx, R.layout.interview_list_item, articleIdList);
		parent = ctx;
		db = new DatabaseHandler(ctx).open();
		this.inArchive = inArchive;
		initializeLists();
		db.close();
	}	
	
	
	public void initializeLists(){
		titleList = new ArrayList<String>();
		writerList = new ArrayList<String>();
		dateList = new ArrayList<String>();
		textList = new ArrayList<String>();
		seenList = new ArrayList<Integer>();
		favoriteList = new ArrayList<Integer>();
		
		log("start initialize list");
		Cursor cursor;		
		if(!inArchive){
			log("inArchive true");
			cursor = db.interviewHandler.getAll();
		}
		else{
			log("inArchive false");
			cursor = db.interviewHandler.getAllArchived();
		}
		log("passed");
		if (cursor.moveToFirst()){
			titleList.add(cursor.getString(1));
			writerList.add(cursor.getString(5));
			textList.add(cursor.getString(3));
			dateList.add(cursor.getString(2));
			seenList.add(cursor.getInt(10));
			favoriteList.add(cursor.getInt(9));
			
			if(!inArchive){
				for(int i=0;i<Commons.INTERVIEW_ENTRY_COUNT-1;i++){
					if(cursor.moveToNext()){
						titleList.add(cursor.getString(1));
						writerList.add(cursor.getString(5));
						textList.add(cursor.getString(3));
						dateList.add(cursor.getString(2));
						seenList.add(cursor.getInt(10));
						favoriteList.add(cursor.getInt(9));
					}
					else
						break;
				}
			}
			else{
				while(cursor.moveToNext()){
					titleList.add(cursor.getString(1));
					writerList.add(cursor.getString(5));
					textList.add(cursor.getString(3));
					dateList.add(cursor.getString(2));
					seenList.add(cursor.getInt(10));
					favoriteList.add(cursor.getInt(9));
				}
			}
			
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null){
			LayoutInflater inflater = (LayoutInflater) this.parent
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.interview_list_item, parent, false);
		}
		
		TextView titleView = (TextView) convertView.findViewById(R.id.labelTitle);
		TextView dateView = (TextView) convertView.findViewById(R.id.labelDate);
		TextView sourceView = (TextView) convertView.findViewById(R.id.labelSource);
		TextView textView = (TextView) convertView.findViewById(R.id.labelText);
		
		ImageView seenTag = (ImageView) convertView.findViewById(R.id.new_tag);
		ImageView favoriteTag = (ImageView) convertView.findViewById(R.id.favorite_tag);
		ImageView indexImgView = (ImageView) convertView.findViewById(R.id.indexImgView);
		
		LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.linear_layout);
		
		titleView.setText(titleList.get(position));
		dateView.setText(dateList.get(position));
		sourceView.setText(writerList.get(position));
		if(!textList.get(position).equals(""))
			textView.setText(textList.get(position));
		else
			textView.setVisibility(View.GONE);
		
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)linearLayout.getLayoutParams();		   
		if(seenList.get(position)==0){
			seenTag.setVisibility(View.VISIBLE);
			params.setMargins(7, 7, 0, 0);
		}
		else{
			seenTag.setVisibility(View.GONE);
			params.setMargins(0, 0, 0, 0);
		}
		linearLayout.setLayoutParams(params);
		
		if(!inArchive && favoriteList.get(position)==1){
			favoriteTag.setVisibility(View.VISIBLE);
		}
		else{
			favoriteTag.setVisibility(View.GONE);
		}
		
		return convertView;
	}

	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}
	
}
