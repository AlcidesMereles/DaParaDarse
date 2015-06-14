package lp4.untref.daparadarse;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Spider on 07/06/2015.
 */
public class Fragment_Chats extends Fragment {


    OnHeadlineSelectedListener mCallback;
    View rootView;
    Button miBoton;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Nos aseguramos de que la actividad contenedora haya implementado la
        // interfaz de retrollamada. Si no, lanzamos una excepción
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " debe implementar OnHeadlineSelectedListener");
        }

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fm_chats, container, false);
        return rootView;
    }

    public interface OnHeadlineSelectedListener {
        void onArticleSelected(int position);
    }


}
