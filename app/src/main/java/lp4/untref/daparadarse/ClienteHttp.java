package lp4.untref.daparadarse;

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
import javaphpmysql.JSONObject;
import javaphpmysql.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteHttp {

    private static final String SERVER_PATH = "http://daparadarse.site88.net/";

    public String enviarPost(Map<String, String> map) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(
                "http://www.daparadarse.site88.net/Android/PutData.php");
        HttpResponse response = null;
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("id", map.get("facebookID")));
            params.add(new BasicNameValuePair("nombre", map.get("nombre")));
            params.add(new BasicNameValuePair("apellido", map.get("apellido")));
            params.add(new BasicNameValuePair("edad", map.get("edad")));
            params.add(new BasicNameValuePair("sexo", map.get("sexo")));
            params.add(new BasicNameValuePair("nacimiento", map.get("nacimiento")));
            params.add(new BasicNameValuePair("ciudad", map.get("ciudad")));
            params.add(new BasicNameValuePair("provincia", map.get("provincia")));
            params.add(new BasicNameValuePair("pais", map.get("pais")));
            params.add(new BasicNameValuePair("mujeres", map.get("mujeres")));
            params.add(new BasicNameValuePair("hombres", map.get("hombres")));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            response = httpClient.execute(httpPost, localContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    public String leer() {
        HttpClient cliente = new DefaultHttpClient();
        HttpContext contexto = new BasicHttpContext();
        HttpGet httpget = new HttpGet(SERVER_PATH + "mostrarTablaUsuarios.php");
        String resultado = null;
        try {
            HttpResponse response = cliente.execute(httpget, contexto);
            HttpEntity entity = response.getEntity();
            resultado = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ArrayList<String> obtDatosJSON(String response) {
        ArrayList<String> listado = new ArrayList<String>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray json = jsonObject.getJSONArray("usuarios");
            String texto = "";
            for (int i = 0; i < json.length(); i++) {
                texto = json.getJSONObject(i).getString("nombre") + " - " +
                        json.getJSONObject(i).getString("apellido") + " - " +
                        json.getJSONObject(i).getString("edad");
                listado.add(texto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listado;
    }
}