package com.example.employeemanagement;

/*
Final Project
Android Application development
Ronen Agarunov - ragarunov@myseneca.ca
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class all extends AppCompatActivity {

    // Declarations
    private EmployeeManager employeeManager;
    private EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

        // Pull view and navigation button
        ListView employeeList = findViewById(R.id.employee_list);
        Button doneButton = findViewById(R.id.doneListButton);

        // Set button action
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });

        // Set adapter for list of employees
        employeeManager = new EmployeeManager(this);
        adapter = new EmployeeAdapter(this, employeeManager.getEmployees());

        employeeList.setAdapter(adapter);
    }

    // List adapter
    private class EmployeeAdapter extends ArrayAdapter<Employee> {

        private Context context;
        private List<Employee> employees;

        // Constructor
        private EmployeeAdapter(Context context, List<Employee> employees) {
            super(context, -1, employees);

            this.context = context;
            this.employees = employees;
        }

        // Update list view after every change
        void updateEmployees(List<Employee> employees) {
            this.employees = employees;
            notifyDataSetChanged();
        }

        // Returns count of employees list
        @Override
        public int getCount() {
            return employees.size();
        }

        // Pull and set view, using inflation
        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {

            final EmployeeViewHolder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.employee_list, parent, false);

                holder = new EmployeeViewHolder(); // new Holder object to pull data into
                holder.employeeId = convertView.findViewById(R.id.employeeTextView);
                holder.employeeName = convertView.findViewById(R.id.employeeNameTextView);
                convertView.setTag(holder);
            }
            else {
                holder = (EmployeeViewHolder) convertView.getTag();
            }

            // Sets data into respectable variables to print on list
            holder.employeeId.setText(String.valueOf(employees.get(position).getEmpId()));
            holder.employeeName.setText(employees.get(position).getName());

            // Get database id into a separate value
            // id_ != employeeId
            holder.id_ = employees.get(position).getId();

            // Remove image
            ImageButton removeButton = convertView.findViewById(R.id.remove_employee);
            removeButton.setTag(employees.get(position));

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    employeeManager.removeEmployee(holder.id_); // Send entry id to remove from db functionality
                    adapter.updateEmployees(employeeManager.getEmployees()); // Update list
                }
            });

            return convertView;
        }
    }

    // Holds view
    public static class EmployeeViewHolder {
        TextView employeeId;
        TextView employeeName;
        long id_;
    }
}
