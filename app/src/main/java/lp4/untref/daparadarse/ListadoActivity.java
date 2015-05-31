package lp4.untref.daparadarse;

        import java.util.ArrayList;
        import org.apache.http.HttpEntity;
        import org.apache.http.HttpResponse;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.apache.http.protocol.BasicHttpContext;
        import org.apache.http.protocol.HttpContext;
        import org.apache.http.util.EntityUtils;
        import android.os.Bundle;
        import android.app.Activity;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import javaphpmysql.JSONObject;
        import javaphpmysql.JSONArray;

public class ListadoActivity extends Activity {

    private static final String SERVER_PATH = "http://daparadarse.site88.net/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
/**
        Thread tr = new Thread(){
            @Override
            public void run(){
                final String resultado = leer();
                runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                cargaListado(obtDatosJSON(resultado));
                            }
                        });
            }
        };
        tr.start();
**/
    }
/**
    public String leer(){
        HttpClient cliente =new DefaultHttpClient();
        HttpContext contexto = new BasicHttpContext();
        HttpGet httpget = new HttpGet(SERVER_PATH+"selectAllJSON.php");
        String resultado=null;
        try {
            HttpResponse response = cliente.execute(httpget,contexto);
            HttpEntity entity = response.getEntity();
            resultado = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
           e.printStackTrace();
        }
        return resultado;
    }

    public void cargaListado(ArrayList<String> datos){
        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datos);
        ListView listado = (ListView) findViewById(R.id.listView1);
        listado.setAdapter(adaptador);
    }

    public ArrayList<String> obtDatosJSON(String response){
        ArrayList<String> listado= new ArrayList<String>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray json = jsonObject.getJSONArray("personas");
            String texto="";
            for (int i=0; i<json.length();i++){
                texto = json.getJSONObject(i).getString("nombre") +" - "+
                        json.getJSONObject(i).getString("apellido") +" - "+
                        json.getJSONObject(i).getString("edad");
                listado.add(texto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listado;
    }
**/
}