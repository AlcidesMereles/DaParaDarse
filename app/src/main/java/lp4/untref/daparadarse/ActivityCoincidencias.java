package lp4.untref.daparadarse;

import android.app.ProgressDialog;
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
 * Created by Spider on 18/06/2015.
 */
public class ActivityCoincidencias extends ActionBarActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String, String>> listaConcidencias;

    //private static GuardarId unId;
    //private static String elId = unId.getId();

    // url to get all user list
    //private static String urlCoincidencias = "http://daparadarse.site88.net/Android/mostrarCoincidencias.php?id="+elId;
    private static String urlCoincidencias = "http://daparadarse.site88.net/Android/mostrarCoincidencias.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USUARIOS = "usuarios";
    private static final String TAG_APELLIDO = "apellido";
    private static final String TAG_NOMBRE = "nombre";
    private static final String TAG_ID = "id";
    private static final String TAG = "MainFragment";

    // usuariosJ JSONArray
    JSONArray usuariosJ = null;

    ListView lista;

    String facebookID;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coincidencias);

        // Hashmap para el ListView
        listaConcidencias = new ArrayList<HashMap<String, String>>();

        // Cargar los usuarios en el Background Thread
        new CargarCoincidencias().execute();
        lista = (ListView) findViewById(R.id.listaCoincidencias);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }


        class CargarCoincidencias extends AsyncTask<String, String, String> {


                    /**
                     * Antes de empezar el background thread Show Progress Dialog
                     */
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        pDialog = new ProgressDialog(ActivityCoincidencias.this);
                        pDialog.setMessage("Cargando coincidencias. Por favor espere...");
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
                        JSONObject json = jParser.makeHttpRequest(urlCoincidencias, "GET", params);

                        // Check your log cat for JSON reponse
                        Log.d("Coincidencias: ", json.toString());

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
                                    String apellidoServidor = c.getString(TAG_APELLIDO);
                                    String nombreServidor = c.getString(TAG_NOMBRE);
                                    String idServidor = c.getString(TAG_ID);


                                    // creating new HashMap
                                    HashMap map = new HashMap();
                                    // adding each child node to HashMap key => value
                                    map.put(TAG_APELLIDO, apellidoServidor);
                                    map.put(TAG_NOMBRE, nombreServidor);
                                    listaConcidencias.add(map);


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
                                        ActivityCoincidencias.this,
                                        listaConcidencias,
                                        R.layout.single_post,
                                        new String[]{
                                                TAG_APELLIDO,
                                                TAG_NOMBRE,
                                        },
                                        new int[]{
                                                R.id.apellidoInteresado,
                                                R.id.nombreInteresado,
                                        });
                                // updating listview
                                //setListAdapter(adapter);
                                lista.setAdapter(adapter);
                            }
                        });
                    }
        }

}
