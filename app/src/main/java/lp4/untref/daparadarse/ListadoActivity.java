package lp4.untref.daparadarse;

        import java.io.BufferedReader;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.List;

        import org.apache.http.HttpEntity;
        import org.apache.http.HttpResponse;
        import org.apache.http.NameValuePair;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.entity.UrlEncodedFormEntity;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.client.methods.HttpPost;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.apache.http.message.BasicNameValuePair;
        import org.apache.http.protocol.BasicHttpContext;
        import org.apache.http.protocol.HttpContext;
        import org.apache.http.util.EntityUtils;
        import android.os.Bundle;
        import android.app.Activity;
        import android.view.Menu;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;
        import javaphpmysql.JSONObject;
        import javaphpmysql.JSONArray;

public class ListadoActivity extends Activity {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String SERVER_PATH = "http://daparadarse.site88.net/";

    public  String getJason(){
                StringBuffer response = null;
                Button btnMostrar = (Button)findViewById(R.id.btnMostrar);
        Thread tr = new Thread() {
            @Override
            public void run() {
                StringBuffer response = null;
                try {
                    // Generar la URL
                    String url = "http://daparadarse.site88.net/selectAllJSON.php";
                    // Creamos un nuevo objeto URL con la url donde pedir el JSON
                    URL obj = new URL(url);
                    // Creamos un objeto de conexiÃ³n
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    // AÃ±adimos la cabecera
                    con.setRequestMethod("GET");
                    con.setRequestProperty("User-Agent", USER_AGENT);
                    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                    // Enviamos la peticiÃ³n por POST
                    con.setDoOutput(true);
                    // Capturamos la respuesta del servidor
                    int responseCode = con.getResponseCode();
                    System.out.println("\nSending 'POST' request to URL : " + url);
                    System.out.println("Response Code : " + responseCode);

                    BufferedReader in = new BufferedReader(new InputStreamReader(
                            con.getInputStream()));
                    String inputLine;
                    response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    // Mostramos la respuesta del servidor por consola
                    System.out.println("Respuesta del servidor: " + response);
                    System.out.println();

                    // cerramos la conexiÃ³n
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        tr.start();
        return response.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        Thread tr = new Thread(){
            @Override
            public void run(){
                final String Resultado = leer();
                runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                cargaListado(obtDatosJSON(Resultado));
                            }
                        });
            }
        };
        tr.start();

    }

//    Thread tr = new Thread(){
//        @Override
//        public void run(){
//
//            Button btnMostrar = (Button)findViewById(R.id.btnMostrar);
//            final TextView  campoTexto = (TextView)findViewById(R.id.datosUsuario);
//            final String Resultado = getJason();
//            StringBuffer response = null;
//
//            try {
//                // Generar la URL
//                String url = "http://daparadarse.site88.net/selectAllJSON.php";
//                // Creamos un nuevo objeto URL con la url donde pedir el JSON
//                URL obj = new URL(url);
//                // Creamos un objeto de conexiÃ³n
//                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//                // AÃ±adimos la cabecera
//                con.setRequestMethod("GET");
//                con.setRequestProperty("User-Agent", USER_AGENT);
//                con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//                // Enviamos la peticiÃ³n por POST
//                con.setDoOutput(true);
//                // Capturamos la respuesta del servidor
//                int responseCode = con.getResponseCode();
//                System.out.println("\nSending 'POST' request to URL : " + url);
//                System.out.println("Response Code : " + responseCode);
//
//                BufferedReader in = new BufferedReader(new InputStreamReader(
//                        con.getInputStream()));
//                String inputLine;
//                response = new StringBuffer();
//
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                // Mostramos la respuesta del servidor por consola
//                System.out.println("Respuesta del servidor: " + response);
//                System.out.println();
//
//                // cerramos la conexiÃ³n
//                in.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            runOnUiThread(
//                    new Runnable() {
//
//                        @Override
//                        public void run() {
//                            JSONObject jsonObject = new JSONObject(Resultado);
//                            //javaphpmysql.JSONArray jsonArray = jsonObject.getJSONArray("personas");
//                            String nombre = jsonObject.getString("personas");
//                            campoTexto.setText(nombre);
//
//                        }
//                    });
//        }
//    };
//    tr.start();

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.listado, menu);
//        return true;
//    }

    public void mostrarOnClick(View view){
        Thread nt = new Thread() {
            @Override
            public void run() {

                Button btnMostrar = (Button)findViewById(R.id.btnMostrar);
                final TextView  campoTexto = (TextView)findViewById(R.id.datosUsuario);
                EditText nombre = (EditText) findViewById(R.id.et_nombre);
                EditText apellido = (EditText) findViewById(R.id.et_apellido);
                EditText edad = (EditText) findViewById(R.id.et_edad);
                CheckBox modo = (CheckBox) findViewById(R.id.ck_modo);

                try {

                    final String res;
                    res = leer();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            JSONObject jsonObject = new JSONObject(res);
                            javaphpmysql.JSONArray jsonArray = jsonObject.getJSONArray("personas");
                            JSONObject otroJson = (JSONObject)jsonArray.get(0);
                            String nombre = otroJson.getString("nombre");
                            campoTexto.setText(nombre);

                        }
                    });
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        };
        nt.start();
    }



    public String leer(){
        HttpClient cliente =new DefaultHttpClient();
        HttpContext contexto = new BasicHttpContext();
        HttpGet httpget = new HttpGet("http://daparadarse.site88.net/selectAllJSON.php");
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


    public String enviarGet(String nombre, String apellido, String edad) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpResponse response = null;

        HttpGet httpget = new HttpGet(
                "http://daparadarse.site88.net/selectAllJSON.php");
        try {
            response = httpClient.execute(httpget, localContext);

        } catch (Exception e) {

        }
        System.out.println(response.toString());
        return response.toString();
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
                        json.getJSONObject(i).getString("edad") +" - "+
                        json.getJSONObject(i).getString("modo");
                listado.add(texto);
            }
        } catch (Exception e) {
// TODO: handle exception
        }
        return listado;
    }

}