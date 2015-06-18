package lp4.untref.daparadarse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

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
	
	// url to get all user list
	private static String urlCoincidencias = "http://daparadarse.site88.net/Android/mostrarCoincidencias.php";
	
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_USUARIOS = "usuarios";
	private static final String TAG_APELLIDO = "apellido";
	private static final String TAG_NOMBRE = "nombre";
	private static final String TAG = "MainFragment";
   
	// usuariosJ JSONArray
	JSONArray usuariosJ = null;
	
	ListView lista;
   
	//String facebookID, nombre, apellido;
	//TextView suNombre;
	//TextView suApellido;

   /** Create, automatically open (if applicable), save, and restore the
    Active Session in a way that is similar to Android UI lifecycles.
	private UiLifecycleHelper uiHelper;
    Called when session changes
   private Session.StatusCallback callback = new Session.StatusCallback() {
       @Override
       public void call(Session session, SessionState state,
                        Exception exception) {
           onSessionStateChange(session, state, exception);
       }
   };**/

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
			pDialog.setMessage("Cargando comercios. Por favor espere...");
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
						String apellido = c.getString(TAG_APELLIDO);
						String name = c.getString(TAG_NOMBRE);

						// creating new HashMap
						HashMap map = new HashMap();

						// adding each child node to HashMap key => value
						map.put(TAG_APELLIDO, apellido);
						map.put(TAG_NOMBRE, name);

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
									R.id.single_post_tv_apellido,
									R.id.single_post_tv_nombre,
							});
					// updating listview
					//setListAdapter(adapter);
					lista.setAdapter(adapter);
				}
			});
		}
	}
	
	/**
	// When session is changed, this method is called from callback method
	private void onSessionStateChange(Session session, SessionState state,
                                     Exception exception) {

		if (state.isOpened()) {
			Log.i(TAG, "Logged in...");
			// make request to the /me API to get Graph user
			Request.newMeRequest(session, new Request.GraphUserCallback() {

				// callback after Graph API response with user
				// object
				@Override
				public void onCompleted(GraphUser user, Response response) {
					if (user != null) {

						//nombre = user.getFirstName();
						//apellido = user.getLastName();
						facebookID = user.getId();
						CargarCoincidencias coincidencias = new CargarCoincidencias();
						coincidencias.execute(facebookID);

					}
				}
			}).executeAsync();
		} else if (state.isClosed()) {
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

	public interface OnHeadlineSelectedListener {
		void onArticleSelected(int position);
	}
**/
}
