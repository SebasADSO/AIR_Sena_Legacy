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
    Spinner docselect, estado_cuenta;

    String ip = "10.201.131.13";
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
        txt_nombre = findViewById(R.id.txt_username);
        txt_apellidos = findViewById(R.id.txt_lastname);
        docselect = findViewById(R.id.docselect_update);
        txt_n_doc = findViewById(R.id.txt_n_doc);
        txt_email_user = findViewById(R.id.txt_email_user);
        btt_save = findViewById(R.id.btt_save);
        btt_cancel = findViewById(R.id.btt_cancel);
        estado_cuenta = findViewById(R.id.estado_cuenta);
        ListElement_User element = (ListElement_User) getIntent().getSerializableExtra("ListElement_user");
        assert element != null;
        txt_nombre.setText(element.getNombre_usuario());
        txt_apellidos.setText(element.getApell_usuario());
        txt_email_user.setText(element.getEmail_usuario());
        txt_n_doc.setText(element.getCedula_usuario().replace("Nª DOC: ", ""));
        ArrayAdapter<String> selector=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, documentos);
        docselect.setAdapter(selector);
        ArrayAdapter<String> selector2=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, estados);
        estado_cuenta.setAdapter(selector2);
        ImageButton logout = findViewById(R.id.btt_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu_home_back = new Intent(admin_usercontrolpanel.this, MainActivity.class);
                startActivity(menu_home_back);
                finishAffinity();
            }
        });
        cod = element.getCod_usuario().replace("ID: ", "");
        getdocselect(element.getTipo_docu_usuario());
        getestadouser(element.getEstado().replace("ESTADO: ", ""));
        btt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txt_n_doc.getText().toString().isEmpty()) {
                    update("http://localhost/AIR_Database/admin_userupdate.php".replace(change, ip));
                }
                else  {
                    Toast.makeText(admin_usercontrolpanel.this, "El numero de documento es invalido", Toast.LENGTH_LONG).show();
                }
            }
        });
        btt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(admin_usercontrolpanel.this, admin_usermanegre.class);
                intent2.putExtra("doc", "123");
                startActivity(intent2);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private  void update(String URL) {
        final ProgressDialog loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(getApplicationContext(), "Información actualizada", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                Intent intent = new Intent(admin_usercontrolpanel.this, admin_usermanegre.class);
                intent.putExtra("doc", "123");
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Error: Intente nuevamente", Toast.LENGTH_SHORT).show();
                loading.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("cod_usuario", cod);
                parametros.put("tipo_docu_usuario", docselect.getSelectedItem().toString());
                parametros.put("numero_documento", txt_n_doc.getText().toString());
                parametros.put("estado", estado_cuenta.getSelectedItem().toString());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void getdocselect(String doc_s) {
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
    public void getestadouser(String cuenta_e) {
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