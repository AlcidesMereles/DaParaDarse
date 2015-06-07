package lp4.untref.daparadarse;

        import java.util.ArrayList;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.app.Activity;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;

public class ListadoActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        TareaObtencionDeDatos obtencionDatos = new TareaObtencionDeDatos();
        obtencionDatos.execute();
    }

    public void cargaListado(ArrayList<String> datos){
        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datos);
        ListView listado = (ListView) findViewById(R.id.listView1);
        listado.setAdapter(adaptador);
    }


    private class TareaObtencionDeDatos extends AsyncTask<Void,Void,ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void...params) {
            ClienteHttp cliente = new ClienteHttp();
            return cliente.obtDatosJSON(cliente.leer());
        }

        @Override
        protected void onPostExecute(ArrayList<String> resultado) {
            super.onPostExecute(resultado);
            cargaListado(resultado);
        }
    }
}