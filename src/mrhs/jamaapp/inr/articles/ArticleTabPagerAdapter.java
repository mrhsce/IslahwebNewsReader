package mrhs.jamaapp.inr.articles;

import mrhs.jamaapp.inr.Commons;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class ArticleTabPagerAdapter extends FragmentPagerAdapter {
	private static final boolean LOCAL_SHOW_LOG = true;
	
	public ArticleTabPagerAdapter(FragmentManager fm) {
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
			tmp.putBoolean("inArchive", false);
			ArticleFragment tmpFragment = new ArticleFragment();
			log("set argument");
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}
		case 1:{
			Bundle tmp = new Bundle();
			tmp.putString("type", Commons.ARTICLE_TYPE_DINODAVAT);
			tmp.putBoolean("inArchive", false);
			ArticleFragment tmpFragment = new ArticleFragment();
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}			
		case 2:
		{
			Bundle tmp = new Bundle();
			tmp.putString("type", Commons.ARTICLE_TYPE_ANDISHE);
			tmp.putBoolean("inArchive", false);
			ArticleFragment tmpFragment = new ArticleFragment();
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}	
		case 3:
		{
			Bundle tmp = new Bundle();
			tmp.putString("type", Commons.ARTICLE_TYPE_AHLESONNAT);
			tmp.putBoolean("inArchive", false);
			ArticleFragment tmpFragment = new ArticleFragment();
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}
		case 4:
		{
			Bundle tmp = new Bundle();
			tmp.putString("type", Commons.ARTICLE_TYPE_FARHANG);
			tmp.putBoolean("inArchive", false);
			ArticleFragment tmpFragment = new ArticleFragment();
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}	
		case 5:
		{
			Bundle tmp = new Bundle();
			tmp.putString("type", Commons.ARTICLE_TYPE_SIASI);
			tmp.putBoolean("inArchive", false);
			ArticleFragment tmpFragment = new ArticleFragment();
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}	
		case 6:
		{
			Bundle tmp = new Bundle();
			tmp.putString("type", Commons.ARTICLE_TYPE_EJTEMAEI);
			tmp.putBoolean("inArchive", false);
			ArticleFragment tmpFragment = new ArticleFragment();
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}	
		case 7:
		{
			Bundle tmp = new Bundle();
			tmp.putString("type", Commons.ARTICLE_TYPE_TARIKH);
			tmp.putBoolean("inArchive", false);
			ArticleFragment tmpFragment = new ArticleFragment();
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}	
		case 8:
		{
			Bundle tmp = new Bundle();
			tmp.putString("type", Commons.ARTICLE_TYPE_ADABOHONAR);
			tmp.putBoolean("inArchive", false);
			ArticleFragment tmpFragment = new ArticleFragment();
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}	
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 9;
	}
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}

}
