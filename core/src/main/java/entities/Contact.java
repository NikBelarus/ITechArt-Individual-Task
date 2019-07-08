package entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Contact {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("surName")
    private String surName;

    @JsonProperty("fathersName")
    private String fathersName;

    @JsonProperty("birthdate")
    private String birthdate;

    @JsonProperty("sex")
    private String sex;

    @JsonProperty("citizenship")
    private String citizenship;

    @JsonProperty("familyStatus")
    private String familyStatus;

    @JsonProperty("webSite")
    private String webSite;

    @JsonProperty("email")
    private String email;

    @JsonProperty("currentWorkspase")
    private String currentWorkspase;

    @JsonProperty("photo")
    private String photo;

    @JsonProperty("address")
    private Address address;

    @JsonProperty("phones")
    private ArrayList<Phone> phones;

    @JsonProperty("attachments")
    private ArrayList<Attachment> attachments;

    public Contact() {}

    public Contact(Integer id, String name, String surName, String fathersName, String birthdate, String sex,
                   String citizenship, String familyStatus, String webSite, String email, String currentWorkspase,
                   String photo, Address address, ArrayList<Phone> phones, ArrayList<Attachment> attachments) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.fathersName = fathersName;
        this.birthdate = birthdate;
        this.sex = sex;
        this.citizenship = citizenship;
        this.familyStatus = familyStatus;
        this.webSite = webSite;
        this.email = email;
        this.currentWorkspase = currentWorkspase;
        this.photo = photo;
        this.address = address;
        this.phones = phones;
        this.attachments = attachments;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ArrayList<Phone> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<Phone> phones) {
        this.phones = phones;
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFamilyStatus() {
        return familyStatus;
    }

    public void setFamilyStatus(String familyStatus) {
        this.familyStatus = familyStatus;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentWorkspase() {
        return currentWorkspase;
    }

    public void setCurrentWorkspase(String currentWorkspase) {
        this.currentWorkspase = currentWorkspase;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surName='" + surName + '\'' +
                ", fathersName='" + fathersName + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", sex='" + sex + '\'' +
                ", citizenship='" + citizenship + '\'' +
                ", familyStatus='" + familyStatus + '\'' +
                ", webSite='" + webSite + '\'' +
                ", email='" + email + '\'' +
                ", currentWorkspase='" + currentWorkspase + '\'' +
                ", photo='" + photo + '\'' +
                ", address=" + address +
                ", phones=" + phones +
                ", attachments=" + attachments +
                '}';
    }
}