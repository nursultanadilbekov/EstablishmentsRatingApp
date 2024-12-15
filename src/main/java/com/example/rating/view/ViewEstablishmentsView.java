package com.example.rating.view;

import com.example.rating.model.Establishment;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ViewEstablishmentsView {
    private JPanel panel;
    private JPanel listPanel;

    public ViewEstablishmentsView() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel("View Establishments");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        listPanel = new JPanel(new BorderLayout());

        panel.add(label, BorderLayout.NORTH);
        panel.add(listPanel, BorderLayout.CENTER);
    }

    public JPanel getView() {
        return panel;
    }

    public void updateList(List<Establishment> establishments) {
        listPanel.removeAll();
        DefaultListModel<String> model = new DefaultListModel<>();

        for (Establishment establishment : establishments) {
            model.addElement(establishment.getName() + " - " + establishment.getCategory());
        }

        JList<String> list = new JList<>(model);
        JScrollPane scrollPane = new JScrollPane(list);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        listPanel.revalidate();
        listPanel.repaint();
    }
}
