package dto;

public class ContactInfoForAutoPosting {
    private String name;
    private String surName;
    private String fathersName;
    private String email;

    public ContactInfoForAutoPosting(){}

    public ContactInfoForAutoPosting(String name, String surName, String fathersName, String email) {
        this.name = name;
        this.surName = surName;
        this.fathersName = fathersName;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ContactInfoForAutoPosting{" +
                "name='" + name + '\'' +
                ", surName='" + surName + '\'' +
                ", fathersName='" + fathersName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
