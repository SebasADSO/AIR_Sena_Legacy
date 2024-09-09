package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

public class MainActivity extends AppCompatActivity {
    // Llamado de los elementos textview, edittext, button y creacion de string
    String ip = app_config.ip_server;
    String change = "localhost";
    EditText ndoc, password;
private Spinner docselect;
private String[] documentos = {"Cedula de Ciudadania", "Tarjeta de Indetidad", "Cedula de Extranjeria", "PEP", "Permiso por protección Temporal"};
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
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
        // Metodo le asigna una funcion al momento de dar click al boton login usuario
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Se pasa a una condicional en la cual se valida si los campos no estan vacios y devuelve true
                if (!ndoc.getText().toString().trim().isEmpty() && !password.getText().toString().trim().isEmpty()) {
                    // Ejecucion del metodo login con los parametros asignados
                    login_user("http://localhost/AIR_Database/Login_usuario.php".replace(change, ip));
                }
                // Se pasa a una condicional en la cual se valida si los campos estan estan vacios, devuelve false y no permite el acceso
                else if (ndoc.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Los campos estan vacios", Toast.LENGTH_LONG).show();
                }
            }
        });
        // Ejecuta el metodo que solicitara al usuario los permisos necesarios para su funcionamineto
        requestPermissions();
        // Llama a los Spinners y se asignan sus correspondientes adaptadores
        docselect = findViewById(R.id.docselect);
        ArrayAdapter<String>selector=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, documentos);
        docselect.setAdapter(selector);
        // Asignacion de una funcion al momento de dar click al boton para registrar
        Button register = findViewById(R.id.bttregister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_pag=new Intent(MainActivity.this, MainActivity2.class);
                startActivity(register_pag);
            }
        });
        // Asignacion de una funcion al momento de dar click al boton para recuperar contraseña
        Button recover = findViewById(R.id.bttrecover);
        recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recover_pag=new Intent(MainActivity.this, recover_password_01.class);
                startActivity(recover_pag);
            }
        });
        // Asignacion de una funcion al momento de dar click al boton login de administrador
        Button admin = findViewById(R.id.bttadmin);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(MainActivity.this, admin_login.class);
                startActivity(intent4);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    // Creacion del metodo que validara la informacion suministrada por el usuario y la validara con la base de datos
    private  void login_user(String URL) {
        // Se crea una nueva solicitud a un seervidor esta solicitud se dara con el metodo POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            // Al obtener una respuesta del servidor se actira este metodo que pasara como respuesta correcta
            public void onResponse(String response) {
                // Se evaluara si la respuesta no esta vacia y devolvera true
                if(!response.isEmpty()){
                    //Log.d("Respuesta", response);
                    // Valida si esta inactivo en el sistema
                    if (response.contains("INACTIVO")) {
                        Toast.makeText(MainActivity.this, "El usuario se encuentra desactivado", Toast.LENGTH_LONG).show();
                    }
                    // Valida si esta activo en el sistema
                    else if (response.contains("ACTIVO")) {
                        // Se abrira una nueva actividad que este caso sera el menu usuario
                        Intent login = new Intent(MainActivity.this, menu_home.class);
                        // Se mandara los datos a la otra actividad que se definio en el Intent
                        login.putExtra("doc", ndoc.getText().toString());
                        // Se inicia la actividad del Intent
                        startActivity(login);
                    }
                }
                // Si es false a la anterior condicional se ejecutara siguiente codigo
                else {
                    // Se lanazara un mensaje tipo Toast, que nos notificara que no se encontraron usuarios o estan inactivos
                    Toast.makeText(MainActivity.this, "Usuario no registrado o datos incorrectos", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            // Al obtener una respuesta negativa se pasara un metodo que nos notificara que algo fallo
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error en la conexión", Toast.LENGTH_LONG).show();
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
    // Se asigna un codigo de permiso
    final int REQUEST_PERMISSION_CODE = 1;
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    // Se crea un metodo que solicitara los permisos necesarios para el funcionamiento del aplicativo, los permisos son Acesso a la camara, acceso al amacenamiento interno en android 11 y anteriores, acceso a fotos e notificaciones en android 12 y superiores
    private void requestPermissions() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA}, REQUEST_PERMISSION_CODE);

            }
        }
        else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_MEDIA_IMAGES, android.Manifest.permission.CAMERA, android.Manifest.permission.POST_NOTIFICATIONS}, REQUEST_PERMISSION_CODE);
            }
        }
    }
    // Metodo encargada de validar el estado de los permisos que deben ser 1 para consedido y 0 para  denegado y le notificamos al usuario de su error
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "La app puede funcionar correctamente", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "La app necesita todos los permisos para funcionar correctamente", Toast.LENGTH_LONG).show();
            }
        }
    }
}