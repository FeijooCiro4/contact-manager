package com;

import com.Controller.SendContactController;
import com.Model.ContactList;
import com.View.ContactView;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ContactView view = new ContactView();
            ContactList model = new ContactList();
            SendContactController controller = new SendContactController(model, view);
            view.setVisible(true);
        });
    }
}