package demo;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import com.andtinder.model.CardModel;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;
import lp4.untref.daparadarse.R;

public class pantallasTinder extends Activity {

    /**
     * This variable is the container that will host our cards
     */
    private CardContainer mCardContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mainlayout);

        mCardContainer = (CardContainer) findViewById(R.id.layoutview);

        Resources r = getResources();

        SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(this);

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
            @Override
            public void OnClickListener() {
                Log.i("Swipeable Cards","I am pressing the card");
            }
        });

        cardModel.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
            @Override
            public void onLike() {
                Log.i("Swipeable Cards","I like the card");
            }

            @Override
            public void onDislike() {
                Log.i("Swipeable Cards","I dislike the card");
            }
        });

        adapter.add(cardModel);

        mCardContainer.setAdapter(adapter);
    }
}
