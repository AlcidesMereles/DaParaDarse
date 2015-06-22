package lp4.untref.daparadarse;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.facebook.widget.LoginButton;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LoginFragment extends Fragment {

    OnSetIdListener mIsetListener = null;
    private static final String TAG = "MainFragment";
    // Create, automatically open (if applicable), save, and restore the
    // Active Session in a way that is similar to Android UI lifecycles.
    private UiLifecycleHelper uiHelper;
    private View otherView;

    private boolean existeId;
    String nombre, apellido, facebookID, sexo,
            edad, mujeres, hombres;
    private String nacimiento,
            nombreCiudad,
            nombreProvincia,
            nombrePais;
    private Button btnGuardar;
    private CheckBox interesanMujeres;
    private CheckBox interesanHombres;
    EditText edadText;
    EditText ciudad;
    EditText provincia;
    EditText pais;
    EditText rangoDeEdadDesde;
    EditText rangoDeEdadHasta;

    public interface OnSetIdListener {
        public void OnSetId(String id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mujeres = "0";
        hombres = "0";

        // To maintain FB Login session
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        btnGuardar = (Button) view.findViewById(R.id.btnGuardar);
        edadText = (EditText) view.findViewById(R.id.editTextEdad);
        ciudad = (EditText) view.findViewById(R.id.editTextCiudad);
        provincia = (EditText) view.findViewById(R.id.editTextProvincia);
        pais = (EditText) view.findViewById(R.id.editTextPais);
        rangoDeEdadDesde = (EditText) view.findViewById(R.id.editTextEdadDesde);
        rangoDeEdadHasta = (EditText) view.findViewById(R.id.editTextEdaHasta);
        interesanMujeres = (CheckBox) view.findViewById(R.id.checkBoxMujeres);
        interesanHombres = (CheckBox) view.findViewById(R.id.checkBoxHombres);


        // Looks for Login button
        LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
        authButton.setFragment(this);
        // Set View that should be visible after log-in invisible initially
        otherView = view.findViewById(R.id.view_formulario);
        otherView.setVisibility(View.GONE);

        //authButton.setReadPermissions(Arrays.asList("user_likes", "user_status","email","user_birthday"));
        return view;
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
        final TextView name = (TextView) getView().findViewById(R.id.name);
        final TextView unIDFacebook = (TextView) getView().findViewById(R.id.idFacebook);

        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
            // make request to the /me API to get Graph user
            Request.newMeRequest(session, new Request.GraphUserCallback() {

                // callback after Graph API response with user
                // object
                @Override
                public void onCompleted(GraphUser user, Response response) {
                    if (user != null) {
                        MainActivity mActivity = (MainActivity) getActivity();
                        mActivity.setFacebookID(user.getId());
                        // Set view visibility to true
                        otherView.setVisibility(View.VISIBLE);
// Set User name
                        name.setText("Bienvenido " + user.getName());
                        nombre = user.getFirstName();
                        apellido = user.getLastName();
                        sexo = user.getProperty("gender").toString().equals("male") ? "hombre" : "mujer";
                        facebookID = user.getId();
                        ComprobarExistenciaId c = new ComprobarExistenciaId();
                        try {
                            c.execute(facebookID).get();
                            AsyncTask.Status cStatus = c.getStatus();
                            System.out.println(cStatus);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).executeAsync();
        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
            otherView.setVisibility(View.GONE);
        }
    }


    ///

    private void setButton() {

        Button boton = (Button) otherView.findViewById(R.id.botonPerfil);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* si estoy conectado muestro el boton de reserva online */
                Intent miIntent = new Intent(getActivity(), MostrarPerfil.class);
                miIntent.putExtra("id", facebookID);
                startActivity(miIntent);
            }
        });
        Button botonPerfil = (Button) otherView.findViewById(R.id.fotoPerfil);
        botonPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/* si estoy conectado muestro el boton de reserva online */
                Intent miIntent = new Intent(getActivity(), subirFotoServidor.class);
                miIntent.putExtra("id", facebookID);
                startActivity(miIntent);
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edad = edadText.getText().toString();
                nombreCiudad = ciudad.getText().toString();
                nombreProvincia = provincia.getText().toString();
                nombrePais = pais.getText().toString();
                //TODO: Agregar la informacion que falta
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
//TODO: Refactorizar codigo.
                if (map.containsValue("") || (!interesanMujeres.isChecked() && !interesanHombres.isChecked())) {
                    Toast.makeText(getActivity(), "Complete todos los campos por favor.", Toast.LENGTH_SHORT).show();
// } else if (!sonTodosEnteros()) {
// Toast.makeText(getApplicationContext(), "Error: Debe ingresar solo numeros enteros", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(edadText.getText().toString()) < 18) {
                    Toast.makeText(getActivity(), "Debes ser mayor de 18 años para poder usar la aplicacion", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(rangoDeEdadDesde.getText().toString()) < 18 ||
                        Integer.parseInt(rangoDeEdadHasta.getText().toString()) < 18) {
                    Toast.makeText(getActivity(), "La edad minima es de 18 años", Toast.LENGTH_SHORT).show();
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
                    mIsetListener.OnSetId(facebookID);
                }
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mIsetListener = (MainActivity) activity;
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

    private class ComprobarExistenciaId extends AsyncTask<String, Boolean, String> {
        String res;

        @Override
        protected String doInBackground(String... params) {
            HttpClient cliente = new DefaultHttpClient();
            HttpContext contexto = new BasicHttpContext();
            HttpGet httpget = new HttpGet("http://www.daparadarse.site88.net/Android/comprobarExistenciaId.php?id=" + facebookID);
            String resultado = null;
            try {
                HttpResponse response = cliente.execute(httpget, contexto);
                HttpEntity entity = response.getEntity();
                res = EntityUtils.toString(entity, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.i("Resultado de si existe", res);
            Boolean bool = new Boolean(res.equals("1"));
            publishProgress(bool);
            return res;
        }

        @Override
        protected void onProgressUpdate(Boolean... values) {
            super.onProgressUpdate(values);
            existeId = values[0];
        }

        @Override
        protected void onPostExecute(String resultado) {
            existeId = resultado.equals("1");
            if (existeId) {
                mIsetListener.OnSetId(facebookID);
            }
            setButton();
        }
    }

}
