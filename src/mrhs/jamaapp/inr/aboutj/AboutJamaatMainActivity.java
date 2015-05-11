package mrhs.jamaapp.inr.aboutj;

import mrhs.jamaapp.inr.Commons;
import mrhs.jamaapp.inr.R;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

@SuppressWarnings("deprecation")
public class AboutJamaatMainActivity extends FragmentActivity implements ActionBar.TabListener{

	private static final boolean LOCAL_SHOW_LOG = true;

	private ViewPager viewPager;
    private AboutJTabPagerAdaptor mAdapter;
    public ActionBar actionBar;
    public Menu optionMenu;
    // Tab titles
    private String[] tabs = {"ما کیستیم","اساس نامه", "رویکردها","اعضای شورای مرکزی"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_main);
		// Show the Up button in the action bar.
		// Initilization
		log("Started");
        viewPager = (ViewPager) findViewById(R.id.article_pager);
        setupActionBar();    
        mAdapter = new AboutJTabPagerAdaptor(getSupportFragmentManager());
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
        actionBar.setHomeButtonEnabled(true);
        // Hide the action bar title
        actionBar.setDisplayShowTitleEnabled(false);
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
	public boolean onCreateOptionsMenu(Menu menu) {
		log("onCreateOptionMenu");
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about_jamaat_main, menu);
		optionMenu = menu;
		menu.findItem(R.id.menu_spinner).setVisible(false);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		
		case android.R.id.home:
			onBackPressed();
			
			break;
		}
		
		return true;
	}
	
	private void log(String message){
		if(Commons.SHOW_LOG && LOCAL_SHOW_LOG)
			Log.d(this.getClass().getSimpleName(),message);
	}

}
