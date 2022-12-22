package com.example.music;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.music.Adaptadores.ProductoAdapter;
import com.example.music.DB.DBFirebase;
import com.example.music.DB.DBHelper;
import com.example.music.Entidades.Producto;
import com.example.music.Service.ProductoService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivityGuitar extends AppCompatActivity {
    private DBHelper dbHelper;
    private DBFirebase dbFirebase;
    private ProductoService productoService;
    private ListView listViewProducts;
    private ProductoAdapter productoAdapter;
    private ArrayList<Producto> arrayProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_guitar);
        arrayProductos = new ArrayList<>();
        listViewProducts = (ListView) findViewById(R.id.listViewProducts);
        try {
            dbHelper = new DBHelper(this);
            dbFirebase = new DBFirebase();
            productoService = new ProductoService();
            arrayProductos = productoService.cursorToArray(dbHelper.getProducts());

        }catch (Exception e){
            Log.e("DB", e.toString());
        }

        productoAdapter = new ProductoAdapter(this, arrayProductos);
        listViewProducts.setAdapter(productoAdapter);

        dbFirebase.getProducts(productoAdapter, arrayProductos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.action_add:
                intent = new Intent(getApplicationContext(), FormProduct.class);
                startActivity(intent);
                return true;
            case R.id.action_map:
                ArrayList<String> latitudes = new ArrayList<>();
                ArrayList<String> longitudes = new ArrayList<>();
                for(int i=0; i<arrayProductos.size(); i++){
                    String latitud = String.valueOf(arrayProductos.get(i).getLatitud());
                    String longitud = String.valueOf(arrayProductos.get(i).getLongitud());
                    latitudes.add(latitud);
                    longitudes.add(longitud);
                }

                intent = new Intent(getApplicationContext(), MainActivityMaps.class);
                intent.putStringArrayListExtra("latitudes", latitudes);
                intent.putStringArrayListExtra("longitudes", longitudes);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}