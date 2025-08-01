package com.View;

import com.Model.Contact;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class CustomContactTableModel extends DefaultTableModel {
    public CustomContactTableModel(){
        super(new Object[]{"ID", "Nombre", "Teléfono", "Mail", "Eliminar", "Editar"}, 0);
    }

    public void setContactsOnTable(ArrayList<Contact> contacts){
        setRowCount(0);
        for(Contact contact : contacts){
            addRow(new Object[]{contact.getIdContact(), contact.getName(), contact.getPhone(), contact.getMail(), "Eliminar", "Editar"});
        }
        fireTableDataChanged();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if ((columnIndex == 4)||(columnIndex == 5)) {
            return JButton.class;
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 4 || column == 5;
    }
}
