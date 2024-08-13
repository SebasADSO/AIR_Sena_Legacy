package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.EventLogTags;
import android.util.Log;
import android.view.View;
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
    String ip = "192.168.43.143";
    String change = "localhost";
    RequestQueue requestQueue;
    String ndoc, rol;
    List<ListElement> elements;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_consulta_apr);
        Bundle extras = this.getIntent().getExtras();
        rol = extras.getString("rol");
        ndoc = extras.getString("doc");
        ImageButton logout = findViewById(R.id.btt_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu_home_back = new Intent(menu_consultaApr.this, MainActivity.class);
                startActivity(menu_home_back);
                finishAffinity();
            }
        });
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
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void consultas(String URL) {
        final ProgressDialog loading = ProgressDialog.show(this, "cargando...", "Espere por favor");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                elements = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        loading.dismiss();
                        elements.add(new ListElement("ID: "+jsonObject.getString("id_reporte"), "Codigo del usuario: "+jsonObject.getString("cod_usuario_fk"), "Encabezado: "+jsonObject.getString("encabezado_reporte"), "Descripcion: "+ jsonObject.getString("descripcion_reporte"), "Ubicación: "+jsonObject.getString("ubicacion"), "Fecha y hora: "+jsonObject.getString("fecha_hora_reporte"), jsonObject.getString("soporte_reporte")));
                        Log.d("Bien", response.toString());

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
    public void moveToDescription(ListElement item) {
        switch (rol) {
            case "aprendiz":
                Intent intent = new Intent(menu_consultaApr.this, DescriptionActivity.class);
                intent.putExtra("ListElement", item);
                startActivity(intent);
                break;
            case "instructor":
                Intent intent2 = new Intent(menu_consultaApr.this, DescriptionActivity.class);
                intent2.putExtra("ListElement", item);
                startActivity(intent2);
                break;
            case "funcionario":
                Intent intent3 = new Intent(menu_consultaApr.this, menu_consultafunc.class);
                intent3.putExtra("ListElement", item);
                startActivity(intent3);
                break;
        }

    }
}