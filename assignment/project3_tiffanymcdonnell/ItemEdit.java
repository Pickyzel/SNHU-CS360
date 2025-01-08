package com.assignment.project3_tiffanymcdonnell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;

public class ItemEdit extends AppCompatActivity {

    private EditText itemIdentification;
    private EditText ItemDesc;
    private EditText ItemCount;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_edit);

        db = new Database(this);

        itemIdentification = findViewById(R.id.editItem);
        ItemDesc = findViewById(R.id.editItemDes);
        ItemCount = findViewById(R.id.editCount);


        Button btnSave = findViewById(R.id.btnUpdate);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("PRODUCT_ID")) {
            // If PRODUCT_ID is passed, it's an edit operation
            loadProductData(intent);
        }

        btnSave.setOnClickListener(v -> saveProduct());
    }


    private void loadProductData(Intent intent) {
        // Load product data from the intent and populate the EditText fields
        //Intent intent = getIntent();
        if (intent != null) {
            String productId = intent.getStringExtra("PRODUCT_ID");
            String productDesc = intent.getStringExtra("PRODUCT_DESC");
            int productQuantity = intent.getIntExtra("PRODUCT_QUANTITY", -1); // Default to -1 if not found

            // Populate the fields
            itemIdentification.setText(productId);
            ItemDesc.setText(productDesc);
            if (productQuantity != -1) {
                ItemCount.setText(String.valueOf(productQuantity));
            }
        }
    }

    private void saveProduct() {
        String productId = itemIdentification.getText().toString();
        String productDesc = ItemDesc.getText().toString();
        int quantity;
        try {
            quantity = Integer.parseInt(ItemCount.getText().toString());
        } catch (NumberFormatException e) {
            // Handle error where quantity is not an integer
            return;
        }

        Item product = new Item(productId, productDesc, quantity);
        if (db.ItemExists(productId)) {
            db.updateProduct(product);
        } else {
            db.addItem(product);
        }

        finish(); // Close the activity and go back to the previous one
    }

}