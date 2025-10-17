/*
Example 1 String

By: Oliver B
Description: User guesses which 3 letter names (not case sensitive) are in the list 
using a GUI text box.

Some code taken from the level 2 summative
*/

//imports required
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class StringExample {
    public static void main(String[] args) {
        
        //sets up frame
        JFrame frame = new JFrame("Guess the three letter names in the list");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //sets the frame size
        frame.setSize(400, 100);
        frame.setLayout(new FlowLayout());
        
        //sets up label
        JLabel label = new JLabel("Enter Name: ");
        //sets up input field
        JTextField inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(250, 25));
        
        //list of 3 letter names
        String[] Names = {"Joe","Bob","Ben","Dan","Meg","Sam","Oli","Ali","Tim"};
        
        //sets up check button
        JButton checkButton = new JButton("Check");
        
        //adds an action listener to the check button
        checkButton.addActionListener(x -> {
            
            //the text from the user
            String text = inputField.getText();
            //variable for if the name is in the list or not
            boolean inList = false;
            //checks if the name is in the list (isnt case sensitive)
            for (String i : Names) {
                if (text.toLowerCase().equals(i.toLowerCase())) {
                    inList = true;
                }
            }
            //informs user about the text eg. in list, not in list, too many letters, too few letters
            if (text.length() >= 4) {
                JOptionPane.showMessageDialog(frame, text+" is too many letters", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else if (text.length() < 3) {
                JOptionPane.showMessageDialog(frame, text+" is too few letters", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else if (inList) {
                JOptionPane.showMessageDialog(frame, text+" is in the list of names", "Message", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, text+" is not in the list of names", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
            
        });
        
        //informs the user what to do
        JOptionPane.showMessageDialog(frame, "Guess what three letter names one at a time are in the list. \n Not case sensitive.", "Message", JOptionPane.INFORMATION_MESSAGE);
        
        //adds everthing to the frame
        frame.add(label);
        frame.add(inputField);
        frame.add(checkButton);
        
        
        //sets the frame to visible
        frame.setVisible(true);
        
    }//ends main
}//ends class InputBoxToConsoleGUI