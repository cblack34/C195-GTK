package dao.models;

import java.util.Calendar;

/**
 * Data Structure to hold information about CustomerDao.
 */
public class Customer {
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
     * @param id The ID of the CustomerDao
     * @param divisionID The ID of the division of the CustomerDao.
     * @param name The name of the CustomerDao.
     * @param address The address of the CustomerDao.
     * @param postalCode The Postal Code of the CustomerDao.
     * @param phone The Phone Number of the CustomerDao.
     * @param createdBy The user that created the CustomerDao.
     * @param updatedBy The user that last updated the CustomerDao.
     * @param created The datetime the CustomerDao was created.
     * @param updated The datetime the CustomerDao was last updated.
     */
    public Customer(int id, int divisionID, String name, String address, String postalCode, String phone, String createdBy, String updatedBy, Calendar created, Calendar updated) {
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
     * @return The ID of the CustomerDao
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The ID of the CustomerDao
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The ID of the division of the CustomerDao.
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * @param divisionID The ID of the division of the CustomerDao.
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * @return The name of the CustomerDao.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name of the CustomerDao.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The address of the CustomerDao.
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address The address of the CustomerDao.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return The Postal Code of the CustomerDao.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode The Postal Code of the CustomerDao.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return The Phone Number of the CustomerDao.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone The Phone Number of the CustomerDao.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return The user that created the CustomerDao.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy The user that created the CustomerDao.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return The user that last updated the CustomerDao.
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy The user that last updated the CustomerDao.
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return The datetime the CustomerDao was created.
     */
    public Calendar getCreated() {
        return created;
    }

    /**
     * @param created The datetime the CustomerDao was created.
     */
    public void setCreated(Calendar created) {
        this.created = created;
    }

    /**
     * @return The datetime the CustomerDao was last updated.
     */
    public Calendar getUpdated() {
        return updated;
    }

    /**
     * @param updated The datetime the CustomerDao was last updated.
     */
    public void setUpdated(Calendar updated) {
        this.updated = updated;
    }
}
