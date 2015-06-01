package lp4.untref.daparadarse;

import android.os.AsyncTask;

import java.util.Map;

public class TareaEnvioDeDatos  extends AsyncTask<Map<String,String>,Void,String> {

        @Override
        protected String doInBackground(Map<String,String>...params) {
            ClienteHttp cliente = new ClienteHttp();
            return cliente.enviarPost(params[0]);

        }

}
