package com.example.myapplication;

import android.annotation.SuppressLint;
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

public class register_userinfo extends AppCompatActivity {
    private Spinner docselect;

    String ip = "10.201.131.13";
    String change = "localhost";

    Boolean bandera = false;

    private EditText txt_nombre, txt_apellidos, txt_email_user, txt_n_doc ,txt_password, txt_re_password;

    private String[] documentos = {"Cedula de Ciudadania", "Tarjeta de Indetidad", "Cedula de Extranjeria", "PEP", "Permiso por protección Temporal"};
    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_userinfo);
        txt_nombre = findViewById(R.id.txt_username);
        txt_apellidos = findViewById(R.id.txt_lastname);
        docselect = findViewById(R.id.docselect_register);
        txt_n_doc = findViewById(R.id.txt_n_doc);
        txt_email_user = findViewById(R.id.txt_email_user);
        txt_password = findViewById(R.id.txt_password_user);
        txt_re_password = findViewById(R.id.txt_password_user_confirm);
        ArrayAdapter<String> selector=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, documentos);
        docselect.setAdapter(selector);
        Bundle rolselect = getIntent().getExtras();
        String roltest = rolselect.getString("rol");
        Button inforol = findViewById(R.id.btt_next_register);
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String textPattern = "[a-zA-Z ]+";
        ImageButton show_pass = findViewById(R.id.btt_pass_show);
        show_pass.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        txt_password.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        txt_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });ImageButton show_pass2 = findViewById(R.id.btt_pass_show2);
        show_pass2.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        txt_re_password.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    case MotionEvent.ACTION_UP:
                        txt_re_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });
        inforol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobar_user("http://localhost/AIR_Database/register_comprobar.php".replace(change, ip));
                Intent register_validar=new Intent(register_userinfo.this, verificar_info.class);
                        if(txt_nombre.getText().toString().isEmpty() || txt_apellidos.getText().toString().isEmpty() || txt_n_doc.getText().toString().isEmpty() || txt_email_user.getText().toString().isEmpty() || txt_password.getText().toString().isEmpty()) {
                            Toast.makeText( register_userinfo.this,"No puede dejar campos vacios", Toast.LENGTH_LONG).show();
                        }
                       else if (!txt_nombre.getText().toString().isEmpty() || !txt_apellidos.getText().toString().isEmpty() || !txt_n_doc.getText().toString().isEmpty() || !txt_email_user.getText().toString().isEmpty() || !txt_password.getText().toString().isEmpty()) {
                            if ((txt_email_user.getText().toString()).matches(emailPattern)) {
                                if ((txt_nombre.getText().toString().matches(textPattern)) && (txt_apellidos.getText().toString()).matches(textPattern)) {
                                    if (txt_password.getText().toString().trim().equals(txt_re_password.getText().toString().trim()) && (txt_password.getText().toString().trim()).length() == 8) {
                                        if  (bandera == true) {
                                            Bundle extras = new Bundle();
                                            extras.putString("nombre", txt_nombre.getText().toString());
                                            extras.putString("apellido", txt_apellidos.getText().toString());
                                            extras.putString("Tipo de documento", docselect.getSelectedItem().toString());
                                            extras.putString("N de documento", txt_n_doc.getText().toString());
                                            extras.putString("correo", txt_email_user.getText().toString());
                                            extras.putString("contraseña", txt_password.getText().toString());
                                            extras.putString("rol", roltest);
                                            register_validar.putExtra("datos", extras);
                                            startActivity(register_validar);
                                        }
                                    }
                                    else if ((txt_password.getText().toString().trim()).length() < 8 || (txt_password.getText().toString().trim()).length() > 8) {
                                        Toast.makeText( register_userinfo.this,"La contraseña debe contener:"+"\r\n"+"Una longitud de 8 caracteres"+"\r\n"+"Sin espacios", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText( register_userinfo.this,"Las contraseñas no son iguales", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else  {
                                    Toast.makeText(register_userinfo.this, "Los campos de nombre y apellido solo permite letras y espacios", Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                Toast.makeText( register_userinfo.this,"El correo electronico es invalido", Toast.LENGTH_LONG).show();
                            }
                        } else if (roltest.isEmpty()) {
                            Toast.makeText(register_userinfo.this, "Error: El aplicativo se reiniciara en breve", Toast.LENGTH_LONG).show();
                            finishAffinity();
                            startActivity(new Intent(register_userinfo.this, MainActivity.class));
                        }
                }

        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public boolean isValidEmail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private  void comprobar_user(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.isEmpty()){
                    bandera = true;
                }
                else {
                    Toast.makeText(register_userinfo.this, "El usuario ya se encuentra registrado", Toast.LENGTH_LONG).show();
                    bandera = false;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(register_userinfo.this, "Algo salio mal...", Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("numero_documento", txt_n_doc.getText().toString());
                parametros.put("email", txt_email_user.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
