package lp4.untref.daparadarse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.ViewGroup;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener, Fragment_Chats.OnHeadlineSelectedListener{//, LoginFragment.OnSetIdListener {
    private ViewPager mViewPager;

    FragmentManager fm;
    public Fragment miFragment;
    String facebookID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);

        mViewPager.setOnPageChangeListener(this);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab = actionBar.newTab().setText("Perfil").setTabListener(this);
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("Elegir").setTabListener(this);
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("Chats").setTabListener(this);
        actionBar.addTab(tab);

    }


    //implements on pager selected
    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {
        getSupportActionBar().setSelectedNavigationItem(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    //implements tab listener
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onArticleSelected(int position) {

    }

    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }


        public Fragment getItem(int arg0) {
            switch (arg0) {
                case 0:
                    return new LoginFragment();
                case 1:
                    return new Fragment_Elegir();
                case 2:
                    return new Fragment_Chats();
                default:
                    return null;
            }
        }

        public int getCount() {
            return 3;
        }
        //TODO: Probar y borrar si no se usa
        @Override
        public int getItemPosition(Object object) {
            if (object instanceof Fragment_Elegir &&
                    miFragment instanceof Fragment_Elegir) {
                return POSITION_NONE;
            }
            return POSITION_UNCHANGED;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }
    }
/**
    @Override
    public void OnSetId(String userID) {

        facebookID = userID;
        Fragment_Elegir fe;
        fe = (Fragment_Elegir) getSupportFragmentManager().getFragments().get(1);
        fe.OnSetId(facebookID);
    }
**/
    public String getFacebookID() {
        return facebookID;
    }
}
