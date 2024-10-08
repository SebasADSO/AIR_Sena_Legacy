package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class usuario_infoA extends AppCompatActivity {
    // Llamado de los elementos textview, edittext, button y creacion de string
    String ip = app_config.ip_server;
    String change = "localhost";
    RequestQueue requestQueue;
    String ndoc;
    TextView txt_cod_program, txt_num_ficha, txt_nombre_programa, fecha1, fecha2, fecha3, jornadaview;
    Button btt_next_usuario, btt_next_rolinfo, btt_next_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_usuario_info);
        // Se obtiene un bundle con la informacion de la anterior activity
        Bundle extras = this.getIntent().getExtras();
        ndoc = extras.getString("doc");
        // Se llaman los elemntos del xml
        txt_cod_program = findViewById(R.id.txt_cod_program);
        txt_num_ficha = findViewById(R.id.txt_num_ficha);
        txt_nombre_programa = findViewById(R.id.txt_nombre_programa);
        fecha1 = findViewById(R.id.fecha1);
        fecha2 = findViewById(R.id.fecha2);
        fecha3 = findViewById(R.id.fecha3);
        jornadaview = findViewById(R.id.jornadaview);
        ImageButton logout = findViewById(R.id.btt_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu_home_back = new Intent(usuario_infoA.this, MainActivity.class);
                startActivity(menu_home_back);
                finishAffinity();
            }
        });
        btt_next_usuario = findViewById(R.id.btt_next_condicion);
        btt_next_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent condicio = new Intent(usuario_infoA.this, usuario_condicion.class);
                condicio.putExtra("doc", ndoc);
                startActivity(condicio);
            }
        });
        btt_next_rolinfo = findViewById(R.id.btt_next_rolinfo);
        btt_next_rolinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent user = new Intent(usuario_infoA.this, Menu_usuario.class);
                user.putExtra("doc", ndoc);
                startActivity(user);
            }
        });
        btt_next_menu = findViewById(R.id.btt_next_menu);
        btt_next_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(usuario_infoA.this, menu_home.class);
                menu.putExtra("doc", ndoc);
                startActivity(menu);
                finishAffinity();
            }
        });
        // Llamado de los metodos consultas
        buscarrol("http://localhost/AIR_Database/userinfo_rolaprendiz.php?cedula_usuario=".replace(change, ip)+ndoc+"");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    /**

     * Realiza una solicitud HTTP de tipo POST a la dirección URL proporcionada, enviando parámetros específicos y mostrando un diálogo de progreso mientras se realiza la solicitud.

     *

     * @param URL La dirección URL del servidor donde se realizará la solicitud HTTP.

     */
    private void buscarrol(String URL) {
        final ProgressDialog loading = ProgressDialog.show(this, "cargando...", "Espere por favor");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        loading.dismiss();
                        txt_cod_program.setText(jsonObject.getString("cod_programa"));
                        txt_num_ficha.setText(jsonObject.getString("numero_ficha"));
                        txt_nombre_programa.setText(jsonObject.getString("nombre_programa"));
                        fecha1.setText(jsonObject.getString("fecha_inicio"));
                        fecha2.setText(jsonObject.getString("fecha_final"));
                        fecha3.setText(jsonObject.getString("inicio_produc"));
                        jornadaview.setText(jsonObject.getString("jornada_programa"));
                        Log.d("Bien", response.toString());

                    } catch (JSONException e) {
                        loading.dismiss();
                        Log.d("Mal", e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.d("error", error.getMessage().toString());
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}