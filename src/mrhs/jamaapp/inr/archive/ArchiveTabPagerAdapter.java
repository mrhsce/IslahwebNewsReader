package mrhs.jamaapp.inr.archive;

import mrhs.jamaapp.inr.articles.ArticleFragment;
import mrhs.jamaapp.inr.news.NewsFragment;
import android.os.Bundle;
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
			
			Bundle tmp = new Bundle();
			tmp.putString("type", "");
			tmp.putBoolean("inArchive", true);
			NewsFragment tmpFragment = new NewsFragment();
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}			
		case 1:
		{
			Bundle tmp = new Bundle();
			tmp.putString("type", "");
			tmp.putBoolean("inArchive", true);
			ArticleFragment tmpFragment = new ArticleFragment();
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}	
		case 2:
		{
			Bundle tmp = new Bundle();
			tmp.putBoolean("inArchive", true);
			InterviewFragment tmpFragment = new InterviewFragment();
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}
		case 3:
		{
			Bundle tmp = new Bundle();
			tmp.putBoolean("inArchive", true);
			AnnounceFragment tmpFragment = new AnnounceFragment();
			tmpFragment.setArguments(tmp);
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
