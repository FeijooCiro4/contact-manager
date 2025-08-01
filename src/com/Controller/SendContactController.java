package com.Controller;

import com.Model.Contact;
import com.Model.ContactList;
import com.Model.ContactModelListener;
import com.View.ContactView;
import com.View.ContactViewListener;

import javax.swing.*;
import java.util.ArrayList;

public class SendContactController implements ContactModelListener, ContactViewListener {
    private ContactView view;
    private ContactList model;

    public SendContactController(ContactList model, ContactView view){
        this.model = model;
        this.view = view;

        this.model.addContactListener(this);
        this.view.addContactViewListener(this);

        view.displayContactTable(model.getContactList());
    }

    @Override
    public void onContactChanged(ArrayList<Contact> contacts) {
        view.displayContactTable(contacts);
    }

    @Override
    public void onAddContactRequested() {
        String txtName = view.getTfName();
        String txtPhone = view.getTfPhone();
        String txtMail = view.getTfMail();

        if(!validData(txtName, txtPhone, txtMail)){
            this.view.setTextErrorToLabelInput();
            return;
        }

        this.view.setTextNotErrorToLabelInput();
        this.model.addContact(txtName, txtPhone, txtMail);
        view.clearInputFields();
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
        String newPhone = model.getContactFromListWhitId(idContact).getPhone();;
        String newMail = model.getContactFromListWhitId(idContact).getMail();

        if(newName != null && newPhone != null && newMail != null) {
            StringBuilder dataToChange = new StringBuilder("<html>" +
                    "<p>DATOS A CAMBIAR</p>" +
                    "<p>&emsp;&emsp;&emsp; 1. Nombre.</p>" +
                    "<p>&emsp;&emsp;&emsp; 2. Teléfono.</p>" +
                    "<p>&emsp;&emsp;&emsp; 3. Mail.</p>" +
                    "<p>&emsp;&emsp;&emsp; 0. Salir.</p>" +
                    "<p>Ingrese una opción:</p>" +
                    "</html>");

            try {
                int optionChange;
                do {
                    optionChange = Integer.parseInt(JOptionPane.showInputDialog(view, dataToChange));

                    switch (optionChange) {
                        case 1:
                            newName = JOptionPane.showInputDialog(view, "Ingrese el nuevo nombre.");
                            break;
                        case 2:
                            newPhone = JOptionPane.showInputDialog(view, "Ingrese el nuevo teléfono.");
                            break;
                        case 3:
                            newMail = JOptionPane.showInputDialog(view, "Ingrese el nuevo mail.");
                            break;
                        case 0:
                            break;
                        default:
                            JOptionPane.showMessageDialog(view, "Opción inváida");
                            break;
                    }
                } while (optionChange != 0);

                if (validData(newName, newPhone, newMail) && !model.modifyContactFromList(newName, newPhone, newMail, idContact)) {
                    JOptionPane.showMessageDialog(view, "Ha ocurrido un error");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view, "Ha ocurrido un error");
            }
        } else {
            JOptionPane.showMessageDialog(view, "Ha ocurrido un error");
        }
    }

    @Override
    public void onClearContactListRequested() {
        int confirm = JOptionPane.showConfirmDialog(view, "¿Usted está seguro de vaciar a lista?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION){
            model.clearContactList();
        }
    }

    private boolean validData(String name, String phone, String mail){
        return !name.isEmpty() && !phone.isEmpty() && !mail.isEmpty() && isValidPhone(phone) && isValidMail(mail);
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
}
