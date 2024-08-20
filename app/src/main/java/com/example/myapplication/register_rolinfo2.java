package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class register_rolinfo2 extends AppCompatActivity {

    EditText txt_cargo_register;

    String ip = "10.201.131.13";
    String change = "localhost";

    CheckBox cb_lunes, cb_martes, cb_miercoles, cb_jueves, cb_viernes, cb_sabado;

    String user_id, user_rol, estado = "", dias = "";

    private Spinner Spinner_formacion;

    private String[] Nivel = {"Tecnico", "Tecnologo", "Especialista", "Doctorado", "Universitario",};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_rolinfo2);
        Bundle user_extra = getIntent().getExtras();
        user_id = user_extra.getString("user_id");
        user_rol = user_extra.getString("rol");
        cb_lunes = findViewById(R.id.cb_lunes);
        cb_martes = findViewById(R.id.cb_martes);
        cb_miercoles = findViewById(R.id.cb_miercoles);
        cb_jueves = findViewById(R.id.cb_jueves);
        cb_viernes = findViewById(R.id.cb_viernes);
        cb_sabado = findViewById(R.id.cb_sabado);
        Spinner_formacion = findViewById(R.id.Spinner_formacion);
        ArrayAdapter<String> selector=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Nivel);
        Spinner_formacion.setAdapter(selector);
        txt_cargo_register = findViewById(R.id.txt_cargo_register);
        txt_cargo_register.setText(user_rol);
        Button siguiente = findViewById(R.id.btt_recover_pass);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDias();
                if (txt_cargo_register.getText().toString().isEmpty()) {
                    Toast.makeText( register_rolinfo2.this,"No puede dejar campos vacios", Toast.LENGTH_LONG).show();
                }
                else if (dias.length() < 3) {
                    Toast.makeText( register_rolinfo2.this,"Debe llenar seleccionar al menos un dia", Toast.LENGTH_LONG).show();
                }else {
                    servicio("http://localhost/AIR_Database/register_instruc_func.php".replace(change, ip));
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private  void servicio(String URL) {
        final ProgressDialog loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(getApplicationContext(), "Correcto", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                Intent condicion = new Intent(register_rolinfo2.this, register_condicion.class);
                condicion.putExtra("user_id", user_id);
                condicion.putExtra("rol", user_rol);
                startActivity(condicion);
                estado = "Correct";
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                estado = "failed";
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("cod_usuario_fk", user_id);
                parametros.put("espec_encargado", user_rol);
                parametros.put("nivel_formacion", Spinner_formacion.getSelectedItem().toString());
                parametros.put("dia_laboral", dias);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void setDias() {
                if (cb_lunes.isChecked()) {
                    if (!dias.contains("Lunes; ")) {
                        dias += "Lunes; ";
                    }
                } else {
                    if (dias.contains("Lunes; ")) {
                        dias = dias.replace("Lunes; ", "");
                    }
                }
                if (cb_martes.isChecked()) {
                    if (!dias.contains("Martes; ")) {
                        dias += "Martes; ";
                    }
                } else {
                    if (dias.contains("Martes; ")) {
                        dias = dias.replace("Martes; ", "");
                    }
                }
                if (cb_miercoles.isChecked()) {
                    if (!dias.contains("Miercoles; ")) {
                        dias += "Miercoles; ";
                    }
                } else {
                    if (dias.contains("Miercoles; ")) {
                        dias = dias.replace("Miercoles; ", "");
                    }
                }
                if (cb_jueves.isChecked()) {
                    if (!dias.contains("Jueves; ")) {
                        dias += "Jueves; ";
                    }
                } else {
                    if (dias.contains("Jueves; ")) {
                        dias = dias.replace("Jueves; ", "");
                    }
                }
                if (cb_viernes.isChecked()) {
                    if (!dias.contains("Viernes; ")) {
                        dias += "Viernes; ";
                    }
                } else {
                    if (dias.contains("Viernes; ")) {
                        dias = dias.replace("Viernes; ", "");
                    }
                }
                if (cb_sabado.isChecked()) {
                    if (!dias.contains("Sabado; ")) {
                        dias += "Sabado; ";
                    }
                } else {
                    if (dias.contains("Sabado; ")) {
                        dias = dias.replace("Sabado; ", "");
                    }
                }
    }
}