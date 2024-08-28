package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class admin_usermanegre extends AppCompatActivity {
    // Llamado de los elementos textview, edittext, button y creacion de string
    String ndoc;
    String ip = app_config.ip_server;
    String change = "localhost";
    RequestQueue requestQueue;
    List<ListElement_User> elements;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_usermanegre);
        // Se obtiene un bundle con la informacion de la anterior activity
        Bundle extras = this.getIntent().getExtras();
        ndoc = extras.getString("doc");
        ImageButton logout = findViewById(R.id.btt_logout);
        // Se llama el metodo de consultas
        consultas("http://localhost/AIR_Database/admin_userlist.php".replace(change, ip));
        // Metodo le asigna una funcion al momento de dar click al boton salir
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu_home_back = new Intent(admin_usermanegre.this, admin_login.class);
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
    // Se creara un metodo get para optener los datos de los usuarios
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
                        elements.add(new ListElement_User("ID: "+jsonObject.getString("cod_usuario"), jsonObject.getString("tipo_docu_usuario"), "Nª DOC: "+jsonObject.getString("cedula_usuario"), jsonObject.getString("nombre_usuario"), jsonObject.getString("apell_usuario"), jsonObject.getString("email_usuario"), jsonObject.getString("pass_user"), "ESTADO: "+jsonObject.getString("estado"), jsonObject.getString("rol_user")));
                        Log.d("Bien", elements.toString());
                    } catch (JSONException e) {
                        loading.dismiss();
                        Log.d("Mal", e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                ListAdapter_user listAdapter2 = new ListAdapter_user(elements, admin_usermanegre.this, new ListAdapter_user.OnItemClickListener() {
                    @Override
                    public void onItemClick(ListElement_User item) {
                        moveToDescriptionUser(item);
                        Toast.makeText(getApplicationContext(), "Correcto", Toast.LENGTH_LONG).show();
                    }
                });
                // Se establece el adaptador a listelement del metodo
                RecyclerView recyclerView = findViewById(R.id.listRecycleView2);
                recyclerView.setLayoutManager(new LinearLayoutManager(admin_usermanegre.this));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(listAdapter2);
            }
        }, new Response.ErrorListener() {
            @Override
            // Si es negativa devolvera un maensaje de error
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Log.d("error", error.getMessage().toString());
                Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        }
        );
        // Se lanza la solicitud
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    // Metodo encargado del mover hacia la vista de informacion del usuario
    public void moveToDescriptionUser(ListElement_User item) {
        Intent intent = new Intent(admin_usermanegre.this, admin_usercontrolpanel.class);
        intent.putExtra("ListElement_user", item);
        startActivity(intent);
    }
}