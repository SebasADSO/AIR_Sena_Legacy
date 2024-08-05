package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Menu_usuario extends AppCompatActivity {
    RequestQueue requestQueue;
    private TextView txt_nombre, txt_apellidos, txt_email_user, txt_n_doc , docselect;
    String ndoc, rol;
    Button btt_next_condicion, btt_next_rolinfo, btt_next_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_usuario);
        Bundle extras = this.getIntent().getExtras();
        ndoc = extras.getString("doc");
        txt_nombre = findViewById(R.id.txt_username);
        txt_apellidos = findViewById(R.id.txt_lastname);
        docselect = findViewById(R.id.docselect_register);
        txt_n_doc = findViewById(R.id.txt_n_doc);
        txt_email_user = findViewById(R.id.txt_email_user);
        btt_next_condicion = findViewById(R.id.btt_next_condicion);
        btt_next_rolinfo = findViewById(R.id.btt_next_rolinfo);
        btt_next_menu = findViewById(R.id.btt_next_menu);
        btt_next_condicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent condicion = new Intent(Menu_usuario.this, usuario_condicion.class);
                condicion.putExtra("doc", ndoc);
                startActivity(condicion);
            }
        });
        btt_next_rolinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (rol){
                    case "aprendiz":
                        return;
                    case "instructor":
                        return;
                    case "funcionario":
                        return;
                }
            }
        });
        btt_next_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(Menu_usuario.this, menu_home.class);
                menu.putExtra("doc", ndoc);
                startActivity(menu);
                finishAffinity();
            }
        });
        buscarid("http://10.201.131.12/AIR_Database/userinfo_datauser.php?cedula_usuario="+ndoc+"");
        buscarol("http://10.201.131.12/AIR_Database/userinfo_buscarrol.php?cedula_usuario="+ndoc+"");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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
                        txt_nombre.setText(jsonObject.getString("nombre_usuario"));
                        txt_apellidos.setText(jsonObject.getString("apell_usuario"));
                        docselect.setText(jsonObject.getString("tipo_docu_usuario"));
                        txt_n_doc.setText(jsonObject.getString("cedula_usuario"));
                        txt_email_user.setText(jsonObject.getString("email_usuario"));
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