package lp4.untref.daparadarse;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import javaphpmysql.JSONArray;

public class Fragment_Chats extends Fragment {

    boolean existenCoincidencias;
    View rootView;
    View fmPerfil;
    Button miBoton;
    private String facebookID;
    GuardarId unID = null;
    JSONParser jParser = new JSONParser();


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fm_chats, container, false);
        fmPerfil = inflater.inflate(R.layout.fragment_login, container, false);

        TextView elId = (TextView) fmPerfil.findViewById(R.id.idFacebook);
        TextView miId = (TextView) rootView.findViewById(R.id.idPerfil);
        miId.setText(elId.getText());

        Button boton = (Button) rootView.findViewById(R.id.boton);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ComprobarExistenciaCoincidencias().execute();
            }
        });
        return rootView;
    }

    public class Listener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), ActivityCoincidencias.class);
            startActivity(intent);
        }
    }

    private class ComprobarExistenciaCoincidencias extends AsyncTask<Void, Void, String> {
        private String urlCoincidencias = "http://daparadarse.site88.net/Android/mostrarCoincidencias.php";
        private boolean coincidencias;
        private String id;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity mActivity = (MainActivity) getActivity();
            id = mActivity.getFacebookID();
        }

        @Override
        protected String doInBackground(Void... params) {
            JSONArray json;
            List<String> args = new LinkedList<String>();
            args.add(id);
            if (id != null) {
                json = new ClienteHttp().getJsonResponse(urlCoincidencias, id);
                coincidencias = json != null;
            }
            String a = " ";
            return a;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (coincidencias && id != null) {
                /* si estoy conectado muestro el boton de reserva online */
                Intent miIntent = new Intent(getActivity(), ActivityCoincidencias.class);
                miIntent.putExtra("facebookID", id);
                startActivity(miIntent);
            } else {
                Toast.makeText(getActivity(), "No hay coincidencias", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
