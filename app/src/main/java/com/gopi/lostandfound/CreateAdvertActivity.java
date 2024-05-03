package com.gopi.lostandfound;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.gopi.lostandfound.services.DBHelper;
import com.gopi.lostandfound.services.Item;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateAdvertActivity extends AppCompatActivity {

    private RadioGroup radioGroupPostType;
    private EditText editTextName, editTextPhone, editTextDescription, editTextDate, editTextLocation;
    private Button btnSave;
    private DBHelper dbHelper;
    int year=0, month=0, day=0;
    String selectedDateSTR="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_advert);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        radioGroupPostType = findViewById(R.id.RGPostType);
        editTextName = findViewById(R.id.ETName);
        editTextPhone = findViewById(R.id.ETPhone);
        editTextDescription = findViewById(R.id.ETDescription);
        editTextDate = findViewById(R.id.ETDate);
        editTextLocation = findViewById(R.id.ETLocation);
        btnSave = findViewById(R.id.BTNSave);


        // Get the current date and format it
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Customize the format as needed
        String currentDate = sdf.format(new Date());
        // Set the formatted date in the TextView
        editTextDate.setText(currentDate);

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                // Do something with the selected date
                String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                // You can display the date in a TextView or EditText, save it to a variable, etc.
                editTextDate.setText(selectedDate);
            }
        };


        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        btnSave.setOnClickListener(v -> saveItem());
    }

    private void saveItem() {
        String type = radioGroupPostType.getCheckedRadioButtonId() == R.id.RBLost ? "Lost" : "Found";
        String name = editTextName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();

        // Validate input
        if (name.isEmpty() || phone.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty()) {
            // Show error message or handle invalid input
            return;
        }

        // Create new Item object
        Item item = new Item(type, name, phone, description, date, location);

        // Add item to database
        dbHelper.addItem(item);

        // Clear input fields
        editTextName.setText("");
        editTextPhone.setText("");
        editTextDescription.setText("");
        editTextDate.setText("");
        editTextLocation.setText("");

        // Show success message or navigate to another screen
         Toast.makeText(this, "Item saved successfully", Toast.LENGTH_SHORT).show();
        // startActivity(new Intent(this, AnotherActivity.class));
    }


    public void showDatePickerDialog() {
        // Get the current date to pre-select in the DatePickerDialog
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog with the correct parameters
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                // Handle the selected date and set it in the EditText
                year = selectedYear;
                month = selectedMonth;
                day = selectedDay;
                selectedDateSTR = day + "-" + (month + 1) + "-" + year; // Format the date as needed
                editTextDate.setText(selectedDateSTR);
            }
        }, year, month, day);
        datePickerDialog.show();
    }


}
