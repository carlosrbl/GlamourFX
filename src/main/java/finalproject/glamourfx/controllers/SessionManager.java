package finalproject.glamourfx.controllers;

import finalproject.glamourfx.data.Customer;

public class SessionManager
{
    public static Customer currentCustomer;

    public static void setCurrentCustomer(Customer customer) {
        currentCustomer = customer;
    }

    public static Customer getCurrentCustomer() {
        return currentCustomer;
    }
}
