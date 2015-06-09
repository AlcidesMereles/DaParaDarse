package lp4.untref.daparadarse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andtinder.view.CardContainer;

import demo.pantallasTinder;

/**
 * Created by Spider on 07/06/2015.
 */
public class Fragment_Elegir extends Fragment {
    View rootView;
    private pantallasTinder pantalla;
    private CardContainer mCardContainer;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fm_elegir, container, false);
/**
 mCardContainer = (CardContainer) getView().findViewById(R.id.layoutview);

 Resources r = getResources();

 SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(this.pantalla);

 adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.mipmap.picture1)));
 adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.mipmap.picture2)));
 adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.mipmap.picture3)));
 adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.mipmap.picture1)));
 adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.mipmap.picture2)));
 adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.mipmap.picture3)));
 adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.mipmap.picture1)));
 adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.mipmap.picture2)));
 adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.mipmap.picture3)));
 adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.mipmap.picture1)));
 adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.mipmap.picture2)));
 adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.mipmap.picture3)));
 adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.mipmap.picture1)));
 adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.mipmap.picture2)));
 adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.mipmap.picture3)));
 adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.mipmap.picture1)));
 adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.mipmap.picture2)));
 adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.mipmap.picture3)));
 adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.mipmap.picture1)));
 adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.mipmap.picture2)));
 adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.mipmap.picture3)));
 adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.mipmap.picture1)));
 adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.mipmap.picture2)));
 adapter.add(new CardModel("Title6", "Description goes here", r.getDrawable(R.mipmap.picture3)));
 adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.mipmap.picture1)));
 adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.mipmap.picture2)));
 adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.mipmap.picture3)));
 adapter.add(new CardModel("Title4", "Description goes here", r.getDrawable(R.mipmap.picture1)));
 adapter.add(new CardModel("Title5", "Description goes here", r.getDrawable(R.mipmap.picture2)));

 CardModel cardModel = new CardModel("Title1", "Description goes here", r.getDrawable(R.mipmap.picture1));
 cardModel.setOnClickListener(new CardModel.OnClickListener() {
@Override public void OnClickListener() {
Log.i("Swipeable Cards", "I am pressing the card");
}
});

 cardModel.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
@Override public void onLike() {
Log.i("Swipeable Cards","I like the card");
}

@Override public void onDislike() {
Log.i("Swipeable Cards","I dislike the card");
}
});

 adapter.add(cardModel);

 mCardContainer.setAdapter(adapter);**/
        return rootView;
    }

}
