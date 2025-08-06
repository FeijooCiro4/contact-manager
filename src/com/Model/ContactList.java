package com.Model;

import java.util.ArrayList;
import java.util.Random;

public class ContactList{
    private ArrayList<Contact> contacts;
    private ArrayList<Contact> contactsFilter;
    private ArrayList<ContactModelListener> listeners;
    private boolean isFilterActive;
    private int idOfContactToChange;

    public ContactList(){
        this.contacts = new ArrayList<>();
        this.contactsFilter = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.isFilterActive = false;
    }

    public void setFilterActive(boolean isActive){
        this.isFilterActive = isActive;
        notifyListeners();
    }

    public void filterContactsOfContactList(String valueSearched){
        clearFilterOldElements();
        for(Contact contact : contacts){
            if(contact.getName().contains(valueSearched) || contact.getMail().contains(valueSearched) || contact.getPhone().contains(valueSearched)){
                this.contactsFilter.add(contact);
            }
        }
        notifyListeners();
    }

    public void clearFilterOldElements(){
        this.contactsFilter.clear();
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
        setIdContact(contact);
        this.contacts.add(contact);
        notifyListeners();
    }

    private void setIdContact(Contact contact){
        int idTemp;
        boolean elementInList;

        do{
            Random rand = new Random();
            idTemp = Math.abs(rand.nextInt());
            elementInList = isContactInList(idTemp);
        } while(elementInList);

        contact.setExistingIdContact(idTemp);
    }

    public boolean isContactInList(int idContactToSearch){
        if(contacts != null) {
            for (Contact contact : contacts) {
                if (contact.getIdContact() == idContactToSearch) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean modifyContactFromList(String name, String phone, String mail, int idContactToSearch){
        boolean modificationSuccessful, modificationFilteredListSuccessful;

        modificationSuccessful = changeOriginalContactList(name, phone, mail, idContactToSearch);

        if(isFilterActive){
            modificationFilteredListSuccessful = changeFilteredContactList(name, phone, mail, idContactToSearch);
            if(!modificationFilteredListSuccessful) modificationSuccessful = false;
        }

        notifyListeners();
        return modificationSuccessful;
    }

    private boolean changeOriginalContactList(String name, String phone, String mail, int idContactToSearch){
        boolean modificationSuccessful;

        try {
            int indexOfContactSearched = indexOfContactFromList(contacts, idContactToSearch);

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

        return modificationSuccessful;
    }

    private boolean changeFilteredContactList(String name, String phone, String mail, int idContactToSearch){
        boolean modificationSuccessful;

        try {
            int indexOfContactSearched = indexOfContactFromList(contactsFilter, idContactToSearch);

            if (indexOfContactSearched >= 0) {
                Contact contact = new Contact(name, phone, mail);
                contact.setExistingIdContact(idContactToSearch);

                this.contactsFilter.set(indexOfContactSearched, contact);
                modificationSuccessful = true;
            } else {
                modificationSuccessful = false;
            }
        } catch (IndexOutOfBoundsException e){
            modificationSuccessful = false;
        }

        return modificationSuccessful;
    }

    public boolean deleteContactFromList(int idContactToDelete){
        boolean deletionSuccessful, deletionOfElementFromFilteredListSuccessful;

        deletionSuccessful = deleteOriginalContactList(idContactToDelete);

        if(isFilterActive){
            deletionOfElementFromFilteredListSuccessful = deleteFilteredContactList(idContactToDelete);
            if(!deletionOfElementFromFilteredListSuccessful) deletionSuccessful = false;
        }

        notifyListeners();
        return deletionSuccessful;
    }

    private boolean deleteOriginalContactList(int idContactToDelete){
        boolean deletionSuccessful;

        try {
            int indexOfContactSearched = indexOfContactFromList(contacts, idContactToDelete);

            if (indexOfContactSearched >= 0) {
                this.contacts.remove(indexOfContactSearched);
                deletionSuccessful = true;
            } else {
                deletionSuccessful = false;
            }
        } catch (IndexOutOfBoundsException e){
            deletionSuccessful = false;
        }

        return deletionSuccessful;
    }

    private boolean deleteFilteredContactList(int idContactToDelete){
        boolean deletionSuccessful;

        try {
            int indexOfContactSearched = indexOfContactFromList(contactsFilter, idContactToDelete);

            if (indexOfContactSearched >= 0) {
                this.contactsFilter.remove(indexOfContactSearched);
                deletionSuccessful = true;
            } else {
                deletionSuccessful = false;
            }
        } catch (IndexOutOfBoundsException e){
            deletionSuccessful = false;
        }

        return deletionSuccessful;
    }

    private int indexOfContactFromList(ArrayList<Contact> contactList, int idContactToSearch){
        if(contacts != null) {
            for (Contact contact : contactList) {
                if (contact.getIdContact() == idContactToSearch) {
                    return contacts.indexOf(contact);
                }
            }
        }

        return -1;
    }

    public void clearContactList(){
        if(isFilterActive){
            deleteElementsOfListCleanedOnFilteredList();
            this.contactsFilter.clear();
            setFilterActive(false);
        } else {
            this.contacts.clear();
            notifyListeners();
        }
    }

    private void deleteElementsOfListCleanedOnFilteredList(){
        for(int i=0; i<contacts.size(); i++){
            for (Contact contactFiltered : contactsFilter) {
                if (contacts.get(i).getIdContact() == contactFiltered.getIdContact()) {
                    this.contacts.remove(i);
                }
            }
        }
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
        ArrayList<Contact> currentContacts;
        if(isFilterActive){
            currentContacts = new ArrayList<>(contactsFilter);
        } else {
            currentContacts = new ArrayList<>(contacts);
        }

        for(ContactModelListener listener : listeners){
            listener.onContactChanged(currentContacts);
        }
    }

    public void setIdOfContactToChange(int id){
        this.idOfContactToChange = id;
    }

    public int getIdOfContactToChange(){
        return idOfContactToChange;
    }
}