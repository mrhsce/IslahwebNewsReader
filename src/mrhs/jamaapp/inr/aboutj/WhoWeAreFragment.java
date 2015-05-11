package mrhs.jamaapp.inr.aboutj;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import mrhs.jamaapp.inr.Commons;
import mrhs.jamaapp.inr.R;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WhoWeAreFragment extends Fragment {
	private static final boolean LOCAL_SHOW_LOG = false;
	
	
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
 
        View rootView = inflater.inflate(R.layout.fragment_whoweare, container, false);
        Typeface mitraFont = Typeface.createFromAsset(getActivity().getAssets(),"fonts/mitra.ttf");
        TextView txtView = (TextView) rootView.findViewById(R.id.textView);
        txtView.setText(getText());
        txtView.setTypeface(mitraFont);
        return rootView;
    }
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser){
			// Here the action bar should be set up
			if(((AboutJamaatMainActivity)getActivity()).optionMenu != null)
			((AboutJamaatMainActivity)getActivity()).optionMenu.findItem(R.id.menu_spinner).setVisible(false);
		}
	}
	
	public String getText(){
		BufferedReader reader = null;
		StringBuilder returnString = new StringBuilder();
		try {
		    reader = new BufferedReader(
		        new InputStreamReader(getActivity().getAssets().open("aboutj/whoWeAre"), "UTF-8"));

		    // do reading, usually loop until end of file reading  
		    String line = "";
	        while ((line = reader.readLine()) != null) {
	            returnString.append(line+"\n");
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
