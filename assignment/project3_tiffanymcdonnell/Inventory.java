package com.assignment.project3_tiffanymcdonnell;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.Manifest;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Inventory extends AppCompatActivity implements ItemAdapter.OnItemClickListener, ItemAdapter.OnItemDeleteClickListener {

    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private Database db;
    private Switch sms;
    private static final int PERMISSION_SEND_SMS = 123;
    private static final int LOW_INVENTORY_THRESHOLD = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        db = new Database(this);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create and set up the DividerItemDecoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        updateList();

        Button addItemButton = findViewById(R.id.btnAddItem);
        addItemButton.setOnClickListener(v -> {
            Intent intent = new Intent(Inventory.this, ItemEdit.class);
            startActivity(intent);
        });

        sms = findViewById(R.id.switch1);
        sms.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // isChecked will be true if the switch is in the On position
            if (isChecked) {
                checkForSmsPermission();
            }
        });
    }


    @Override
    public void onItemClick(Item item) {
        Intent intent = new Intent(Inventory.this, ItemEdit.class);
        intent.putExtra("PRODUCT_ID", item.getItemName());
        intent.putExtra("PRODUCT_DESC", item.getItemDesc());
        intent.putExtra("PRODUCT_QUANTITY", item.getCount());
        startActivity(intent);
    }

    @Override
    public void onItemDelete(Item item, int position) {

        db.deleteItem(item);

        itemAdapter.removeItem(position);

        if (item.getCount() <= LOW_INVENTORY_THRESHOLD) {
            sendLowInventorySms(item);
        }
    }

    private void updateList() {
        List<Item> itemList = db.getList();
        if (itemAdapter == null) {
            itemAdapter = new ItemAdapter(itemList, this, this);
            recyclerView.setAdapter(itemAdapter);
        } else {
            itemAdapter = new ItemAdapter(itemList, this, this);
            recyclerView.swapAdapter(itemAdapter, false);
        }
    }

    private void checkForSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_SEND_SMS);
        } else {
            sendSmsMessage();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();

        checkInventoryLevels();
    }

    private void checkInventoryLevels() {
        List<Item> productList = db.getList();
        for (Item product : productList) {
            if (product.getCount() <= LOW_INVENTORY_THRESHOLD) {
                sendLowInventorySms(product);
            }
        }
    }

    private void sendSmsMessage() {
        String phoneNumber = "1234567890"; // Dummy number for testing
        String message = "This is a test SMS message.";

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
                Log.d("SMS_TEST", "SMS sent to " + phoneNumber + ": " + message);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Sending SMS failed (simulated).", Toast.LENGTH_LONG).show();
                Log.e("SMS_TEST", "SMS sending failed", e);
            }
        } else {
            Log.d("SMS_TEST", "SMS permission not granted.");
            // Permission not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_SEND_SMS);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSmsMessage();
            } else {
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_LONG).show();

            }
        }
    }

    private void sendLowInventorySms(Item item) {
        // Only attempt to send an SMS if the switch is on
        if (sms.isChecked()) {
            String phoneNumber = "5555555555"; //
            String message = "Low counts: " + item.getItemDesc() + "! Only " + item.getCount() + " left in stock.";

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_SEND_SMS);
            } else {
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "Low inventory alert for " + item.getItemDesc() + "! Only " + item.getCount() + " left in stock.", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Sending SMS failed.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        } else {
            // The switch is off, do not send SMS
            Toast.makeText(this, "SMS notification is disabled", Toast.LENGTH_SHORT).show();
        }
    }
}