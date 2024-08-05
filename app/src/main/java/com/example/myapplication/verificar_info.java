package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class verificar_info extends AppCompatActivity {

    TextView name, last, td, ndoc, email, rol, terminos;

    Button crear;

    String estado = "";

    int user_id_base;

    String user_name, user_last, user_tipo_doc, user_n_doc, user_correo, user_pass, user_rol, user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Bundle extras = this.getIntent().getExtras();
        Bundle user_info = extras.getBundle("datos");
        setContentView(R.layout.activity_verificar_info);
        Random rand = new Random();
        user_id_base = rand.nextInt(200);
        user_id = Integer.toString(user_id_base);
        name = findViewById(R.id.txt_name);
        last = findViewById(R.id.txt_apellido);
        td = findViewById(R.id.txt_td);
        ndoc = findViewById(R.id.txt_ndoc);
        email = findViewById(R.id.txt_email);
        rol = findViewById(R.id.textView32);
        terminos=findViewById(R.id.textView24);
        user_name = user_info.getString("nombre");
        user_last = user_info.getString("apellido");
        user_tipo_doc = user_info.getString("Tipo de documento");
        user_n_doc = user_info.getString("N de documento");
        user_correo = user_info.getString("correo");
        user_pass = user_info.getString("contraseña");
        user_rol = user_info.getString("rol");
        name.setText("Nombres:"+"\r\n"+user_name);
        last.setText("Apellidos:"+"\r\n"+user_last);
        td.setText("Tipo de documento:"+"\r\n"+user_tipo_doc);
        ndoc.setText("N° de documento:"+"\r\n"+user_n_doc);
        email.setText("Correo electrinco:"+"\r\n"+user_correo);
        rol.setText("Rol seleccionado:"+"\r\n"+user_rol);
        terminos.setText("A dar al boton crear usted acepta que esta de acuerdo con los terminos de uso y la politica de privacidad");
        crear = findViewById(R.id.subir);
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Instructor_Funcionario = new Intent(verificar_info.this, register_rolinfo2.class);
                Intent Aprendiz = new Intent(verificar_info.this, register_rolinfo.class);
                servicio("http://10.201.131.12/AIR_Database/register_userinfo.php");
                switch (user_rol) {
                    case "aprendiz":
                        if (estado.equals("Correct")) {
                            Aprendiz.putExtra("user_id", user_id);
                            Aprendiz.putExtra("rol", user_rol);
                            startActivity(Aprendiz);
                        } else if (estado.equals("failed")) {
                            Toast.makeText(verificar_info.this, "Error: En establecer conexion con el servidor", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "instructor":
                        if (estado.equals("Correct")) {
                            Instructor_Funcionario.putExtra("user_id", user_id);
                            Instructor_Funcionario.putExtra("rol", user_rol);
                            startActivity(Instructor_Funcionario);
                        } else if (estado.equals("failed")) {
                            Toast.makeText(verificar_info.this, "Error: En establecer conexion con el servidor", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case "funcionario":
                        if (estado.equals("Correct")) {
                            Instructor_Funcionario.putExtra("user_id", user_id);
                            Instructor_Funcionario.putExtra("rol", user_rol);
                            startActivity(Instructor_Funcionario);
                        } else if (estado.equals("failed")) {
                            Toast.makeText(verificar_info.this, "Error: En establecer conexion con el servidor", Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:
                        Toast.makeText(verificar_info.this, "Error: El aplicativo se reiniciara en breve", Toast.LENGTH_LONG).show();
                        finishAffinity();
                        startActivity(new Intent(verificar_info.this, MainActivity.class));
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                estado = "Correct";
                Toast.makeText(getApplicationContext(), "Usuario registrado en el sistema", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                estado = "failed";
                Toast.makeText(getApplicationContext(), "Intente nuevamente", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("cod_usuario", user_id);
                parametros.put("nombre", user_name);
                parametros.put("apellido", user_last);
                parametros.put("tipo_documento", user_tipo_doc);
                parametros.put("numero_documento", user_n_doc);
                parametros.put("email", user_correo);
                parametros.put("contrasena", user_pass);
                parametros.put("rol_user", user_rol);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(verificar_info.this);
        requestQueue.add(stringRequest);
    }
}