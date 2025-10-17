/*
By: Oliver B

Description: A program that uses GUI to let the user add names to a list and tells them
if they've already added a name to the list. The user should be able to see the list
through a text field. All this should be in GUI, no console.
*/

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PracProb1 {
    public static void main(String[] args) {
        
        ArrayList<String> names = new ArrayList<String>();
        
        //sets up frame
        JFrame frame = new JFrame("Create a list of names");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //sets the frame size
        frame.setSize(400, 400);
        frame.setLayout(new FlowLayout());
        
        //sets up label
        JLabel label = new JLabel("Enter Name: ");
        
        //sets up input field
        JTextField inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(250, 25));
        
        //sets up add button
        JButton addButton = new JButton("Add");
        
        //sets up text area
        JTextArea textArea = new JTextArea(20,30);
        textArea.setEditable(false);
        
        //creates the scroll pane
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        
        //adds an action listener to the check button
        addButton.addActionListener(x -> {
            
            //the text from the user
            String text = inputField.getText();
            
            //variable set to true when the name is in list, false otherwise
            boolean inList = false;
            
            //check if name is in list
            for (int i = 0; i<names.size(); i++) {
                if (names.get(i).equals(text)) {
                    inList = true;
                }
            }
            
            if (inList) {
                JOptionPane.showMessageDialog(frame, "Name already in list.", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                names.add(text);
                
                //adds name to text area automatically
                StringBuilder sb = new StringBuilder();
                for (String name : names) {
                    sb.append(name + "\n");
                }
                textArea.setText(sb.toString());
            }
            
        });
        
        //adds everthing to the frame
        frame.add(label);
        frame.add(inputField);
        frame.add(addButton);
        frame.add(textArea);
        
        
        //sets the frame to visible
        frame.setVisible(true);
        
    }// ends main
}//ends class MyProgram