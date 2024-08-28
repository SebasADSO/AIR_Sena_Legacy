package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DescriptionActivity extends AppCompatActivity {
    // Llamado de los elementos textview, edittext, button y creacion de string
    RequestQueue requestQueue;
    String ip = app_config.ip_server;
    String change = "localhost";
    String id, ndoc, rol;
    TextView id_reporte, cod_usuario, encabezado, descripcion, ubicacion, fecha_hora, txt_nivel_peligro, txt_tipo_peligro, txt_fecha_revision, txt_estado;
    ImageView soporte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_description);
        // se obtine los elemento del ListElement_user
        ListElement element = (ListElement) getIntent().getSerializableExtra("ListElement");
        // Se obtiene un bundle con la informacion de la anterior activity
        Bundle extras = getIntent().getExtras();
        rol =extras.getString("rol");
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

        // Metodo le asigna una funcion al momento de dar click al boton salir
        ImageButton logout = findViewById(R.id.btt_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu_home_back = new Intent(DescriptionActivity.this, MainActivity.class);
                startActivity(menu_home_back);
                finishAffinity();
            }
        });

        //Se establece los textos a las cajas de texto
        id_reporte.setText(element.getId_reporte());
        cod_usuario.setText(element.getCod_usuario_fk());
        encabezado.setText(element.getEncabezado_reporte());
        descripcion.setText(element.getDescripcion_reporte());
        ubicacion.setText(element.getUbicacion());
        fecha_hora.setText(element.getFecha_hora_reporte());



        Toast.makeText(DescriptionActivity.this, id, Toast.LENGTH_SHORT).show();

        // Se llama a la informacion del estado del reporte por medio del id de reporte
        estado("http://localhost/AIR_Database/consulta_estado.php?id_reporte=".replace(change, ip)+id.replace("ID:", "")+"");

        // Se ajusta el link donde se obtendra la imagen del reporte
        String link = element.getSoporte_reporte().replace(change, ip);

        // Se usa la dependencia de Picasso para poder obtener imagenes desde la web y se muestra atravez de un ImagenView
        Picasso.get()
                .load(link)
                .into(soporte);

        // Retrocede hacia el anterior menu, devolviendo los datos necesarios para su funcionamiento
        Button btt_salir = findViewById(R.id.btt_salir);
        btt_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent salir = new Intent(DescriptionActivity.this, menu_consultaApr.class);
                salir.putExtra("doc", ndoc);
                salir.putExtra("rol", rol);
                startActivity(salir);
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
}