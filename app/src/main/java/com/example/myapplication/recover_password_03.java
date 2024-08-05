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

public class recover_password_03 extends AppCompatActivity {

    EditText password, password_confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recover_password03);
        password = findViewById(R.id.txt_password);
        password_confirm = findViewById(R.id.txt_password_confirm);
        String parser_pass = password.getText().toString();
        String parser_pass_valid = password_confirm.getText().toString();
        Button cambiar = findViewById(R.id.btt_recover_cambiar);
        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parser_pass.isEmpty() || parser_pass_valid.isEmpty()) {
                    Toast.makeText( recover_password_03.this,"No puede dejar campos vacios", Toast.LENGTH_LONG).show();
                }
                else if (parser_pass.equals(parser_pass_valid)) {
                    Intent pass_04 = new Intent(recover_password_03.this, recover_password_04.class);
                    startActivity(pass_04);
                }
                else {
                    Toast.makeText( recover_password_03.this,"Las contraseñas no son iguales", Toast.LENGTH_LONG).show();
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