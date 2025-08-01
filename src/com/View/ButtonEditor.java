package com.View;

import javax.swing.*;
import java.awt.*;

public class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private int clickedItemId;
    private ContactViewListener viewListener;

    public ButtonEditor(JCheckBox checkBox, ContactViewListener listener) {
        super(checkBox);
        this.viewListener = listener;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> {
            fireEditingStopped();
            if(viewListener != null){
                if(button.getText().equals("Eliminar")){
                    viewListener.onDeleteContactRequested(clickedItemId);
                } else if(button.getText().equals("Editar")){
                    viewListener.onChangeContactRequested(clickedItemId);
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(UIManager.getColor("Button.background"));
        }
        String label = (value == null) ? "" : value.toString();
        button.setText(label);

        clickedItemId = (int) table.getModel().getValueAt(row, 0);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return button.getText();
    }

    @Override
    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }
}
