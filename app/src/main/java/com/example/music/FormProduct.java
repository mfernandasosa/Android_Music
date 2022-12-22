package com.example.music;


import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.music.DB.DBFirebase;
import com.example.music.DB.DBHelper;
import com.example.music.Entidades.Producto;
import com.example.music.Service.ProductoService;
import com.example.music.Service.ProductoService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FormProduct extends AppCompatActivity {
    private Button btnFormProduct;
    private EditText editNameFormProduct, editDescriptionFormProduct, editPriceFormProduct, editIdFormProduct;
    private TextView textLatitudFormProduct, textLongitudFormProduct;
    private ImageView imgFormProduct;
    private DBHelper dbHelper;
    private DBFirebase dbFirebase;
    private ActivityResultLauncher<String> content;
    private ProductoService productoService;
    private MapView mapView;
    private MapController mapController;
    private StorageReference storageReference;
    private String urlImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_product);

        urlImage = "";
        btnFormProduct = (Button) findViewById(R.id.btnFormProduct);
        editNameFormProduct = (EditText) findViewById(R.id.editNameFormProduct);
        editDescriptionFormProduct = (EditText) findViewById(R.id.editDescriptionFormProduct);
        editPriceFormProduct = (EditText) findViewById(R.id.editPriceFormProduct);
        imgFormProduct = (ImageView) findViewById(R.id.imgFormProduct);
        textLatitudFormProduct = (TextView) findViewById(R.id.textLatitudFormProduct);
        textLongitudFormProduct = (TextView) findViewById(R.id.textLongitudFormProduct);
        mapView = (MapView) findViewById(R.id.mapForm);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        GeoPoint madrid = new GeoPoint(40.416775, -3.70379);
        mapView.setBuiltInZoomControls(true);
        mapController = (MapController) mapView.getController();
        mapController.setCenter(madrid);
        mapController.setZoom(8);

        mapView.setMultiTouchControls(true);

        MapEventsReceiver mapEventsReceiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                textLatitudFormProduct.setText(String.valueOf(p.getLatitude()));
                textLongitudFormProduct.setText(String.valueOf(p.getLongitude()));

                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };
        MapEventsOverlay eventsOverlay = new MapEventsOverlay(getApplicationContext(), mapEventsReceiver);
        mapView.getOverlays().add(eventsOverlay);

        editPriceFormProduct.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_SHORT).show();
                    return true;
                }

                return false;
            }
        });

        try {
            dbHelper = new DBHelper(this);
            dbFirebase = new DBFirebase();
            productoService = new ProductoService();
            storageReference = FirebaseStorage.getInstance().getReference();
            content = registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    new ActivityResultCallback<Uri>() {
                        @Override
                        public void onActivityResult(Uri result) {
                            Uri uri = result;
                            StorageReference filePath = storageReference.child("images").child(uri.getLastPathSegment());
                            filePath.putFile(uri)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Toast.makeText(getApplicationContext(), "Imagen Cargada", Toast.LENGTH_SHORT).show();
                                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Uri downLoadUrl = uri;
                                                    urlImage = downLoadUrl.toString();
                                                    Glide.with(FormProduct.this)
                                                            .load(downLoadUrl)
                                                            .override(500, 500)
                                                            .into(imgFormProduct);
                                                }
                                            });
                                        }
                                    });
                        }
                    });
        } catch (Exception e) {
            Log.e("DB", e.toString());
        }


        Intent intentIN = getIntent();
        editNameFormProduct.setText(intentIN.getStringExtra("name"));
        editDescriptionFormProduct.setText(intentIN.getStringExtra("description"));
        editPriceFormProduct.setText(String.valueOf(intentIN.getIntExtra("price", 0)));
        textLatitudFormProduct.setText(String.valueOf(intentIN.getDoubleExtra("latitud", 0.0)));
        textLongitudFormProduct.setText(String.valueOf(intentIN.getDoubleExtra("longitud", 0.0)));

        imgFormProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content.launch("image/*");
            }
        });

        btnFormProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Producto producto = new Producto(
                        editNameFormProduct.getText().toString(),
                        editDescriptionFormProduct.getText().toString(),
                        Integer.parseInt(editPriceFormProduct.getText().toString()),
                        urlImage,
                        Double.parseDouble(textLatitudFormProduct.getText().toString().trim()),
                        Double.parseDouble(textLongitudFormProduct.getText().toString().trim())
                );

                if (intentIN.getBooleanExtra("edit", false)) {
                    String id = intentIN.getStringExtra("id");
                    producto.setId(id);
                    dbFirebase.updateProduct(producto);
                } else {
                    //dbHelper.insertProduct(producto);
                    dbFirebase.insertProduct(producto);
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                startActivity(intent);
            }
        });
    }

    public void clean() {
        editNameFormProduct.setText("");
        editDescriptionFormProduct.setText("");
        editPriceFormProduct.setText("");
        imgFormProduct.setImageResource(R.drawable.ic_launcher_background);
    }
}
