package mrhs.jamaapp.inr.articles;

import java.util.ArrayList;

import mrhs.jamaapp.inr.Commons;
import mrhs.jamaapp.inr.R;
import mrhs.jamaapp.inr.SdCardHandler;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
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

public class ArticleListArrayAdaptor extends ArrayAdapter<Integer> {

private static final boolean LOCAL_SHOW_LOG = true;
	
	private String articleType;
	private Context context;
	private ArticleFragment parent;
	
	private SdCardHandler sdHandler;
	
	private boolean showType;
	private boolean inArchive;
	
	private String[] tabs = { "دین و دعوت", "اندیشه","اهل سنت","فرهنگ","سیاسی","اجتماعی","تاریخ","ادب و هنر"};
	
	private ArrayList<String> titleList,writerList,dateList,textList,typeList,indexImgAdr;
	private ArrayList<Integer> seenList,favoriteList;
	
	private Integer maxShownPosition = -1; // Used to indicate the last shown element in the list
	
	public ArticleListArrayAdaptor(Context ctx,ArrayList<Integer> articleIdList,boolean showtype,
			boolean inArchive,ArticleFragment parent,SdCardHandler sd) {
		// TODO Auto-generated constructor stub
		super(ctx, R.layout.article_list_item, articleIdList);
		this.parent = parent;
		this.articleType = parent.type;
		sdHandler = sd;
		showType = showtype;
		this.inArchive = inArchive;
		context = ctx;
		initializeLists();		
	}
	
	public void initializeLists(){
		titleList = new ArrayList<String>();
		writerList = new ArrayList<String>();
		dateList = new ArrayList<String>();
		textList = new ArrayList<String>();
		seenList = new ArrayList<Integer>();
		favoriteList = new ArrayList<Integer>();
		indexImgAdr = new ArrayList<String>();
		
		if(showType)
			typeList = new ArrayList<String>();
		
		Cursor cursor;
		if(!inArchive){
			if(!showType)
				cursor = parent.db.articleHandler.getAllByType(articleType);
			else
				cursor = parent.db.articleHandler.getAll();
		}
		else{
			cursor = parent.db.articleHandler.getAllArchived();
		}
		
		
		if (cursor.moveToFirst()){
			titleList.add(cursor.getString(1));
			writerList.add(cursor.getString(5));
			dateList.add(cursor.getString(2));
			textList.add(cursor.getString(3));
			seenList.add(cursor.getInt(10));
			favoriteList.add(cursor.getInt(11));
			indexImgAdr.add(cursor.getString(4));
			
			if(showType)
				typeList.add(cursor.getString(6));
			
			if(!inArchive){
				for(int i=0;i<Commons.ARTICLE_ENTRY_COUNT-1;i++){
					if(cursor.moveToNext()){
						titleList.add(cursor.getString(1));
						writerList.add(cursor.getString(5));
						dateList.add(cursor.getString(2));
						textList.add(cursor.getString(3));
						seenList.add(cursor.getInt(10));
						favoriteList.add(cursor.getInt(11));
						indexImgAdr.add(cursor.getString(4));
						if(showType)
							typeList.add(cursor.getString(6));
					}
					else
						break;
				}
			}
			else{
				while(cursor.moveToNext()){
					titleList.add(cursor.getString(1));
					writerList.add(cursor.getString(5));
					dateList.add(cursor.getString(2));
					textList.add(cursor.getString(3));
					seenList.add(cursor.getInt(10));
					favoriteList.add(cursor.getInt(11));
					indexImgAdr.add(cursor.getString(4));
					if(showType)
						typeList.add(cursor.getString(6));
				}
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
		TextView typeView = (TextView) convertView.findViewById(R.id.labelType);
		
		ImageView seenTag = (ImageView) convertView.findViewById(R.id.new_tag);
		ImageView favoriteTag = (ImageView) convertView.findViewById(R.id.favorite_tag);
		ImageView indexImgView = (ImageView) convertView.findViewById(R.id.indexImgView);
		
		LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.linear_layout);
		
		Bitmap bitmap = sdHandler.getImage(indexImgAdr.get(position));
		if(bitmap != null)
			indexImgView.setImageBitmap(bitmap);
		else{
			indexImgView.setImageResource(R.drawable.ic_launcher);
		}
		
		if(showType){
			typeView.setVisibility(View.VISIBLE);
			typeView.setText(tabs[Integer.parseInt(typeList.get(position))]);
		}
		else
			typeView.setVisibility(View.GONE);
			
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
			slide.setInterpolator(context,android.R.anim.decelerate_interpolator);
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
