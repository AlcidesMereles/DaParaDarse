package lp4.untref.daparadarse;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;

import demo.pantallasTinder;

public class PruebaEnviarDatos extends ActionBarActivity {
    // Create, automatically open (if applicable), save, and restore the
    // Active Session in a way that is similar to Android UI lifecycles.
    private View otherView;
    private static final String TAG = "MainActivity";
    private String nombre, apellido, edad, facebookID, sexo, mujeres, hombres;
    private String nacimiento,
            nombreCiudad,
            nombreProvincia,
            nombrePais;
    private Button btnGuardar;
    private CheckBox interesanMujeres;
    private CheckBox interesanHombres;
    EditText fechaDeNacimiento;
    EditText ciudad;
    EditText provincia;
    EditText pais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_enviar_datos);
        // Set View that should be visible after log-in invisible initially
        otherView = (View) findViewById(R.id.other_views);
        otherView.setVisibility(View.VISIBLE);
        // To maintain FB Login session
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        fechaDeNacimiento = (EditText) findViewById(R.id.editTextFechaNacimiento);
        ciudad = (EditText) findViewById(R.id.editTextCiudad);
        provincia = (EditText) findViewById(R.id.editTextProvincia);
        pais = (EditText) findViewById(R.id.editTextPais);
        interesanMujeres = (CheckBox) findViewById(R.id.checkBoxMujeres);
        interesanHombres = (CheckBox) findViewById(R.id.checkBoxHombres);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                TextView name = (TextView) findViewById(R.id.name);
                // Set view visibility to true
                otherView.setVisibility(View.VISIBLE);

                nacimiento = fechaDeNacimiento.getText().toString();
                nombreCiudad = ciudad.getText().toString();
                nombreProvincia = provincia.getText().toString();
                nombrePais = pais.getText().toString();
                if (interesanMujeres.isChecked()) {
                    mujeres = "1";
                } else {
                    mujeres = "0";
                }
                if (interesanHombres.isChecked()) {
                    hombres = "1";
                } else {
                    hombres = "0";
                }

                Map<String, String> map = new HashMap<String, String>();
                map.put("nombre", nombre);
                map.put("apellido", apellido);
                map.put("edad", edad);
                map.put("facebookID", facebookID);
                map.put("sexo", sexo);
                map.put("nacimiento", nacimiento);
                map.put("ciudad", nombreCiudad);
                map.put("provincia", nombreProvincia);
                map.put("pais", nombrePais);
                map.put("mujeres", mujeres);
                map.put("hombres", hombres);

                Log.i(TAG, nacimiento);
                Log.i(TAG, nombreCiudad);
                Log.i(TAG, nombreProvincia);
                Log.i(TAG, nombrePais);
                Log.i(TAG, mujeres);
                Log.i(TAG, hombres);

                TareaEnvioDeDatos envioDeDatos = new TareaEnvioDeDatos();
                envioDeDatos.execute(map);
            }
        });

    }

}