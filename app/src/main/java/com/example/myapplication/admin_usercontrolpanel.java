package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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

public class admin_usercontrolpanel extends AppCompatActivity {
    // Llamado de los elementos textview, edittext, button y creacion de string
    Spinner docselect, estado_cuenta;

    String ip = app_config.ip_server;
    String change = "localhost";
    RequestQueue requestQueue;

    EditText txt_n_doc;
    TextView txt_nombre, txt_apellidos, txt_email_user;

    Button btt_save, btt_cancel;
    String cod;
    String[] documentos = {"Cedula de Ciudadania", "Tarjeta de Indetidad", "Cedula de Extranjeria", "PEP", "Permiso por protección Temporal"};
    String[] estados = {"ACTIVO", "INACTIVO"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_usercontrolpanel);
        // Llamado de los elementos de la vista del xml
        txt_nombre = findViewById(R.id.txt_username);
        txt_apellidos = findViewById(R.id.txt_lastname);
        docselect = findViewById(R.id.docselect_update);
        txt_n_doc = findViewById(R.id.txt_n_doc);
        txt_email_user = findViewById(R.id.txt_email_user);
        btt_save = findViewById(R.id.btt_save);
        btt_cancel = findViewById(R.id.btt_cancel);
        estado_cuenta = findViewById(R.id.estado_cuenta);
        // Recibir la informacion enviada en la anterior activity
        ListElement_User element = (ListElement_User) getIntent().getSerializableExtra("ListElement_user");
        assert element != null;
        // Estableciendo datos en los campos llamados
        txt_nombre.setText(element.getNombre_usuario());
        txt_apellidos.setText(element.getApell_usuario());
        txt_email_user.setText(element.getEmail_usuario());
        // Asignacion de los adaptadores
        txt_n_doc.setText(element.getCedula_usuario().replace("Nª DOC: ", ""));
        ArrayAdapter<String> selector=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, documentos);
        docselect.setAdapter(selector);
        ArrayAdapter<String> selector2=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, estados);
        estado_cuenta.setAdapter(selector2);
        // Metodo que le asiganara una funcion al boton
        ImageButton logout = findViewById(R.id.btt_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Se abrira una nueva actividad que este caso sera el menu usuario
                Intent menu_home_back = new Intent(admin_usercontrolpanel.this, admin_login.class);
                // Se lanazara la nueva actividad abierta
                startActivity(menu_home_back);
                // Finalizacion de la actividad actual
                finishAffinity();
            }
        });
        // Aqui se remplazara un valor del string por vacio
        cod = element.getCod_usuario().replace("ID: ", "");
        // Metodo que seleccionara el documento del usuario
        getdocselect(element.getTipo_docu_usuario());
        // Metodo que seleccionara el estado del usuario
        getestadouser(element.getEstado().replace("ESTADO: ", ""));
        // Se asignara un evento que ejecutara una funcion
        btt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Condicional que evaluara si los campos estan vacios, devuelve true
                if (!txt_n_doc.getText().toString().isEmpty()) {
                    // Ejecucion del metodo que actualizara los datos
                    update("http://localhost/AIR_Database/admin_userupdate.php".replace(change, ip));
                }
                // En el caso se false
                else  {
                    Toast.makeText(admin_usercontrolpanel.this, "El numero de documento es invalido", Toast.LENGTH_LONG).show();
                }
            }
        });
        // Se asignara un evento que ejecutara una funcion
        btt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se abrira una nueva actividad que este caso sera el actualizador de usuarios
                Intent intent2 = new Intent(admin_usercontrolpanel.this, admin_usermanegre.class);
                // Se mandara los datos a la otra actividad que se definio en el Intent
                intent2.putExtra("doc", "123");
                // Se inicia la actividad del Intent
                startActivity(intent2);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    // Creacion del metodo que validara la informacion suministrada por el administrador y la validara con la base de datos
    private  void update(String URL) {
        // Llamado de un mensaje de carga preteminado de la version de android
        final ProgressDialog loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor");
        // Se crea una nueva solicitud a un seervidor esta solicitud se dara con el metodo POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            // Al obtener una respuesta del servidor se actira este metodo que pasara como respuesta correcta
            public void onResponse(String s) {
                Toast.makeText(getApplicationContext(), "Información actualizada", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                Intent intent = new Intent(admin_usercontrolpanel.this, admin_usermanegre.class);
                intent.putExtra("doc", "123");
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            // Al obtener una respuesta negativa se pasara un metodo que nos notificara que algo fallo
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Error: Intente nuevamente", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        }){
            @Override
            // Se crea un Map que enviara los datos especificados al servicio web para actualizar con la base de datos
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("cod_usuario", cod);
                parametros.put("tipo_docu_usuario", docselect.getSelectedItem().toString());
                parametros.put("numero_documento", txt_n_doc.getText().toString());
                parametros.put("estado", estado_cuenta.getSelectedItem().toString());
                return parametros;
            }
        };
        // Se crea una peticion con este formulario, se especifica que las respuestas sera un String y se lanza la peticion
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    // Metodo que asignara el documento de spinner
    public void getdocselect(String doc_s) {
        // Un switch case donde evaluara a la variable
        switch (doc_s) {
            case "Cedula de Ciudadania":
                docselect.setSelection(0);
                break;
            case "Tarjeta de Indetidad":
                docselect.setSelection(1);
                break;
            case "Cedula de Extranjeria":
                docselect.setSelection(2);
                break;
            case "PEP":
                docselect.setSelection(3);
                break;
            default:
                docselect.setSelection(4);
                break;
        }
    }
    // Metodo que asignara el estado de spinner
    public void getestadouser(String cuenta_e) {
        // Un switch case donde evaluara a la variable
        switch (cuenta_e){
            case "ACTIVO":
                estado_cuenta.setSelection(0);
                break;
            case "INACTIVO":
                estado_cuenta.setSelection(1);
                break;
        }
    }
}