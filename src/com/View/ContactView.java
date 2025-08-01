package com.View;

import com.Model.Contact;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ContactView extends JFrame {
    private JPanel mainPanel;
    private JTextField tfName;
    private JTextField tfPhone;
    private JTextField tfMail;
    private JButton btnSend;
    private JLabel lblInputError;
    private JTable tblContacts;
    private JButton btnClear;

    private CustomContactTableModel tableModel;
    private ArrayList<ContactViewListener> listeners;

    public ContactView(){
        this.listeners = new ArrayList<>();

        setContentPane(mainPanel);
        setTitle("Gestor de Contactos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 520);
        setResizable(true);
        setLocationRelativeTo(null);

        tableModel = new CustomContactTableModel();
        tblContacts.setModel(tableModel);

        tblContacts.getColumn("Eliminar").setCellRenderer(new ButtonRenderer());
        tblContacts.getColumn("Editar").setCellRenderer(new ButtonRenderer());

        btnSend.addActionListener(e -> {
            for(ContactViewListener listener : listeners){
                listener.onAddContactRequested();
            }
        });

        btnClear.addActionListener(e -> {
            for(ContactViewListener listener : listeners){
                listener.onClearContactListRequested();
            }
        });
    }

    public void addContactViewListener(ContactViewListener listener){
        this.listeners.add(listener);
        tblContacts.getColumn("Eliminar").setCellEditor(new ButtonEditor(new JCheckBox(), listener));
        tblContacts.getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), listener));
    }

    public void displayContactTable(ArrayList<Contact> contacts){
        tableModel.setContactsOnTable(contacts);

        if(tblContacts.getRowCount() == 1){
            btnClear.setEnabled(true);
        } else if(tblContacts.getRowCount() == 0){
            btnClear.setEnabled(false);
        }
    }

    public String getTfName(){
        return tfName.getText();
    }

    public String getTfPhone(){
        return tfPhone.getText();
    }

    public String getTfMail(){
        return tfMail.getText();
    }

    public void clearInputFields() {
        tfName.setText("");
        tfPhone.setText("");
        tfMail.setText("");
    }

    public void setTextErrorToLabelInput(){
        lblInputError.setText("Error: Campos vacíos o con información errónea.");
        lblInputError.setForeground(Color.red);
    }

    public void setTextNotErrorToLabelInput(){
        lblInputError.setText("");
        lblInputError.setForeground(Color.white);
    }
}
