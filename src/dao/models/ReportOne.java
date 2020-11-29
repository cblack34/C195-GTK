package dao.models;

/**
 * Data Structure to hold information for The First Report about Appointments.
 */
public class ReportOne {
    int count;
    String type;
    String month;

    /** Constructs the Data Structure
     * @param count The count for this appointment type
     * @param type The type of appointment.
     */
    public ReportOne(int count, String type, String month) {
        this.count = count;
        this.type = type;
        this.month = month;
    }

    /** Get the count for this type of appointment
     * @return The count for this type of appointment
     */
    public int getCount() {
        return count;
    }

    /**
     * Set the count for this type of appointment
     */
    public void setCount(int count) {
        this.count = count;
    }

    /** Get they type of appointment counted in this report.
     * @return The type of appointment
     */
    public String getType() {
        return type;
    }

    /**
     * Set the Type for this appointment type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /** Get the Name of the month for this report item.
     * @return Name of the Month for this report item.
     */
    public String getMonth() {
        return month;
    }

    /** Set the name of the month for this report item.
     * @param month Name of the month for this report Item.
     */
    public void setMonth(String month) {
        this.month = month;
    }
}
