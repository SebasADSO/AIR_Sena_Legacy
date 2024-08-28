package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
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
    // Llamado de los elementos textview, edittext, button y creacion de string
    String email;
    EditText password, password_confirm;
    String ip = app_config.ip_server;
    String change = "localhost";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        // Se llaman los elemntos del xml
        setContentView(R.layout.activity_recover_password03);
        password = findViewById(R.id.txt_password);
        password_confirm = findViewById(R.id.txt_password_confirm);
        // Se obtiene un bundle con la informacion de la anterior activity
        Bundle extras = this.getIntent().getExtras();
        ScrollView scrollView = findViewById(R.id.scrollView5);
        email = extras.getString("email");
        // Evento para cambiar los input de password a text mientras se oprima el boton
        ImageButton show_pass = findViewById(R.id.btt_pass_show);
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
        // Evento para cambiar los input de password a text mientras se oprima el boton
        ImageButton show_pass2 = findViewById(R.id.btt_pass_show2);
        show_pass2.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // Toma los cambios del evento
                switch ( event.getAction() ) {
                    // Si el boton se oprime cambia de textpassword a text
                    case MotionEvent.ACTION_DOWN:
                        password_confirm.setInputType(InputType.TYPE_CLASS_TEXT);
                        break;
                    // Si ele boton deja de oprimirse se cambia de text a textpassword
                    case MotionEvent.ACTION_UP:
                        password_confirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        break;
                }
                return true;
            }
        });
        String passPattern  = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])([A-Za-z\\d$@$!%*?&]|[^ ]){8,15}$";
        Button cambiar = findViewById(R.id.btt_recover_cambiar);
        // Boton de evento para enviar la solicitud
        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().isEmpty() || password_confirm.getText().toString().isEmpty()) {
                    Toast.makeText( recover_password_03.this,"No puede dejar campos vacios", Toast.LENGTH_LONG).show();
                }
                else if ((password.getText().toString()).equals(password_confirm.getText().toString())) {
                    if (password.getText().toString().trim().matches(passPattern)) {
                        servicio("http://localhost/AIR_Database/recover_pass.php".replace(change, ip));
                        Intent pass_04 = new Intent(recover_password_03.this, recover_password_04.class);
                        startActivity(pass_04);
                    }
                    else if (!password.getText().toString().trim().matches(passPattern)) {
                        onButtonShowPopupWindowClick(scrollView);
                    }
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
    /**

     * Realiza una solicitud HTTP de tipo POST a la dirección URL proporcionada, enviando parámetros específicos y mostrando un diálogo de progreso mientras se realiza la solicitud.

     *

     * @param URL La dirección URL del servidor donde se realizará la solicitud HTTP.

     */
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
    // Metodo para el Popup Window
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