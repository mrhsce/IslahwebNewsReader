package mrhs.jamaapp.inr.announces;

import java.util.ArrayList;

import mrhs.jamaapp.inr.Commons;
import mrhs.jamaapp.inr.R;
import mrhs.jamaapp.inr.database.DatabaseHandler;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AnnounceArrayAdaptor extends ArrayAdapter<Integer>{

private static final boolean LOCAL_SHOW_LOG = true;
	
	private Context parent;
	
	private ArrayList<String> titleList,dateList;
	private ArrayList<Integer> seenList,favoriteList;
	
	DatabaseHandler db;
	
	boolean inArchive;
	
	private Integer maxShownPosition = -1; // Used to indicate the last shown element in the list
	
	public AnnounceArrayAdaptor(Context ctx,ArrayList<Integer> articleIdList,boolean inArchive) {
		// TODO Auto-generated constructor stub
		super(ctx, R.layout.announce_list_item, articleIdList);
		parent = ctx;
		this.inArchive = inArchive;
		db = new DatabaseHandler(ctx).open();
		initializeLists();
		db.close();
	}
	
	public void initializeLists(){
		titleList = new ArrayList<String>();
		dateList = new ArrayList<String>();
		seenList = new ArrayList<Integer>();
		favoriteList = new ArrayList<Integer>();
		
		Cursor cursor;
		if(!inArchive){
			cursor = db.anouncementHandler.getAll();
		}
		else{
			cursor = db.anouncementHandler.getAllArchived();
		}
		if (cursor.moveToFirst()){
			titleList.add(cursor.getString(1));
			dateList.add(cursor.getString(2));
			seenList.add(cursor.getInt(5));
			favoriteList.add(cursor.getInt(6));
		
			if(!inArchive){
				for(int i=0;i<Commons.ANNOUNCE_ENTRY_COUNT-1;i++){
					if(cursor.moveToNext()){
						titleList.add(cursor.getString(1));
						dateList.add(cursor.getString(2));
						seenList.add(cursor.getInt(5));
						favoriteList.add(cursor.getInt(6));
					}
					else
						break;
				}
			}
			else{
				while(cursor.moveToNext()){
					titleList.add(cursor.getString(1));
					dateList.add(cursor.getString(2));
					seenList.add(cursor.getInt(5));
					favoriteList.add(cursor.getInt(6));
				}
			}
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null){
			LayoutInflater inflater = (LayoutInflater) this.parent
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.announce_list_item, parent, false);
		}
		
		TextView titleView = (TextView) convertView.findViewById(R.id.labelTitle);
		TextView dateView = (TextView) convertView.findViewById(R.id.labelDate);
		
		ImageView seenTag = (ImageView) convertView.findViewById(R.id.new_tag);
		ImageView favoriteTag = (ImageView) convertView.findViewById(R.id.favorite_tag);
		
		LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.linear_layout);
		
		
		titleView.setText(titleList.get(position));
		dateView.setText(dateList.get(position));
		
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)linearLayout.getLayoutParams();		   
		if(seenList.get(position)==0){
			seenTag.setVisibility(View.VISIBLE);
			params.setMargins(10, 7, 0, 0);
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
		
		if(position > maxShownPosition){
			maxShownPosition = position;
			TranslateAnimation slide = new TranslateAnimation(0, 0,	400, 0);    	    
			slide.setDuration(400);
			slide.setInterpolator(this.parent,android.R.anim.decelerate_interpolator);
		    slide.setFillAfter(true);   
		    convertView.startAnimation(slide); 
		}
		
		return convertView;
	}
	
	private static void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d("DatabaseHandler",message);
	}

}
