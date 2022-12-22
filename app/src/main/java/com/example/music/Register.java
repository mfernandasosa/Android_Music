package com.example.music;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private Button btnRegisterRegister;
    private EditText editEmailRegister, editPassRegister, editConfirmRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegisterRegister = (Button) findViewById(R.id.btnRegisterRegister);
        editEmailRegister = (EditText) findViewById(R.id.editEmailRegister);
        editPassRegister = (EditText) findViewById(R.id.editPassRegister);
        editConfirmRegister = (EditText) findViewById(R.id.editConfirmRegister);
        mAuth = FirebaseAuth.getInstance();

        btnRegisterRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logica de registro.
                String email = editEmailRegister.getText().toString().trim();
                String pass = editPassRegister.getText().toString().trim();
                String confirm = editConfirmRegister.getText().toString().trim();

                if(pass.compareTo(confirm) == 0){
                    // registramos
                    mAuth.createUserWithEmailAndPassword(email,pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(),"Usuario Registrado con exito",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Correo Ya Registrado ", Toast.LENGTH_SHORT).show();
                                    Log.w("Register","error " + e.getMessage());
                                }
                            });
                }else{
                    Toast.makeText(getApplicationContext(),"Contraseña no coincide", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}