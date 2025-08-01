package com.Model;

import java.util.ArrayList;
import java.util.Random;

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

    public void setIdContact(ArrayList<Contact> contacts){
        int idTemp;
        boolean elementInList;

        do{
            Random rand = new Random();
            idTemp = Math.abs(rand.nextInt());
            elementInList = isContactInList(contacts, idTemp);
        } while(elementInList);

        this.idContact = idTemp;
    }

    public void setExistingIdContact(int idContact){
        this.idContact = idContact;
    }

    public boolean isContactInList(ArrayList<Contact> contacts, int idContactToSearch){
        if(contacts != null) {
            for (Contact contact : contacts) {
                if (contact.idContact == idContactToSearch) {
                    return true;
                }
            }
        }

        return false;
    }

    public int indexOfContactFromList(ArrayList<Contact> contacts, int idContactToSearch){
        if(contacts != null) {
            for (Contact contact : contacts) {
                if (contact.idContact == idContactToSearch) {
                    return contacts.indexOf(contact);
                }
            }
        }

        return -1;
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
