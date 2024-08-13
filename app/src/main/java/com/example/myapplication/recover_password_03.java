package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class recover_password_03 extends AppCompatActivity {
    String email;
    EditText password, password_confirm;
    String ip = "192.168.43.143";
    String change = "localhost";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recover_password03);
        password = findViewById(R.id.txt_password);
        password_confirm = findViewById(R.id.txt_password_confirm);
        Bundle extras = this.getIntent().getExtras();
        email = extras.getString("email");
        Button cambiar = findViewById(R.id.btt_recover_cambiar);
        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().isEmpty() || password_confirm.getText().toString().isEmpty()) {
                    Toast.makeText( recover_password_03.this,"No puede dejar campos vacios", Toast.LENGTH_LONG).show();
                }
                else if ((password.getText().toString()).equals(password_confirm.getText().toString())) {
                    servicio("http://localhost/AIR_Database/recover_pass.php".replace(change, ip));
                    Intent pass_04 = new Intent(recover_password_03.this, recover_password_04.class);
                    startActivity(pass_04);
                }
                else {
                    Toast.makeText( recover_password_03.this,"Las contraseñas no son iguales", Toast.LENGTH_LONG).show();
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private  void servicio(String URL) {
        final ProgressDialog loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(getApplicationContext(), "La contraseña a sido cambiada", Toast.LENGTH_SHORT).show();
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
                parametros.put("email_usuario", email);
                parametros.put("pass_user", password.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}