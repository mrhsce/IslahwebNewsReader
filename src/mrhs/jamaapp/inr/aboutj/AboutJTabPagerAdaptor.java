package mrhs.jamaapp.inr.aboutj;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AboutJTabPagerAdaptor extends FragmentPagerAdapter {

	public AboutJTabPagerAdaptor(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public Fragment getItem(int index) {
		// TODO Auto-generated method stub
		
		switch(index){
		case 0:{
			Bundle tmp = new Bundle();
			WhoWeAreFragment tmpFragment = new WhoWeAreFragment();
			return tmpFragment;
		}			
		case 1:
		{
			Bundle tmp = new Bundle();
			MaramnamehFragment tmpFragment = new MaramnamehFragment();
			return tmpFragment;
		}	
		case 2:
		{
			Bundle tmp = new Bundle();
			RoykardFragment tmpFragment = new RoykardFragment();
			return tmpFragment;
		}	
		case 3:
		{
			Bundle tmp = new Bundle();
			MembersFragment tmpFragment = new MembersFragment();
			return tmpFragment;
		}	
		}
		return null;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;
	}
	
}
