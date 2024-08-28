package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class menu_consultaApr extends AppCompatActivity {
    // Llamado de los elementos textview, edittext, button y creacion de string
    String ip = app_config.ip_server;
    String change = "localhost";
    RequestQueue requestQueue;
    String ndoc, rol;
    List<ListElement> elements;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_consulta_apr);
        // Se obtiene un bundle con la informacion de la anterior activity
        Bundle extras = this.getIntent().getExtras();
        rol = extras.getString("rol");
        ndoc = extras.getString("doc");
        ImageButton logout = findViewById(R.id.btt_logout);
        // Metodo le asigna una funcion al momento de dar click al boton salir
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu_home_back = new Intent(menu_consultaApr.this, MainActivity.class);
                startActivity(menu_home_back);
                finishAffinity();
            }
        });
        // Llamado al servidor de acuerdo al rol del usuario
        switch (rol) {
            case "aprendiz":
                consultas("http://localhost/AIR_Database/consultas_apr.php?cedula_usuario=".replace(change, ip)+ndoc+"");
                break;
            case "instructor":
                consultas("http://localhost/AIR_Database/consultas_apr.php?cedula_usuario=".replace(change, ip)+ndoc+"");
                break;
            case "funcionario":
                consultas("http://localhost/AIR_Database/consulta_func.php".replace(change, ip));
                break;
            case "admin":
                consultas("http://localhost/AIR_Database/consulta_func.php".replace(change, ip));
                break;
        }
        // Retrocede hacia el anterior menu, devolviendo los datos necesarios para su funcionamiento
        Button btt_salir = findViewById(R.id.btt_salir);
        btt_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent salir = new Intent(menu_consultaApr.this, menu_home.class);
                salir.putExtra("doc", ndoc);
                startActivity(salir);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    // Se creara un metodo get para optener los datos de los reportes
    private void consultas(String URL) {
        // Se inicia el elemnto grafico de una barra de carga
        final ProgressDialog loading = ProgressDialog.show(this, "cargando...", "Espere por favor");
        // Se crea una peticion que devolvera un array
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            // Si la repuesta es positiva devolvera los datos
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                elements = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        loading.dismiss();
                        elements.add(new ListElement("ID: "+jsonObject.getString("id_reporte"), "Codigo del usuario: "+jsonObject.getString("cod_usuario_fk"), "Encabezado: "+jsonObject.getString("encabezado_reporte"), "Descripcion: "+ jsonObject.getString("descripcion_reporte"), "Ubicación: "+jsonObject.getString("ubicacion"), "Fecha y hora: "+jsonObject.getString("fecha_hora_reporte"), jsonObject.getString("soporte_reporte"), jsonObject.getString("estado")));
                        Log.d("Bien", elements.toString());

                    } catch (JSONException e) {
                        loading.dismiss();
                        Log.d("Mal", e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                ListAdapter listAdapter = new ListAdapter(elements, menu_consultaApr.this, new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ListElement item) {
                        moveToDescription(item);
                    }
                });
                RecyclerView recyclerView = findViewById(R.id.listRecycleView);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(menu_consultaApr.this));
                recyclerView.setAdapter(listAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            // Si es negativa devolvera un maensaje de error
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.d("error", error.getMessage().toString());
                Toast.makeText(getApplicationContext(), "No se encontraron reportes", Toast.LENGTH_SHORT).show();
            }
        }
        );
        // Se lanza la solicitud
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    // se crea un metodo que nos permitira movernos hacia las vista que mostrara la informacion del reporte
    public void moveToDescription(ListElement item) {
        switch (rol) {
            case "aprendiz":
                Intent intent = new Intent(menu_consultaApr.this, DescriptionActivity.class);
                intent.putExtra("doc", ndoc);
                intent.putExtra("rol", rol);
                intent.putExtra("ListElement", item);
                startActivity(intent);
                break;
            case "instructor":
                Intent intent2 = new Intent(menu_consultaApr.this, DescriptionActivity.class);
                intent2.putExtra("doc", ndoc);
                intent2.putExtra("rol", rol);
                intent2.putExtra("ListElement", item);
                startActivity(intent2);
                break;
            case "funcionario":
                Intent intent3 = new Intent(menu_consultaApr.this, menu_consultafunc.class);
                intent3.putExtra("doc", ndoc);
                intent3.putExtra("rol", rol);
                intent3.putExtra("ListElement", item);
                startActivity(intent3);
                break;
            case "admin":
                Intent intent4 = new Intent(menu_consultaApr.this, DescriptionActivity.class);
                intent4.putExtra("doc", ndoc);
                intent4.putExtra("rol", rol);
                intent4.putExtra("ListElement", item);
                startActivity(intent4);
                break;
        }

    }
}