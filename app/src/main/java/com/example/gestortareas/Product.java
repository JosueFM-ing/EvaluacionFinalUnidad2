package com.example.gestortareas;

public class Product {
    private int id;           // Atributo 'id'
    private String name;
    private double price;
    private int imageResId;   // Atributo para la imagen (almacena el ID del recurso de la imagen)

    // Constructor para crear productos con 'name', 'price' y 'imageResId' (usado cuando creamos productos nuevos)
    public Product(String name, double price, int imageResId) {
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;  // Inicializa la imagen
    }

    // Constructor para crear productos con 'id', 'name', 'price' y 'imageResId' (usado cuando recuperamos productos de la base de datos)
    public Product(int id, String name, double price, int imageResId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageResId = imageResId;  // Inicializa la imagen
    }

    // Métodos getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imageResId=" + imageResId +
                '}';
    }
}
