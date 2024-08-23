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
        ListElement element = (ListElement) getIntent().getSerializableExtra("ListElement");
        Bundle extras = getIntent().getExtras();
        rol =extras.getString("rol");
        ndoc = extras.getString("doc");
        id = element.getId_reporte();
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

        ImageButton logout = findViewById(R.id.btt_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu_home_back = new Intent(DescriptionActivity.this, MainActivity.class);
                startActivity(menu_home_back);
                finishAffinity();
            }
        });

        id_reporte.setText(element.getId_reporte());
        cod_usuario.setText(element.getCod_usuario_fk());
        encabezado.setText(element.getEncabezado_reporte());
        descripcion.setText(element.getDescripcion_reporte());
        ubicacion.setText(element.getUbicacion());
        fecha_hora.setText(element.getFecha_hora_reporte());



        Toast.makeText(DescriptionActivity.this, id, Toast.LENGTH_SHORT).show();

        estado("http://localhost/AIR_Database/consulta_estado.php?id_reporte=".replace(change, ip)+id.replace("ID:", "")+"");

        String link = element.getSoporte_reporte().replace(change, ip);

        Picasso.get()
                .load(link)
                .into(soporte);

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
    private void estado(String URL) {
        final ProgressDialog loading = ProgressDialog.show(this, "cargando...", "Espere por favor");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
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
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}