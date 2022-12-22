package com.example.music.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.music.DB.DBFirebase;
import com.example.music.Entidades.Producto;
import com.example.music.FormProduct;
import com.example.music.Lista;
import com.example.music.MainActivity3;
import com.example.music.R;

import java.util.ArrayList;

public class ProductoAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Producto> arrayProductos;

    public ProductoAdapter(Context context, ArrayList<Producto> arrayProductos) {
        this.context = context;
        this.arrayProductos = arrayProductos;
    }

    @Override
    public int getCount() {
        return arrayProductos.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayProductos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        view = layoutInflater.inflate(R.layout.product_template,null);

        Producto producto = arrayProductos.get(i);

        ImageView imgProduct = (ImageView) view.findViewById(R.id.imgProduct);
        TextView tvNameProduct = (TextView) view.findViewById(R.id.tvNameProduct);
        TextView tvDescriptionProduct = (TextView) view.findViewById(R.id.tvDescriptionProduct);
        TextView tvPriceProduct = (TextView) view.findViewById(R.id.tvPriceProduct);
        Button btnDeleteTemplate = (Button) view.findViewById(R.id.btnDeleteTemplate);
        Button btnEditTemplate = (Button) view.findViewById(R.id.btnEditTemplate);

        //Bitmap bitmap = BitmapFactory.decodeByteArray(producto.getImage(), 0, producto.getImage().length);
        //imgProduct.setImageBitmap(bitmap);
        tvNameProduct.setText(producto.getName());
        tvDescriptionProduct.setText(producto.getDescription());
        tvPriceProduct.setText(String.valueOf(producto.getPrice()));

        Glide.with(context)
                .load(producto.getImage())
                .override(500, 500)
                .into(imgProduct);

        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), Lista.class);
                intent.putExtra("name", producto.getName());
                intent.putExtra("description", producto.getDescription());
                intent.putExtra("price", producto.getPrice());
                intent.putExtra("image", producto.getImage());

                context.startActivity(intent);
            }
        });

        btnDeleteTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBFirebase dbFirebase = new DBFirebase();
                dbFirebase.deleteProduct(producto.getId());
                Intent intent = new Intent(context.getApplicationContext(), MainActivity3.class);
                context.startActivity(intent);
            }
        });

        btnEditTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), FormProduct.class);
                intent.putExtra("edit", true);
                intent.putExtra("id", producto.getId());
                intent.putExtra("name", producto.getName());
                intent.putExtra("description", producto.getDescription());
                intent.putExtra("price", producto.getPrice());
                intent.putExtra("image", producto.getImage());
                intent.putExtra("latitud", producto.getLatitud());
                intent.putExtra("longitud", producto.getLongitud());
                context.startActivity(intent);
            }
        });
        return view;
    }
}
