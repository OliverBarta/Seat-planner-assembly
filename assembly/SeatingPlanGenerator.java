/*

Started: 22/5/25
Completed: 

Collaborators:
Alexander C
Daniel L
Matthew Y
Oliver B

Citations:


Project Synopsis: Write a program which takes an input of a CSV, "clean" it to make it more machine readable, then determine the grade based on the class code. From that, sort each class based on its grade into each period (As in, all grade 9 classes are put into period 1, grade 10s in period 2, etc.). Additionally aggregate the emails of of the teachers of the class to send an email with the seating plan for an assembly

Notes:

*/

//imports required
import javax.swing.*;
import java.awt.datatransfer.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

class Course { // by Matthew
    //Establish Needed Variables
    private static final String[] ignoreList = {"ESL", "ELD", "GLS"};
    private String firstName;
    private String lastName;
    private ArrayList<Integer> grades = new ArrayList<Integer>();
    private String code;
    private String period;
    private Boolean ESL = false;
    
    
    public Course(String name, String code, String period) {
        name = name.replace(" ","");
        String[] names = name.split(",");
        this.firstName = names[1]; 
        this.lastName = names[0];
        this.period = period;
        this.code = code;
        
        for (String ignore : ignoreList) {
            if (code.contains(ignore)) this.ESL = true;
        }
        
        if (Character.toString(code.charAt(code.indexOf("-") - 1)).equals("V") || Character.toString(code.charAt(code.indexOf("-") - 1)).equals("Z")) {
            this.ESL = true;
        }
        
        int n = code.length();
        
        for (int i = 0; i < n; i++) {
            String x = Character.toString(code.charAt(i));
            if (x.matches("\\d")&& !x.matches("0")) { // is number
                grades.add(8+Integer.parseInt(x));
            } else if (x.equals("-")) {
                break;
            }
        }
        grades = new ArrayList<>(new LinkedHashSet<>(grades));
    }
    
    public String getCode() {
        return code;
    } // end getter for course code
    
    public String getName() {
        return firstName + " " + lastName;
    } // end getter for teacher name
    
    public void setName(String _firstName, String _lastName) {
        firstName = _firstName;
        lastName = _lastName;
    } // end setter for teacher name
    
    public ArrayList<Integer> getGrades() {
        return grades;
    } // end getter for ArrayList of grades
    
    public String getEmail() {
        return firstName + "_" + lastName + "@wrdsb.ca";
    } // end getter for teacher email
    
    public void sendEmail() {
        
    } // end email sender function 
    
    @Override
    public String toString() {
        return "Course Code: " + code + "\nTeacher: " + firstName + " " + lastName + "\nGrades: " + grades.toString() + "\nPeriod: " + period;
    } // end override console display for instance of Coures
}



public class SeatingPlanGenerator {
    private static HashMap<String, ArrayList<Course>> courses;
    private static final String[] periods = {"A", "B", "C", "D"};
    private static Boolean started = false;
    private static String[] fileAsArray;
    
    //the grade selected in the drop down GUI
    private static int columnGrade = 9;
    
    //the period selected in the drop down GUI
    private static String columnPeriod = "A";
    
    static final String PATH = "";
    
    public static void main(String[] args) throws Exception{
        
        generateGUI();
    }//ends main void
    
    public static void initCourses() { // by Matthew
        courses = new HashMap<String, ArrayList<Course>>();
        
        for (String period: periods) {
            courses.put(period, new ArrayList<Course>());
        }
    }
    
    public static void getData(String[] stringFile) { //By Daniel
        //Loading Prompt
        initCourses();
        JDialog dialog = new JDialog();
        dialog.setTitle("Processing...");
        dialog.setSize(325, 75);
        dialog.setUndecorated(true);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setLocationRelativeTo(null);
        dialog.add(new JLabel("Please wait while we process the file...", SwingConstants.CENTER));
        dialog.setVisible(true);
        for (int i = 2; i < stringFile.length; i++){
            String[] lines = stringFile[i].split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            lines[0] = lines[0].replace("\"","");
            String testStr = lines[0];
            // Until the test string has exactly 1 comma
            while (!testStr.contains(",") || !(testStr.indexOf(",") == testStr.lastIndexOf(","))){ // Latter half of or statement by Alexander C
                testStr = JOptionPane.showInputDialog(null, "Please Enter Proper Name for: "+ lines[0]+". (Ex: Smith, John).");
                if (!testStr.contains(",") || !(testStr.indexOf(",") == testStr.lastIndexOf(","))){
                    JOptionPane.showMessageDialog(null, "Sorry, format is invalid (missing comma between names)");
                }
            }
            lines[0] = testStr;
            
            if (lines.length != 5){
                    lines = Arrays.copyOf(lines, 5);
                    for (int j = 0; j < lines.length; j++) {
                        if (lines[j] == null) lines[j] = "";
                    }
                }
            
            Course course;
            for (int j = 1; j < 5; j++) {
                lines[j] = lines[j].replaceAll("^\\s+", "");
                lines[j] = lines[j].replace(" ","-");
                if (lines[j].contains("-")){
                    course = new Course(lines[0], lines[j], periods[j-1]);
                    courses.get(periods[j-1]).add(course);
                }
            }
            dialog.dispose();
        }
    }
    public static void setButtons(JButton startButton, JButton downloadButton, JButton uploadButton, JButton copyEmailsButton, Boolean start, Boolean download, Boolean upload,  Boolean getEmail){//By Daniel
        startButton.setEnabled(start);
        downloadButton.setEnabled(download);
        uploadButton.setEnabled(upload);
        copyEmailsButton.setEnabled(getEmail);
    }
    public static void setComboBox(JComboBox grade, JComboBox period, Boolean g, Boolean p){//By Daniel
        grade.setEnabled(g);
        period.setEnabled(p);
    }
    
    public static ArrayList<Course> getCourses(String period, int grade) { // by Matthew
        ArrayList<Course> validCourses = new ArrayList<Course>();
        for (Course course: courses.get(period)) {
            if (course.getGrades().contains(grade)) {
                validCourses.add(course);
            }
        }
        return validCourses;
    }

    //Almost all the GUI done by Oliver B unlesss otherwise stated. Lines 196-473
    public static void generateGUI() {
        
        //sets up the frame
        JFrame frame = new JFrame("WCI Seating Plan Generator");
        //makes it exit when closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //sets the frame size
        frame.setSize(700, 600);
        frame.setLocationRelativeTo(null);
        
        //Create Icon By Daniel: 
        try{
            Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
            frame.setIconImage(icon);
        }catch(Exception imagException){
            System.out.println("Image Failed to Load");
        }
        
        
        //creates menubar
        JMenuBar menuBar = new JMenuBar();
        //creates a part of the menu bar
        JMenu menu = new JMenu("Options");
        
        //creates an item
        JMenuItem quitItem = new JMenuItem("Quit Program");
        
        //adds the item that quits the program to the menu bar
        menu.add(quitItem);
        
        //adds the part of the menu to the menu bar
        menuBar.add(menu);
        
        //adds the menu bar to the frame.
        frame.setJMenuBar(menuBar);
        
        //creates a new panel where most of the user input will happen
        JPanel inputPanel = new JPanel();

        //the possible columns in the drop down columnBox
        String[] columnGrades = {"9", "10", "11", "12"};
        
        //initialises new drop down called columnBox
        JComboBox<String> columnBoxGrades = new JComboBox<>(columnGrades);
        //sets the drop down to select the first index by default
        columnBoxGrades.setSelectedIndex(0);

        inputPanel.add(new JLabel("Grade:"));
        //adds the columnBox to the panel
        inputPanel.add(columnBoxGrades);

        inputPanel.add(new JLabel("Period:"));
        //the possible columns in the drop down columnBox
        String[] columnPerds = {"A", "B", "C", "D"};

        //initialises new drop down for periods
        JComboBox<String> columnBoxPeriods = new JComboBox<>(columnPerds);
        //sets the drop down to select the first index by default
        columnBoxPeriods.setSelectedIndex(0);
        //adds the columnBox to the panel
        inputPanel.add(columnBoxPeriods);

        JButton startButton = new JButton("Start");

        JButton copyEmailsButton = new JButton("Copy Emails");

        inputPanel.add(startButton);
        inputPanel.add(copyEmailsButton);
        
        JButton uploadButton = new JButton("Upload");
        
        JButton downloadButton = new JButton("Download");
        
        inputPanel.add(uploadButton);
        inputPanel.add(downloadButton);
        
        //creates center panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        JTextArea numberArea = new JTextArea(20,40);
        //sets the text area to be un-editable by the user
        numberArea.setEditable(false);
        
        //creates the scroll pane
        JScrollPane scrollPane = new JScrollPane(numberArea);
        
        //adds the panel with the buttons and drop downs and the scroll pane
        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        //adds the panel to the frame
        frame.add(centerPanel, BorderLayout.CENTER);
        

        //listener for the columbbox that deals with the grades
        columnBoxGrades.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                setButtons(startButton, downloadButton, uploadButton, copyEmailsButton,true,false,true,false);
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    columnGrade = Integer.valueOf((String)e.getItem());
                }
            }
        });//ends columnBoxGrades

        
        //listener for the columbbox that deals with the periods
        columnBoxPeriods.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                setButtons(startButton, downloadButton, uploadButton, copyEmailsButton,true,false,true,false);
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    columnPeriod = (String)e.getItem();
                }
            }
        });//ends columnBoxPeriods
        
        // initialises filechooser
        JFileChooser fileChooser;
        fileChooser = new JFileChooser();
        // Only accept CSV files
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("CSV Files", "csv"));
        //Button State Update, By Daniel
        setButtons(startButton, downloadButton, uploadButton, copyEmailsButton, false, false, true, false);
        setComboBox(columnBoxGrades, columnBoxPeriods,false,false);
        //button to upload file
        uploadButton.addActionListener(e -> {
            if (e.getSource() == uploadButton) {
                int returnVal = fileChooser.showOpenDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        setButtons(startButton, downloadButton, uploadButton, copyEmailsButton,false,false,true,false);
                        // Process the selected file (e.g., read its contents, display it, etc.)
                        byte[] fileData = Files.readAllBytes(selectedFile.toPath());
                        JOptionPane.showMessageDialog(null, "File uploaded successfully!");
                        // Convert byte data to string
                        String fileContent = new String(fileData);
    
                        // Print file content to the console
                        fileAsArray = fileContent.split("\n");
                        getData(fileAsArray);
                        setComboBox(columnBoxGrades, columnBoxPeriods,true,true);
                        setButtons(startButton, downloadButton, uploadButton, copyEmailsButton,true,false,true,false);
                    } catch (IOException ex) {
                        setComboBox(columnBoxGrades, columnBoxPeriods,false,false);
                        JOptionPane.showMessageDialog(null, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        //download button
        downloadButton.addActionListener(e -> { // Made by Oliver and Daniel
            try {
                // Load the image
                File inputFile = new File("Auditorium.png");
                BufferedImage image = ImageIO.read(inputFile);

                // Get Graphics2D context
                Graphics2D g2d = image.createGraphics();

                // Enable antialiasing for smoother text
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                // Set text properties
                g2d.setColor(Color.BLACK); // Text color
                Font font = new Font("Arial", Font.BOLD, 18);
                g2d.setFont(font); // Font and size
                FontMetrics metrics = g2d.getFontMetrics(font);
                
                int x1 = 118;
                int x2 = 581;
                int x3 = 1047;


                int y1 = 142;
                int y2 = 350;
                int y3 = 558;
                int y4 = 765;

                ArrayList<Course> validCourses = getCourses(columnPeriod, columnGrade);
                int[] xPositions = {x1, x2, x3};
                int[][] yPositions = {
                    {y1, y2, y3, y4},                     // x1 column
                    {60, 164, 248, 332, 415, 500, 585, 670}, // x2 column (+ overflow)
                    {y1, y2, y3, y4}                      // x3 column
                };

                int courseIndex = 0;
                for (int col = 0; col < xPositions.length; col++) {
                    for (int row = 0; row < yPositions[col].length; row++) {
                        if (courseIndex < validCourses.size()) {
                            Course course = validCourses.get(courseIndex++);
                            g2d.drawString(course.getCode(), xPositions[col] - (metrics.stringWidth(course.getCode())) / 2, yPositions[col][row]);
                            g2d.drawString(course.getName(), xPositions[col] - (metrics.stringWidth(course.getName())) / 2, yPositions[col][row] + 20);
                        }
                    }
                }
                if (validCourses.size() > 16){
                    g2d.drawString("Insufficent Space for:", x2 - (metrics.stringWidth("Insufficent Space for:"))/2, 830);
                    for(int val = validCourses.size(); val > 16;val--){
                        g2d.drawString(validCourses.get(val-1).getCode()+" ("+validCourses.get(val-1).getName()+")",x2 - (metrics.stringWidth((validCourses.get(val-1).getCode()+validCourses.get(val-1).getName())+3)/2),850+(20*(Math.abs(16-val))));
                    }
                    JOptionPane.showMessageDialog(null, "Warning: Insufficent Space For Selected Group");
                } 
                
                g2d.drawString("Overflow", x2 - (metrics.stringWidth("Overflow"))/2, 740);
                


                // Clean up
                g2d.dispose();
                
                // Show save dialog
                fileChooser.setDialogTitle("Save Auditorium Seating Plan");
                fileChooser.setSelectedFile(new File("New_Seating_Plan.png")); // default filename
                
                
                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    ImageIO.write(image, "png", fileToSave);
                    JOptionPane.showMessageDialog(null, "Image saved successfully!");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Failed to load or save image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        //button to start
        startButton.addActionListener(e -> {
            started = true;
            numberArea.setText("");
            StringBuilder sb = new StringBuilder();
            setButtons(startButton, downloadButton, uploadButton, copyEmailsButton,true,true,true,true);
            ArrayList<Course> validCourses = getCourses(columnPeriod, columnGrade);
            
            for (Course course: validCourses) {
                sb.append(course.getCode()+" by: "+course.getName()+" ("+course.getEmail().toLowerCase()+") ");
                sb.append("\n");
                
            }
            
            numberArea.setText(sb.toString());
            
        });//ends start button listener

        //button to copy the emails
        copyEmailsButton.addActionListener(e -> {
            if (started){
                try{
                    ArrayList<Course> validCourses = getCourses(columnPeriod, columnGrade);
                    StringBuilder sb2 = new StringBuilder();
                    for (Course course: validCourses){
                        sb2.append(course.getEmail().toLowerCase()+", ");
                    }
                    JOptionPane.showMessageDialog(null, "Warning: Some emails may be incorrect. Please double check before sending.");
                    StringSelection selection = new StringSelection(sb2.toString());
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(selection, null);
                    JOptionPane.showMessageDialog(null, "Emails copied to clipboard!");
                }catch(Exception x){
                    JOptionPane.showMessageDialog(null, "Sorry an unknown Error has occured!");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Please press select your specifications and press start!");
            }
            
            
        });//ends copy email button listener
        
        //quit item listener
        quitItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                System.exit(0);//end program
                
            }//end action performed
        });//ends quit item listener
        
        //sets the frame to visible so it can be seen
        frame.setVisible(true);
    }
    
    
} // ends MyProgram class 

