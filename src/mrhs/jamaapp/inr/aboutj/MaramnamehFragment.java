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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class MaramnamehFragment extends Fragment {
	private static final boolean LOCAL_SHOW_LOG = true;
	
	TextView txtView;	
	
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
 
        View rootView = inflater.inflate(R.layout.fragment_maramnameh, container, false); 
        Typeface mitraFont = Typeface.createFromAsset(getActivity().getAssets(),"fonts/mitra.ttf");
        txtView = (TextView) rootView.findViewById(R.id.textView);
        txtView.setText(getTextFromAsset(0));
        txtView.setTypeface(mitraFont);
        
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
		SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(getActivity().getActionBar()
		        .getThemedContext(), R.array.fragment_maramnameh_array, android.R.layout.simple_dropdown_item_1line); //  create the adapter from a StringArray
		spinner.setAdapter(mSpinnerAdapter); // set the adapter
		
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int index, long arg3) {
				// TODO Auto-generated method stub
				txtView.setText(getTextFromAsset(index));				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public String getTextFromAsset(Integer type){
		String addr = "";
		switch(type){
		case 0:
			addr ="madkhal";
			break;
		case 1:
			addr ="resalat jamaat";
			break;
		case 2:
			addr ="maramnameh";
			break;
		case 3:
			addr ="asas1-koliat";
			break;
		case 4:
			addr ="asas2-arkan jamaat";
			break;
		case 5:
			addr ="asas3-ozvegiri";
			break;
		case 6:
			addr ="asas4-mali";
			break;
		case 7:
			addr ="asas5-enhelal";
			break;
		}
		addr = "aboutj/maramnameh/"+addr;
		
		BufferedReader reader = null;
		StringBuilder returnString = new StringBuilder();
		try {
		    reader = new BufferedReader(
		        new InputStreamReader(getActivity().getAssets().open(addr), "UTF-8"));

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
