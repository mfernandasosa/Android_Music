package com.example.music.DB;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.example.music.Adaptadores.ProductoAdapter;
import com.example.music.Entidades.Producto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBFirebase {
    private FirebaseFirestore db;

    public DBFirebase() {
        this.db =  FirebaseFirestore.getInstance();
    }

    public void insertProduct(Producto producto){
        Map<String, Object> prod = new HashMap<>();
        prod.put("id", producto.getId());
        prod.put("name", producto.getName());
        prod.put("description", producto.getDescription());
        prod.put("price", producto.getPrice());
        prod.put("image", producto.getImage());
        prod.put("latitud", producto.getLatitud());
        prod.put("longitud", producto.getLongitud());

        // Add a new document with a generated ID
        db.collection("instrumentos").add(prod);
    }

    public void getProducts(ProductoAdapter productoAdapter, ArrayList<Producto> list){
        db.collection("instrumentos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Producto producto = new Producto(
                                        document.getData().get("id").toString(),
                                        document.getData().get("name").toString(),
                                        document.getData().get("description").toString(),
                                        Integer.parseInt(document.getData().get("price").toString()),
                                        document.getData().get("image").toString(),
                                        Double.parseDouble(document.getData().get("latitud").toString()),
                                        Double.parseDouble(document.getData().get("longitud").toString())

                                );
                                list.add(producto);
                            }
                            productoAdapter.notifyDataSetChanged();
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void deleteProduct(String id){
        db.collection("instrumentos").whereEqualTo("id", id)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                documentSnapshot.getReference().delete();
                            }
                        }
                    }
                });
    }

    public void updateProduct(Producto producto){
        db.collection("instrumentos").whereEqualTo("id", producto.getId())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                documentSnapshot.getReference().update(
                                        "name", producto.getName(),
                                        "description", producto.getDescription(),
                                        "price", producto.getPrice(),
                                        "image", producto.getImage(),
                                         "latitud", producto.getLatitud(),
                                         "longitud", producto.getLongitud()
                                );
                            }
                        }
                    }
                });
    }
}