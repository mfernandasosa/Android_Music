package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Ingreso extends AppCompatActivity {
    private Button btnProduct1, btnProduct2, btnProduct3;
    private TextView textProduct1, textProduct2, textProduct3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingreso);

        btnProduct1 = (Button) findViewById(R.id.btnProduct1);
        btnProduct2 = (Button) findViewById(R.id.btnProduct2);
        btnProduct3 = (Button) findViewById(R.id.btnProduct3);
        textProduct1 = (TextView) findViewById(R.id.textProduct1);
        textProduct2 = (TextView) findViewById(R.id.textProduct2);
        textProduct3 = (TextView) findViewById(R.id.textProduct3);

        btnProduct1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivityGuitar.class);
                intent.putExtra( "title", textProduct1.getText().toString());
                startActivity(intent);

            }
        });

        btnProduct2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivityPianos.class);
                intent.putExtra( "title", textProduct2.getText().toString());
                startActivity(intent);

            }
        });

        btnProduct3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                intent.putExtra( "title", textProduct3.getText().toString());
                startActivity(intent);

            }
        });

    }
}