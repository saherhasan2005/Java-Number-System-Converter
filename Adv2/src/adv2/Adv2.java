/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package adv2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class Adv2 extends JFrame implements ActionListener {

    private JTextField inputText;
    private JRadioButton decimalButton, binaryButton, octalButton, hexButton;
    private JLabel decimalLabel, binaryLabel, octalLabel, hexLabel;
    private ButtonGroup group;

    public Adv2() {
        setTitle("Numbering System Converter");
        setLayout(null);

        JPanel inputPanel = new JPanel(null);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Number To Convert:"));
        inputPanel.setBounds(10, 10, 460, 80);

        JLabel numberLabel = new JLabel("Number:");
        numberLabel.setBounds(20, 30,200, 20);
        inputPanel.add(numberLabel);

        inputText = new JTextField();
        inputText.setBounds(120, 30, 270, 22);
        inputPanel.add(inputText);

        add(inputPanel);

        decimalButton = new JRadioButton("Decimal", true);
        binaryButton = new JRadioButton("Binary");
        octalButton = new JRadioButton("Octal");
        hexButton = new JRadioButton("HexaDecimal");

        decimalButton.setBounds(20, 100, 100, 20);
        binaryButton.setBounds(130, 100, 100, 20);
        octalButton.setBounds(230, 100, 100, 20);
        hexButton.setBounds(340, 100, 120, 20);

        group = new ButtonGroup();
        group.add(decimalButton);
        group.add(binaryButton);
        group.add(octalButton);
        group.add(hexButton);

        add(decimalButton);
        add(binaryButton);
        add(octalButton);
        add(hexButton);

        JSeparator separator = new JSeparator();
        separator.setBounds(10, 150, 460, 2);
        add(separator);

        JPanel outputPanel = new JPanel(null);
        outputPanel.setBorder(BorderFactory.createTitledBorder("Other Numbering System"));
        outputPanel.setBounds(10, 180, 460, 180);

        decimalLabel = new JLabel("Decimal : ");
        binaryLabel = new JLabel("Binary : ");
        octalLabel = new JLabel("Octal : ");
        hexLabel = new JLabel("HexaDecimal : ");

        decimalLabel.setBounds(20, 40, 300, 20);
        binaryLabel.setBounds(20, 70, 300, 20);
        octalLabel.setBounds(20, 100, 300, 20);
        hexLabel.setBounds(20, 130, 300, 20);

        outputPanel.add(decimalLabel);
        outputPanel.add(binaryLabel);
        outputPanel.add(octalLabel);
        outputPanel.add(hexLabel);

        add(outputPanel);

        inputText.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                convert();
            }
            public void removeUpdate(DocumentEvent e) { 
                convert();
            }
            public void changedUpdate(DocumentEvent e) {
                convert(); 
            }
        });

        decimalButton.addActionListener(this);
        binaryButton.addActionListener(this);
        octalButton.addActionListener(this);
        hexButton.addActionListener(this);

    
        setSize(500, 410);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void convert() {
        String input = inputText.getText().trim();
        if (input.isEmpty()) {
            clearLabels();
            return;
        }

        try {
            int number;
            if (decimalButton.isSelected()) {
                number = parseDecimal(input);
            } else if (binaryButton.isSelected()) {
                number = parseBase(input, 2);
            } else if (octalButton.isSelected()) {
                number = parseBase(input, 8);
            } else {
                number = parseHex(input);
            }

            decimalLabel.setText("Decimal : " + number);
            binaryLabel.setText("Binary : " + toBase(number, 2));
            octalLabel.setText("Octal : 0" + toBase(number, 8));
            hexLabel.setText("HexaDecimal : 0x" + toBase(number, 16).toUpperCase());
        } catch (Exception e) {
            decimalLabel.setText("Decimal : Invalid");
            binaryLabel.setText("Binary : Invalid");
            octalLabel.setText("Octal : Invalid");
            hexLabel.setText("HexaDecimal : Invalid");
        }
    }


    private int parseDecimal(String input) {
        int result = 0;
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c))
                throw new NumberFormatException();
            result = result * 10 + (c - '0');
        }
        return result;
    }

    private int parseBase(String input, int base) {
        int result = 0;
        for (char c : input.toCharArray()) {
            int digit = c - '0';
            if (digit < 0 || digit >= base)
                throw new NumberFormatException();
            result = result * base + digit;
        }
        return result;
    }

    private int parseHex(String input) {
        input = input.toUpperCase();
        int result = 0;
        for (char c : input.toCharArray()) {
            int digit;
            if (c >= '0' && c <= '9') digit = c - '0';
            else if (c >= 'A' && c <= 'F') digit = 10 + (c - 'A');
            else throw new NumberFormatException();
            result = result * 16 + digit;
        }
        return result;
    }

    private String toBase(int number, int base) {
        if (number == 0) return "0";
        String digits = "0123456789ABCDEF";
        StringBuilder result = new StringBuilder();
        while (number > 0) {
            result.insert(0, digits.charAt(number % base));
            number /= base;
        }
        return result.toString();
    }

    private void clearLabels() {
        decimalLabel.setText("Decimal : ");
        binaryLabel.setText("Binary : ");
        octalLabel.setText("Octal : ");
        hexLabel.setText("HexaDecimal : ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        convert();
    }
    public static void main(String[] args) {
       
        new Adv2();
        
    }
    
}
