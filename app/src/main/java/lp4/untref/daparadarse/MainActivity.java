package lp4.untref.daparadarse;

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

public class MainActivity extends ActionBarActivity {
    private static final String TAG = "MainActivity";
    EditText edadText;
    EditText ciudad;
    EditText provincia;
    EditText pais;
    TextView rangoDeEdadDesde;
    TextView rangoDeEdadHasta;
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
        mujeres = "0";
        hombres = "0";
        // Set View that should be visible after log-in invisible initially
        otherView = findViewById(R.id.other_views);
        otherView.setVisibility(View.GONE);
        rangoDeEdadDesde = (EditText) findViewById(R.id.editTextEdadDesde);
        rangoDeEdadHasta = (EditText) findViewById(R.id.editTextEdaHasta);
        // To maintain FB Login session
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        botonGaleria = (Button) findViewById(R.id.button);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        edadText = (EditText) findViewById(R.id.editTextEdad);
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
                        facebookID = user.getId();
                        sexo = user.getProperty("gender").toString().equals("male") ? "hombre" : "mujer";

                        btnGuardar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                edad = edadText.getText().toString();
                                nombreCiudad = ciudad.getText().toString();
                                nombreProvincia = provincia.getText().toString();
                                nombrePais = pais.getText().toString();

                                //TODO Agregar la informacion que falta
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("nombre", nombre);
                                map.put("apellido", apellido);
                                map.put("edad", edadText.getText().toString());
                                map.put("facebookID", facebookID);
                                map.put("sexo", sexo);
                                map.put("ciudad", ciudad.getText().toString());
                                map.put("provincia", provincia.getText().toString());
                                map.put("pais", pais.getText().toString());
                                map.put("rangoDeEdadDesde", rangoDeEdadDesde.getText().toString());
                                map.put("rangoDeEdadHasta", rangoDeEdadHasta.getText().toString());

                                //TODO Refactorizar codigo.
                                if (map.containsValue("") || (!interesanMujeres.isChecked() && !interesanHombres.isChecked())) {
                                    Toast.makeText(getApplicationContext(), "Complete todos los campos por favor.", Toast.LENGTH_SHORT).show();
//                                } else if (!sonTodosEnteros()) {
//                                    Toast.makeText(getApplicationContext(), "Error: Debe ingresar solo numeros enteros", Toast.LENGTH_SHORT).show();
                                } else if (Integer.parseInt(edadText.getText().toString()) < 18) {
                                    Toast.makeText(getApplicationContext(), "Debes ser mayor de 18 años para poder usar la aplicacion", Toast.LENGTH_SHORT).show();
                                } else if (Integer.parseInt(rangoDeEdadDesde.getText().toString()) < 18 ||
                                        Integer.parseInt(rangoDeEdadHasta.getText().toString()) < 18) {
                                    Toast.makeText(getApplicationContext(), "La edad minima es de 18 años", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (interesanMujeres.isChecked()) {
                                        mujeres = "1";
                                    }
                                    if (interesanHombres.isChecked()) {
                                        hombres = "1";
                                    }

                                    map.put("mujeres", mujeres);
                                    map.put("hombres", hombres);
                                    TareaEnvioDeDatos envioDeDatos = new TareaEnvioDeDatos();
                                    envioDeDatos.execute(map);
                                    Intent pasoDelIdDeFacebook = new Intent(MainActivity.this,ListadoActivity.class);
                                    pasoDelIdDeFacebook.putExtra("facebookUserID", facebookID);
                                    startActivity(pasoDelIdDeFacebook);
                                }

                                //TODO borrar lineas despues de probar
                                Log.i(TAG, edad);
                                Log.i(TAG, nombreCiudad);
                                Log.i(TAG, nombreProvincia);
                                Log.i(TAG, nombrePais);
                                Log.i(TAG, mujeres);
                                Log.i(TAG, hombres);


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

    //TODO Si se puede hacer el ingreso de numeros sin este metodo,borrarlo
    private boolean sonTodosEnteros() {
        try {
            Integer.parseInt(rangoDeEdadDesde.getText().toString());
            Integer.parseInt(rangoDeEdadHasta.getText().toString());
            Integer.parseInt(edadText.getText().toString());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}