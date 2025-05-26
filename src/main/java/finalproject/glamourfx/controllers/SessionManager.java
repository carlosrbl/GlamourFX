/**
 * @author Carlos
 * This class manages the current customer logged in
 */

package finalproject.glamourfx.controllers;

import finalproject.glamourfx.data.Customer;

public class SessionManager
{
    public static Customer currentCustomer;

    /**
     * Sets the current customer.
     * @param customer The customer to set as the current customer.
     */
    public static void setCurrentCustomer(Customer customer) {
        currentCustomer = customer;
    }

    /**
     * Gets the current customer.
     * @return The current customer.
     */
    public static Customer getCurrentCustomer() {
        return currentCustomer;
    }
}
