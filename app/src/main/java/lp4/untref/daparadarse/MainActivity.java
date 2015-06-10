package lp4.untref.daparadarse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

/**
 * Created by Spider on 07/06/2015.
 */
public class MainActivity extends ActionBarActivity implements ActionBar.TabListener, ViewPager.OnPageChangeListener, Fragment_Perfil.OnHeadlineSelectedListener {
    private ViewPager mViewPager;
    private String nombre, apellido, edad, facebookID, sexo, mujeres, hombres, ciudad, provincia, pais;
    private LoginFragment loginFragment;
    private static final String TAG = "MainActivity";
    private UiLifecycleHelper uiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // To maintain FB Login session
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        loginFragment = new LoginFragment();
        Bundle argumentos = new Bundle();
//        argumentos.putString("nombre", nombre);
//        argumentos.putString("apellido", apellido);
//        argumentos.putString("edad", edad);
//        argumentos.putString("sexo", sexo);
//        argumentos.putString("ciudad", ciudad);
//        argumentos.putString("provincia", provincia);
//        argumentos.putString("pais", pais);
//        argumentos.putString("mujeres", mujeres);
//        argumentos.putString("facebookID", facebookID);
//        loginFragment.setArguments(argumentos);

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(adapter);

        mViewPager.setOnPageChangeListener(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab = actionBar.newTab().setText("Perfil").setTabListener(this);
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("Elegir").setTabListener(this);
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("Chats").setTabListener(this);
        actionBar.addTab(tab);

/**
 if(actionBar.getTabCount()==1){
 findViewById(R.id.pager).setOnClickListener(new View.OnClickListener() {
@Override public void onClick(View v) {
startActivity(new Intent(MainActivity.this, demo.pantallasTinder.class));
}
});
 }
 **/

    }

    // Called when session changes
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    // When session is changed, this method is called from callback method
    private void onSessionStateChange(Session session, SessionState state,
                                      Exception exception) {

        // When Session is successfully opened (User logged-in)
        if (state.isOpened()) {
            LoginFragment fragmentActual = (LoginFragment) getSupportFragmentManager().getFragments().get(0);
            fragmentActual.setViewVisible(View.VISIBLE);
            Log.i(TAG, "Logged in...");
            // make request to the /me API to get Graph user
            Request.newMeRequest(session, new Request.GraphUserCallback() {
                // callback after Graph API response with user
// object
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    //otherView.setVisibility(View.VISIBLE);
                    if (user != null) {
                        nombre = user.getFirstName();
                        apellido = user.getLastName();
                        facebookID = user.getId();
                        sexo = user.getProperty("gender").toString()
                                .equals("male") ? "hombre" : "mujer";
                        if (1 == 1) {

                        }
////                            LoginFragment fragment = new LoginFragment();
////                            Bundle args = new Bundle();
////                            args.putString("nombre", nombre);
////                            args.putString("apellido", apellido);
////                            args.putString("facebookID", facebookID);
////                            args.putString("sexo", sexo);
////                            fragment.setArguments(args);
////                            transaction.add(R.id.contenedor,
////                                    fragment, "lg_fragment");
////
////                            // Paso 4: Confirmar el cambio
////                            transaction.commit();
////
////                            // Set User name
////                            nombreCompleto = user.getName();
////
////
//////                                        }
//////
//////
//////
////                                    }
////                                });
//                        }
////                    }
                    }
                }
            }).executeAsync();

        } else if (state.isClosed()) {
            LoginFragment fragmentActual = (LoginFragment) getSupportFragmentManager().getFragments().get(0);
            fragmentActual.setViewVisible(View.INVISIBLE);
            Log.i(TAG, "Logged out...");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uiHelper.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "OnActivityResult...");
    }

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
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
//        Fragment fragment = getSupportFragmentManager().getFragments().get(0);
//        fragmentTransaction.hide(fragment);
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
                    return loginFragment;
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

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }
    }
}
