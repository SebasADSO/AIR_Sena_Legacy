package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class menu_home extends AppCompatActivity {

    RequestQueue requestQueue;

    String ip = "192.168.43.143";
    String change = "localhost";

    TextView txt_mensaje;

    String ndoc, rol;

    ImageButton btt_reportar, btt_consultar, btt_usuario, btt_config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_home);
        txt_mensaje = findViewById(R.id.txt_mensaje);
        txt_mensaje.setText("Bienvenid@");
        ImageButton logout = findViewById(R.id.btt_logout);
        btt_reportar = findViewById(R.id.btt_reportar);
        btt_consultar = findViewById(R.id.btt_consultar);
        btt_usuario = findViewById(R.id.btt_usuario);
        btt_config = findViewById(R.id.btt_config);
        Bundle extras = this.getIntent().getExtras();
        ndoc = extras.getString("doc");
        buscarol("http://localhost/AIR_Database/userinfo_buscarrol.php?cedula_usuario=".replace(change, ip)+ndoc+"");
        btt_reportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reportar = new Intent(menu_home.this, menu_reportar.class);
                reportar.putExtra("doc", ndoc);
                startActivity(reportar);
            }
        });
        btt_consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent consulta = new Intent(menu_home.this, menu_consultaApr.class);
                consulta.putExtra("rol", rol);
                consulta.putExtra("doc", ndoc);
                startActivity(consulta);
            }
        });
        btt_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent user = new Intent(menu_home.this, Menu_usuario.class);
                user.putExtra("doc", ndoc);
                startActivity(user);
            }
        });
        btt_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent config = new Intent(menu_home.this, Menu_config.class);
                startActivity(config);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu_home_back = new Intent(menu_home.this, MainActivity.class);
                startActivity(menu_home_back);
                finishAffinity();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void buscarol(String URL) {
        final ProgressDialog loading = ProgressDialog.show(this, "cargando...", "Espere por favor");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        loading.dismiss();
                        rol = jsonObject.getString("rol_user");
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