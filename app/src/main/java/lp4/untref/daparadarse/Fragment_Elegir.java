package lp4.untref.daparadarse;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.andtinder.model.CardModel;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javaphpmysql.JSONArray;

/**
 * Created by Spider on 07/06/2015.
 */
public class Fragment_Elegir extends Fragment implements LoginFragment.OnSetIdListener {
    View rootView;
    private CardContainer mCardContainer;

    private ImageView iv;

    private OnLiked mCallback;
    private List<Drawable> imagen;
    private JSONArray miJsonArray;
    private String id;
    private Perfil[] perfilesUsuarios;//TODO: Revisar posible cambio.

    public interface OnLiked {
        public void onLike();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagen = new LinkedList<Drawable>();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        id = ((MainActivity) getActivity()).getFacebookID();
        if (id != null) {

            System.out.println(id);
        } else {
            System.out.println("Es nulo");
        }
        rootView = inflater.inflate(R.layout.mainlayout, container, false);


        return rootView;
    }

    public void setImagen(Perfil[] p) {

        mCardContainer = (CardContainer) rootView.findViewById(R.id.layoutview);

        SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(getActivity());

        CardModel[] cards = new CardModel[p.length];

        for (int i = 0; i < p.length; i++) {
            final CardModel cardModel = new CardModel(p[i].nombre + " " + p[i].apellido + " " + p[i].edad, p[i].ciudad + " - " + p[i].provincia, p[i].myDrawable);

            cardModel.setNombreUsuario(p[i].nombre);
            cardModel.setApellidoUsuario(p[i].apellido);
            cardModel.setEdadUsuario(p[i].edad);
            cardModel.setIdUsuario(p[i].id);

            cardModel.setOnClickListener(new CardModel.OnClickListener() {
                @Override
                public void OnClickListener() {
                    Toast.makeText(getActivity(), "Estoy apretando", Toast.LENGTH_SHORT).show();
                    Log.i("Swipeable Cards", "I am pressing the card");
                }
            });

            cardModel.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
                @Override
                public void onLike() {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("nombre", cardModel.getNombreUsuario());
                    System.out.println("Este es el nombre: " + cardModel.getNombreUsuario());
                    map.put("otroId", cardModel.getIdUsuario());
                    map.put("id", id);
                    EnviarGustos envio = new EnviarGustos();
                    envio.execute(map);
                    Log.i("Swipeable Cards", "I like the card");
                }

                @Override
                public void onDislike() {
                    Log.i("Swipeable Cards", "I dislike the card");
                }
            });
            cards[i] = cardModel;
        }

        for (int i = 0; i < cards.length; i++) {

            adapter.add(cards[i]);
        }

        mCardContainer.setAdapter(adapter);

    }

    private void setDrawable(Drawable myDrawable) {
        imagen.add(myDrawable);
        setImagen(perfilesUsuarios);
    }

    public void setJSONArray(JSONArray json) {
        miJsonArray = json;
        setImagen(perfilesUsuarios);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback = (OnLiked) activity;
    }

    @Override
    public void OnSetId(String userID) {
        id = userID;
        if (id != null) {
            LoadDrawable load;
            load = new LoadDrawable();
            load.execute("");
        }
    }

    private class LoadDrawable extends AsyncTask<String, Void, String> {
        Drawable[] d;
        String[] directorios;
        JSONArray json;
        Perfil[] perfiles;


        @Override
        protected String doInBackground(String... params) {
            InputStream is;

            ClienteHttp cliente = new ClienteHttp();
            json = cliente.obtenerJSON(id);
            directorios = new String[json.length()];//TODO: Refactorizar.
            d = new Drawable[json.length()];
            perfiles = new Perfil[json.length()];
            for (int i = 0; i < json.length(); i++) {
                perfiles[i] = new Perfil();
                perfiles[i].nombre = json.getJSONObject(i).getString("nombre");
                perfiles[i].apellido = json.getJSONObject(i).getString("apellido");
                perfiles[i].edad = json.getJSONObject(i).getString("edad");
                perfiles[i].ciudad = json.getJSONObject(i).getString("ciudad");
                perfiles[i].provincia = json.getJSONObject(i).getString("provincia");
                perfiles[i].pais = json.getJSONObject(i).getString("pais");
                perfiles[i].sexo = json.getJSONObject(i).getString("sexo");
                perfiles[i].id = json.getJSONObject(i).getString("id");
            }
            System.out.println(json.length());
            for (int i = 0; i < directorios.length; i++) {
                directorios[i] = "http://daparadarse.site88.net/Android/imagenes/img_" + json.getJSONObject(i).getString("id") + "/" + "1.jpg";
                System.out.println(directorios[i]);
            }
            System.out.println(json);


            try {


                //"http://icons.iconarchive.com/icons/crountch/one-piece-jolly-roger/72/Luffys-flag-2-icon.png"
                for (int i = 0; i < directorios.length; i++) {
                    is = (InputStream) new URL(directorios[i]).getContent();
                    d[i] = Drawable.createFromStream(is, "src name");
                    perfiles[i].myDrawable = d[i];
                    System.out.println(d[i]);
                }
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            for (int i = 0; i < d.length; i++) {
                perfilesUsuarios = perfiles;
                //setDrawable(d[i]);
                setJSONArray(json);
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private class JSONtask extends AsyncTask<Void, Void, JSONArray> {


        JSONArray jArray;

        @Override
        protected JSONArray doInBackground(Void... params) {

            ClienteHttp cliente = new ClienteHttp();
            return cliente.obtenerJSON(id);
        }

        @Override
        protected void onPostExecute(JSONArray j) {
            super.onPostExecute(j);
        }
    }

    private class Perfil {
        Drawable myDrawable;
        String nombre;
        String apellido;
        String sexo;
        String edad;
        String ciudad;
        String provincia;
        String pais;
        String id;
    }

    class EnviarGustos extends AsyncTask<Map<String, String>, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            for (int i = 0; i <.length;
//            i++){
//                map.put("nombre", perfilesUsuarios[i].nombre);
//                map.put("apellido", perfilesUsuarios[i].apellido);
//                map.put("edad", perfilesUsuarios[i].edad);
//                map.put("facebookID", perfilesUsuarios[i].id);
//                map.put("sexo", perfilesUsuarios[i].sexo);
//            }
        }

        @Override
        protected String doInBackground(Map<String, String>... params) {

            ClienteHttp cliente = new ClienteHttp();
            cliente.enviarGustos(params[0]);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}
