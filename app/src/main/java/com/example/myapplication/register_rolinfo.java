package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class register_rolinfo extends AppCompatActivity {
    TextView fecha1, fecha2, fecha3;

    String ip = app_config.ip_server;
    String change = "localhost";

    String fechaI, fechaF, fechaP, estado = "", user_id, user_rol;

    EditText txt_cod_program, txt_num_ficha, txt_nombre_programa;
    private Spinner jornadaSelect;
    private String[] jornada = {"Tarde", "Manana", "Nocturna", "Diurna"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_rolinfo);
        Bundle user_extra = getIntent().getExtras();
        user_id = user_extra.getString("user_id");
        user_rol = user_extra.getString("rol");
        fecha1 = findViewById(R.id.fecha1);
        fecha2 = findViewById(R.id.fecha2);
        fecha3 = findViewById(R.id.fecha3);
        jornadaSelect = findViewById(R.id.jornadaSeleccion);
        ArrayAdapter<String>selector=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, jornada);
        jornadaSelect.setAdapter(selector);
        txt_cod_program = findViewById(R.id.txt_cod_program);
        txt_num_ficha = findViewById(R.id.txt_num_ficha);
        String textPattern = "[a-zA-Z ]+";
        txt_nombre_programa = findViewById(R.id.txt_nombre_programa);
        Button siguiente = findViewById(R.id.btt_next_condicion);
        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt_cod_program.getText().toString().isEmpty() || txt_num_ficha.getText().toString().isEmpty() || txt_nombre_programa.getText().toString().isEmpty()) {
                    Toast.makeText( register_rolinfo.this,"No puede dejar campos vacios", Toast.LENGTH_LONG).show();
                }
                else if (fechaI.isEmpty() || fechaP.isEmpty() || fechaF.isEmpty()) {
                    Toast.makeText( register_rolinfo.this,"Debe llenar seleccionar las fechas solicitadas", Toast.LENGTH_LONG).show();
                }
                else {
                    if ((txt_nombre_programa.getText().toString().matches(textPattern))) {
                        servicio("http://localhost/AIR_Database/register_aprendiz.php".replace(change, ip));
                    }
                    else {
                        Toast.makeText(register_rolinfo.this, "Solo se permite letras y espacios en el nombre del programa", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void fecha_inicial (View view) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fechaI = year + "-" + (month+1) + "-" + dayOfMonth;
                fecha1.setText(fechaI);
            }
        }, year, month, day);
        dialog.show();
    }
    public void fecha_final (View view) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fechaF = year + "-" + (month+1) + "-" + dayOfMonth;
                fecha2.setText(fechaF);
            }
        }, year, month, day);
        dialog.show();
    }
    public void fecha_productiva (View view) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fechaP = year + "-" + (month+1) + "-" + dayOfMonth;
                fecha3.setText(fechaP);
            }
        }, year, month, day);
        dialog.show();
    }
    private  void servicio(String URL) {
        final ProgressDialog loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(getApplicationContext(), "Información registrada correctamente", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                Intent condicion = new Intent(register_rolinfo.this, register_condicion.class);
                condicion.putExtra("user_id", user_id);
                condicion.putExtra("rol", user_rol);
                startActivity(condicion);
                estado = "Correct";
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Intente nuevamente", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                estado = "failed";
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("cod_usuario_fk", user_id);
                parametros.put("cod_programa", txt_cod_program.getText().toString());
                parametros.put("numero_ficha", txt_num_ficha.getText().toString());
                parametros.put("nombre_programa", txt_nombre_programa.getText().toString());
                parametros.put("jornada_programa", jornadaSelect.getSelectedItem().toString());
                parametros.put("fecha_inicio", fechaP);
                parametros.put("fecha_final", fechaF);
                parametros.put("inicio_produc", fechaP);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
