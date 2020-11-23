package dao.models;

/**
 * Data Structure to hold information about Contact
 */
public class Contact {
    private int id;
    private String name;
    private String email;

    /** Construct the data Structure
     * @param id The id of the contact.
     * @param name The Name of the contact.
     * @param email The email of the contact.
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * @return ID of the contact
     */
    public int getId() {
        return id;
    }

    /**
     * @param id ID of the contact
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The Name of the contact.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The Name of the contact.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The email of the contact.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email of the contact.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
