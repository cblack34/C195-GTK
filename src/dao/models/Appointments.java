package dao.models;

import java.util.Calendar;

/**
 * Data Structure to hold information about Appointments
 */
public class Appointments {
    private int id;
    private int customerID;
    private int userID;
    private int contactID;

    private String title;
    private String description;
    private String location;
    private String type;
    private String createdBy;
    private String updatedBy;

    private Calendar start;
    private Calendar end;
    private Calendar created;
    private Calendar updated;

    /** Constructs the Data Structure
     * @param id The id of the Appointment.
     * @param customerID The ID of the customer associated with the Appointment.
     * @param userID The ID of the User associated with the Appointment.
     * @param contactID The ID of the contact Associated with the Appointment.
     * @param title The Title or Subject of the Appointment.
     * @param description The description of the Appointment.
     * @param location The location of the Appointment.
     * @param type The type of Appointment.
     * @param createdBy The user that created the Appointment.
     * @param updatedBy The user that updated the Appointment.
     * @param start The start datetime of the Appointment.
     * @param end The end datetime of the Appointment.
     * @param created The datetime the Appointment was created.
     * @param updated The datetime the Appointment was last updated.
     */
    public Appointments(int id, int customerID, int userID, int contactID, String title, String description, String location, String type, String createdBy, String updatedBy, Calendar start, Calendar end, Calendar created, Calendar updated) {
        this.id = id;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.start = start;
        this.end = end;
        this.created = created;
        this.updated = updated;
    }

    /**
     * @return appointment ID
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the appointment ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return customer ID
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID Set the customerID of the customer associated with this appointment.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * @return userID of user associated with this appointment
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @param userID Set the userID of the user associated with this appointment.
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * @return contact ID of contact associated with this appointment.
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * @param contactID ID of contact associated with this appointment.
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * @return The title or subject of this appointment.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the Title or subject of this appointment.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return descrition of this appointment.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description description of this appointment.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return location of this appointment.
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location The location of this appointment.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return The type of appointment.
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type of appointment.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The user that created the appointment.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy The user that created the appointment.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return The user that updated the appointment last.
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy The user that updated the appointment last.
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the start datetime of the appointment.
     */
    public Calendar getStart() {
        return start;
    }

    /**
     * @param start The start datetime of the appointment.
     */
    public void setStart(Calendar start) {
        this.start = start;
    }

    /**
     * @return The end datetime of the appointment.
     */
    public Calendar getEnd() {
        return end;
    }

    /**
     * @param end The end datetime of the appointment.
     */
    public void setEnd(Calendar end) {
        this.end = end;
    }

    /**
     * @return The datetime the appointment was created.
     */
    public Calendar getCreated() {
        return created;
    }

    /**
     * @param created The datetime the appointment was created.
     */
    public void setCreated(Calendar created) {
        this.created = created;
    }

    /**
     * @return The datetime the appointment was last updated.
     */
    public Calendar getUpdated() {
        return updated;
    }

    /**
     * @param updated The datetime the appointment was last updated.
     */
    public void setUpdated(Calendar updated) {
        this.updated = updated;
    }
}
