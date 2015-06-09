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

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";
    EditText fechaDeNacimiento;
    EditText ciudad;
    EditText provincia;
    EditText pais;
    // Create, automatically open (if applicable), save, and restore the
    // Active Session in a way that is similar to Android UI lifecycles.
    private UiLifecycleHelper uiHelper;
    private View otherView;
    private Button botonGaleria;
    private String nombre, apellido, edad, facebookID, sexo, mujeres, hombres;
    private String nacimiento,
            nombreCiudad,
            nombreProvincia,
            nombrePais;
    private Button btnGuardar;
    private CheckBox interesanMujeres;
    private CheckBox interesanHombres;
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
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        fechaDeNacimiento = (EditText) findViewById(R.id.editTextFechaNacimiento);
        ciudad = (EditText) findViewById(R.id.editTextCiudad);
        provincia = (EditText) findViewById(R.id.editTextProvincia);
        pais = (EditText) findViewById(R.id.editTextPais);
        interesanMujeres = (CheckBox) findViewById(R.id.checkBoxMujeres);
        interesanHombres = (CheckBox) findViewById(R.id.checkBoxHombres);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainTabSwipe.class));
            }
        });


    }

    public void irAGaleria(View view) {

        Intent intent = new Intent(MainActivity.this, MainTabSwipe.class);
        startActivity(intent);
    }

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

                        // Set view visibility to true
                        otherView.setVisibility(View.VISIBLE);
                        // Set User name
                        name.setText("Bienvenido " + user.getName());
                        nombre = user.getFirstName();
                        apellido = user.getLastName();
                        edad = user.getId();//Solo pruebo.
                        facebookID = user.getId();
                        sexo = user.getProperty("gender").toString().equals("male") ? "hombre" : "mujer";


                        nacimiento = fechaDeNacimiento.getText().toString();
                        nombreCiudad = ciudad.getText().toString();
                        nombreProvincia = provincia.getText().toString();
                        nombrePais = pais.getText().toString();
                        if (interesanMujeres.isChecked()) {
                            mujeres = "1";
                        } else {
                            mujeres = "0";
                        }
                        if (interesanHombres.isChecked()) {
                            hombres = "1";
                        } else {
                            hombres = "0";
                        }

                        btnGuardar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //TODO
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("nombre", nombre);
                                map.put("apellido", apellido);
                                map.put("edad", edad);
                                map.put("facebookID", facebookID);
                                map.put("sexo", sexo);
                                map.put("nacimiento", nacimiento);
                                map.put("ciudad", nombreCiudad);
                                map.put("provincia", nombreProvincia);
                                map.put("pais", nombrePais);
                                map.put("mujeres", mujeres);
                                map.put("hombres", hombres);

                                Log.i(TAG, nacimiento);
                                Log.i(TAG, nombreCiudad);
                                Log.i(TAG, nombreProvincia);
                                Log.i(TAG, nombrePais);
                                Log.i(TAG, mujeres);
                                Log.i(TAG, hombres);

                                TareaEnvioDeDatos envioDeDatos = new TareaEnvioDeDatos();
                                envioDeDatos.execute(map);

                                //Paso 1: Obtener la instancia del administrador de fragmentos
                                FragmentManager fragmentManager = getSupportFragmentManager();

                                //Paso 2: Crear una nueva transacción
                                FragmentTransaction transaction = fragmentManager.beginTransaction();

                                //Paso 3: Crear un nuevo fragmento y añadirlo
                                LoginFragment fragment = new LoginFragment();
                                transaction.add(R.id.contenedor, fragment);

                                //Paso 4: Confirmar el cambio
                                transaction.commit();
                            }
                        });

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

}