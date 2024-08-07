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

    String code = "", user_rol, user_id, cod_verificar, estado = "";

    int cod_verificar_base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_validar);
        Bundle user_info = this.getIntent().getExtras();
        user_id = user_info.getString("user_id");
        user_rol = user_info.getString("user_rol");
        code_one = findViewById(R.id.code_one);
        code_two = findViewById(R.id.code_two);
        code_three = findViewById(R.id.code_three);
        code_four = findViewById(R.id.code_four);
        code_five = findViewById(R.id.code_five);
        code_six = findViewById(R.id.code_six);
        channel();
        getCode();
        sendNotification();
        Button validar = findViewById(R.id.btt_register_verificar);
        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code += code_one.getText().toString() + code_two.getText().toString() + code_three.getText().toString() + code_four.getText().toString() + code_five.getText().toString() + code_six.getText().toString();
                Intent intent = new Intent(register_validar.this, register_succes.class);
                if (cod_verificar.equals(code)) {
                    servicio("http://192.168.43.143/AIR_Database/activar_user.php");
                    startActivity(intent);
                }
                else {
                    Toast.makeText(register_validar.this, "El codigo es incorrecto", Toast.LENGTH_LONG).show();
                }
            }
        });
        Button email_cambiar = findViewById(R.id.btt_register_change);
        email_cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = findViewById(R.id.txt_email_user);
                if (email.isEnabled()) {
                    email.setEnabled(false);
                }
                else {
                    email.setEnabled(true);
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "32")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Codigo de validación")
                .setContentText("Para completar el registro.")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("debe ingresar el siguiente codigo en el aplicativo, Su codigo es: "+cod_verificar))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
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

    private  void servicio(String URL) {
        final ProgressDialog loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(getApplicationContext(), "Correcto", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                estado = "Correct";
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                estado = "failed";
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("cod_usuario", user_id);
                parametros.put("estado", "ACTIVO");
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}