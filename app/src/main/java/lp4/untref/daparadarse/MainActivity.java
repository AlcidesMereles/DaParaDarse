package lp4.untref.daparadarse;

import java.util.HashMap;
import java.util.Map;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import demo.pantallasTinder;

public class MainActivity extends ActionBarActivity {
    // Create, automatically open (if applicable), save, and restore the
    // Active Session in a way that is similar to Android UI lifecycles.
    private UiLifecycleHelper uiHelper;
    private View otherView;
    private static final String TAG = "MainActivity";
    public Button botonGaleria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set View that should be visible after log-in invisible initially
        otherView = (View) findViewById(R.id.other_views);
        otherView.setVisibility(View.GONE);
        // To maintain FB Login session
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        botonGaleria = (Button) findViewById(R.id.button);

    }

    public void irAGaleria(View view){

        Intent intent = new Intent(this, pantallasTinder.class);
        startActivity(intent);
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
        final TextView name = (TextView) findViewById(R.id.name);
        // When Session is successfully opened (User logged-in)
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
        // make request to the /me API to get Graph user
            Request.newMeRequest(session, new Request.GraphUserCallback() {
                // callback after Graph API response with user
                // object
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {
                        String nombre,apellido,edad,facebookID,sexo;
                        // Set view visibility to true
                        otherView.setVisibility(View.VISIBLE);
                        // Set User name
                        name.setText("Bienvenido " + user.getName());
                        nombre = user.getFirstName();
                        apellido = user.getLastName();
                        edad = user.getId();//Solo pruebo.
                        facebookID = user.getId();
                        sexo = user.getProperty("gender").toString().equals("male")?"hombre":"mujer";
                        Map<String,String> map = new HashMap<String, String>();
                        map.put("nombre",nombre);
                        map.put("apellido",apellido);
                        map.put("edad",edad);
                        map.put("facebookID",facebookID);
                        map.put("sexo",sexo);
                        TareaEnvioDeDatos envioDeDatos = new TareaEnvioDeDatos ();
                        envioDeDatos.execute(map);
                    }
                }
            }).executeAsync();
        }else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
            otherView.setVisibility(View.GONE);
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
}