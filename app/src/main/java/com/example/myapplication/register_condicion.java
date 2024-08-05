package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class register_condicion extends AppCompatActivity {

    String user_rol, user_id, estado = "";

    String[] condiciones;

    private Spinner condicion_one, condicion_two, condicion_three, condicion_four, condicion_five;
    private String[] discapacidad_list = {"Ninguna", "Discapacidad física", "Discapacidad auditiva", "Discapacidad visual", "Discapacidad intelectual", "Discapacidad psíquica"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_condicion);
        Bundle user_info = this.getIntent().getExtras();
        user_id = user_info.getString("user_id");
        user_rol = user_info.getString("user_rol");
        condicion_one = findViewById(R.id.condicion_one);
        condicion_two = findViewById(R.id.condicion_two);
        condicion_three = findViewById(R.id.condicion_three);
        condicion_four = findViewById(R.id.condicion_four);
        condicion_five = findViewById(R.id.condicion_five);
        ArrayAdapter<String> selector=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, discapacidad_list);
        condicion_one.setAdapter(selector);
        condicion_two.setAdapter(selector);
        condicion_three.setAdapter(selector);
        condicion_four.setAdapter(selector);
        condicion_five.setAdapter(selector);
        Button siguiente = findViewById(R.id.btt_next_verificar);
        Intent validar = new Intent(register_condicion.this, register_validar.class);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                condiciones = new String[]{condicion_one.getSelectedItem().toString(), condicion_two.getSelectedItem().toString(), condicion_three.getSelectedItem().toString(), condicion_four.getSelectedItem().toString(), condicion_five.getSelectedItem().toString()};
                validar.putExtra("user_id", user_id);
                validar.putExtra("rol", user_rol);
                for (int i = 0; i < condiciones.length; i++) {
                    servicio("http://10.201.131.12/AIR_Database/register_condicion.php", i);
                }
                startActivity(validar);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private  void servicio(String URL, int index) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                estado = "Correct";
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                estado = "failed";
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("cod_usuario_fk", user_id);
                parametros.put("condicion_usuario", condiciones[index]);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}