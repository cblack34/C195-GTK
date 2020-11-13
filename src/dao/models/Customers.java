package dao.models;

import java.util.Calendar;

/**
 * Data Structure to hold information about Customers.
 */
public class Customers {
    private int id;
    private int divisionID;

    private String name;
    private String address;
    private String postalCode;
    private String phone;

    private String createdBy;
    private String updatedBy;
    private Calendar created;
    private Calendar updated;

    /** Constructor of the Data Structure
     * @param id The ID of the Customer
     * @param divisionID The ID of the division of the Customer.
     * @param name The name of the Customer.
     * @param address The address of the Customer.
     * @param postalCode The Postal Code of the Customer.
     * @param phone The Phone Number of the Customer.
     * @param createdBy The user that created the Customer.
     * @param updatedBy The user that last updated the Customer.
     * @param created The datetime the Customer was created.
     * @param updated The datetime the Customer was last updated.
     */
    public Customers(int id, int divisionID, String name, String address, String postalCode, String phone, String createdBy, String updatedBy, Calendar created, Calendar updated) {
        this.id = id;
        this.divisionID = divisionID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.created = created;
        this.updated = updated;
    }

    /**
     * @return The ID of the Customer
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The ID of the Customer
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The ID of the division of the Customer.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * @param divisionID The ID of the division of the Customer.
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * @return The name of the Customer.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name of the Customer.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The address of the Customer.
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address The address of the Customer.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return The Postal Code of the Customer.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode The Postal Code of the Customer.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return The Phone Number of the Customer.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone The Phone Number of the Customer.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return The user that created the Customer.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy The user that created the Customer.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return The user that last updated the Customer.
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy The user that last updated the Customer.
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return The datetime the Customer was created.
     */
    public Calendar getCreated() {
        return created;
    }

    /**
     * @param created The datetime the Customer was created.
     */
    public void setCreated(Calendar created) {
        this.created = created;
    }

    /**
     * @return The datetime the Customer was last updated.
     */
    public Calendar getUpdated() {
        return updated;
    }

    /**
     * @param updated The datetime the Customer was last updated.
     */
    public void setUpdated(Calendar updated) {
        this.updated = updated;
    }
}
