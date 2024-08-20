package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class menu_consultafunc extends AppCompatActivity {
    String ip = "10.201.131.13";
    String change = "localhost";
    RequestQueue requestQueue;
    TextView id_reporte, cod_usuario, encabezado, descripcion, ubicacion, fecha_hora, txt_estado, txt_fecha_revision;
    EditText txt_nivel_peligro, txt_tipo_peligro;
    ImageView soporte;
    String fecha, id, ndoc, cod_re;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_consultafunc);
        ImageButton logout = findViewById(R.id.btt_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu_home_back = new Intent(menu_consultafunc.this, MainActivity.class);
                startActivity(menu_home_back);
                finishAffinity();
            }
        });
        ListElement element = (ListElement) getIntent().getSerializableExtra("ListElement");
        Bundle extras = getIntent().getExtras();
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
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:ss:mm");
        fecha = df.format(c);
        ImageButton logout2 = findViewById(R.id.btt_logout);
        logout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu_home_back = new Intent(menu_consultafunc.this, MainActivity.class);
                startActivity(menu_home_back);
                finishAffinity();
            }
        });
        String textPattern = "[a-zA-Z ]+";
        id_reporte.setText(element.getId_reporte());
        cod_usuario.setText(element.getCod_usuario_fk());
        encabezado.setText(element.getEncabezado_reporte());
        descripcion.setText(element.getDescripcion_reporte());
        ubicacion.setText(element.getUbicacion());
        fecha_hora.setText(element.getFecha_hora_reporte());

        Toast.makeText(menu_consultafunc.this, id, Toast.LENGTH_SHORT).show();
        buscarid("http://localhost/AIR_Database/userinfo_datauser.php?cedula_usuario=".replace(change, ip)+ndoc+"");

        estado("http://localhost/AIR_Database/consulta_estado.php?id_reporte=".replace(change, ip)+id.replace("ID:", "")+"");

        String link = element.getSoporte_reporte().replace(change, ip);

        Picasso.get()
                .load(link)
                .into(soporte);
        Button btt_revisar = findViewById(R.id.btt_revisar);
        btt_revisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_tipo_peligro.getText().toString().matches(textPattern) && txt_nivel_peligro.getText().toString().matches(textPattern)) {
                    revisar("http://localhost/AIR_Database/revision_update.php?id_reporte_fk=".replace(change, ip)+id.replace("ID:", "")+"");
                }
                else {
                    Toast.makeText(menu_consultafunc.this, "Solo se permite letras en los campos", Toast.LENGTH_LONG).show();
                }
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
    private  void revisar(String URL) {
        final ProgressDialog loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(getApplicationContext(), "REVISADO CON EXITO", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Error: Conexión perdida", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("tipo_peligro", txt_tipo_peligro.getText().toString());
                parametros.put("nivel_peligro", txt_nivel_peligro.getText().toString());
                parametros.put("fecha_revision", fecha);
                parametros.put("estado", "REVISADO");
                parametros.put("cod_usuario_fk", cod_re);
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
                        cod_re = jsonObject.getString("cod_usuario");
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