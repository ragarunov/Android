package com.example.employeemanagement;

/*
Final Project
Android Application development
Ronen Agarunov - ragarunov@myseneca.ca
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add extends AppCompatActivity {

    // Declarations
    private EditText EmployeeID;
    private EditText fullName;
    private EditText Position;
    private EditText Address;
    private EditText Phone;

    private EmployeeManager employeeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Set navigation buttons
        Button okButton = findViewById(R.id.submitButton);
        Button cancelButton = findViewById(R.id.clearButton);

        // Set fields
        EmployeeID = findViewById(R.id.AddIdEditText);
        fullName = findViewById(R.id.AddNameEditText);
        Position = findViewById(R.id.AddPositionEditText);
        Address = findViewById(R.id.AddAddressEditText);
        Phone = findViewById(R.id.AddPhoneEditText);

        employeeManager = new EmployeeManager(this);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNew(EmployeeID, fullName, Position, Address, Phone);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });
    }

    // Check if fields passed are empty, present a "Toast" message if they are
    // Create a new Employee object to employeeManager and add to database
    // Return to Main activity once done.
    private void addNew(EditText empId, EditText fName, EditText position, EditText address, EditText phone) {

        if (employeeManager.isEmpty(empId.getText().toString()) || employeeManager.isEmpty(fName.getText().toString()) ||
                employeeManager.isEmpty(position.getText().toString())
                || employeeManager.isEmpty(address.getText().toString()) ||
                employeeManager.isEmpty(phone.getText().toString())) {

            String text = "Cannot leave fields empty";
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }
        else if (employeeManager.doesExist(Integer.parseInt(empId.getText().toString()))) {
            Toast.makeText(this, "Employee ID already in the system", Toast.LENGTH_SHORT).show();
        }
        else {
            int empId_ = Integer.parseInt(empId.getText().toString()); // Parse values
            String formattedPhone = PhoneNumberUtils.formatNumber(phone.getText().toString()); // Format phone number xxx-xxx-xxxx (US/Canada zone)
            Employee emp = new Employee(
                    empId_,
                    fName.getText().toString(),
                    position.getText().toString(),
                    address.getText().toString(),
                    formattedPhone
            );

            // Add employee object to database
            employeeManager.addEmployee(emp);

            Toast.makeText(this, "Employee added!", Toast.LENGTH_SHORT).show();

            // Return to main
            finish();
        }
    }
}
