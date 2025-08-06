package com.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ContactFormPanel extends JPanel {
    private JTextField tfName, tfPhone, tfMail;
    private JButton btnSend;
    private JLabel lblInputError;

    public ContactFormPanel(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

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

        add(lblTittle);
        add(Box.createRigidArea(new Dimension(0, 100)));
        add(inputPanel);
        add(Box.createRigidArea(new Dimension(0, 200)));
        add(bottomPanel);
    }

    public void addBtnSendListener(ActionListener listener){
        btnSend.addActionListener(listener);
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
