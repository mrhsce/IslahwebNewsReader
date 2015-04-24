package mrhs.jamaapp.inr.articles;

import mrhs.jamaapp.inr.main.Commons;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ArticleTabPagerAdapter extends FragmentPagerAdapter {

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
			tmp.putString("type", Commons.ARTICLE_TYPE_DINODAVAT);
			ArticleFragment tmpFragment = new ArticleFragment();
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}			
		case 1:
		{
			Bundle tmp = new Bundle();
			tmp.putString("type", Commons.ARTICLE_TYPE_ANDISHE);
			ArticleFragment tmpFragment = new ArticleFragment();
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}	
		case 2:
		{
			Bundle tmp = new Bundle();
			tmp.putString("type", Commons.ARTICLE_TYPE_AHLESONNAT);
			ArticleFragment tmpFragment = new ArticleFragment();
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}
		case 3:
		{
			Bundle tmp = new Bundle();
			tmp.putString("type", Commons.ARTICLE_TYPE_FARHANG);
			ArticleFragment tmpFragment = new ArticleFragment();
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}	
		case 4:
		{
			Bundle tmp = new Bundle();
			tmp.putString("type", Commons.ARTICLE_TYPE_SIASI);
			ArticleFragment tmpFragment = new ArticleFragment();
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}	
		case 5:
		{
			Bundle tmp = new Bundle();
			tmp.putString("type", Commons.ARTICLE_TYPE_EJTEMAEI);
			ArticleFragment tmpFragment = new ArticleFragment();
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}	
		case 6:
		{
			Bundle tmp = new Bundle();
			tmp.putString("type", Commons.ARTICLE_TYPE_TARIKH);
			ArticleFragment tmpFragment = new ArticleFragment();
			tmpFragment.setArguments(tmp);
			return tmpFragment;
		}	
		case 7:
		{
			Bundle tmp = new Bundle();
			tmp.putString("type", Commons.ARTICLE_TYPE_ADABOHONAR);
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
		return 8;
	}

}
