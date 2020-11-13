package dao.models;

import java.util.Calendar;

/**
 * Data Structure to hold information about Countries
 */
public class Countries {
    private int id;

    private String country;
    private String createdBy;
    private String updatedPy;

    private Calendar created;
    private Calendar updated;

    /**
     * @param id The id of the Country.
     * @param country The name of the Country.
     * @param createdBy The user that added the Country to the db.
     * @param updatedPy The user that last updated the Country in the db.
     * @param created The date the Country was added to the db.
     * @param updated The date the Country was last updated in the db.
     */
    public Countries(int id, String country, String createdBy, String updatedPy, Calendar created, Calendar updated) {
        this.id = id;
        this.country = country;
        this.createdBy = createdBy;
        this.updatedPy = updatedPy;
        this.created = created;
        this.updated = updated;
    }

    /**
     * @return The id of the Country.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The id of the Country.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The name of the Country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country The name of the Country.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return The user that added the Country to the db.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy The user that added the Country to the db.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return The user that last updated the Country in the db.
     */
    public String getUpdatedPy() {
        return updatedPy;
    }

    /**
     * @param updatedPy The user that last updated the Country in the db.
     */
    public void setUpdatedPy(String updatedPy) {
        this.updatedPy = updatedPy;
    }

    /**
     * @return The date the Country was added to the db.
     */
    public Calendar getCreated() {
        return created;
    }

    /**
     * @param created The date the Country was added to the db.
     */
    public void setCreated(Calendar created) {
        this.created = created;
    }

    /**
     * @return The date the Country was last updated in the db.
     */
    public Calendar getUpdated() {
        return updated;
    }

    /**
     * @param updated The date the Country was last updated in the db.
     */
    public void setUpdated(Calendar updated) {
        this.updated = updated;
    }
}
