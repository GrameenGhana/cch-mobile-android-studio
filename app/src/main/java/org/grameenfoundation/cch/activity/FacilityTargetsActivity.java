package org.grameenfoundation.cch.activity;


import org.digitalcampus.mobile.learningGF.R;
import org.digitalcampus.oppia.application.DbHelper;
import org.digitalcampus.oppia.service.TrackerService;
import org.grameenfoundation.poc.BaseActivity;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;




public class FacilityTargetsActivity extends BaseActivity implements ActionBar.TabListener, OnSharedPreferenceChangeListener{
	 private DbHelper dbh;
	 private static Context mContext;

	 
	 SectionsPagerAdapter mSectionsPagerAdapter;
	public static String current_month;
	
	private static final String EVENT_PLANNER_ID = "Event Planner";
	public static final String TAG = FacilityTargetsActivity.class.getSimpleName();
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
	private SharedPreferences prefs;
	private DbHelper db;
	private JSONObject data;
@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_new_event_planner);
	    mContext = FacilityTargetsActivity.this;
        dbh = new DbHelper(mContext);

    getSupportActionBar().setNavigationMode(getSupportActionBar().NAVIGATION_MODE_TABS);
    getSupportActionBar().setTitle("Planner");
    getSupportActionBar().setSubtitle("Child Health Facility Targets");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager
        .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                        getActionBar().setSelectedNavigationItem(position);
                }
        });
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            getSupportActionBar().addTab(getSupportActionBar().newTab()
                    .setText(mSectionsPagerAdapter.getPageTitle(i))
                    .setTabListener(this));
        }
       
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
		PreferenceManager.setDefaultValues(this, R.xml.prefs, false);
		
		Intent service = new Intent(this, TrackerService.class);
		Bundle tb = new Bundle();
		tb.putBoolean("backgroundData", true);
		service.putExtras(tb);
		this.startService(service);
		try 
	    {
			if (!(getIntent().getStringExtra("FRAGMENT_IDX")).isEmpty()) {	
				int page = Integer.parseInt(getIntent().getStringExtra("FRAGMENT_IDX"));
				mViewPager.setCurrentItem(page, true);	
			}				
		} catch (NullPointerException e) { Log.e(TAG,"Trying to switch panes failed :("); }	
        
}



    public class SectionsPagerAdapter extends FragmentPagerAdapter {


		public SectionsPagerAdapter(FragmentManager fm) {
                 super(fm);
         }

         @Override
         public Fragment getItem(int position) {
                Fragment fragment = null;
                 if(position==0 ){
                      //  fragment= new AgeGroupsFacilityTargetsActivity();
                 }else if(position==1){
                // fragment= new SchoolHealthFacilityTargetActivity();
                 }
                 return fragment;
         }

         @Override
         public int getCount() {
                 return 2;
         }

         @Override
         public CharSequence getPageTitle(int position) {
                 db=new DbHelper(FacilityTargetsActivity.this);
     			
                 switch (position) {
                         case 0:
                                 return "ANTIGENS";
                         case 1:
                                 return "SCHOOL HEALTH";
                        
                 }
                 return null;
         }
 }

	 @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if ((keyCode == KeyEvent.KEYCODE_BACK)) {
				FacilityTargetsActivity.this.finish();
				
			} 
			
		    return true; 
		}
	  public void saveToLog(Long starttime) 
		{
		  Long endtime = System.currentTimeMillis(); 
		  data=new JSONObject();
		    try {
		    	data.put("page", "Target Setting");
		    	data.put("ver", db.getVersionNumber(FacilityTargetsActivity.this));
		    	data.put("battery", db.getBatteryStatus(FacilityTargetsActivity.this));
		    	data.put("device", db.getDeviceName());
				data.put("imei", db.getDeviceImei(FacilityTargetsActivity.this));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		  dbh.insertCCHLog(EVENT_PLANNER_ID, data.toString(), starttime.toString(), endtime.toString());	
		}
	 public void onDestroy(){
		 super.onDestroy();
		 Long starttime=System.currentTimeMillis();  
		// saveToLog(starttime); 
	 }


	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		Log.d(TAG, key + " changed");
		if(key.equalsIgnoreCase(getString(R.string.prefs_server))){
			Editor editor = sharedPreferences.edit();
			if(!sharedPreferences.getString(getString(R.string.prefs_server), "").endsWith("/")){
				String newServer = sharedPreferences.getString(getString(R.string.prefs_server), "").trim()+"/";
				editor.putString(getString(R.string.prefs_server), newServer);
		    	editor.commit();
			}
		}
		if(key.equalsIgnoreCase(getString(R.string.prefs_points)) || key.equalsIgnoreCase(getString(R.string.prefs_badges))){
			supportInvalidateOptionsMenu();
		}
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
		  mViewPager.setCurrentItem(tab.getPosition());
		
	}
	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
