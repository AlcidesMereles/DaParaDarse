package lp4.untref.daparadarse;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;

import com.andtinder.model.CardModel;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;

import lp4.untref.daparadarse.R;

import com.andtinder.view.CardContainer;

import demo.pantallasTinder;

/**
 * Created by Spider on 07/06/2015.
 */
public class Fragment_Elegir extends Fragment {
    View rootView;
    private pantallasTinder pantalla;
    private CardContainer mCardContainer;

    private ImageView iv;

    private Drawable imagen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LoadDrawable().execute("");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.mainlayout, container, false);


        return rootView;
    }

    public void setImagen() {

        mCardContainer = (CardContainer) rootView.findViewById(R.id.layoutview);

        Resources r = getResources();

        SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(getActivity());

        adapter.add(new CardModel("Title1", "Description goes here", imagen));
//        adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.mipmap.picture2)));
//        adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.mipmap.picture3)));
//        adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.mipmap.picture1)));
//        adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.mipmap.picture2)));
//        adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.mipmap.picture3)));
//        adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.mipmap.picture1)));
//        adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.mipmap.picture2)));
//        adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.mipmap.picture3)));
//        adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.mipmap.picture1)));
//        adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.mipmap.picture2)));
//        adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.mipmap.picture3)));
//        adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.mipmap.picture1)));
//        adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.mipmap.picture2)));
//        adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.mipmap.picture3)));
//        adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.mipmap.picture1)));
//        adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.mipmap.picture2)));
//        adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.mipmap.picture3)));
//        adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.mipmap.picture1)));
//        adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.mipmap.picture2)));
//        adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.mipmap.picture3)));
//        adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.mipmap.picture1)));
//        adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.mipmap.picture2)));
//        adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.mipmap.picture3)));
//        adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.mipmap.picture1)));
//        adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.mipmap.picture2)));
//        adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.mipmap.picture3)));
//        adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.mipmap.picture1)));
//        adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.mipmap.picture2)));

        CardModel cardModel = new CardModel("Title1", "Description goes here", r.getDrawable(R.mipmap.picture1));
        cardModel.setOnClickListener(new CardModel.OnClickListener() {
            @Override
            public void OnClickListener() {
                Log.i("Swipeable Cards", "I am pressing the card");
            }
        });

        cardModel.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
            @Override
            public void onLike() {
                Log.i("Swipeable Cards", "I like the card");
            }

            @Override
            public void onDislike() {
                Log.i("Swipeable Cards", "I dislike the card");
            }
        });

        adapter.add(cardModel);

        mCardContainer.setAdapter(adapter);

    }

    private void setDrawable(Drawable myDrawable) {
        imagen = myDrawable;
        setImagen();
    }

    private class LoadDrawable extends AsyncTask<String, Void, String> {
        Drawable d;

        @Override
        protected String doInBackground(String... params) {
            InputStream is;
            try {
                is = (InputStream) new URL(
                        "http://icons.iconarchive.com/icons/crountch/one-piece-jolly-roger/72/Luffys-flag-2-icon.png")
                        .getContent();
                d = Drawable.createFromStream(is, "src name");

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
            setDrawable(d);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

}
