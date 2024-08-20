package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
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
    String ip = "10.201.131.13";
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
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ndoc.getText().toString().trim().isEmpty() && !password.getText().toString().trim().isEmpty()) {
                    login_user("http://localhost/AIR_Database/Login_usuario.php".replace(change, ip));
                } else if (ndoc.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Los campos estan vacios", Toast.LENGTH_LONG).show();
                }
            }
        });
        requestPermissions();
        docselect = findViewById(R.id.docselect);
        ArrayAdapter<String>selector=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, documentos);
        docselect.setAdapter(selector);
        Button register = findViewById(R.id.bttregister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_pag=new Intent(MainActivity.this, MainActivity2.class);
                startActivity(register_pag);
            }
        });
        Button recover = findViewById(R.id.bttrecover);
        recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recover_pag=new Intent(MainActivity.this, recover_password_01.class);
                startActivity(recover_pag);
            }
        });
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
    private  void login_user(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Intent login = new Intent(MainActivity.this, menu_home.class);
                    login.putExtra("doc", ndoc.getText().toString());
                    startActivity(login);
                }
                else {
                    Toast.makeText(MainActivity.this, "Usuario no registrado o esta INACTIVO", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "El usuario no existe", Toast.LENGTH_LONG).show();
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
    final int REQUEST_PERMISSION_CODE = 1;
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_MEDIA_IMAGES, android.Manifest.permission.CAMERA, android.Manifest.permission.POST_NOTIFICATIONS}, REQUEST_PERMISSION_CODE);
        }
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
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