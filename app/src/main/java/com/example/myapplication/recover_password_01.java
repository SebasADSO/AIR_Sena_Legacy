package com.example.myapplication;

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

public class recover_password_01 extends AppCompatActivity {
    // Llamado de los elementos textview, edittext, button y creacion de string
    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recover_password01);
        // Se llaman los elemntos del xml
        email = findViewById(R.id.txt_email_recover);
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Button recover = findViewById(R.id.btt_recover_pass);
        // Se establece un evento para el boton siguiente
        recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((email.getText().toString()).matches(emailPattern)){
                    Intent pass_02 = new Intent(recover_password_01.this, recover_password_02.class);
                    pass_02.putExtra("email", email.getText().toString());
                    startActivity(pass_02);
                }
                else {
                    Toast.makeText(recover_password_01.this, "El correo electronico es invalido", Toast.LENGTH_LONG).show();
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}