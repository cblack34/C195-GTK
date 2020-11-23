package dao.models;

import java.util.Calendar;

/**
 * Data Structure to hold information about First_Level_Division
 */
public class First_Level_Division {
    private int id;
    private int countryID;

    private String division;
    private String createdBy;
    private String updatedBY;

    private Calendar created;
    private Calendar updated;

    /** Constructs the Data Structure.
     * @param id The ID of the First_Level_Division.
     * @param countryID The ID of the Country associated with the First_Level_Division.
     * @param division The division of the First_Level_Division.
     * @param createdBy The user that created the First_Level_Division.
     * @param updatedBY The user that last updated the First_Level_Division.
     * @param created The datetime the First_Level_Division was created.
     * @param updated the datetime the First_Level_Division was last updated.
     */
    public First_Level_Division(int id, int countryID, String division, String createdBy, String updatedBY, Calendar created, Calendar updated) {
        this.id = id;
        this.countryID = countryID;
        this.division = division;
        this.createdBy = createdBy;
        this.updatedBY = updatedBY;
        this.created = created;
        this.updated = updated;
    }

    /**
     * @return The ID of the First_Level_Division.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The ID of the First_Level_Division.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The ID of the Country associated with the First_Level_Division.
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * @param countryID The ID of the Country associated with the First_Level_Division.
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * @return The division of the First_Level_Division.
     */
    public String getDivision() {
        return division;
    }

    /**
     * @param division The division of the First_Level_Division.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * @return The user that created the First_Level_Division.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy The user that created the First_Level_Division.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return The user that last updated the First_Level_Division.
     */
    public String getUpdatedBY() {
        return updatedBY;
    }

    /**
     * @param updatedBY The user that last updated the First_Level_Division.
     */
    public void setUpdatedBY(String updatedBY) {
        this.updatedBY = updatedBY;
    }

    /**
     * @return The datetime the First_Level_Division was created.
     */
    public Calendar getCreated() {
        return created;
    }

    /**
     * @param created The datetime the First_Level_Division was created.
     */
    public void setCreated(Calendar created) {
        this.created = created;
    }

    /**
     * @return the datetime the First_Level_Division was last updated.
     */
    public Calendar getUpdated() {
        return updated;
    }

    /**
     * @param updated the datetime the First_Level_Division was last updated.
     */
    public void setUpdated(Calendar updated) {
        this.updated = updated;
    }
}
