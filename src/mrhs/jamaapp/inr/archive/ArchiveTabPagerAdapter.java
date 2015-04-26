package mrhs.jamaapp.inr.archive;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ArchiveTabPagerAdapter extends FragmentPagerAdapter {

	public ArchiveTabPagerAdapter(FragmentManager fm) {
		// TODO Auto-generated constructor stub
		super(fm);
	}
	
	@Override
	public Fragment getItem(int index) {
		// TODO Auto-generated method stub
		switch(index){
		case 0:{
			
			return null;
		}			
		case 1:
		{
			return null;
		}	
		case 2:
		{
			return null;
		}
		case 3:
		{
			return null;
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
