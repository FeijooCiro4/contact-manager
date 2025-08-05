package com.Model;

public class Contact {
    private int idContact;
    private final String name;
    private final String phone;
    private final String mail;

    public Contact(String name, String phone, String mail){
        this.name = name;
        this.phone = phone;
        this.mail = mail;
    }

    public void setExistingIdContact(int idContact){
        this.idContact = idContact;
    }

    public int getIdContact() {
        return idContact;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }
}
