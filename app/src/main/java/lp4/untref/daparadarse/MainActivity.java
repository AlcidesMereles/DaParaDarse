package lp4.untref.daparadarse;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";
    public Button botonGaleria;
    public ProgressDialog miProgressDialog;
    // Create, automatically open (if applicable), save, and restore the
    // Active Session in a way that is similar to Android UI lifecycles.
    private UiLifecycleHelper uiHelper;
    private View otherView;
    private String facebookID;
    private String nombre;
    private String apellido;
    private String edad;
    private String sexo;
    // Called when session changes
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state,
                         Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set View that should be visible after log-in invisible initially
        otherView = findViewById(R.id.other_views);
        otherView.setVisibility(View.GONE);
        // To maintain FB Login session
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        botonGaleria = (Button) findViewById(R.id.button);
        findViewById(R.id.botonPantalla1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irAGaleria();
                //startActivity(new Intent(MainActivity.this, pantallasTinder.class));
            }
        });

    }

    public void irAGaleria(){

        Intent intent;
        intent = new Intent(MainActivity.this, demo.pantallasTinder.class);
        startActivity(intent);
    }

    // When session is changed, this method is called from callback method
    private void onSessionStateChange(Session session, SessionState state,
                                      Exception exception) {
        final TextView name = (TextView) findViewById(R.id.name);
        final TextView gender = (TextView) findViewById(R.id.gender);
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
                        // Set view visibility to true
                        otherView.setVisibility(View.VISIBLE);
                        // Set User name
                        name.setText("Bienvenido " + user.getName());
                        nombre = user.getFirstName();
                        apellido = user.getLastName();
                        edad = user.getId();//Solo pruebo.
                        facebookID = user.getId();
                        sexo = user.getProperty("gender").toString();
                        gender.setText("Your gender:" + user.getProperty("gender").toString());

                        Thread nt = new Thread() {
                            @Override
                            public void run() {

                                try {
                                    final String res;

                                    res = enviarPost(facebookID,nombre,apellido,edad,sexo);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        nt.start();
                    }
                }
            }).executeAsync();
        } else if (state.isClosed()) {
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

    public String enviarPost(String facebookID,String nombre, String apellido, String edad,String sexo) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(
                "http://www.daparadarse.site88.net/Android/PutData.php");
        HttpResponse response = null;
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>(3);
            params.add(new BasicNameValuePair("id", facebookID));
            params.add(new BasicNameValuePair("nombre", nombre));
            params.add(new BasicNameValuePair("apellido", apellido));
            params.add(new BasicNameValuePair("edad", edad));
            params.add(new BasicNameValuePair("sexo", sexo));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            response = httpClient.execute(httpPost, localContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }
}