package com.View;

import com.Model.Contact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;

public class ContactView extends JFrame {
    private JTextField tfName, tfPhone, tfMail, tfFilter;
    private JButton btnSend, btnClear, btnSearch;
    private JLabel lblInputError;
    private JTable tblContacts;
    private JScrollPane scrollPane;

    private CustomContactTableModel tableModel;
    private ArrayList<ContactViewListener> listeners;

    public ContactView(){
        this.listeners = new ArrayList<>();

        setTitle("Gestor de Contactos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 520);
        setResizable(true);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel formPanel = createFormPanel();
        mainPanel.add(formPanel, BorderLayout.WEST);

        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        prepareFilter();
        prepareTable();
        prepareListeners();
    }

    private JPanel createFormPanel(){
        JPanel mainFormPanel = new JPanel();
        mainFormPanel.setLayout(new BoxLayout(mainFormPanel, BoxLayout.Y_AXIS));

        mainFormPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel lblTittle = new JLabel("Gestor de Contactos");
        lblTittle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTittle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        tfName = new JTextField(20);
        tfPhone = new JTextField(20);
        tfMail = new JTextField(20);

        inputPanel.add(new JLabel("Nombre:"));
        inputPanel.add(tfName);
        inputPanel.add(new JLabel("Teléfono:"));
        inputPanel.add(tfPhone);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(tfMail);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        lblInputError = new JLabel();
        lblInputError.setFont(new Font("Arial", Font.BOLD, 10));
        lblInputError.setForeground(Color.red);
        lblInputError.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnSend = new JButton("Agregar");
        btnSend.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottomPanel.add(lblInputError);
        bottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        bottomPanel.add(btnSend);

        mainFormPanel.add(lblTittle);
        mainFormPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        mainFormPanel.add(inputPanel);
        mainFormPanel.add(Box.createRigidArea(new Dimension(0, 200)));
        mainFormPanel.add(bottomPanel);

        return mainFormPanel;
    }

    private JPanel createTablePanel(){
        JPanel tablePanel = new JPanel(new BorderLayout());

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

        tablePanel.add(filterPanel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(btnClear, BorderLayout.SOUTH);

        return tablePanel;
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

    private void prepareListeners(){
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

        btnSearch.addActionListener(e -> {
            for(ContactViewListener listener : listeners){
                listener.onSearchContactListRequested();
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

        btnClear.setEnabled(tblContacts.getRowCount() > 0);
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

    public String getTfFilter(){
        return tfFilter.getText().equals("Escribe algo...") ? null : tfFilter.getText();
    }

    public void cleanTfFilter(){
        tfFilter.setText("Escribe algo...");
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
