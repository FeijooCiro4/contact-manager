package com.View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ContactView extends JFrame {
    private ContactFormPanel formPanel;
    private ContactTablePanel tablePanel;

    private ArrayList<ContactViewListener> listeners;

    public ContactView(){
        this.listeners = new ArrayList<>();

        setTitle("Gestor de Contactos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 520);
        setResizable(true);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        this.formPanel = new ContactFormPanel();
        this.tablePanel = new ContactTablePanel();

        mainPanel.add(formPanel, BorderLayout.WEST);
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    public void addContactViewListener(ContactViewListener listener){
        this.listeners.add(listener);

        formPanel.addBtnSendListener(e -> listener.onSaveContactRequested());

        tablePanel.addBtnClearListener(e -> listener.onClearContactListRequested());
        tablePanel.addBtnSearchListener(e -> listener.onSearchContactListRequested());

        tablePanel.getContactTable().getColumn("Eliminar").setCellEditor(new ButtonEditor(new JCheckBox(), listener));
        tablePanel.getContactTable().getColumn("Editar").setCellEditor(new ButtonEditor(new JCheckBox(), listener));
    }

    public ContactFormPanel getFormPanel(){
        return formPanel;
    }

    public ContactTablePanel getTablePanel(){
        return tablePanel;
    }
}
