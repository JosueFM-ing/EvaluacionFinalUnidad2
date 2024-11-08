package com.example.gestortareas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "catalog.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCTS = "products";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla de productos con los campos id, nombre, precio y imagen
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, price REAL, image INTEGER)";
        db.execSQL(CREATE_PRODUCTS_TABLE);
        Log.d("DatabaseHelper", "Database created with table: " + TABLE_PRODUCTS);

        // Insertar productos de ejemplo directamente en la base de datos al crearla
        insertSampleProducts(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar la tabla si existe y crearla nuevamente
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    private void insertSampleProducts(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
        if (cursor.getCount() == 0) {
            Log.d("DatabaseHelper", "Inserting sample products...");
            insertProductDirect(db, "Martillo", 12.99, R.drawable.martillo);
            insertProductDirect(db, "Destornillador", 8.49, R.drawable.destornillador);
            insertProductDirect(db, "Taladro", 59.99, R.drawable.taladro);
            insertProductDirect(db, "Llave inglesa", 14.99, R.drawable.llaveinglesa);
            insertProductDirect(db, "Cinta métrica", 5.99, R.drawable.cintametrica);
        } else {
            Log.d("DatabaseHelper", "Sample products already exist in the database.");
        }
        cursor.close();
    }

    // Método para insertar un producto directamente en la base de datos
    private void insertProductDirect(SQLiteDatabase db, String name, double price, int imageResId) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("price", price);
        values.put("image", imageResId);  // Guardar el ID del recurso de la imagen
        long result = db.insert(TABLE_PRODUCTS, null, values);
        if (result != -1) {
            Log.d("DatabaseHelper", "Sample product inserted: " + name);
        } else {
            Log.e("DatabaseHelper", "Failed to insert product: " + name);
        }
    }

    // Método para obtener todos los productos de la base de datos
    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS;
        return db.rawQuery(selectQuery, null);
    }
}
