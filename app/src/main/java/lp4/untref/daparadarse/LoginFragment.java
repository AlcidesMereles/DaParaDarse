package lp4.untref.daparadarse;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    //TODO
    private CheckBox interesanMujeres;
    private CheckBox interesanHombres;
    EditText edadText;
    EditText ciudad;
    EditText provincia;
    EditText pais;
    EditText rangoDeEdadDesde;
    EditText rangoDeEdadHasta;
    private Button btnGuardar;
    private String nombre, apellido, edad, facebookID, sexo, mujeres, hombres;
    String nacimiento, nombreCiudad, nombreProvincia, nombrePais;
    private UiLifecycleHelper uiHelper;
    private View otherView;
    private boolean isOpen = false;


    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }

    private static final String TAG = "LoginFragment";

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        otherView = view.findViewById(R.id.view_formulario);
        if (savedInstanceState == null) {
            otherView.setVisibility(View.INVISIBLE);
        } else {
            otherView.setVisibility(View.VISIBLE);
        }
//        final MainActivity mainActivity;
//        mujeres = "0";
//        hombres = "0";
//
//        rangoDeEdadDesde = (EditText) view.findViewById(R.id.editTextEdadDesde);
//        rangoDeEdadHasta = (EditText) view.findViewById(R.id.editTextEdaHasta);
//        btnGuardar = (Button) view.findViewById(R.id.btnGuardar);
//        ciudad = (EditText) view.findViewById(R.id.editTextCiudad);
//        provincia = (EditText) view.findViewById(R.id.editTextProvincia);
//        edadText = (EditText) view.findViewById(R.id.editTextEdad);
//        ciudad = (EditText) view.findViewById(R.id.editTextCiudad);
//        provincia = (EditText) view.findViewById(R.id.editTextProvincia);
//        pais = (EditText) view.findViewById(R.id.editTextPais);
//        interesanMujeres = (CheckBox) view.findViewById(R.id.checkBoxMujeres);
//        interesanHombres = (CheckBox) view.findViewById(R.id.checkBoxHombres);
//
//
//        Log.i("Tag del Fragment", getTag());
//
//
//        try {
//            mainActivity = (MainActivity) getActivity();
////            facebookID = mainActivity.facebookID;
////            sexo = mainActivity.sexo;
////            nombre = mainActivity.nombre;
////            apellido = mainActivity.apellido;
//        } catch (java.lang.ClassCastException e) {
//        }
//
//
//        TextView name = (TextView) view.findViewById(R.id.name);
//        name.setText("Bienvenido " + nombre + " " + apellido);
//
//
//        btnGuardar
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        edad = edadText.getText().toString();
//                        nombreCiudad = ciudad.getText()
//                                .toString();
//                        nombreProvincia = provincia.getText()
//                                .toString();
//                        nombrePais = pais.getText().toString();
//                        // TODO Agregar la informacion que falta
//                        Map<String, String> map = new HashMap<String, String>();
//                        map.put("nombre", nombre);
//                        map.put("apellido", apellido);
//                        map.put("edad", edadText.getText()
//                                .toString());
//                        map.put("facebookID", facebookID);
//                        map.put("sexo", sexo);
//                        map.put("ciudad", ciudad.getText()
//                                .toString());
//                        map.put("provincia", provincia.getText().toString());
//                        map.put("pais", pais.getText().toString());
//                        map.put("rangoDeEdadDesde",
//                                rangoDeEdadDesde.getText()
//                                        .toString());
//                        map.put("rangoDeEdadHasta",
//                                rangoDeEdadHasta.getText()
//                                        .toString());
//                        // TODO Refactorizar codigo.
//                        if (map.containsValue("")
//                                || (!interesanMujeres
//                                .isChecked() && !interesanHombres
//                                .isChecked())) {
//                            Toast.makeText(
//                                    getActivity().getApplicationContext(),
//                                    "Complete todos los campos por favor.",
//                                    Toast.LENGTH_SHORT).show();
//                            // } else if (!sonTodosEnteros()) {
//                            // Toast.makeText(getApplicationContext(),
//                            // "Error: Debe ingresar solo numeros enteros",
//                            // Toast.LENGTH_SHORT).show();
//                        } else if (Integer.parseInt(edadText
//                                .getText().toString()) < 18) {
//                            Toast.makeText(
//                                    getActivity().getApplicationContext(),
//                                    "Debes ser mayor de 18 años para poder usar la aplicacion",
//                                    Toast.LENGTH_SHORT).show();
//                        } else if (Integer
//                                .parseInt(rangoDeEdadDesde
//                                        .getText().toString()) < 18
//                                || Integer
//                                .parseInt(rangoDeEdadHasta
//                                        .getText()
//                                        .toString()) < 18) {
//                            Toast.makeText(
//                                    getActivity().getApplicationContext(),
//                                    "La edad minima es de 18 años",
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            if (interesanMujeres.isChecked()) {
//                                mujeres = "1";
//                            }
//                            if (interesanHombres.isChecked()) {
//                                hombres = "1";
//                            }
//                            map.put("mujeres", mujeres);
//                            map.put("hombres", hombres);
//                            TareaEnvioDeDatos envioDeDatos = new TareaEnvioDeDatos();
//                            envioDeDatos.execute(map);
////                            Intent pasoDelIdDeFacebook = new Intent(
////                                    getActivity(),
////                                    ListadoActivity.class);
////                            pasoDelIdDeFacebook.putExtra(
////                                    "facebookUserID",
////                                    facebookID);
////                            startActivity(pasoDelIdDeFacebook);
//
////                            if (getActivity() instanceof MainActivity) {
////                                Intent intent = new Intent(getActivity(), MainActivity.class);
////                                intent.putExtra("facebookID", facebookID);
////                                intent.putExtra("nombre", nombre);
////                                intent.putExtra("apellido", apellido);
////                                intent.putExtra("sexo", sexo);
////                                intent.putExtra("edad", edad);
////                                intent.putExtra("ciudad", ciudad.getText().toString());
////                                intent.putExtra("provincia", provincia.getText().toString());
////                                intent.putExtra("pais", pais.getText().toString());
////                                intent.putExtra("mujeres", mujeres);
////                                intent.putExtra("hombres", hombres);
////                                startActivity(intent);
////                            }
//                        }
//
//
//                        // TODO borrar lineas despues de probar
//                        Log.i("edad", edad);
//                        Log.i("nombreCiudad", nombreCiudad);
//                        Log.i("nombreProvincia", nombreProvincia);
//                        Log.i("nombrePais", nombrePais);
//                        Log.i("mujeres", mujeres);
//                        Log.i("hombres", hombres);
//                    }
//                });
        return view;
    }

    public Fragment getThisFragment() {
        return this;
    }

    public void setViewVisible(int visibilidad) {
        otherView.setVisibility(visibilidad);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("visibility", otherView.getVisibility());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            setViewVisible(savedInstanceState.getInt("visibility"));
        }
    }

}
