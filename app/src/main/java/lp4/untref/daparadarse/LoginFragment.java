package lp4.untref.daparadarse;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private CheckBox interesanMujeres;
    private CheckBox interesanHombres;
    EditText edadText;
    EditText ciudad;
    EditText provincia;
    EditText pais;
    TextView rangoDeEdadDesde;
    TextView rangoDeEdadHasta;
    private Button btnGuardar;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        btnGuardar = (Button) view.findViewById(R.id.btnGuardar);
        ciudad = (EditText) view.findViewById(R.id.editTextCiudad);
        provincia = (EditText) view.findViewById(R.id.editTextProvincia);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MainTabSwipe.class);
                startActivity(intent);

            }
        });
        return view;
    }

}
