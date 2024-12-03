import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private JFrame frame;
    private JTextField fileNameField;
    private JTextField nameField;
    private JTextField addressField;
    private JTextField contactField;
    private JTextField ageField;
    private JTextField sexField;
    private JTextField bgField;
    private JTextField diseaseField;
    private JTextField daysField;
    private JTextField wchargeField;
    private JTextField docField;
    private JTextField serField;
    private JTextArea outputArea;
    private String currentDateTime;

    private final String USERNAME = "kshitij";
    private final String PASSWORD = "8777";

    public Main() {
        frame = new JFrame("E-Health Care Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout(3, 2));

        showLoginScreen();
        frame.setVisible(true);
    }

    private void showLoginScreen() {
        JPanel loginPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        loginPanel.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        loginPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 30));
        loginPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        loginPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30));
        loginPanel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 25));
        buttonPanel.add(loginButton);

        JButton logoutButton = new JButton("Exit");
        logoutButton.setPreferredSize(new Dimension(100, 25));
        buttonPanel.add(logoutButton);

        loginButton
                .addActionListener(e -> authenticate(usernameField.getText(), new String(passwordField.getPassword())));
        logoutButton.addActionListener(e -> System.exit(0));

        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());

        frame.add(loginPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.revalidate();
    }

    private void authenticate(String username, String password) {
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            showMainMenu();
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showMainMenu() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(7, 1));

        JButton addRecordButton = new JButton("Add New Patient Record");
        JButton viewHistoryButton = new JButton("View Patient History");
        JButton generateBillButton = new JButton("Generate Bill");
        JButton updateRecordButton = new JButton("Update Patient Record");
        JButton helpButton = new JButton("Help");
        JButton exitButton = new JButton("Exit");

        menuPanel.add(addRecordButton);
        menuPanel.add(viewHistoryButton);
        menuPanel.add(generateBillButton);
        menuPanel.add(updateRecordButton);
        menuPanel.add(helpButton);
        menuPanel.add(exitButton);

        frame.getContentPane().removeAll();
        frame.add(menuPanel, BorderLayout.WEST);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        addRecordButton.addActionListener(e -> showAddRecordScreen());
        viewHistoryButton.addActionListener(e -> viewPatientHistory());
        generateBillButton.addActionListener(e -> showGenerateBillScreen());
        updateRecordButton.addActionListener(e -> showUpdateRecordScreen());
        helpButton.addActionListener(e -> showHelpScreen());
        exitButton.addActionListener(e -> System.exit(0));

        frame.revalidate();
    }

    private void showAddRecordScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2));

        panel.add(new JLabel("File Name:"));
        fileNameField = new JTextField();
        panel.add(fileNameField);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Address:"));
        addressField = new JTextField();
        panel.add(addressField);

        panel.add(new JLabel("Contact Number:"));
        contactField = new JTextField();
        panel.add(contactField);

        panel.add(new JLabel("Age:"));
        ageField = new JTextField();
        panel.add(ageField);

        panel.add(new JLabel("Sex:"));
        sexField = new JTextField();
        panel.add(sexField);

        panel.add(new JLabel("Blood Group:"));
        bgField = new JTextField();
        panel.add(bgField);

        panel.add(new JLabel("Any Major Disease:"));
        diseaseField = new JTextField();
        panel.add(diseaseField);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> savePatientRecord());
        panel.add(saveButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMainMenu());
        panel.add(backButton);

        frame.getContentPane().removeAll();
        frame.add(panel, BorderLayout.CENTER);
        frame.revalidate();
    }

    private void savePatientRecord() {
        if (isAnyFieldEmpty(nameField, addressField, contactField, ageField, sexField, bgField, diseaseField)) {
            JOptionPane.showMessageDialog(frame, "Please fill in all the fields.", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fileName = fileNameField.getText();
        getCurrentDateTime();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".txt"))) {
            writer.write("Date of Admission: " + currentDateTime);
            writer.newLine();
            writer.write("Name: " + nameField.getText());
            writer.newLine();
            writer.write("Address: " + addressField.getText());
            writer.newLine();
            writer.write("Contact Number: " + contactField.getText());
            writer.newLine();
            writer.write("Age: " + ageField.getText());
            writer.newLine();
            writer.write("Sex: " + sexField.getText());
            writer.newLine();
            writer.write("Blood Group: " + bgField.getText());
            writer.newLine();
            writer.write("Any Major Disease: " + diseaseField.getText());
            writer.newLine();
            writer.write("*************************************************************************");
            JOptionPane.showMessageDialog(frame, "Patient record saved successfully.");
            showMainMenu();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error saving patient record: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewPatientHistory() {
        String fileName = JOptionPane.showInputDialog(frame, "Enter the patient's file name:");
        outputArea.setText("");
        File file = new File(fileName + ".txt");
    
        if (!file.exists()) {
            JOptionPane.showMessageDialog(frame, "File does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        try {
            // Read the existing content and store only the latest patient record
            StringBuilder newContent = new StringBuilder();
            StringBuilder latestRecord = new StringBuilder();
            boolean isNewRecord = false;
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Check if we are reading a new record
                    if (line.startsWith("Date of Admission:")) {
                        isNewRecord = true;
                        latestRecord.setLength(0); // Clear previous content
                    }
                    if (isNewRecord) {
                        latestRecord.append(line).append("\n");
                    }
                }
            }
    
            // Display the latest record in the output area
            outputArea.setText(latestRecord.toString());
    
            // Save only the latest record back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(latestRecord.toString());
            }
    
            JOptionPane.showMessageDialog(frame, "Displaying latest patient record.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void showGenerateBillScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        panel.add(new JLabel("File Name:"));
        fileNameField = new JTextField();
        panel.add(fileNameField);

        panel.add(new JLabel("Number of Days:"));
        daysField = new JTextField();
        panel.add(daysField);

        panel.add(new JLabel("Ward Charges:"));
        wchargeField = new JTextField();
        panel.add(wchargeField);

        panel.add(new JLabel("Doctor Charges:"));
        docField = new JTextField();
        panel.add(docField);

        panel.add(new JLabel("Service Charges:"));
        serField = new JTextField();
        panel.add(serField);

        JButton generateButton = new JButton("Generate Bill");
        generateButton.addActionListener(e -> generateBill());
        panel.add(generateButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMainMenu());
        panel.add(backButton);

        frame.getContentPane().removeAll();
        frame.add(panel, BorderLayout.CENTER);
        frame.revalidate();
    }

    private void generateBill() {
        if (isAnyFieldEmpty(daysField, wchargeField, docField, serField)) {
            JOptionPane.showMessageDialog(frame, "Please fill in all the billing fields.", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int days = Integer.parseInt(daysField.getText());
            int wardCharges = Integer.parseInt(wchargeField.getText());
            int doctorCharges = Integer.parseInt(docField.getText());
            int serviceCharges = Integer.parseInt(serField.getText());

            getCurrentDateTime();
            try (PrintWriter writer = new PrintWriter(
                    new BufferedWriter(new FileWriter(fileNameField.getText() + ".txt", true)))) {
                writer.println("Date of Billing: " + currentDateTime);
                writer.println("Number of Days: " + days);
                writer.println("Ward Charges: " + wardCharges);
                writer.println("Doctor Charges: " + doctorCharges);
                writer.println("Service Charges: " + serviceCharges);
                writer.println("Total Bill: " + (days * wardCharges + doctorCharges + serviceCharges));
                writer.println("*************************************************************************");
                JOptionPane.showMessageDialog(frame, "Bill generated successfully.");
                showMainMenu();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter valid numbers.", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error generating bill: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showUpdateRecordScreen() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2));

        panel.add(new JLabel("File Name:"));
        fileNameField = new JTextField();
        panel.add(fileNameField);

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Address:"));
        addressField = new JTextField();
        panel.add(addressField);

        panel.add(new JLabel("Contact Number:"));
        contactField = new JTextField();
        panel.add(contactField);

        panel.add(new JLabel("Age:"));
        ageField = new JTextField();
        panel.add(ageField);

        panel.add(new JLabel("Sex:"));
        sexField = new JTextField();
        panel.add(sexField);

        panel.add(new JLabel("Blood Group:"));
        bgField = new JTextField();
        panel.add(bgField);

        panel.add(new JLabel("Any Major Disease:"));
        diseaseField = new JTextField();
        panel.add(diseaseField);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> updatePatientRecord());
        panel.add(updateButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> showMainMenu());
        panel.add(backButton);

        frame.getContentPane().removeAll();
        frame.add(panel, BorderLayout.CENTER);
        frame.revalidate();
    }

    private void updatePatientRecord() {
        if (isAnyFieldEmpty(nameField, addressField, contactField, ageField, sexField, bgField, diseaseField)) {
            JOptionPane.showMessageDialog(frame, "Please fill in all the fields.", "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String fileName = fileNameField.getText();
        File file = new File(fileName + ".txt");

        if (!file.exists()) {
            JOptionPane.showMessageDialog(frame, "File does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Read the existing content
            StringBuilder fileContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.append(line).append("\n");
                }
            }

            // Append the updated patient information
            getCurrentDateTime();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
                writer.write("Date of Admission: " + currentDateTime);
                writer.newLine();
                writer.write("Name: " + nameField.getText());
                writer.newLine();
                writer.write("Address: " + addressField.getText());
                writer.newLine();
                writer.write("Contact Number: " + contactField.getText());
                writer.newLine();
                writer.write("Age: " + ageField.getText());
                writer.newLine();
                writer.write("Sex: " + sexField.getText());
                writer.newLine();
                writer.write("Blood Group: " + bgField.getText());
                writer.newLine();
                writer.write("Any Major Disease: " + diseaseField.getText());
                writer.newLine();
                writer.write("*************************************************************************");
                writer.write(fileContent.toString());
                JOptionPane.showMessageDialog(frame, "Patient record updated successfully.");
                showMainMenu();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error updating patient record: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showHelpScreen() {
        JTextArea helpArea = new JTextArea();
        helpArea.setText("Help Information:\n" +
                "1. Add New Patient Record: To add a new patient record, enter all the required details and save them.\n"
                +
                "2. View Patient History: Enter the patient's file name to view their history.\n" +
                "3. Generate Bill: Enter the required details to generate a bill for the patient.\n" +
                "4. Update Patient Record: Enter the patient's file name and updated details to modify the existing record.\n"
                +
                "5. Exit: Closes the application.\n");
        helpArea.setEditable(false);

        JOptionPane.showMessageDialog(frame, new JScrollPane(helpArea), "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    private void getCurrentDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        currentDateTime = LocalDateTime.now().format(formatter);
    }

    private boolean isAnyFieldEmpty(JTextField... fields) {
        for (JTextField field : fields) {
            if (field.getText().trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}