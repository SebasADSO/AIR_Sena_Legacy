package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class usuario_menuupdate extends AppCompatActivity {
    // Llamado de los elementos textview, edittext, button y creacion de string
    String ndoc;
    Button infochange, passchange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_usuario_menuupdate);
        // Se obtiene un bundle con la informacion de la anterior activity
        Bundle extras = this.getIntent().getExtras();
        ndoc = extras.getString("doc");
        // Se llaman los elemntos del xml
        infochange = findViewById(R.id.infochange);
        infochange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(usuario_menuupdate.this, usuario_infoupdate.class);
                intent.putExtra("doc", ndoc);
                startActivity(intent);
            }
        });
        passchange = findViewById(R.id.passchange);
        passchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(usuario_menuupdate.this, recover_password_01.class);
                startActivity(intent2);
            }
        });
        // Evento para cerrar sesion
        ImageButton logout = findViewById(R.id.btt_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu_home_back = new Intent(usuario_menuupdate.this, MainActivity.class);
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