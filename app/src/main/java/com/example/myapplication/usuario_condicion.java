package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class usuario_condicion extends AppCompatActivity {
    RequestQueue requestQueue;
    //private TextView txt_nombre, txt_apellidos, txt_email_user, txt_n_doc ,txt_password, docselect;
    String ndoc, rol;
    Button btt_next_usuario, btt_next_rolinfo, btt_next_menu;
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_usuario_condicion);
        Bundle extras = this.getIntent().getExtras();
        ndoc = extras.getString("doc");
//        txt_nombre = findViewById(R.id.txt_username);
//        txt_apellidos = findViewById(R.id.txt_lastname);
//        txt_n_doc = findViewById(R.id.txt_n_doc);
//        txt_email_user = findViewById(R.id.txt_email_user);
//        txt_password = findViewById(R.id.txt_password_user);
        btt_next_usuario = findViewById(R.id.btt_next_condicion);
        btt_next_rolinfo = findViewById(R.id.btt_next_rolinfo);
        btt_next_menu = findViewById(R.id.btt_next_menu);
        container = findViewById(R.id.padre);
        btt_next_rolinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btt_next_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(usuario_condicion.this, menu_home.class);
                menu.putExtra("doc", ndoc);
                startActivity(menu);
                finishAffinity();
            }
        });
        buscarid("http://192.168.43.143/AIR_Database/userinfo_condicion.php?cedula_usuario="+ndoc+"");
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
                        Log.d("Bien", response.toString());
//                        txt_nombre.setText(jsonObject.getString("condicion_usuario"));
//                        txt_apellidos.setText(jsonObject.getString("condicion_usuario"));
//                        docselect.setText(jsonObject.getString("condicion_usuario"));
//                        txt_n_doc.setText(jsonObject.getString("condicion_usuario"));
//                        txt_email_user.setText(jsonObject.getString("condicion_usuario"));
//                        txt_password.setText(jsonObject.getString("condicion_usuario"));
                       TextView textView = new TextView(getApplicationContext());
                       textView.setWidth(200);
                       textView.setHeight(50);
                       //textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                       //textView.setTextSize(34);
                       //textView.setBackground(Drawable.createFromPath("@drawable/roundcorner"));
                       //textView.setPadding(10,10,10,10);
                       textView.setText(jsonObject.getString("condicion_usuario"));
                       container.addView(textView);
                       //rol = jsonObject.getString("rol_user");
                    } catch (JSONException e) {
                        loading.dismiss();
                        Log.d("Mal", e.getMessage());
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