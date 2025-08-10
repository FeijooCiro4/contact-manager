package com.Controller;

import com.Model.Contact;
import com.Model.ContactList;
import com.Model.ContactModelListener;
import com.View.ContactView;
import com.View.ContactViewListener;

import javax.swing.*;
import java.util.ArrayList;

public class ContactController implements ContactModelListener, ContactViewListener {
    private ContactView view;
    private ContactList model;

    private boolean isEditing;

    public ContactController(ContactList model, ContactView view){
        this.model = model;
        this.view = view;

        this.model.addContactListener(this);
        this.view.addContactViewListener(this);

        this.isEditing = false;

        view.getTablePanel().displayContactTable(model.getContactList());
    }

    @Override
    public void onContactChanged(ArrayList<Contact> contacts) {
        view.getTablePanel().displayContactTable(contacts);
    }

    @Override
    public void onSaveContactRequested() {
        String txtName = view.getFormPanel().getTfName();
        String txtPhone = view.getFormPanel().getTfPhone();
        String txtMail = view.getFormPanel().getTfMail();

        if(!validData(txtName, txtPhone, txtMail)){
            this.view.getFormPanel().setTextErrorToLabelInput();
            return;
        }

        this.view.getFormPanel().setTextNotErrorToLabelInput();

        if(isEditing){
            modifyContactFromContactList(txtName, txtPhone, txtMail);
        } else {
            addContactOnContactList(txtName, txtPhone, txtMail);
        }

        this.view.getFormPanel().clearInputFields();
    }

    @Override
    public void onDeleteContactRequested(int idContact) {
        int confirm = JOptionPane.showConfirmDialog(view, "¿Usted está seguro de eliminar este contacto?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION){
            if(!model.deleteContactFromList(idContact)){
                JOptionPane.showMessageDialog(view, "Ha ocurrido un error");
            }
        }
    }

    @Override
    public void onChangeContactRequested(int idContact) {
        String newName = model.getContactFromListWhitId(idContact).getName();
        String newPhone = model.getContactFromListWhitId(idContact).getPhone();
        String newMail = model.getContactFromListWhitId(idContact).getMail();

        this.view.getFormPanel().setTextFieldForm(newName, newPhone, newMail);
        this.view.getFormPanel().setTextToBtnSend(true);
        this.model.setIdOfContactToChange(idContact);
        setEditing(true);
    }

    @Override
    public void onClearContactListRequested() {
        int confirm = JOptionPane.showConfirmDialog(view, "¿Usted está seguro de vaciar a lista?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION){
            model.clearContactList();
            view.getTablePanel().cleanTfFilter();
        }
    }

    @Override
    public void onSearchContactListRequested() {
        String valueSearched = view.getTablePanel().getTfFilter();

        if(valueSearched != null){
            model.filterContactsOfContactList(valueSearched);
            model.setFilterActive(true);
        } else {
            model.setFilterActive(false);
        }
    }

    private void addContactOnContactList(String txtName, String txtPhone, String txtMail) {
        if(view.getFormPanel().getTextOfBtnSend().equals("Agregar")){
            this.model.addContact(txtName, txtPhone, txtMail);
        } else {
            JOptionPane.showMessageDialog(view, "Ha ocurrido un error");
        }
    }

    private void modifyContactFromContactList(String txtName, String txtPhone, String txtMail) {
        if(view.getFormPanel().getTextOfBtnSend().equals("Actualizar")){
            if(!this.model.modifyContactFromList(txtName, txtPhone, txtMail, this.model.getIdOfContactToChange())){
                JOptionPane.showMessageDialog(view, "Ha ocurrido un error");
            }
            this.view.getFormPanel().setTextToBtnSend(false);
        } else {
            JOptionPane.showMessageDialog(view, "Ha ocurrido un error");
        }

        setEditing(false);
    }

    private boolean validData(String name, String phone, String mail){
        boolean isValidPhone = isValidPhone(phone);
        boolean isValidMail = isValidMail(mail);

        this.view.getFormPanel().setColorOnNameTextField(name.isEmpty());
        this.view.getFormPanel().setColorOnPhoneTextField(phone.isEmpty() || !isValidPhone);
        this.view.getFormPanel().setColorOnMailTextField(mail.isEmpty() || !isValidMail);

        return !name.isEmpty() && !phone.isEmpty() && !mail.isEmpty() && isValidPhone && isValidMail;
    }

    private boolean isValidPhone(String phone){
        boolean isValid = true;

        for(int i=0; i<phone.length(); i++){
            if(!Character.isDigit(phone.charAt(i))){
                isValid = false;
                break;
            }
        }

        return isValid;
    }

    private boolean isValidMail(String mail){
        boolean isValid = false;
        int atSingCount = 0;
        int pointCount = 0;
        ArrayList<Integer> indexOfPoints = new ArrayList<>();
        int indexOfAtSing = mail.length();

        for(int i=0; i<mail.length(); i++){
            if(mail.charAt(i) == '.'){
                pointCount++;
                indexOfPoints.add(i);
            }
            if(mail.charAt(i) == '@'){
                atSingCount++;
                indexOfAtSing = i;
            }
        }

        if(atSingCount == 1 && pointCount > 0){
            for(Integer indexPoint : indexOfPoints){
                if(indexOfAtSing < indexPoint){
                    isValid = true;
                    break;
                }
            }
        }

        return isValid;
    }

    private void setEditing(boolean value){
        this.isEditing = value;
    }
}
