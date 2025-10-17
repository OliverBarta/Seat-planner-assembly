/*
Example 2 int

By: Oliver B
Description: A caclculator where the user can enter two integers and choose to add, subtract or multiply them.

some code taken from example problem string
*/

//imports required
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class intExample {
    
    private static String operation = "add";
    
    public static void main(String[] args) {
        
        //sets up frame
        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //sets the frame size
        frame.setSize(400, 100);
        frame.setLayout(new FlowLayout());
        
        //sets up label
        JLabel label = new JLabel("Enter numbers: ");
        
        //sets up input field 1
        JTextField inputField1 = new JTextField();
        inputField1.setPreferredSize(new Dimension(50, 25));
        
        //sets up input field 2
        JTextField inputField2 = new JTextField();
        inputField2.setPreferredSize(new Dimension(50, 25));
        
        //sets up solve button
        JButton solveButton = new JButton("Solve");
        
        //label for answer
        
        JLabel answer = new JLabel("Enter integers and solve");
        
        //sets up operation choosing drop down
        String[] operationOptions = {"add","subtract","multiply"};
        JComboBox<String> columnBox = new JComboBox<>(operationOptions);
        columnBox.setSelectedIndex(0);
        
        //listener for the drop down menu
        columnBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    operation = (String)e.getItem();
                }
            }
        });
        
        //adds an action listener to the solve button
        solveButton.addActionListener(x -> {
            try {
                //the text from the user
                int v1 = Integer.valueOf(inputField1.getText());
                int v2 = Integer.valueOf(inputField2.getText());
                
                if (operation.equals("add")) {
                    answer.setText("Answer: "+Integer.toString(v1+v2));
                } else if (operation.equals("subtract")) {
                    answer.setText("Answer: "+Integer.toString(v1-v2));
                } else {
                    answer.setText("Answer: "+Integer.toString(v1*v2));
                }
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Not an integer.", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        
        //adds everthing to the frame
        frame.add(label);
        frame.add(inputField1);
        frame.add(columnBox);
        frame.add(inputField2);
        frame.add(solveButton);
        frame.add(answer);
        
        
        //sets the frame to visible
        frame.setVisible(true);
        
    }//ends main
}//ends class InputBoxToConsoleGUI