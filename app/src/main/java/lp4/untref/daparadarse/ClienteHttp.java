package lp4.untref.daparadarse;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ClienteHttp {

    public String enviarPost(Map<String,String> map) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpContext localContext = new BasicHttpContext();
        HttpPost httpPost = new HttpPost(
                "http://www.daparadarse.site88.net/Android/PutData.php");
        HttpResponse response = null;
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>(3);
            params.add(new BasicNameValuePair("facebookID",map.get("id")));
            params.add(new BasicNameValuePair("nombre", map.get("nombre")));
            params.add(new BasicNameValuePair("apellido", map.get("apellido")));
            params.add(new BasicNameValuePair("edad", map.get("edad")));
            params.add(new BasicNameValuePair("sexo", map.get("sexo")));
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            response = httpClient.execute(httpPost, localContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString();
    }

}
