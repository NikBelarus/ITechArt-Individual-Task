package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import entities.Contact;

import java.util.ArrayList;

public class ContactsAndNum {
    @JsonProperty("people")
    private ArrayList<Contact> contacts;

    @JsonProperty("contactsNum")
    private Integer num;

    public ContactsAndNum(){}

    public ContactsAndNum(ArrayList<Contact> contacts, Integer num) {
        this.contacts = contacts;
        this.num = num;
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "ContactsAndNum{" +
                "contacts=" + contacts +
                ", num=" + num +
                '}';
    }
}
