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
        // Se llama el metodo de consultas y a la url se replasa los valores localhost por la ip del servidor
        consultas("http://localhost/AIR_Database/admin_userlist.php".replace(change, ip));
        // Metodo le asigna una funcion al momento de dar click al boton salir
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Se crea una nueva instancia de una actividad
                Intent menu_home_back = new Intent(admin_usermanegre.this, admin_login.class);
                // Se inicia la instancia
                startActivity(menu_home_back);
                // Finaliza la activity actual
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
                // Se crea una variable json vacia
                JSONObject jsonObject = null;
                // Se crea una lista de elementos con el listelement_user
                elements = new ArrayList<>();
                // Un bucle que procesara la respuesta elemento por elemento
                for (int i = 0; i < response.length(); i++) {
                    // Try Catch para manejar posibles errores
                    try {
                        // Se va almacenando la respuesta en el json vacio
                        jsonObject = response.getJSONObject(i);
                        // Finaliza el elemento grafico de carga
                        loading.dismiss();
                        // Al listelement se pasan los elementos para su adaptador
                        elements.add(new ListElement_User("ID: "+jsonObject.getString("cod_usuario"), jsonObject.getString("tipo_docu_usuario"), "Nª DOC: "+jsonObject.getString("cedula_usuario"), jsonObject.getString("nombre_usuario"), jsonObject.getString("apell_usuario"), jsonObject.getString("email_usuario"), jsonObject.getString("pass_user"), "ESTADO: "+jsonObject.getString("estado"), jsonObject.getString("rol_user")));
                        // logcat para  verificar que dichos elementos por consola (No visible al usuario).
                        Log.d("Bien", elements.toString());
                        // Si hay un error en la respuesta
                    } catch (JSONException e) {
                        // Finaliza el elemento grafico de carga
                        loading.dismiss();
                        Log.d("Mal", e.getMessage());
                        // Toast del error de la respuesta
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                // Se asigna los elementos al adaptador listelement_user con los valores de la respuesta
                ListAdapter_user listAdapter2 = new ListAdapter_user(elements, admin_usermanegre.this, new ListAdapter_user.OnItemClickListener() {
                    @Override
                    // La anotación @Override simplemente se utiliza, para forzar al compilador a comprobar en tiempo de compilación que estás sobrescribiendo correctamente un método, y de este modo evitar errores en tiempo de ejecución, los cuales serían mucho más difíciles de detectar.
                    public void onItemClick(ListElement_User item) {
                        // Ejecutara un metodo que nos enviara a una vista de modificacion de datos del usuario
                        moveToDescriptionUser(item);
                        // Toast para todo correcto
                        Toast.makeText(getApplicationContext(), "Correcto", Toast.LENGTH_LONG).show();
                    }
                });
                // Se crea una variable que contendra al recyclerview en el xml
                RecyclerView recyclerView = findViewById(R.id.listRecycleView2);
                // Se asigna un layout para poder mostrarse en pantalla
                recyclerView.setLayoutManager(new LinearLayoutManager(admin_usermanegre.this));
                // En el caso de requirir se corregira el tamaño teniendo el cuenta el tamaño de la activity
                recyclerView.setHasFixedSize(true);
                // Se asigna los elementos al adaptador
                recyclerView.setAdapter(listAdapter2);
            }
        }, new Response.ErrorListener() {
            @Override
            // Si es negativa devolvera un maensaje de error
            public void onErrorResponse(VolleyError error) {
                // Finaliza el elemento grafico de carga
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
        // Se abrira una nueva actividad que este caso sera el control panel de usuario
        Intent intent = new Intent(admin_usermanegre.this, admin_usercontrolpanel.class);
        // Se enviara los elementos del listelemnt_user a la actividad
        intent.putExtra("ListElement_user", item);
        // Se inicia la actividad
        startActivity(intent);
    }
}