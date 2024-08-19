package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
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

public class admin_login extends AppCompatActivity {

    String ip = "192.168.43.143";
    String change = "localhost";
    EditText ndoc, password;
    private Spinner docselect;
    private String[] documentos = {"Cedula de Ciudadania", "Tarjeta de Indetidad", "Cedula de Extranjeria", "PEP", "Permiso por protección Temporal"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_login);
        Button login = findViewById(R.id.bttlogin);
        // Llamar los botones de la vista MainActivity
        ImageButton show_pass = findViewById(R.id.btt_pass_show);
        ndoc = findViewById(R.id.ndoc);
        password = findViewById(R.id.password);
        // Asignar un evento del tipo touch (Toque)
        show_pass.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // Toma los cambios del evento
                switch ( event.getAction() ) {
                    // Si el boton se oprime cambia de textpassword a text
                    case MotionEvent.ACTION_DOWN:
                        password.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    // Si ele boton deja de oprimirse se cambia de text a textpassword
                    case MotionEvent.ACTION_UP:
                        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ndoc.getText().toString().trim().isEmpty() && !password.getText().toString().trim().isEmpty()) {
                    login_admin("http://localhost/AIR_Database/Login_admin.php".replace(change, ip));
                } else if (ndoc.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty()) {
                    Toast.makeText(admin_login.this, "Los campos estan vacios", Toast.LENGTH_LONG).show();
                }
            }
        });
        docselect = findViewById(R.id.docselect);
        ArrayAdapter<String> selector=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, documentos);
        docselect.setAdapter(selector);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private  void login_admin(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Intent login = new Intent(admin_login.this, admin_usermanegre.class);
                    login.putExtra("doc", ndoc.getText().toString());
                    startActivity(login);
                }
                else {
                    Toast.makeText(admin_login.this, "Usuario no registrado o esta INACTIVO", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(admin_login.this, "El usuario no existe", Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("tipo", docselect.getSelectedItem().toString());
                parametros.put("usuario", ndoc.getText().toString());
                parametros.put("password", password.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}