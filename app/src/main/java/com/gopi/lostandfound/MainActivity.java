package com.gopi.lostandfound;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button CreateAdvertBTN, ShowItemsBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        CreateAdvertBTN = findViewById(R.id.BTNCreateAdvert);
        ShowItemsBTN = findViewById(R.id.BTNShowItems);

        // Set click listeners for buttons
        CreateAdvertBTN.setOnClickListener(view -> {
            // Navigate to CreateAdvertActivity when CreateAdvertBTN is clicked
            Intent intent = new Intent(MainActivity.this, CreateAdvertActivity.class);
            startActivity(intent);
        });

        ShowItemsBTN.setOnClickListener(view -> {
            // Navigate to ShowItemsActivity when ShowItemsBTN is clicked
            Intent intent = new Intent(MainActivity.this, ShowItemsActivity.class);
            startActivity(intent);
        });


    }
}