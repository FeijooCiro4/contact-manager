package com;

import com.Controller.ContactController;
import com.Model.ContactList;
import com.View.ContactView;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ContactView view = new ContactView();
            ContactList model = new ContactList();
            ContactController controller = new ContactController(model, view);
            view.setVisible(true);
        });
    }
}