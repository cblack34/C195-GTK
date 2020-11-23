package dao.models;

import java.util.Calendar;

/**
 * Data Structure to hold information about User.
 */
public class User {
    private int id;

    private String name;
    private String password;
    private String createdBy;
    private String updatedBy;

    private Calendar created;
    private Calendar updated;

    /**
     * Constructor of the User Class.
     * @param id The ID of the user.
     * @param name The name of the user.
     * @param password The password of the user.
     * @param createdBy The creator of the user.
     * @param updatedBy The last user to update the user.
     * @param created The datetime the user was created
     * @param updated The datetime the user was last updated.
     */
    public User(int id, String name, String password, String createdBy, String updatedBy, Calendar created, Calendar updated) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.created = created;
        this.updated = updated;
    }

    /**
     * @return ID of the user
     */
    public int getId() {
        return id;
    }

    /**
     * @param id ID of the user
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * @param name Name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password Password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return Datetime user was created.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy Datetime user was created.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return Last user to update this user
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy Last user to update this user
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return Datetime user was created.
     */
    public Calendar getCreated() {
        return created;
    }

    /**
     * @param created Datetime user was created.
     */
    public void setCreated(Calendar created) {
        this.created = created;
    }

    /**
     * @return Datetime user was updated.
     */
    public Calendar getUpdated() {
        return updated;
    }

    /**
     * @param updated Datetime user was updated.
     */
    public void setUpdated(Calendar updated) {
        this.updated = updated;
    }
}
