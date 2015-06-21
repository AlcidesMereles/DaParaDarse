package lp4.untref.daparadarse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Spider on 20/06/2015.
 */
public class MostrarPerfil extends ActionBarActivity {
    private static String urlVerificarGustosAlmacenados = null;
    private ProgressDialog pDialog;

    JSONParser jParser = new JSONParser();
    JSONArray usuariosJ = null;

    ListView lista;
    ArrayList<HashMap<String, String>> listaPerfil;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USUARIOS = "usuarios";
    private static final String TAG_EDADMAXIMA = "edadMaxima";
    private static final String TAG_NOMBRE = "nombre";
    private static final String TAG_EDADMINIMA = "edadMinima";
    private static final String TAG_CIUDAD = "ciudad";
    private static final String TAG_SEXO = "sexo";
    private static final String TAG = "MainFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_perfil);

        // Hashmap para el ListView
        listaPerfil = new ArrayList<HashMap<String, String>>();


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String idUsuario =bundle.getString("id");

        urlVerificarGustosAlmacenados = "http://daparadarse.site88.net/Android/verificarGustosAlmacenados.php?id="+ idUsuario;
        // Cargar los usuarios en el Background Thread
        new CargarPerfil().execute();

        lista = (ListView) findViewById(R.id.listaPerfil);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }


    class CargarPerfil extends AsyncTask<String, String, String> {


        /**
         * Antes de empezar el background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MostrarPerfil.this);
            pDialog.setMessage("Cargando perfil. Por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * obteniendo todos los productos
         */
        protected String doInBackground(String... args) {
            // Building Parameters
            List params = new ArrayList();
            // getting JSON string from URL
            JSONObject json = jParser.makeHttpRequest(urlVerificarGustosAlmacenados, "GET", params);

            // Check your log cat for JSON reponse
            Log.d("Perfil: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    usuariosJ = json.getJSONArray(TAG_USUARIOS);

                    // looping through All Users
                    //Log.i("ramiro", "usuariosJ.length" + usuariosJ.length());
                    for (int i = 0; i < usuariosJ.length(); i++) {
                        JSONObject c = usuariosJ.getJSONObject(i);

                        // Storing each json item in variable
                        String nombreServidor = c.getString(TAG_NOMBRE);
                        String sexoServidor = c.getString(TAG_SEXO);
                        String ciudadServidor = c.getString(TAG_CIUDAD);
                        String edadMinimaServidor = c.getString(TAG_EDADMINIMA);
                        String edadMaximaServidor = c.getString(TAG_EDADMAXIMA);

                        // creating new HashMap
                        HashMap map = new HashMap();
                        // adding each child node to HashMap key => value
                        map.put(TAG_NOMBRE, nombreServidor);
                        map.put(TAG_SEXO, sexoServidor);
                        map.put(TAG_CIUDAD, ciudadServidor);
                        map.put(TAG_EDADMINIMA, edadMinimaServidor);
                        map.put(TAG_EDADMAXIMA, edadMaximaServidor);

                        listaPerfil.add(map);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */

                    ListAdapter adapter = new SimpleAdapter(
                            MostrarPerfil.this,
                            listaPerfil,
                            R.layout.perfil_usuario,
                            new String[]{
                                    TAG_NOMBRE,
                                    TAG_SEXO,
                                    TAG_CIUDAD,
                                    TAG_EDADMINIMA,
                                    TAG_EDADMAXIMA,
                            },
                            new int[]{
                                    R.id.nombreUsuario,
                                    R.id.generoBuscado,
                                    R.id.ciudadUsuario,
                                    R.id.edadMinima,
                                    R.id.edadMaxima,
                            });
                    // updating listview
                    //setListAdapter(adapter);
                    lista.setAdapter(adapter);
                }
            });
        }
    }
}
