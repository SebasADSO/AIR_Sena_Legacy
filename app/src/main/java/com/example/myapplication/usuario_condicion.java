package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
    String ip = "192.168.43.143";
    String change = "localhost";
    RequestQueue requestQueue;
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
        ImageButton logout = findViewById(R.id.btt_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu_home_back = new Intent(usuario_condicion.this, MainActivity.class);
                startActivity(menu_home_back);
                finishAffinity();
            }
        });
        btt_next_usuario = findViewById(R.id.btt_next_userinfo);
        btt_next_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent user = new Intent(usuario_condicion.this, Menu_usuario.class);
                user.putExtra("doc", ndoc);
                startActivity(user);
            }
        });
        btt_next_rolinfo = findViewById(R.id.btt_next_rolinfo);
        btt_next_menu = findViewById(R.id.btt_next_menu);
        container = findViewById(R.id.padre);
        btt_next_rolinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (rol){
                    case "aprendiz":
                        Intent aprendiz = new Intent(usuario_condicion.this, usuario_infoA.class);
                        aprendiz.putExtra("doc", ndoc);
                        startActivity(aprendiz);
                        break;
                    case "instructor":
                        Intent insfuc = new Intent(usuario_condicion.this, usuario_info2.class);
                        insfuc.putExtra("doc", ndoc);
                        startActivity(insfuc);
                        break;
                    case "funcionario":
                        Intent insfuc2 = new Intent(usuario_condicion.this, usuario_info2.class);
                        insfuc2.putExtra("doc", ndoc);
                        startActivity(insfuc2);
                        break;
                }
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
        buscarid("http://localhost/AIR_Database/userinfo_condicion.php?cedula_usuario=".replace(change, ip)+ndoc+"");
        buscarol("http://localhost/AIR_Database/userinfo_buscarrol.php?cedula_usuario=".replace(change, ip)+ndoc+"");
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
                        TextView textView = new TextView(getApplicationContext());
                        TextView textView1 = new TextView(getApplicationContext());
                        textView.setWidth(200);
                        textView.setMaxHeight(150);
                        textView.setMinHeight(150);
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        textView.setTextSize(20);
                        textView.setBackgroundResource(R.drawable.roundcorner);
                        textView.setPadding(10,10,10,10);
                        textView1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        textView1.setTextSize(20);
                        textView1.setText("Condicion "+(i+1));
                        textView1.setPadding(10,10,10,10);
                        textView.setText(jsonObject.getString("condicion_usuario"));
                        container.addView(textView1);
                        container.addView(textView);
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
                Log.d("error", error.getMessage().toString());
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