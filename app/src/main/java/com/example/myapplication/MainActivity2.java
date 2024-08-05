package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        Intent user_data = new Intent(MainActivity2.this, register_userinfo.class);
        Button aprendiz = findViewById(R.id.aprendiz);
        aprendiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_data.putExtra("rol", "aprendiz");
                startActivity(user_data);
            }
        });
        Button instructor = findViewById(R.id.instructor);
        instructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_data.putExtra("rol", "instructor");
                startActivity(user_data);
            }
        });
        Button funcionario = findViewById(R.id.funcionario);
        funcionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_data.putExtra("rol", "funcionario");
                startActivity(user_data);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}