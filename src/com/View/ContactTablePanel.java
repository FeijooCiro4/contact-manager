package com.View;

import com.Model.Contact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

public class ContactTablePanel extends JPanel {
    private JTextField tfFilter;
    private JButton btnClear, btnSearch;
    private JTable tblContacts;
    private JScrollPane scrollPane;

    private CustomContactTableModel tableModel;

    public ContactTablePanel(){
        setLayout(new BorderLayout());

        tfFilter = new JTextField(35);
        btnSearch = new JButton("Buscar");
        tblContacts = new JTable();
        btnClear = new JButton("Vaciar tabla");

        tableModel = new CustomContactTableModel();
        tblContacts.setModel(tableModel);
        scrollPane = new JScrollPane(tblContacts);

        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.add(tfFilter, BorderLayout.WEST);
        filterPanel.add(btnSearch, BorderLayout.EAST);

        add(filterPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnClear, BorderLayout.SOUTH);

        prepareFilter();
        prepareTable();
    }

    private void prepareFilter(){
        tfFilter.setText("Escribe algo...");
        tfFilter.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if(tfFilter.getText().equals("Escribe algo...")){
                    tfFilter.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e){
                if(tfFilter.getText().isEmpty()){
                    tfFilter.setText("Escribe algo...");
                }
            }
        });
    }

    private void prepareTable(){
        tblContacts.getColumn("Eliminar").setCellRenderer(new ButtonRenderer());
        tblContacts.getColumn("Editar").setCellRenderer(new ButtonRenderer());
    }

    public void addBtnClearListener(ActionListener listener){
        btnClear.addActionListener(listener);
    }

    public void addBtnSearchListener(ActionListener listener){
        btnSearch.addActionListener(listener);
    }

    public JTable getContactTable(){
        return tblContacts;
    }

    public void displayContactTable(ArrayList<Contact> contacts){
        tableModel.setContactsOnTable(contacts);

        btnClear.setEnabled(tblContacts.getRowCount() > 0);
    }

    public String getTfFilter(){
        return tfFilter.getText().equals("Escribe algo...") ? null : tfFilter.getText();
    }

    public void cleanTfFilter(){
        tfFilter.setText("Escribe algo...");
    }
}
