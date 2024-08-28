package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Menu_config extends AppCompatActivity {
    // Variables y otros elementos
    String email_s = "air_sena@sena.edu.co";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_config);
        // Llamados de los elementos del xml
        TextView txt_support = findViewById(R.id.txt_support);
        // Se establece un texto al mismo
        txt_support.setText("Correo de Soporte: "+"\n"+email_s);
        ImageButton logout = findViewById(R.id.btt_logout);
        // Se asigna un evento que permitira salir de la sesion actual
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu_home_back = new Intent(Menu_config.this, MainActivity.class);
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