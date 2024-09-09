package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class recover_password_02 extends AppCompatActivity {
    // Llamado de los elementos textview, edittext, button y creacion de string
    EditText code_one, code_two, code_three, code_four, code_five, code_six;
    String code = "", cod_verificar;
    int cod_verificar_base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recover_password02);
        // Se obtiene un bundle con la informacion de la anterior activity
        Bundle extras = this.getIntent().getExtras();
        String email = extras.getString("email");
        // Se llaman los elemntos del xml
        code_one = findViewById(R.id.code_one);
        code_two = findViewById(R.id.code_two);
        code_three = findViewById(R.id.code_three);
        code_four = findViewById(R.id.code_four);
        code_five = findViewById(R.id.code_five);
        code_six = findViewById(R.id.code_six);
        // Se llaman los metodos de la notificacion
        channel();
        getCode();
        sendNotification();
        Button home = findViewById(R.id.btt_recover_home);
        // Evento para cambiar la contraseña (Ojo a futuro implementar JavaMail o similar esta evento es de prueba y debe ser removida)
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code += code_one.getText().toString() + code_two.getText().toString() + code_three.getText().toString() + code_four.getText().toString() + code_five.getText().toString() + code_six.getText().toString();
                if (cod_verificar.equals(code)) {
                    Intent home = new Intent(recover_password_02.this, recover_password_03.class);
                    home.putExtra("email", email);
                    startActivity(home);
                }
                else {
                    Toast.makeText(recover_password_02.this, "El codigo es incorrecto", Toast.LENGTH_LONG).show();
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
                .setContentText("Para recuperar la contraseña.")
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
}