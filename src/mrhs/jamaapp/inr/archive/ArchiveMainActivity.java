package mrhs.jamaapp.inr.archive;

import mrhs.jamaapp.inr.R;
import mrhs.jamaapp.inr.R.layout;
import mrhs.jamaapp.inr.R.menu;
import mrhs.jamaapp.inr.main.Commons;
import mrhs.jamaapp.inr.main.MainActivity;
import mrhs.jamaapp.inr.news.NewsTabPagerAdapter;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

@SuppressWarnings("deprecation")
public class ArchiveMainActivity extends FragmentActivity implements ActionBar.TabListener {
	private static final boolean LOCAL_SHOW_LOG = true;

	private ViewPager viewPager;
    private ArchiveTabPagerAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = { "آرشیو اخبار", "آرشیو مقالات", "آرشیو بیانیه ها","آرشیو مصاحبه ها" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_main);
		// Show the Up button in the action bar.
		// Initilization
		log("Started");
        viewPager = (ViewPager) findViewById(R.id.pager);
        setupActionBar();    
        mAdapter = new ArchiveTabPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);       
       
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this)); // tab name is not visible
        }
        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        	 
            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }
         
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
         
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });   
        
	}
	
	private void setupActionBar(){

        actionBar = getActionBar(); 
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(arg0.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case android.R.id.home:
			this.finish();
			break;
//		case R.id.action_refresh:
//			refresh
//			
//			break;
		}
		
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		log("onCreateOptionMenu");
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.archive_main, menu);		
		return true;
	}
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}

}
