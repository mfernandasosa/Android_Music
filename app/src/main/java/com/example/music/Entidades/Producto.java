package com.example.music.Entidades;


import java.util.UUID;

public class Producto {
    private String id;
    private String name;
    private String description;
    private int price;
    private String image;
    private Double latitud;
    private Double longitud;

    public Producto(String id, String name, String description, int price, String image,Double latitud, Double longitud) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.description = description;
        this.price = price;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Producto(String name, String description, int price, String image, Double latitud, Double longitud) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public void setId(String id) {
        this.id = id;
    }

   public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getId(){return id;}

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
