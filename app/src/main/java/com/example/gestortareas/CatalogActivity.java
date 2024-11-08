package com.example.gestortareas;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CatalogActivity extends AppCompatActivity {

    private RecyclerView rvProductCatalog;
    private SearchView searchView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private Button btnViewCart;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        rvProductCatalog = findViewById(R.id.rv_product_catalog);
        searchView = findViewById(R.id.search_view);
        btnViewCart = findViewById(R.id.btn_view_cart);
        databaseHelper = new DatabaseHelper(this);

        // Obtener los productos de la base de datos
        productList = getProductsFromDatabase();

        productAdapter = new ProductAdapter(productList);
        rvProductCatalog.setLayoutManager(new LinearLayoutManager(this));
        rvProductCatalog.setAdapter(productAdapter);

        // Configuración del filtro de búsqueda
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterProducts(newText);
                return true;
            }
        });

        // Configuración del botón "Ver Carrito"
        btnViewCart.setOnClickListener(v -> {
            Intent intent = new Intent(CatalogActivity.this, CartActivity.class);
            startActivity(intent);
        });
    }

    // Método para obtener los productos desde la base de datos
    private List<Product> getProductsFromDatabase() {
        List<Product> products = new ArrayList<>();
        Cursor cursor = databaseHelper.getAllProducts();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int idIndex = cursor.getColumnIndex("id");
                    int nameIndex = cursor.getColumnIndex("name");
                    int priceIndex = cursor.getColumnIndex("price");
                    int imageIndex = cursor.getColumnIndex("image");

                    if (idIndex != -1 && nameIndex != -1 && priceIndex != -1 && imageIndex != -1) {
                        int id = cursor.getInt(idIndex);
                        String name = cursor.getString(nameIndex);
                        double price = cursor.getDouble(priceIndex);
                        int imageResId = cursor.getInt(imageIndex); // Obtén el ID de la imagen

                        products.add(new Product(id, name, price, imageResId));
                    } else {
                        Log.e("CatalogActivity", "Columnas no encontradas en el cursor.");
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return products;
    }

    // Método para filtrar los productos de acuerdo al texto ingresado
    private void filterProducts(String query) {
        List<Product> filteredList = new ArrayList<>();
        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(product);
            }
        }
        productAdapter = new ProductAdapter(filteredList);
        rvProductCatalog.setAdapter(productAdapter);
    }
}
