package com.example.employeemanagement;

/*
Final Project
Android Application development
Ronen Agarunov - ragarunov@myseneca.ca
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.employeemanagement.domain.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    // Declarations
    private EmployeeManager employeeManager;
    private EditText find; // Get value for search employee

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Navigation buttons
        Button addButton = findViewById(R.id.addButton);
        Button findButton = findViewById(R.id.findButton);
        Button showAllButton = findViewById(R.id.allButton);
        Button aboutButton = findViewById(R.id.aboutButton);
        Button exitButton = findViewById(R.id.exitButton);

        employeeManager = new EmployeeManager(this);

        // Get value for search employee
        find = findViewById(R.id.findEditText);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Add.class);
                startActivity(intent);
            }
        });

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findButtonClick(find);
            }
        });

        showAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, all.class);
                startActivity(intent);
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutButtonClick();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

    // Check if find value given is empty
    // Creates a Dialog window presenting all the information of requested employee
    // If employee doesn't exist, returns a message telling so
    private void findButtonClick(EditText find) {

        if (employeeManager.isEmpty(find.getText().toString())) {
            Toast.makeText(this, "Cannot leave field empty", Toast.LENGTH_SHORT).show();
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            int empId_ = Integer.parseInt(find.getText().toString()); // Parse

            builder.setTitle("Employee #" + empId_);

            String dialogEmpId = employeeManager.getEmployee(empId_);

            builder.setMessage(dialogEmpId);

            builder.setNegativeButton(
                    getResources().getString(R.string.done),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }
            );

            builder.show(); // Show
        }
    }

    private void aboutButtonClick() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("About");

        String message = "This app was created by Ronen Agarunov\n";
        message += "F2018 - Android Application Development\n\n";
        message += "Contact:\n";
        message += "ironenag@gmail.com OR ragarunov@myseneca.ca";

        builder.setMessage(message);

        builder.setNegativeButton(
                getResources().getString(R.string.done),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
        );

        builder.show(); // Show
    }
}
