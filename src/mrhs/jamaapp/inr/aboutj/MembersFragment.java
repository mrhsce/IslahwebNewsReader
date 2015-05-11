package mrhs.jamaapp.inr.aboutj;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import mrhs.jamaapp.inr.Commons;
import mrhs.jamaapp.inr.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class MembersFragment extends Fragment {
	private static final boolean LOCAL_SHOW_LOG = false;
	
	
TextView txtView;
ImageView imgView;
String membersXml;	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		if(savedInstanceState != null){
			
		}
	}	
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
	}

	
	
		
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_members, container, false); 
        membersXml = getMembersXmlFromAsset();
        Typeface mitraFont = Typeface.createFromAsset(getActivity().getAssets(),"fonts/mitra.ttf");
        txtView = (TextView) rootView.findViewById(R.id.textView);
        txtView.setText(getName("عبدالرحمن پیرانی"));
        txtView.setTypeface(mitraFont);
        
        imgView = (ImageView) rootView.findViewById(R.id.imgView);
        imgView.setImageBitmap(getBitmap("عبدالرحمن پیرانی"));
        
        return rootView;
    }
	
	
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser){
			if(((AboutJamaatMainActivity)getActivity()).optionMenu != null){
				((AboutJamaatMainActivity)getActivity()).optionMenu.findItem(R.id.menu_spinner).setVisible(true);
				setUpSpinner();
			}
		}
	}
	
	public void setUpSpinner(){
		Spinner spinner = (Spinner) ((AboutJamaatMainActivity)getActivity()).optionMenu.findItem(R.id.menu_spinner).getActionView();
		final SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(getActivity().getActionBar()
		        .getThemedContext(), R.array.fragment_member_array, android.R.layout.simple_dropdown_item_1line); //  create the adapter from a StringArray
		spinner.setAdapter(mSpinnerAdapter); // set the adapter
		
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int index, long arg3) {
				// TODO Auto-generated method stub
				txtView.setText(getName(mSpinnerAdapter.getItem(index).toString()));
				imgView.setImageBitmap(getBitmap(mSpinnerAdapter.getItem(index).toString()));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public String getName(String name){
		Document doc=Jsoup.parse(membersXml);
		Elements links=doc.select("member[name="+name+"]");
		String memberName = links.select("member").attr("name");			
		
		return memberName;
	}
	
	public Bitmap getBitmap(String name){
		Document doc=Jsoup.parse(membersXml);
		Elements links=doc.select("member[name="+name+"] picture");
		String imgAddr = links.text();
		try {
			return BitmapFactory.decodeStream(getActivity().getAssets().open("aboutj/members/images/"+imgAddr));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getMembersXmlFromAsset(){
		BufferedReader reader = null;
		StringBuilder returnString = new StringBuilder();
		try {
		    reader = new BufferedReader(
		        new InputStreamReader(getActivity().getAssets().open("aboutj/members/members.xml"), "UTF-8"));

		    // do reading, usually loop until end of file reading  
		    String line = "";
	        while ((line = reader.readLine()) != null) {
	            returnString.append(line);
	        }
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    if (reader != null) {
		         try {
		             reader.close();
		         } catch (IOException e) {
		             e.printStackTrace();
		         }
		    }
		}
		return returnString.toString();
	}
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}
}
