/*
By: Oliver B

Description: A simple Notes app that lets the user type a message into an input field and append it to a scrolling text area. 
The user can also clear all notes or save the current notes to a file.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class PracProb2NotesApp {
    public static void main(String[] args) {
        
        // sets up the frame
        JFrame frame = new JFrame("Simple Notes App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 450);
        frame.setLayout(new FlowLayout());

        // label
        JLabel label = new JLabel("Type your note:");

        // input field
        JTextField inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(300, 25));

        // text area (note display)
        JTextArea textArea = new JTextArea(15, 40);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // buttons
        JButton addNoteButton = new JButton("Add Note");
        JButton clearButton = new JButton("Clear All");
        JButton saveButton = new JButton("Save Notes");

        // add note action
        addNoteButton.addActionListener(e -> {
            String note = inputField.getText().trim();
            if (!note.isEmpty()) {
                textArea.append("- " + note + "\n");
                inputField.setText("");
            }
        });

        // clear action
        clearButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to clear all notes?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                textArea.setText("");
            }
        });

        // save action
        saveButton.addActionListener(e -> {
            try {
                FileWriter writer = new FileWriter("notes.txt");
                writer.write(textArea.getText());
                writer.close();
                JOptionPane.showMessageDialog(frame, "Notes saved to notes.txt");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error saving notes: " + ex.getMessage());
            }
        });

        // add components to frame
        frame.add(label);
        frame.add(inputField);
        frame.add(addNoteButton);
        frame.add(clearButton);
        frame.add(saveButton);
        frame.add(scrollPane);

        frame.setVisible(true);
    }
}