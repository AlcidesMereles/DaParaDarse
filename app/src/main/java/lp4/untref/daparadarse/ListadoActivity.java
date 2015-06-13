package lp4.untref.daparadarse;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListadoActivity extends Activity {

    private String facebookUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        Intent intent = getIntent();
        facebookUserID = intent.getExtras().getString("facebookUserID");
        TareaObtencionDeDatos obtencionDatos = new TareaObtencionDeDatos();
        obtencionDatos.execute();
    }

    public void cargaListado(ArrayList<String> datos){
        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datos);
        ListView listado = (ListView) findViewById(R.id.listView1);
        listado.setAdapter(adaptador);
    }


    private class TareaObtencionDeDatos extends AsyncTask<String,Void,ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String...params) {
            ClienteHttp cliente = new ClienteHttp();
            return cliente.obtDatosJSON(cliente.leer(facebookUserID));
        }

        @Override
        protected void onPostExecute(ArrayList<String> resultado) {
            super.onPostExecute(resultado);
            cargaListado(resultado);
        }
    }
}