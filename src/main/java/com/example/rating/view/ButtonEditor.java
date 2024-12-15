package com.example.rating.view;

import com.example.rating.model.DatabaseManager;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private JButton button;
    private int row;
    private JTable table;
    private DatabaseManager dbManager;
    private JFrame parentFrame;

    public ButtonEditor(JCheckBox checkBox, DatabaseManager dbManager, JFrame parentFrame) {
        this.dbManager = dbManager;
        this.parentFrame = parentFrame;
        button = createStyledButton("Лайк/Дизлайк");
        button.addActionListener(this);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        this.row = row;

        if (isSelected) {
            button.setBackground(new Color(100, 149, 237)); // Меняем фон при выделении
        } else {
            button.setBackground(new Color(70, 130, 180)); // Стандартный фон
        }

        return button;
    }

    private JButton createStyledButton(String text) {
        JButton styledButton = new JButton(text);
        styledButton.setFont(new Font("Arial", Font.PLAIN, 14));
        styledButton.setForeground(Color.WHITE); // Белый текст
        styledButton.setBackground(new Color(70, 130, 180)); // Синий фон
        styledButton.setFocusPainted(false); // Убираем рамку фокуса
        styledButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // Отступы
        styledButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Курсор в виде руки
        return styledButton;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int id = (int) table.getValueAt(row, 0);
        String name = (String) table.getValueAt(row, 1);

        Object[] options = {"Лайк", "Дизлайк"};
        int choice = JOptionPane.showOptionDialog(parentFrame, "Что вы хотите сделать с " + name + "?",
                "Выбор действия", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        try {
            if (choice == JOptionPane.YES_OPTION) {
                dbManager.likeEstablishment(id);
                JOptionPane.showMessageDialog(parentFrame, name + " получил лайк!");
            } else if (choice == JOptionPane.NO_OPTION) {
                dbManager.dislikeEstablishment(id);
                JOptionPane.showMessageDialog(parentFrame, name + " получил дизлайк!");
            }

            // Обновление значений лайков/дизлайков в таблице
            table.setValueAt(dbManager.getTopEstablishments().get(row).getLikes(), row, 4);
            table.setValueAt(dbManager.getTopEstablishments().get(row).getDislikes(), row, 5);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parentFrame, "Ошибка обновления базы данных.");
        }
        fireEditingStopped();
    }
}
