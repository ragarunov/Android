package com.example.employeemanagement;

/*
Final Project
Android Application development
Ronen Agarunov - ragarunov@myseneca.ca
 */

import android.support.annotation.NonNull;

public class Employee {

    // Declarations
    private long id;
    private long empId_;
    private String name_;
    private String position_;
    private String address_;
    private String phone_;

    // Constructors
    Employee(long empId, String name, String position, String address, String phone) {
        this(empId, name, position, address, phone, -1);
    }

    Employee(long empId, String name, String position, String address, String phone, long id) {
        this.empId_ = empId;
        this.name_ = name;
        this.position_ = position;
        this.address_ = address;
        this.phone_ = phone;
        this.id = id;
    }

    // Getters
    public String getName() { return name_; }
    public String getPosition() { return position_; }
    public String getAddress() { return address_; }
    public String getPhone() { return phone_; }
    long getEmpId() { return empId_; }

    public long getId() { return id; }

    @NonNull
    @Override
    public String toString() { return getName(); }
}