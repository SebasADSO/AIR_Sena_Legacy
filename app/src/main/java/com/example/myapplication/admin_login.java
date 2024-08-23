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
    // Llamado de los elementos textview, edittext, button y creacion de string
    String ip = app_config.ip_server;
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
        // Metodo le asigna una funcion al momento de dar click al boton login administrador
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Se pasa a una condicional en la cual se valida si los campos no estan vacios y devuelve true
                if (!ndoc.getText().toString().trim().isEmpty() && !password.getText().toString().trim().isEmpty()) {
                    // Ejecucion del metodo login con los parametros asignados
                    login_admin("http://localhost/AIR_Database/Login_admin.php".replace(change, ip));
                }
                // Se pasa a una condicional en la cual se valida si los campos estan estan vacios, devuelve false y no permite el acceso
                else if (ndoc.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty()) {
                    Toast.makeText(admin_login.this, "Los campos estan vacios", Toast.LENGTH_LONG).show();
                }
            }
        });
        // Llama a los Spinners y se asignan sus correspondientes adaptadores
        docselect = findViewById(R.id.docselect);
        ArrayAdapter<String> selector=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, documentos);
        docselect.setAdapter(selector);
        Button bttexit = findViewById(R.id.bttexit);
        bttexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_home = new Intent(admin_login.this, MainActivity.class);
                startActivity(main_home);
                finishAffinity();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    // Creacion del metodo que validara la informacion suministrada por el administrador y la validara con la base de datos
    private  void login_admin(String URL) {
        // Se crea una nueva solicitud a un seervidor esta solicitud se dara con el metodo POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            // Al obtener una respuesta del servidor se actira este metodo que pasara como respuesta correcta
            public void onResponse(String response) {
                // Se evaluara si la respuesta no esta vacia y devolvera true
                if(!response.isEmpty()){
                    // Se abrira una nueva actividad que este caso sera el gestion de usuarios
                    Intent login = new Intent(admin_login.this, admin_usermanegre.class);
                    // Se mandara los datos a la otra actividad que se definio en el Intent
                    login.putExtra("doc", ndoc.getText().toString());
                    // Se inicia la actividad del Intent
                    startActivity(login);
                }
                // Si es false a la anterior condicional se ejecutara siguiente codigo
                else {
                    // Se lanazara un mensaje tipo Toast, que nos notificara que no se encontraron usuarios o estan inactivos
                    Toast.makeText(admin_login.this, "Usuario no registrado o esta INACTIVO", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            // Al obtener una respuesta negativa se pasara un metodo que nos notificara que algo fallo
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(admin_login.this, "El usuario no existe", Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            // Se crea un Map que enviara los datos especificados al servicio web para validar con la base de datos
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("tipo", docselect.getSelectedItem().toString());
                parametros.put("usuario", ndoc.getText().toString());
                parametros.put("password", password.getText().toString());
                return parametros;
            }
        };
        // Se crea una peticion con este formulario, se especifica que las respuestas sera un String y se lanza la peticion
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}