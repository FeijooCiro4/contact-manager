package com.Model;

import java.util.ArrayList;

public class ContactList{
    private ArrayList<Contact> contacts;
    private ArrayList<ContactModelListener> listeners;

    public ContactList(){
        this.contacts = new ArrayList<>();
        this.listeners = new ArrayList<>();
    }

    public ArrayList<Contact> getContactList(){
        return new ArrayList<>(contacts);
    }

    public Contact getContactFromList(int index){
        return contacts.get(index);
    }

    public Contact getContactFromListWhitId(int idContact){
        for(Contact contact : contacts){
            if(contact.getIdContact() == idContact){
                return contact;
            }
        }

        return null;
    }

    public void addContactListener(ContactModelListener listener){
        this.listeners.add(listener);
    }

    public void addContact(String name, String phone, String mail) {
        Contact contact = new Contact(name, phone, mail);
        contact.setIdContact(contacts);
        this.contacts.add(contact);
        notifyListeners();
    }

    public boolean modifyContactFromList(String name, String phone, String mail, int idContactToSearch){
        boolean modificationSuccessful;

        try {
            int indexOfContactSearched = contacts.getFirst().indexOfContactFromList(contacts, idContactToSearch);

            if (indexOfContactSearched >= 0) {
                Contact contact = new Contact(name, phone, mail);
                contact.setExistingIdContact(idContactToSearch);

                this.contacts.set(indexOfContactSearched, contact);
                modificationSuccessful = true;
            } else {
                modificationSuccessful = false;
            }
        } catch (IndexOutOfBoundsException e){
            modificationSuccessful = false;
        }

        notifyListeners();
        return modificationSuccessful;
    }

    public boolean deleteContactFromList(int idContactToDelete){
        boolean deletionSuccessful;

        try {
            int indexOfContactSearched = contacts.getFirst().indexOfContactFromList(contacts, idContactToDelete);

            if (indexOfContactSearched >= 0) {
                this.contacts.remove(indexOfContactSearched);
                deletionSuccessful = true;
            } else {
                deletionSuccessful = false;
            }
        } catch (IndexOutOfBoundsException e){
            deletionSuccessful = false;
        }

        notifyListeners();
        return deletionSuccessful;
    }

    public void clearContactList(){
        this.contacts.clear();
        notifyListeners();
    }

    public String getListOfContactList(){
        StringBuilder showData = new StringBuilder();

        if (!contacts.isEmpty()) {
            for (Contact contact : contacts) {
                showData.append("Nombre: ").append(contact.getName()).append(" - Teléfono: ")
                        .append(contact.getPhone()).append(" - Mail: ").append(contact.getMail());
            }
        }

        return  showData.toString();
    }

    public void notifyListeners(){
        ArrayList<Contact> currentContacts = new ArrayList<>(contacts);
        for(ContactModelListener listener : listeners){
            listener.onContactChanged(currentContacts);
        }
    }
}