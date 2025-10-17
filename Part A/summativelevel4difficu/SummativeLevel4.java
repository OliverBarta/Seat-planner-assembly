/*
summative level 4 difficulty full answer for part A

By: Oliver Barta
Description: The same input output to a file as before just with extra features that make it
a level 4. I added auto save and a character count, but many different extra features would
work.
*/

//imports required
import java.nio.file.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.undo.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import java.nio.file.Paths;
import java.io.FileWriter;
import javax.swing.*;

import java.nio.file.Files;


public class SummativeLevel4 {
    
    private static boolean unsaved = false;
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Level 4 Text Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new FlowLayout());
        
        JTextArea numberArea = new JTextArea(20,30);
        numberArea.setEditable(true);
        
        JScrollPane scrollPane = new JScrollPane(numberArea);
        
        //sets up read button
        JButton readButton = new JButton("Read file");
        
        //sets up write button
        JButton writeButton = new JButton("Write file");
        
        //character count
        JLabel charCountLabel = new JLabel("Characters: 0");
        
        //character count
        numberArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { update(); }
            public void removeUpdate(DocumentEvent e) { update(); }
            public void changedUpdate(DocumentEvent e) { update(); }
            
            private void update() {
                charCountLabel.setText("Characters: " + numberArea.getText().length());
                unsaved = true;
            }
            
        });
        
        //auto save
        Timer autoSaveTimer = new Timer(10000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try (FileWriter writer = new FileWriter("fileWithText.txt", false)) {
                    writer.write(numberArea.getText());
                    unsaved = false;
                    System.out.println("Auto saved");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
        });
        autoSaveTimer.start();
        
        //listener for read button
        readButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    List<String> lines = Files.readAllLines(Paths.get("fileWithText.txt"));
                    numberArea.setText("");
                    StringBuilder sb = new StringBuilder();
                    //puts new text into text area
                    for (String line : lines) {
                        sb.append(line+"\n");
                    }
                    numberArea.setText(sb.toString());
                    unsaved = false;
                } catch (Exception vd) {
                    vd.printStackTrace();
                }
            }
        });//ends read listener
        
        //listener for write button
        writeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent c) {
                try {
                    FileWriter writer1 = new FileWriter("fileWithText.txt", false);
                    writer1.write(numberArea.getText()); // write the actual text content
                    writer1.close();
                    unsaved = false;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });//ends write listener
        
        
        //adds everthing to the frame
        frame.add(scrollPane);
        frame.add(writeButton);
        frame.add(readButton);
        frame.add(charCountLabel);
        
        
        //sets the frame to visible
        frame.setVisible(true);
        
        
    }//ends main
}//ends summativeLevel4 class