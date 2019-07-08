package filter;

import entities.Address;
import entities.Contact;
import entities.Phone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ContactValidator {
    private Contact contact;
    public ContactValidator(Contact contact){
        this.contact = contact;
    }

    public boolean validate(){//проверим контакт на валидность
        if(!helpValidate(contact.getName(), 20)){
            return false;
        }
        else if(!helpValidate(contact.getSurName(), 20)){
            return false;
        }
        else if(!helpValidate(contact.getFathersName(), 20)){
            return false;
        }
        else if(!checkBirthdate(contact.getBirthdate())){
            return false;
        }
        else if(!contact.getSex().equals("man") && !contact.getSex().equals("woman")){
            return false;
        }
        else if(!helpValidate(contact.getCitizenship(), 20)){
            return false;
        }
        else if(!contact.getFamilyStatus().equals("married") && !contact.getFamilyStatus().equals("not married") && !contact.getFamilyStatus().equals("diverced")){
            return false;
        }
        else if(!helpValidate(contact.getWebSite(), 40)){
            return false;
        }
        else if(!helpValidate(contact.getEmail(), 40) || !contact.getEmail().contains("@")){
            return false;
        }
        else if(!helpValidate(contact.getCurrentWorkspase(), 200)){
            return false;
        }
        else if(!checkAddress(contact.getAddress())){
            return false;
        }
        else if(!checkPhones(contact.getPhones())){
            return false;
        }
        return true;
    }

    public boolean checkAddress(Address address){
        if(!helpValidate(address.getCountry(), 40)){
            return false;
        }
        else if(!helpValidate(address.getCity(), 40)){
            return false;
        }
        else if(!helpValidate(address.getStreet(), 40)){
            return false;
        }
        else if(!helpValidate(address.getHomeNum(), 10)){
            return false;
        }
        else if(!helpValidate(address.getPostcode(), 20)){
            return false;
        }
        else if(address.getApartment() != null){
            if(address.getApartment().length() > 10){
                return false;
            }
        }
        return true;
    }

    public boolean checkPhones(ArrayList<Phone> phones){
        for(int i = 0; i < phones.size(); i++){
            if(phones.get(i).getCountryCode() == null || phones.get(i).getCountryCode() <= 0){
                return false;
            }
            else if(phones.get(i).getOperatorCode() == null || phones.get(i).getOperatorCode() <= 0){
                return false;
            }
            else if(!checkNumber(phones.get(i).getNumber())){
                return false;
            }
            else if(!phones.get(i).getType().equals("mobile") && !phones.get(i).getType().equals("home number")){
                return false;
            }
            else if(phones.get(i).getComment() != null){
                if(phones.get(i).getComment().length() > 1000){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean helpValidate(String s, int n){
        if(s == null || s == "" || s.length() > n){
            return false;
        }
        return true;
    }

    public boolean checkBirthdate(String s){
        s = s.replace("+", "");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy-MM-dd");
        try {
            return simpleDateFormat.format(simpleDateFormat.parse(s)).equals(s);
        }catch (ParseException e) {
            return false;
        }
    }

    private boolean checkNumber(String number){
        if(number == null || number.length() != 7){
            return false;
        }
        try {
            Integer.parseInt(number);
        } catch (Exception e){
            return false;
        }
        return true;
    }
}
