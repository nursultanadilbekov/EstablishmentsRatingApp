package com.example.rating.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private JButton addEstablishmentButton;
    private JButton viewEstablishmentsButton;

    public View() {
        setTitle("Establishment Rating System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addEstablishmentButton = new JButton("Add Establishment");
        viewEstablishmentsButton = new JButton("View Establishments");

        setLayout(new FlowLayout());
        add(addEstablishmentButton);
        add(viewEstablishmentsButton);
    }

    public JButton getAddEstablishmentButton() {
        return addEstablishmentButton;
    }

    public JButton getViewEstablishmentsButton() {
        return viewEstablishmentsButton;
    }
}

