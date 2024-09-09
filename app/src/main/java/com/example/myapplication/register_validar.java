package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.nio.channels.Channel;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.os.Build;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class register_validar extends AppCompatActivity {

    EditText code_one, code_two, code_three, code_four, code_five, code_six;

    String ip = app_config.ip_server;
    String change = "localhost";

    String code = "", user_rol, user_id, cod_verificar, estado = "";

    int cod_verificar_base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_validar);
        // Se recibe datos de la anterior activity
        Bundle user_info = this.getIntent().getExtras();
        user_id = user_info.getString("user_id");
        user_rol = user_info.getString("user_rol");
        // Se llaman los elementos del xml
        code_one = findViewById(R.id.code_one);
        code_two = findViewById(R.id.code_two);
        code_three = findViewById(R.id.code_three);
        code_four = findViewById(R.id.code_four);
        code_five = findViewById(R.id.code_five);
        code_six = findViewById(R.id.code_six);
        // Se llama los metodos que nos permitira crear el canal de notoficacion y enviar la notificacion
        channel();
        getCode();
        sendNotification();
        // Boton que validara el codigo
        Button validar = findViewById(R.id.btt_register_verificar);
        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code += code_one.getText().toString() + code_two.getText().toString() + code_three.getText().toString() + code_four.getText().toString() + code_five.getText().toString() + code_six.getText().toString();
                Intent intent = new Intent(register_validar.this, register_succes.class);
                if (cod_verificar.equals(code)) {
                    servicio("http://localhost/AIR_Database/activar_user.php".replace(change, ip));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(register_validar.this, "El codigo es incorrecto", Toast.LENGTH_LONG).show();
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    // Metodo que creara la notificacion y la enviara
    private void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "32")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Codigo de validación")
                .setContentText("Para completar el registro.")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Debe ingresar el siguiente codigo en el aplicativo, Su codigo es: "+cod_verificar))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
    // Metodo que creara el canal notificacion y la definira
    private void channel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Validación";
            String description = "Notificación de verificación";
            int importancia_canal = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("32", name, importancia_canal);
            channel.setDescription(description);
            NotificationManager notificationManager =  getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    // Metodo que genera un codigo para verificar
    public String getCode() {
        Random rand = new Random();
        cod_verificar_base = rand.nextInt(999999);
        while (true) {
            if (cod_verificar_base>100000) {
                cod_verificar = Integer.toString(cod_verificar_base);
                return cod_verificar;
            }
        }
    }
// Servicio http tipo POST que enviara una solicitud con un encabezado que contendra los datos para actualizar el estado del usuario
    private  void servicio(String URL) {
        final ProgressDialog loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            // Respuesta correcta
            public void onResponse(String s) {
                Toast.makeText(getApplicationContext(), "Correcto", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                estado = "Correct";
            }
        }, new Response.ErrorListener() {
            @Override
            // Respuesta fallida
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                estado = "failed";
            }
        }){
            @Override
            // Datos del encabezado
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("cod_usuario", user_id);
                parametros.put("estado", "ACTIVO");
                return parametros;
            }
        };
        // Contexto y envio de solicitud
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}