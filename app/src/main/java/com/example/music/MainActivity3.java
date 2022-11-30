package com.example.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {
    private Button btnProduct;
    private TextView textProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        textProduct = (TextView) findViewById(R.id.textProduct);
        btnProduct = (Button) findViewById(R.id.btnProduct);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        textProduct.setText(title);

        btnProduct.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent2 = new Intent(getApplicationContext(), MainActivity2.class);
                 startActivity(intent2);
             }
        });
    }
}