package com.example.rating.view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
        setFont(new Font("Arial", Font.PLAIN, 14)); // Шрифт
        setForeground(Color.WHITE); // Цвет текста
        setBackground(new Color(70, 130, 180)); // Синий фон
        setFocusPainted(false); // Убираем фокусную рамку
        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // Отступы
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Курсор в виде руки
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText("Лайк/Дизлайк");

        if (isSelected) {
            setBackground(new Color(100, 149, 237)); // Светлее при выделении
        } else {
            setBackground(new Color(70, 130, 180)); // Обычный синий фон
        }

        if (hasFocus) {
            setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 2)); // Золотая рамка при фокусе
        } else {
            setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // Стандартные отступы
        }

        return this;
    }
}
