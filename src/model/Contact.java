package model;

/** This class is responsible for the functionality of the Contact class. */
public class Contact {
    private int contactId;
    private String contactName;
    private String email;

    /** This constructor initializes the fields from the three parameters.
     *   @param contactId to set contactId
     *   @param contactName to set contactName
     *   @param email to set email
     */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() { return contactName; }
}
