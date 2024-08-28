package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class menu_consultafunc extends AppCompatActivity {
    // Llamado de los elementos textview, edittext, button y creacion de string
    String ip = app_config.ip_server;
    String change = "localhost";
    RequestQueue requestQueue;
    TextView id_reporte, cod_usuario, encabezado, descripcion, ubicacion, fecha_hora, txt_estado, txt_fecha_revision;
    EditText txt_nivel_peligro, txt_tipo_peligro;
    ImageView soporte;
    String fecha, id, ndoc, cod_re, rol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_consultafunc);
        ImageButton logout = findViewById(R.id.btt_logout);
        // Metodo le asigna una funcion al momento de dar click al boton salir
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu_home_back = new Intent(menu_consultafunc.this, MainActivity.class);
                startActivity(menu_home_back);
                finishAffinity();
            }
        });
        // se obtine los elemento del ListElement_user
        ListElement element = (ListElement) getIntent().getSerializableExtra("ListElement");
        // Se obtiene un bundle con la informacion de la anterior activity
        Bundle extras = getIntent().getExtras();
        rol = extras.getString("rol");
        ndoc = extras.getString("doc");
        id = element.getId_reporte();
        // Se llaman los elemntos del xml
        id_reporte = findViewById(R.id.txt_id_reporte);
        cod_usuario = findViewById(R.id.txt_cod_usuario);
        encabezado = findViewById(R.id.txt_encabezado);
        descripcion = findViewById(R.id.txt_desc);
        ubicacion = findViewById(R.id.txt_lugar);
        fecha_hora = findViewById(R.id.txt_fecha);
        soporte = findViewById(R.id.soporte);
        txt_tipo_peligro = findViewById(R.id.txt_tipo_peligro);
        txt_nivel_peligro = findViewById(R.id.txt_nivel_peligro);
        txt_estado = findViewById(R.id.txt_estado);
        txt_fecha_revision = findViewById(R.id.txt_fecha_revision);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:ss:mm");
        fecha = df.format(c);
        ImageButton logout2 = findViewById(R.id.btt_logout);
        logout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu_home_back = new Intent(menu_consultafunc.this, MainActivity.class);
                startActivity(menu_home_back);
                finishAffinity();
            }
        });
        //Se establece los textos a las cajas de texto
        String textPattern = "[a-zA-Z ]+";
        id_reporte.setText(element.getId_reporte());
        cod_usuario.setText(element.getCod_usuario_fk());
        encabezado.setText(element.getEncabezado_reporte());
        descripcion.setText(element.getDescripcion_reporte());
        ubicacion.setText(element.getUbicacion());
        fecha_hora.setText(element.getFecha_hora_reporte());

        // Se llama a la informacion del estado del reporte por medio del id de reporte
        Toast.makeText(menu_consultafunc.this, id, Toast.LENGTH_SHORT).show();
        buscarid("http://localhost/AIR_Database/userinfo_datauser.php?cedula_usuario=".replace(change, ip)+ndoc+"");

        estado("http://localhost/AIR_Database/consulta_estado.php?id_reporte=".replace(change, ip)+id.replace("ID:", "")+"");

        String link = element.getSoporte_reporte().replace(change, ip);

        // Se usa la dependencia de Picasso para poder obtener imagenes desde la web y se muestra atravez de un ImagenView
        Picasso.get()
                .load(link)
                .into(soporte);
        Button btt_revisar = findViewById(R.id.btt_revisar);
        // Retrocede hacia el anterior menu, devolviendo los datos necesarios para su funcionamiento
        btt_revisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_tipo_peligro.getText().toString().matches(textPattern) && txt_nivel_peligro.getText().toString().matches(textPattern)) {
                    revisar("http://localhost/AIR_Database/revision_update.php?id_reporte_fk=".replace(change, ip)+id.replace("ID:", "")+"");
                }
                else {
                    Toast.makeText(menu_consultafunc.this, "Solo se permite letras en los campos", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button btt_salir = findViewById(R.id.btt_salir);
        btt_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salir_menu();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    // Se creara un metodo get para optener los datos del reporte
    private void estado(String URL) {
        // Se inicia el elemnto grafico de una barra de carga
        final ProgressDialog loading = ProgressDialog.show(this, "cargando...", "Espere por favor");
        // Se crea una peticion que devolvera un array
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            // Si la repuesta es positiva devolvera los datos
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        loading.dismiss();
                        txt_tipo_peligro.setText(jsonObject.getString("tipo_peligro"));
                        txt_nivel_peligro.setText(jsonObject.getString("nivel_peligro"));
                        txt_fecha_revision.setText(jsonObject.getString("fecha_revision"));
                        txt_estado.setText(jsonObject.getString("estado"));
                    } catch (JSONException e) {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            // Si es negativa devolvera un maensaje de error
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        }
        );
        // Se lanza la solicitud
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    // Crea un metodo para actualizar
    private  void revisar(String URL) {
        // Se inicia la barra de carga
        final ProgressDialog loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor");
        // Se establece un stringrequest usando POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            // Si la respuesta es positiva ejecuta este bloque
            public void onResponse(String s) {
                Toast.makeText(getApplicationContext(), "REVISADO CON EXITO", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                salir_menu();
            }
        }, new Response.ErrorListener() {
            @Override
            // Si la respuesta es negativa ejecuta este bloque
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Error: Conexión perdida", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        }){
            @Override
            // Parametros a enviar
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("tipo_peligro", txt_tipo_peligro.getText().toString());
                parametros.put("nivel_peligro", txt_nivel_peligro.getText().toString());
                parametros.put("fecha_revision", fecha);
                parametros.put("estado", "REVISADO");
                parametros.put("cod_usuario_fk", cod_re);
                return parametros;
            }
        };
        // Se envia la solicitud
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void buscarid(String URL) {
        final ProgressDialog loading = ProgressDialog.show(this, "cargando...", "Espere por favor");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        loading.dismiss();
                        cod_re = jsonObject.getString("cod_usuario");
                    } catch (JSONException e) {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        }
        );
        // Se lanza la peticion
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    // Devuelve al anterior menu
    public void salir_menu() {
        Intent salir = new Intent(menu_consultafunc.this, menu_consultaApr.class);
        salir.putExtra("doc", ndoc);
        salir.putExtra("rol", rol);
        startActivity(salir);
    }
}