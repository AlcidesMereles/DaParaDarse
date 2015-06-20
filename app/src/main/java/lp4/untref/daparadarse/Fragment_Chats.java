package lp4.untref.daparadarse;

import android.app.Activity;
import android.content.Intent;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fm_chats, container, false);
        Button boton = (Button) rootView.findViewById(R.id.boton);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    /* si estoy conectado muestro el boton de reserva online */
                Intent miIntent = new Intent(getActivity(), ActivityCoincidencias.class);
                startActivity(miIntent);

            }
        });
        return rootView;
    }

    public interface OnHeadlineSelectedListener {
        void onArticleSelected(int position);
    }

    public class Listener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), ActivityCoincidencias.class);
            startActivity(intent);
        }
    }


}
