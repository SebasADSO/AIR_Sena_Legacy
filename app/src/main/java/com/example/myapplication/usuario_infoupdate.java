package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class usuario_infoupdate extends AppCompatActivity {
    // Llamado de los elementos textview, edittext, button y creacion de string
    private TextView txt_n_doc , docselect;
    EditText txt_nombre, txt_apellidos, txt_email_user;
    String ndoc;
    String ip = app_config.ip_server;
    String change = "localhost";
    RequestQueue requestQueue;
    Button btt_next_cancel, btt_update_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_usuario_infoupdate);
        // Se obtiene un bundle con la informacion de la anterior activity
        Bundle extras = this.getIntent().getExtras();
        ndoc = extras.getString("doc");
        // Se llaman los elemntos del xml
        txt_nombre = findViewById(R.id.txt_username);
        txt_apellidos = findViewById(R.id.txt_lastname);
        docselect = findViewById(R.id.docselect_register);
        txt_n_doc = findViewById(R.id.txt_n_doc);
        txt_email_user = findViewById(R.id.txt_email_user);
        buscarid("http://localhost/AIR_Database/userinfo_datauser.php?cedula_usuario=".replace(change, ip)+ndoc+"");
        ImageButton logout = findViewById(R.id.btt_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu_home_back = new Intent(usuario_infoupdate.this, MainActivity.class);
                startActivity(menu_home_back);
                finishAffinity();
            }
        });
        btt_next_cancel = findViewById(R.id.btt_next_cancel);
        btt_next_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(usuario_infoupdate.this, Menu_usuario.class);
                intent.putExtra("doc", ndoc);
                startActivity(intent);
            }
        });
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String textPattern = "[a-zA-Z ]+";
        btt_update_data = findViewById(R.id.btt_update_data);
        btt_update_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_nombre.getText().toString().isEmpty() || txt_apellidos.getText().toString().isEmpty() || txt_email_user.getText().toString().isEmpty()) {
                    Toast.makeText( usuario_infoupdate.this,"No se puede dejar campos vacios", Toast.LENGTH_LONG).show();
                }
                else if(!txt_nombre.getText().toString().isEmpty() || !txt_apellidos.getText().toString().isEmpty() || !txt_email_user.getText().toString().isEmpty()) {
                    if ((txt_email_user.getText().toString()).matches(emailPattern)) {
                        if ((txt_nombre.getText().toString().matches(textPattern)) && (txt_apellidos.getText().toString()).matches(textPattern)) {
                            update("http://localhost/AIR_Database/usuario_updateinfo.php".replace(change, ip));
                        }
                        else  {
                            Toast.makeText(usuario_infoupdate.this, "Los campos de nombre y apellido solo permite letras y espacios", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText( usuario_infoupdate.this,"El correo electronico es invalido", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
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
    /**

     * Realiza una solicitud HTTP de tipo POST a la dirección URL proporcionada, enviando parámetros específicos y mostrando un diálogo de progreso mientras se realiza la solicitud.

     *

     * @param URL La dirección URL del servidor donde se realizará la solicitud HTTP.

     */
    private  void update(String URL) {
        final ProgressDialog loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(getApplicationContext(), "Información actualizada", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                Intent intent2 = new Intent(usuario_infoupdate.this, Menu_usuario.class);
                intent2.putExtra("doc", ndoc);
                startActivity(intent2);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Error: Intente nuevamente", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("nombre", txt_nombre.getText().toString());
                parametros.put("apellido", txt_apellidos.getText().toString());
                parametros.put("numero_documento", ndoc);
                parametros.put("email", txt_email_user.getText().toString());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}