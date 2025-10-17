/*
summative level 3 difficulty full answer for part A
Description: A program where the user can edit a text file through a
text box. Uses read and write buttons for the user to read and 
write what is in the text box to the file.
By: Oliver B
Some code for reading files comes from my assignment 3 Q1

*/

//imports required
import java.nio.file.Paths;
//Import the Scanner class to read text files
import java.io.FileWriter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;
import java.nio.file.Files;

public class Sum3InputBoxToTextFileGUI {
    
    public static void main(String[] args) throws Exception{

        //sets up frame
        JFrame frame = new JFrame("Input Box To Text File");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //sets the frame size
        frame.setSize(400, 400);
        frame.setLayout(new FlowLayout());
        
        JTextArea numberArea = new JTextArea(20,30);
        numberArea.setEditable(true);
        
        //creates the scroll pane
        JScrollPane scrollPane = new JScrollPane(numberArea);
        
        //sets up read button
        JButton readButton = new JButton("Read file");
        
        //sets up write button
        JButton writeButton = new JButton("Write file");
        
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
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });//ends write listener
        
        
        //adds everthing to the frame
        frame.add(scrollPane);
        frame.add(writeButton);
        frame.add(readButton);
        
        
        //sets the frame to visible
        frame.setVisible(true);
        
    }//ends main
}//ends class InputBoxToConsoleGUI