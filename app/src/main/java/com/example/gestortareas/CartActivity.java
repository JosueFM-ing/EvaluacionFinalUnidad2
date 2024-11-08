package com.example.gestortareas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartActivity extends AppCompatActivity {

    private RecyclerView rvCartProducts;
    private TextView tvTotalPrice;
    private Button btnProceedToCheckout;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rvCartProducts = findViewById(R.id.rv_order_products);
        tvTotalPrice = findViewById(R.id.tv_order_total);

        rvCartProducts.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(Cart.getInstance().getProducts());
        rvCartProducts.setAdapter(productAdapter);

        double totalPrice = Cart.getInstance().getTotalPrice();
        tvTotalPrice.setText("Total: $" + totalPrice);



    }
}

