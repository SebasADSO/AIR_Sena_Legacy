package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class menu_home extends AppCompatActivity {

    TextView txt_mensaje;

    String ndoc;

    ImageButton btt_reportar, btt_consultar, btt_usuario, btt_config;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_home);
        txt_mensaje = findViewById(R.id.txt_mensaje);
        txt_mensaje.setText("Bienvenid@");
        ImageButton logout = findViewById(R.id.btt_logout);
        btt_reportar = findViewById(R.id.btt_reportar);
        btt_consultar = findViewById(R.id.btt_consultar);
        btt_usuario = findViewById(R.id.btt_usuario);
        btt_config = findViewById(R.id.btt_config);
        Bundle extras = this.getIntent().getExtras();
        ndoc = extras.getString("doc");
        btt_reportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reportar = new Intent(menu_home.this, menu_reportar.class);
                reportar.putExtra("doc", ndoc);
                startActivity(reportar);
            }
        });
        btt_consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Funciona", Toast.LENGTH_LONG).show();
            }
        });
        btt_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent user = new Intent(menu_home.this, Menu_usuario.class);
                user.putExtra("doc", ndoc);
                startActivity(user);
            }
        });
        btt_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Aun no disponible", Toast.LENGTH_LONG).show();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu_home_back = new Intent(menu_home.this, MainActivity.class);
                startActivity(menu_home_back);
                finishAffinity();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}