package mrhs.jamaapp.inr.news;

import mrhs.jamaapp.inr.main.Commons;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter {

	public TabPagerAdapter(FragmentManager fm) {
		// TODO Auto-generated constructor stub
		super(fm);
	}
	
	@Override
	public Fragment getItem(int index) {
		// TODO Auto-generated method stub
		switch(index){
		case 0:{
			Bundle tmp = new Bundle();
			tmp.putString("type", Commons.NEWS_TYPE_JAMAAT);
			NewsFragment tmpFragment = new NewsFragment();
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}			
		case 1:
		{
			Bundle tmp = new Bundle();
			tmp.putString("type", Commons.NEWS_TYPE_ISLAHWEB);
			NewsFragment tmpFragment = new NewsFragment();
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}	
		case 2:
		{
			Bundle tmp = new Bundle();
			tmp.putString("type", Commons.NEWS_TYPE_SPORT);
			NewsFragment tmpFragment = new NewsFragment();
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}	
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

}
