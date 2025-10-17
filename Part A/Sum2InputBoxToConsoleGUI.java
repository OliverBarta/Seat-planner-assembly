/*
summative level 2 difficulty full answer for part A

By: Oliver B

Description: User enters text into a text box and it is printed to console.
*/

//imports required
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Sum2InputBoxToConsoleGUI {
    public static void main(String[] args) {
        
        //sets up frame
        JFrame frame = new JFrame("Input Box To Console");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //sets the frame size
        frame.setSize(400, 400);
        frame.setLayout(new FlowLayout());
        
        //sets up label
        JLabel label = new JLabel("Enter text: ");
        //sets up input field
        JTextField inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(250, 25));
        
        //sets up print button
        JButton printButton = new JButton("Print to console");
        
        //adds an action listener to the print button
        printButton.addActionListener(x -> {
            
            String text = inputField.getText();
            
            System.out.println(text);
            
        });
        
        //adds everthing to the frame
        frame.add(label);
        frame.add(inputField);
        frame.add(printButton);
        
        
        //sets the frame to visible
        frame.setVisible(true);
        
    }//ends main
}//ends class InputBoxToConsoleGUI