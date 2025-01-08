package com.assignment.cs360_mcdonnell_tiffany_inventory;

import android.os.Bundle;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import adapters.InventoryAdapter;
import data.InventoryItem;

public class InventoryActivity extends AppCompatActivity {

    ArrayList<InventoryItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inventory);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        items = InventoryItem.getExampleInventory();

        InventoryAdapter adapter = new InventoryAdapter(this, R.layout.inventory_items, items);

        GridView gridView = (GridView)findViewById(R.id.inventoryGrid);

        gridView.setAdapter(null);
    }
}