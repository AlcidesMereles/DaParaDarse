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

//SINUSAR
import java.util.List;

import usadosparapruebas.FirstPageFragmentListener;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener, LoginFragment.OnSetIdListener {
    private ViewPager mViewPager;

    FragmentManager fm;
    //SINUSAR
    FragmentTransaction ft;
    public Fragment mFragmentAtPos0;
    String facebookID;


    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        fm = getSupportFragmentManager();

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);

        mViewPager.setOnPageChangeListener(this);

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

    public class PagerAdapter extends FragmentPagerAdapter {
        private final FragmentManager mFragmentManager;
        FirstPageListener listener = new FirstPageListener();

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentManager = fm;
        }

        //SINUSAR
        private final class FirstPageListener implements

                FirstPageFragmentListener {

            public void onSwitchToNextFragment(String id) {
                if (mFragmentManager != null) {
                    mFragmentManager.beginTransaction().remove(mFragmentAtPos0)
                            .commit();
                }
                if (mFragmentAtPos0 instanceof Fragment_Elegir) {
                    mFragmentAtPos0 = new Fragment_Elegir();
                    Bundle myBundle = new Bundle();
                    myBundle.putString("facebookID", facebookID);
                    mFragmentAtPos0.setArguments(myBundle);
                } else { // Instance of NextFragment
                    mFragmentAtPos0 = new Fragment_Elegir();
                }
                notifyDataSetChanged();
            }
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

        //SINUSAR
        @Override
        public int getItemPosition(Object object) {
            if (object instanceof Fragment_Elegir &&
                    mFragmentAtPos0 instanceof Fragment_Elegir) {
                return POSITION_NONE;
            }
            return POSITION_UNCHANGED;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }
    }

    @Override
    public void OnSetId(String userID) {

        facebookID = userID;
        Fragment_Elegir fe;
        fe = (Fragment_Elegir) getSupportFragmentManager().getFragments().get(1);
        fe.OnSetId(facebookID);
    }
    //TODO
//        Fragment_Elegir fElegir = new Fragment_Elegir();
//        Bundle args = new Bundle();
//        args.putString("facebookID", id);
//        fElegir.setArguments(args);
//        fm = getSupportFragmentManager().beginTransaction().remove();
//        ft = fm.beginTransaction();
//        List<Fragment> listaFragmentos = fm.getFragments();
//        for (int i = 0; i < listaFragmentos.size(); i++) {
//            if (listaFragmentos.get(i) instanceof Fragment_Elegir) {
//                actionBar.getTabAt(1).
//                        ft.replace(mViewPager.get, fElegir, "fragment_elegir");
//                ft.commit();
//            }
//        }

    public String getFacebookID() {
        return facebookID;
    }
}
