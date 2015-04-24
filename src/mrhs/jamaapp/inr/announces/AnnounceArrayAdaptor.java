package mrhs.jamaapp.inr.announces;

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

public class AnnounceArrayAdaptor extends ArrayAdapter<Integer>{

private static final boolean LOCAL_SHOW_LOG = true;
	
	private AnnounceMainActivity parent;
	
	private ArrayList<String> titleList,dateList;
	
	public AnnounceArrayAdaptor(AnnounceMainActivity ctx,ArrayList<Integer> articleIdList) {
		// TODO Auto-generated constructor stub
		super(ctx, R.layout.announce_list_item, articleIdList);
		parent = ctx;
		initializeLists();		
	}
	
	public void initializeLists(){
		titleList = new ArrayList<String>();
		dateList = new ArrayList<String>();
		
		Cursor cursor = parent.db.anouncementHandler.getAll();
		if (cursor.moveToFirst()){
			titleList.add(cursor.getString(1));
			dateList.add(cursor.getString(2));
			for(int i=0;i<Commons.ANNOUNCE_ENTRY_COUNT-1;i++){
				if(cursor.moveToNext())
				{
					titleList.add(cursor.getString(1));
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
			LayoutInflater inflater = (LayoutInflater) this.parent
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.announce_list_item, parent, false);
		}
		
		TextView titleView = (TextView) convertView.findViewById(R.id.labelTitle);
		TextView dateView = (TextView) convertView.findViewById(R.id.labelDate);
		
		
		titleView.setText(titleList.get(position));
		dateView.setText(dateList.get(position));
		
		
		return convertView;
	}
	
	private static void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d("DatabaseHandler",message);
	}

}
