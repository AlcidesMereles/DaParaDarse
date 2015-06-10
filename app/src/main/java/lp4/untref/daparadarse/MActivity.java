package lp4.untref.daparadarse;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

import java.util.HashMap;
import java.util.Map;

public class MActivity extends ActionBarActivity {
//    // Create, automatically open (if applicable), save, and restore the
//    // Active Session in a way that is similar to Android UI lifecycles.
//    private UiLifecycleHelper uiHelper;
//    private View otherView;
//    private static final String TAG = "MainActivity";
//    private Button botonGaleria;
//    String nombre, apellido, facebookID, sexo,
//            edad, mujeres, hombres;
//    String nacimiento, nombreCiudad, nombreProvincia, nombrePais;
//    private Button btnGuardar;
//    private CheckBox interesanMujeres;
//    private CheckBox interesanHombres;
//    String nombreCompleto;
//    EditText edadText;
//    EditText ciudad;
//    EditText provincia;
//    EditText pais;
//    EditText rangoDeEdadDesde;
//    EditText rangoDeEdadHasta;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        // To maintain FB Login session
//        uiHelper = new UiLifecycleHelper(this, callback);
//        uiHelper.onCreate(savedInstanceState);
//    }
//
//    // Called when session changes
//    private Session.StatusCallback callback = new Session.StatusCallback() {
//        @Override
//        public void call(Session session, SessionState state,
//                         Exception exception) {
//            onSessionStateChange(session, state, exception);
//        }
//    };
//
//    // When session is changed, this method is called from callback method
//    private void onSessionStateChange(Session session, SessionState state,
//                                      Exception exception) {
//
//        // When Session is successfully opened (User logged-in)
//        if (state.isOpened()) {
//
//            Log.i(TAG, "Logged in...");
//            // make request to the /me API to get Graph user
//            Request.newMeRequest(session, new Request.GraphUserCallback() {
//                // callback after Graph API response with user
//                // object
//                @Override
//                public void onCompleted(GraphUser user, Response response) {
//
//                    if (user != null) {
//                        nombre = user.getFirstName();
//                        apellido = user.getLastName();
//                        facebookID = user.getId();
//                        sexo = user.getProperty("gender").toString()
//                                .equals("male") ? "hombre" : "mujer";
//
//////                            LoginFragment fragment = new LoginFragment();
//////                            Bundle args = new Bundle();
//////                            args.putString("nombre", nombre);
//////                            args.putString("apellido", apellido);
//////                            args.putString("facebookID", facebookID);
//////                            args.putString("sexo", sexo);
//////                            fragment.setArguments(args);
//////                            transaction.add(R.id.contenedor,
//////                                    fragment, "lg_fragment");
//////
//////                            // Paso 4: Confirmar el cambio
//////                            transaction.commit();
//////
//////                            // Set User name
//////                            nombreCompleto = user.getName();
//////
//////
////////                                        }
////////
////////
////////
//////                                    }
//////                                });
////                        }
//////                    }
//                    }
//                }
//            }).executeAsync();
//
//        } else if (state.isClosed())
//
//        {
//            Log.i(TAG, "Logged out...");
//        }
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        uiHelper.onActivityResult(requestCode, resultCode, data);
//        Log.i(TAG, "OnActivityResult...");
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        uiHelper.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        uiHelper.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        uiHelper.onDestroy();
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        uiHelper.onSaveInstanceState(outState);
//    }
//
}