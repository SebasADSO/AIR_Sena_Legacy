package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
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
    // Llamado de los elementos textview, edittext, button y creacion de string
    private Spinner docselect;

    String ip = app_config.ip_server;
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
        // Se llaman los elemntos del xml
        txt_nombre = findViewById(R.id.txt_username);
        txt_apellidos = findViewById(R.id.txt_lastname);
        docselect = findViewById(R.id.docselect_register);
        txt_n_doc = findViewById(R.id.txt_n_doc);
        txt_email_user = findViewById(R.id.txt_email_user);
        txt_password = findViewById(R.id.txt_password_user);
        txt_re_password = findViewById(R.id.txt_password_user_confirm);
        ScrollView scrollView = findViewById(R.id.scrollView2);
        ArrayAdapter<String> selector=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, documentos);
        docselect.setAdapter(selector);
        // Se obtiene un bundle con la informacion de la anterior activity
        Bundle rolselect = getIntent().getExtras();
        String roltest = rolselect.getString("rol");
        Button inforol = findViewById(R.id.btt_next_register);
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String textPattern = "[a-zA-Z ]+";
        String passPattern  = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])([A-Za-z\\d$@$!%*?&]|[^ ]){8,15}$";
        ImageButton show_pass = findViewById(R.id.btt_pass_show);
        // Evento para cambiar los input de password a text mientras se oprima el boton
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
        });
        // Evento para cambiar los input de password a text mientras se oprima el boton
        ImageButton show_pass2 = findViewById(R.id.btt_pass_show2);
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
        // Se establece un evento que validara los EditText y lanzara la consulta
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
                                    if (txt_password.getText().toString().equals(txt_re_password.getText().toString())) {
                                        if (txt_password.getText().toString().matches(passPattern)) {
                                            if  (bandera) {
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
                                        else if (!txt_password.getText().toString().trim().matches(passPattern)) {
                                            onButtonShowPopupWindowClick(scrollView);
                                        }
                                    }
                                    else if (!txt_password.getText().toString().equals(txt_re_password.getText().toString())){
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
    // Valida si el email (En reserva)
    public boolean isValidEmail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    /**

     * Realiza una solicitud HTTP de tipo POST a la dirección URL proporcionada, enviando parámetros específicos y mostrando un diálogo de progreso mientras se realiza la solicitud.

     *

     * @param URL La dirección URL del servidor donde se realizará la solicitud HTTP.

     */
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
    // Metodo que msotrara un Popup Window
    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.password_pw, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}