

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

// Person Class
class Person {
    String name;
    String city;
    String phone;
    String address;
    String credentials;
    String educational_info;
    int age;

    Person(String name, String city, String phone, String address, String credentials, String educational_info, int age) {
        this.name = name;
        this.city = city;
        this.phone = phone;
        this.address = address;
        this.credentials = credentials;
        this.educational_info = educational_info;
        this.age = age;
    }
}

// LoanTaker Class
class LoanTaker extends Person {
    String mortgage;
    double mortgageValue;
    String mortgageDescription;
    String category;
    String incomeDetails;
    String pastLoanHistory;
    String livingSituation;

    LoanTaker(String name, String city, String phone, String address, String credentials, String educational_info, int age,
        String mortgage, double mortgageValue, String mortgageDescription, String category, 
        String incomeDetails, String pastLoanHistory, String livingSituation) {
        super(name, city, phone, address, credentials, educational_info, age);
        this.mortgage = mortgage;
        this.mortgageValue = mortgageValue;
        this.mortgageDescription = mortgageDescription;
        this.category = category;
        this.incomeDetails = incomeDetails;
        this.pastLoanHistory = pastLoanHistory;
        this.livingSituation = livingSituation;
    }
}

// LoanDetails Class
class LoanDetails {
    double amount;
    int returnDays;
    String returnDate;
    String terms;
    boolean viaBank;
    String transactionId;
    double interestRate;
    double penalty;
    String paymentMethod;

    LoanDetails(double amount, int returnDays, String returnDate, String terms, boolean viaBank,
    String transactionId, double interestRate, double penalty, String paymentMethod) {
        this.amount = amount;
        this.returnDays = returnDays;
        this.returnDate = returnDate;
        this.terms = terms;
        this.viaBank = viaBank;
        this.transactionId = transactionId;
        this.interestRate = interestRate;
        this.penalty = penalty;
        this.paymentMethod = paymentMethod;
    }
}

// Main GUI Class
public class LoanManagementSystem extends JFrame {
     // backgroud and text color
    private static final Color BG_COLOR = new Color(253, 251, 212); // #BFC4C8
    //rgb(126, 78, 56)
    //41, 116, 34 green
    //237,232,208 beige
    //253,251,212 cream

    private static final Color TEXT_COLOR = Color.BLACK;
    // Add interest rate calculator constants
    private static final double BASE_INTEREST_RATE = 18.0;
    private static final double PENALTY_PER_DAY = 500.0;
    private static final double SHORT_TERM_DISCOUNT = 2.0;
    private static final double LONG_TERM_PREMIUM = 3.0;
    private static final double MIN_LOAN_AMOUNT = 50000.0; // 50K minimum
    
    // Navigation
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JButton prevButton, nextButton, generateButton, printButton;
    private int currentPanel = 0;
    private final int TOTAL_PANELS = 8;
    
    // Add new fields for interest rate display
    private JLabel interestRateLabel;
    
    // Personal Info with AGE field
    private JTextField nameField, cityField, phoneField, addressField;
    private JTextField credentialsField, educationField, ageField;
    private JComboBox<String> categoryCombo;
    private JRadioButton criminalYes, criminalNo;
    
    // Income Details
    private JTextField companyField, positionField, salaryField, officeAddressField;
    private JTextField businessNameField, businessTypeField, businessIncomeField, businessAddressField;
    
    // Past Loan
    private JRadioButton pastLoanYes, pastLoanNo;
    private JTextField pastAmountField, pastTypeField, pastDurationField;
    private JTextField pastBalanceField, pastProviderField, pastRepaidField;
    private JPanel pastLoanPanel;
    
    // Living Situation
    private JRadioButton houseOwned, houseRented, houseLoaned, noHouse;
    private JTextField houseValueField, houseDetailField;
    private JRadioButton carOwned, carRented, carLoaned, noCar;
    private JTextField carValueField, carDetailField;
    
    // Loan Details
    private JTextField loanAmountField;
    private JLabel loanLimitLabel;
    
    // Mortgage
    private JComboBox<String> mortgageCombo;
    private JTextField mortgageValueField;
    private JTextArea mortgageDescriptionArea;
    
    // Guarantors (2 sets)
    private JTextField[] guarantorName = new JTextField[2];
    private JTextField[] guarantorCity = new JTextField[2];
    private JTextField[] guarantorPhone = new JTextField[2];
    private JTextField[] guarantorAddress = new JTextField[2];
    private JTextField[] guarantorCred = new JTextField[2];
    private JTextField[] guarantorOccupation = new JTextField[2];
    private JRadioButton[] guarantorCriminalYes = new JRadioButton[2];
    private JRadioButton[] guarantorCriminalNo = new JRadioButton[2];
    
    // Loan Terms
    private JTextField durationField;
    private JComboBox<String> durationUnitCombo;
    private JRadioButton termsAccept, termsReject;
    private JRadioButton bankYes, bankNo;
    private JTextField transactionField;
    
    // Affidavit
    private JTextArea affidavitArea;

    public LoanManagementSystem() {
        setTitle("Loan Management System - Bank Edition");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        mainPanel.add(createPersonalPanel(), "1");
        mainPanel.add(createIncomePanel(), "2");
        mainPanel.add(createPastLoanPanel(), "3");
        mainPanel.add(createLivingPanel(), "4");
        mainPanel.add(createLoanPanel(), "5");
        mainPanel.add(createGuarantorsPanel(), "6");
        mainPanel.add(createTermsPanel(), "7");
        mainPanel.add(createAffidavitPanel(), "8");
        
        add(mainPanel, BorderLayout.CENTER);
        
        JPanel navPanel = new JPanel(new BorderLayout());
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        prevButton = new JButton("<< Previous");
        prevButton.setEnabled(false);
        prevButton.setBackground(Color.BLACK);
        prevButton.setForeground(Color.WHITE);
        prevButton.setOpaque(true);
        prevButton.setBorderPainted(false);
        prevButton.addActionListener(e -> navigate(-1));
        
        nextButton = new JButton("Next >>");
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.WHITE);
        nextButton.setOpaque(true);
        nextButton.setBorderPainted(false);
        nextButton.addActionListener(e -> navigate(1));
        
        generateButton = new JButton("Generate Affidavit");
        generateButton.setVisible(false);
        generateButton.setBackground(Color.BLACK);
        generateButton.setForeground(Color.WHITE);
        generateButton.setOpaque(true);
        generateButton.setBorderPainted(false);
        generateButton.addActionListener(e -> generateAffidavit());
        
        printButton = new JButton("[Print Affidavit]");
        printButton.setVisible(false);
        printButton.setBackground(Color.BLACK);
        printButton.setForeground(Color.WHITE);
        printButton.setOpaque(true);
        printButton.setBorderPainted(false);
        printButton.addActionListener(e -> printAffidavit());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(generateButton);
        buttonPanel.add(printButton);
        
        navPanel.add(buttonPanel, BorderLayout.CENTER);
        
        add(navPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    private void navigate(int direction) {
        currentPanel += direction;
        
        if (currentPanel < 0) currentPanel = 0;
        if (currentPanel >= TOTAL_PANELS) currentPanel = TOTAL_PANELS - 1;
        
        prevButton.setEnabled(currentPanel > 0);
        
        if (currentPanel == TOTAL_PANELS - 1) {
            nextButton.setVisible(false);
            generateButton.setVisible(true);
            printButton.setVisible(false); // Initially hide print button
        } else {
            nextButton.setVisible(true);
            generateButton.setVisible(false);
            printButton.setVisible(false);
        }
        
        cardLayout.show(mainPanel, String.valueOf(currentPanel + 1));
    }
    
    private double calculateInterestRate(int returnDays) {
        double baseRate = BASE_INTEREST_RATE;
        
        if (returnDays <= 90) {
            return baseRate - SHORT_TERM_DISCOUNT;
        } else if (returnDays <= 180) {
            return baseRate - 1.0;
        } else if (returnDays <= 365) {
            return baseRate;
        } else if (returnDays <= 730) {
            return baseRate + 1.0;
        } else if (returnDays <= 1095) {
            return baseRate + 2.0;
        } else {
            return baseRate + LONG_TERM_PREMIUM;
        }
    }
    
    private String getInterestRateDescription(int returnDays, double interestRate) {
        String durationDesc;
        if (returnDays <= 30) {
            durationDesc = "Short-term (<= 1 month)";
        } else if (returnDays <= 90) {
            durationDesc = "Short-term (1-3 months)";
        } else if (returnDays <= 180) {
            durationDesc = "Medium-term (3-6 months)";
        } else if (returnDays <= 365) {
            durationDesc = "Medium-term (6-12 months)";
        } else if (returnDays <= 730) {
            durationDesc = "Long-term (1-2 years)";
        } else if (returnDays <= 1095) {
            durationDesc = "Long-term (2-3 years)";
        } else {
            durationDesc = "Very long-term (> 3 years)";
        }
        
        return String.format("%.1f%% (%s)", interestRate, durationDesc);
    }
    
    // ============================================================
    // UPDATED: Personal Panel with AGE field
    // ============================================================
    private JPanel createPersonalPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel title = new JLabel("Personal Information");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(TEXT_COLOR);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(title, gbc);
        
        int row = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;
        
        addLabelField(panel, gbc, "Full Name:", nameField = new JTextField(25), row++);
        addLabelField(panel, gbc, "Age (18-90):", ageField = new JTextField(25), row++);
        addLabelField(panel, gbc, "City:", cityField = new JTextField(25), row++);
        addLabelField(panel, gbc, "Phone Number:", phoneField = new JTextField(25), row++);
        addLabelField(panel, gbc, "Address:", addressField = new JTextField(25), row++);
        addLabelField(panel, gbc, "CNIC/B-Form:", credentialsField = new JTextField(25), row++);
        addLabelField(panel, gbc, "Education:", educationField = new JTextField(25), row++);
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        String[] categories = {"Student", "Job", "Business", "Nothing"};
        categoryCombo = new JComboBox<>(categories);
        categoryCombo.addActionListener(e -> updateLoanLimit());
        panel.add(categoryCombo, gbc);
        row++;
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Criminal Record:"), gbc);
        gbc.gridx = 1;
        JPanel crimPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        criminalYes = new JRadioButton("Yes");
        criminalNo = new JRadioButton("No");
        criminalNo.setSelected(true);
        ButtonGroup crimGroup = new ButtonGroup();
        crimGroup.add(criminalYes);
        crimGroup.add(criminalNo);
        crimPanel.add(criminalYes);
        crimPanel.add(criminalNo);
        panel.add(crimPanel, gbc);
        
        return panel;
    }
    
    private JPanel createIncomePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(BG_COLOR);
        JLabel title = new JLabel("Income Details", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(TEXT_COLOR);
        panel.add(title, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 20, 20));
        
        JPanel jobSection = new JPanel(new GridBagLayout());
        jobSection.setBorder(BorderFactory.createTitledBorder("Job Details (if employed)"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        addLabelField(jobSection, gbc, "Company Name:", companyField = new JTextField(20), row++);
        addLabelField(jobSection, gbc, "Position:", positionField = new JTextField(20), row++);
        addLabelField(jobSection, gbc, "Monthly Salary (Rs):", salaryField = new JTextField(20), row++);
        addLabelField(jobSection, gbc, "Office Address:", officeAddressField = new JTextField(20), row++);
        
        JPanel businessSection = new JPanel(new GridBagLayout());
        businessSection.setBorder(BorderFactory.createTitledBorder("Business Details (if business owner)"));
        
        row = 0;
        addLabelField(businessSection, gbc, "Business Name:", businessNameField = new JTextField(20), row++);
        addLabelField(businessSection, gbc, "Business Type:", businessTypeField = new JTextField(20), row++);
        addLabelField(businessSection, gbc, "Annual Income (Rs):", businessIncomeField = new JTextField(20), row++);
        addLabelField(businessSection, gbc, "Business Address:", businessAddressField = new JTextField(20), row++);
        
        mainPanel.add(jobSection);
        mainPanel.add(businessSection);
        panel.add(mainPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createPastLoanPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(BG_COLOR);

        JLabel title = new JLabel("Past Loan History", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(TEXT_COLOR);
        panel.add(title, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel question = new JLabel("Do you have any past loan history?");
        question.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        centerPanel.add(question, gbc);
        
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pastLoanYes = new JRadioButton("Yes");
        pastLoanNo = new JRadioButton("No");
        pastLoanNo.setSelected(true);
        pastLoanYes.setFont(new Font("Arial", Font.PLAIN, 14));
        pastLoanNo.setFont(new Font("Arial", Font.PLAIN, 14));
        ButtonGroup pastGroup = new ButtonGroup();
        pastGroup.add(pastLoanYes);
        pastGroup.add(pastLoanNo);
        radioPanel.add(pastLoanYes);
        radioPanel.add(pastLoanNo);
        gbc.gridy = 1;
        centerPanel.add(radioPanel, gbc);
        
        pastLoanPanel = new JPanel(new GridBagLayout());
        pastLoanPanel.setBorder(BorderFactory.createTitledBorder("Past Loan Details"));
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 5, 5, 5);
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        
        int row2 = 0;
        addLabelField(pastLoanPanel, gbc2, "Loan Amount (Rs):", pastAmountField = new JTextField(20), row2++);
        addLabelField(pastLoanPanel, gbc2, "Loan Type:", pastTypeField = new JTextField(20), row2++);
        addLabelField(pastLoanPanel, gbc2, "Duration (Months):", pastDurationField = new JTextField(20), row2++);
        addLabelField(pastLoanPanel, gbc2, "Was loan repaid? (Yes/No):", pastRepaidField = new JTextField(20), row2++);
        addLabelField(pastLoanPanel, gbc2, "Outstanding Balance (Rs):", pastBalanceField = new JTextField(20), row2++);
        addLabelField(pastLoanPanel, gbc2, "Loan Provider:", pastProviderField = new JTextField(20), row2++);
        
        pastLoanPanel.setVisible(false);
        
        pastLoanYes.addActionListener(e -> pastLoanPanel.setVisible(true));
        pastLoanNo.addActionListener(e -> pastLoanPanel.setVisible(false));
        
        gbc.gridy = 2;
        centerPanel.add(pastLoanPanel, gbc);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createLivingPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel title = new JLabel("Living Situation", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(TEXT_COLOR);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4;
        panel.add(title, gbc);
        
        int row = 1;
        gbc.gridwidth = 1;
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("House Status:"), gbc);
        gbc.gridx = 1;
        JPanel housePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        houseOwned = new JRadioButton("Owned");
        houseRented = new JRadioButton("Rented");
        houseLoaned = new JRadioButton("Loaned");
        noHouse = new JRadioButton("No House");
        noHouse.setSelected(true);
        
        ButtonGroup houseGroup = new ButtonGroup();
        houseGroup.add(houseOwned);
        houseGroup.add(houseRented);
        houseGroup.add(houseLoaned);
        houseGroup.add(noHouse);
        
        housePanel.add(houseOwned);
        housePanel.add(houseRented);
        housePanel.add(houseLoaned);
        housePanel.add(noHouse);
        panel.add(housePanel, gbc);
        
        gbc.gridx = 2; 
        panel.add(new JLabel("Value (Rs):"), gbc);
        gbc.gridx = 3;
        houseValueField = new JTextField(15);
        houseValueField.setEnabled(false);
        houseValueField.setToolTipText("Enter house value if owned or loaned");
        panel.add(houseValueField, gbc);
        row++;
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("House Details:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        houseDetailField = new JTextField(40);
        houseDetailField.setEnabled(false);
        houseDetailField.setToolTipText("e.g., Location, size, etc.");
        panel.add(houseDetailField, gbc);
        row++;
        
        gbc.gridwidth = 4;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(Box.createVerticalStrut(15), gbc);
        row++;
        
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Car Status:"), gbc);
        gbc.gridx = 1;
        JPanel carPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        carOwned = new JRadioButton("Owned");
        carRented = new JRadioButton("Rented");
        carLoaned = new JRadioButton("Loaned");
        noCar = new JRadioButton("No Car");
        noCar.setSelected(true);
        
        ButtonGroup carGroup = new ButtonGroup();
        carGroup.add(carOwned);
        carGroup.add(carRented);
        carGroup.add(carLoaned);
        carGroup.add(noCar);
        
        carPanel.add(carOwned);
        carPanel.add(carRented);
        carPanel.add(carLoaned);
        carPanel.add(noCar);
        panel.add(carPanel, gbc);
        
        gbc.gridx = 2;
        panel.add(new JLabel("Value (Rs):"), gbc);
        gbc.gridx = 3;
        carValueField = new JTextField(15);
        carValueField.setEnabled(false);
        carValueField.setToolTipText("Enter car value if owned or loaned");
        panel.add(carValueField, gbc);
        row++;
        
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Car Details:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        carDetailField = new JTextField(40);
        carDetailField.setEnabled(false);
        carDetailField.setToolTipText("e.g., Model, year, etc.");
        panel.add(carDetailField, gbc);
        
        ActionListener houseListener = e -> {
            boolean hasHouse = !noHouse.isSelected();
            houseValueField.setEnabled(hasHouse);
            houseDetailField.setEnabled(hasHouse);
            if (!hasHouse) {
                houseValueField.setText("");
                houseDetailField.setText("");
            }
        };
        
        ActionListener carListener = e -> {
            boolean hasCar = !noCar.isSelected();
            carValueField.setEnabled(hasCar);
            carDetailField.setEnabled(hasCar);
            if (!hasCar) {
                carValueField.setText("");
                carDetailField.setText("");
            }
        };
        
        houseOwned.addActionListener(houseListener);
        houseRented.addActionListener(houseListener);
        houseLoaned.addActionListener(houseListener);
        noHouse.addActionListener(houseListener);
        
        carOwned.addActionListener(carListener);
        carRented.addActionListener(carListener);
        carLoaned.addActionListener(carListener);
        noCar.addActionListener(carListener);
        
        return panel;
    }
    
    private JPanel createLoanPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel title = new JLabel("Loan & Mortgage Details");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(TEXT_COLOR);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(title, gbc);
        
        int row = 1;
        
        addLabelField(panel, gbc, "Loan Amount (Rs):", loanAmountField = new JTextField(20), row++);
        
        gbc.gridx = 0; gbc.gridy = row;
        loanLimitLabel = new JLabel("Maximum Loan Limit: Rs. 0 | Minimum: Rs. 50,000");
        loanLimitLabel.setForeground(Color.BLACK);
        loanLimitLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(loanLimitLabel, gbc);
        row++;
        
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(Box.createVerticalStrut(20), gbc);
        row++;
        
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = row;
        JLabel mortgageLabel = new JLabel("Mortgage Item:");
        mortgageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(mortgageLabel, gbc);
        gbc.gridx = 1;
        String[] mortgageItems = {
            "Select Mortgage Item",
            "Residential Property",
            "Commercial Property", 
            "Plot/Land",
            "Car/Vehicle",
            "Jewelry",
            "Other Valuables"
        };
        mortgageCombo = new JComboBox<>(mortgageItems);
        mortgageCombo.setPreferredSize(new Dimension(250, 25));
        mortgageCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(mortgageCombo, gbc);
        row++;
        
        gbc.gridx = 0; gbc.gridy = row;
        JLabel valueLabel = new JLabel("Mortgage Value (Rs):");
        valueLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(valueLabel, gbc);
        gbc.gridx = 1;
        mortgageValueField = new JTextField(20);
        mortgageValueField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(mortgageValueField, gbc);
        row++;
        
        gbc.gridx = 0; gbc.gridy = row;
        JLabel descLabel = new JLabel("Description (Optional):");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(descLabel, gbc);
        gbc.gridx = 1;
        mortgageDescriptionArea = new JTextArea(3, 30);
        mortgageDescriptionArea.setLineWrap(true);
        mortgageDescriptionArea.setWrapStyleWord(true);
        mortgageDescriptionArea.setFont(new Font("Arial", Font.PLAIN, 14));
        //mortgageDescriptionArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane mortgageScroll = new JScrollPane(mortgageDescriptionArea);
        mortgageScroll.setPreferredSize(new Dimension(250, 60));
        panel.add(mortgageScroll, gbc);
        
        updateLoanLimit();
        
        return panel;
    }
    
    private JPanel createGuarantorsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(BG_COLOR);
        
        JLabel title = new JLabel("Guarantors Information (2 Required)", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(TEXT_COLOR);
        panel.add(title, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        
        for (int i = 0; i < 2; i++) {
            JPanel guarantorPanel = new JPanel(new GridBagLayout());
            guarantorPanel.setBorder(BorderFactory.createTitledBorder("Guarantor " + (i + 1)));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            
            int row = 0;
            guarantorName[i] = new JTextField(20);
            addLabelField(guarantorPanel, gbc, "Full Name:", guarantorName[i], row++);
            
            guarantorCity[i] = new JTextField(20);
            addLabelField(guarantorPanel, gbc, "City:", guarantorCity[i], row++);
            
            guarantorPhone[i] = new JTextField(20);
            addLabelField(guarantorPanel, gbc, "Phone:", guarantorPhone[i], row++);
            
            guarantorAddress[i] = new JTextField(20);
            addLabelField(guarantorPanel, gbc, "Address:", guarantorAddress[i], row++);
            
            guarantorCred[i] = new JTextField(20);
            addLabelField(guarantorPanel, gbc, "CNIC:", guarantorCred[i], row++);
            
            guarantorOccupation[i] = new JTextField(20);
            addLabelField(guarantorPanel, gbc, "Occupation:", guarantorOccupation[i], row++);
            
            gbc.gridx = 0; gbc.gridy = row;
            guarantorPanel.add(new JLabel("Criminal Record:"), gbc);
            gbc.gridx = 1;
            JPanel crimPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            guarantorCriminalYes[i] = new JRadioButton("Yes");
            guarantorCriminalNo[i] = new JRadioButton("No");
            guarantorCriminalNo[i].setSelected(true);
            ButtonGroup crimGroup = new ButtonGroup();
            crimGroup.add(guarantorCriminalYes[i]);
            crimGroup.add(guarantorCriminalNo[i]);
            crimPanel.add(guarantorCriminalYes[i]);
            crimPanel.add(guarantorCriminalNo[i]);
            guarantorPanel.add(crimPanel, gbc);
            
            mainPanel.add(guarantorPanel);
        }
        
        panel.add(mainPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createTermsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(BG_COLOR);
        
        JLabel title = new JLabel("Terms & Conditions", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(TEXT_COLOR);
        panel.add(title, BorderLayout.NORTH);
        
        JTabbedPane termsTabs = new JTabbedPane();
        
        JPanel interestPanel = new JPanel(new GridBagLayout());
        interestPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        JLabel interestInfo = new JLabel("<html><b>Dynamic Interest Rate Calculator</b><br>"
                + "Rates change based on loan duration (Base: 18% per annum)</html>");
        interestInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        interestPanel.add(interestInfo, gbc);
        row++;
        
        JTextArea rateTable = new JTextArea(10, 40);
        rateTable.setText("INTEREST RATE SCHEDULE (PER ANNUM):\n");
        rateTable.append("===========================================\n");
        rateTable.append("Duration           | Interest Rate\n");
        rateTable.append("===========================================\n");
        rateTable.append("<= 1 month         | 16.0%\n");
        rateTable.append("1-3 months         | 16.0%\n");
        rateTable.append("3-6 months         | 17.0%\n");
        rateTable.append("6-12 months        | 18.0%\n");
        rateTable.append("1-2 years          | 19.0%\n");
        rateTable.append("2-3 years          | 20.0%\n");
        rateTable.append("> 3 years          | 21.0%\n");
        rateTable.append("===========================================\n");
        rateTable.append("Penalty: Rs. 500 per day after due date\n");
        rateTable.append("Minimum Loan: Rs. 50,000\n");
        rateTable.setEditable(false);
        rateTable.setFont(new Font("Courier New", Font.PLAIN, 12));
        rateTable.setBackground(new Color(245, 245, 245));
        gbc.gridy = row;
        interestPanel.add(new JScrollPane(rateTable), gbc);
        row++;
        
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = row;
        interestPanel.add(new JLabel("Your Interest Rate:"), gbc);
        
        gbc.gridx = 1;
        interestRateLabel = new JLabel("Enter duration to calculate");
        interestRateLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        interestRateLabel.setForeground(Color.BLACK);
        interestPanel.add(interestRateLabel, gbc);
        row++;
        
        termsTabs.addTab("Interest Rates", interestPanel);
        
        JTextArea termsText = new JTextArea(20, 60);
        termsText.setText("LOAN TERMS & CONDITIONS\n");
        termsText.append("===========================================\n");
        termsText.append("1. INTEREST RATES (PER ANNUM):\n");
        termsText.append("   * Based on State Bank of Pakistan guidelines\n");
        termsText.append("   * Rates vary with loan duration (16-21%)\n");
        termsText.append("   * Calculated on reducing balance method\n\n");
        
        termsText.append("2. LOAN AMOUNT LIMITS:\n");
        termsText.append("   * Students/Salaried: Maximum Rs. 20,00,000\n");
        termsText.append("   * Business Owners: Maximum Rs. 50,00,000\n");
        termsText.append("   * Minimum loan: Rs. 50,000\n\n");
        
        termsText.append("3. AGE REQUIREMENTS:\n");
        termsText.append("   * Minimum age: 18 years\n");
        termsText.append("   * Maximum age: 90 years\n\n");
        
        termsText.append("4. LATE PAYMENT PENALTIES:\n");
        termsText.append("   * Rs. 500 per day after due date\n");
        termsText.append("   * Additional 2% monthly on overdue amount\n");
        termsText.append("   * Credit score affected after 30 days delay\n\n");
        
        termsText.append("5. DOCUMENT REQUIREMENTS:\n");
        termsText.append("   * Valid CNIC copy (front & back)\n");
        termsText.append("   * Recent utility bill (proof of address)\n");
        termsText.append("   * Last 3 months bank statement\n");
        termsText.append("   * Income proof (salary slip/business documents)\n");
        termsText.append("   * Two passport-size photographs\n\n");
        
        termsText.append("6. ELIGIBILITY CRITERIA:\n");
        termsText.append("   * Age: 18-90 years\n");
        termsText.append("   * Minimum income: Rs. 25,000/month\n");
        termsText.append("   * Clean credit history (no defaults)\n");
        termsText.append("   * No criminal record (borrower & guarantors)\n\n");
        
        termsText.append("7. PROCESSING FEES:\n");
        termsText.append("   * 1% of loan amount (minimum Rs. 1,000)\n");
        termsText.append("   * Non-refundable after approval\n\n");
        
        termsText.append("8. PREPAYMENT TERMS:\n");
        termsText.append("   * No penalty for early repayment\n");
        termsText.append("   * Interest calculated till repayment date\n\n");
        
        termsText.append("9. DEFAULT CONSEQUENCES:\n");
        termsText.append("   * Legal action after 90 days default\n");
        termsText.append("   * Mortgage item may be seized\n");
        termsText.append("   * Guarantors held jointly liable\n");
        termsText.append("   * Credit bureau reporting\n");
        
        termsText.setEditable(false);
        termsText.setFont(new Font("Arial", Font.PLAIN, 12));
        termsText.setBackground(new Color(245, 245, 245));
        termsTabs.addTab("Full Terms", new JScrollPane(termsText));
        
        panel.add(termsTabs, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 5, 5, 5);
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        
        row = 0;
        
        JLabel durLabel = new JLabel("Return Duration:");
        durLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc2.gridx = 0; gbc2.gridy = row;
        bottomPanel.add(durLabel, gbc2);
        
        gbc2.gridx = 1;
        JPanel durPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        durationField = new JTextField(10);
        durationField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { updateInterestRate(); }
            public void removeUpdate(DocumentEvent e) { updateInterestRate(); }
            public void insertUpdate(DocumentEvent e) { updateInterestRate(); }
        });
        
        String[] units = {"Days", "Months", "Years"};
        durationUnitCombo = new JComboBox<>(units);
        durationUnitCombo.addActionListener(e -> updateInterestRate());
        
        durPanel.add(durationField);
        durPanel.add(durationUnitCombo);
        bottomPanel.add(durPanel, gbc2);
        row++;
        
        gbc2.gridx = 0; gbc2.gridy = row;
        bottomPanel.add(new JLabel("Accept Terms:"), gbc2);
        gbc2.gridx = 1;
        JPanel termsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        termsAccept = new JRadioButton("I Accept All Terms");
        termsReject = new JRadioButton("I Reject");
        termsReject.setSelected(true);
        termsAccept.setFont(new Font("Arial", Font.PLAIN, 10));
        ButtonGroup termsGroup = new ButtonGroup();
        termsGroup.add(termsAccept);
        termsGroup.add(termsReject);
        termsPanel.add(termsAccept);
        termsPanel.add(termsReject);
        bottomPanel.add(termsPanel, gbc2);
        row++;
        
        gbc2.gridx = 0; gbc2.gridy = row;
        bottomPanel.add(new JLabel("Payment Method:"), gbc2);
        gbc2.gridx = 1;
        JPanel bankPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bankYes = new JRadioButton("Bank Transfer");
        bankNo = new JRadioButton("Cash");
        bankNo.setSelected(true);
        ButtonGroup bankGroup = new ButtonGroup();
        bankGroup.add(bankYes);
        bankGroup.add(bankNo);
        bankPanel.add(bankYes);
        bankPanel.add(bankNo);
        bottomPanel.add(bankPanel, gbc2);
        row++;
        
        gbc2.gridx = 0; gbc2.gridy = row;
        bottomPanel.add(new JLabel("Transaction ID:"), gbc2);
        gbc2.gridx = 1;
        transactionField = new JTextField(20);
        transactionField.setEnabled(false);
        transactionField.setToolTipText("Required for bank transfers");
        bottomPanel.add(transactionField, gbc2);
        
        bankYes.addActionListener(e -> transactionField.setEnabled(true));
        bankNo.addActionListener(e -> {
            transactionField.setEnabled(false);
            transactionField.setText("");
        });
        
        panel.add(bottomPanel, BorderLayout.SOUTH);
        return panel;
    }
    
    // ============================================================
    // UPDATED: Affidavit Panel with Print Button
    // ============================================================
    private JPanel createAffidavitPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(BG_COLOR);
        
        JLabel title = new JLabel("LOAN AFFIDAVIT", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(TEXT_COLOR);
        panel.add(title, BorderLayout.NORTH);
        
        affidavitArea = new JTextArea(30, 80);
        affidavitArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        affidavitArea.setEditable(false);
        affidavitArea.setMargin(new Insets(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(affidavitArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Add print button to affidavit panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        printButton = new JButton("[Print Affidavit]");
        printButton.setFont(new Font("Arial", Font.BOLD, 14));
        printButton.setBackground(Color.BLACK);
        printButton.setForeground(Color.WHITE);
        printButton.setOpaque(true);
        printButton.setBorderPainted(false);
        printButton.addActionListener(e -> printAffidavit());
        printButton.setVisible(false); // Initially hidden
        buttonPanel.add(printButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void addLabelField(JPanel panel, GridBagConstraints gbc, String label, JTextField field, int row) {
        gbc.gridx = 0; gbc.gridy = row;
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(jLabel, gbc);
        gbc.gridx = 1;
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(field, gbc);
    }
    
    private void updateLoanLimit() {
        String category = (String) categoryCombo.getSelectedItem();
        double limit = 0;
        
        if (category.equals("Student") || category.equals("Job")) {
            limit = 2000000;
        } else if (category.equals("Business")) {
            limit = 5000000;
        }
        
        loanLimitLabel.setText("Maximum Loan Limit: Rs. " + String.format("%,.0f", limit) + " | Minimum: Rs. 50,000");
    }
    
    private void updateInterestRate() {
        try {
            String durationText = durationField.getText().trim();
            if (durationText.isEmpty()) {
                interestRateLabel.setText("Enter duration to calculate");
                return;
            }
            
            int durationValue = Integer.parseInt(durationText);
            String unit = (String) durationUnitCombo.getSelectedItem();
            
            int returnDays;
            if (unit.equals("Months")) {
                returnDays = durationValue * 30;
            } else if (unit.equals("Years")) {
                returnDays = durationValue * 365;
            } else {
                returnDays = durationValue;
            }
            
            double interestRate = calculateInterestRate(returnDays);
            String description = getInterestRateDescription(returnDays, interestRate);
            interestRateLabel.setText(String.format("%.1f%%", interestRate));
            interestRateLabel.setToolTipText(description);
            
        } catch (NumberFormatException e) {
            interestRateLabel.setText("Invalid duration");
        }
    }
    
    private String capitalizeWords(String input) {
        if (input == null || input.trim().isEmpty()) {
            return input;
        }
        
        String[] words = input.trim().split("\\s+");
        StringBuilder result = new StringBuilder();
        
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1).toLowerCase())
                      .append(" ");
            }
        }
        
        return result.toString().trim();
    }
    
    private String repeatString(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
    
    private String getLivingSituationDescription() {
        StringBuilder living = new StringBuilder();
        
        if (houseOwned.isSelected()) {
            living.append("House: Owned");
            if (!houseValueField.getText().trim().isEmpty()) {
                living.append(", Value: Rs. ").append(houseValueField.getText());
            }
            if (!houseDetailField.getText().trim().isEmpty()) {
                living.append(" (").append(houseDetailField.getText()).append(")");
            }
        } else if (houseRented.isSelected()) {
            living.append("House: Rented");
            if (!houseDetailField.getText().trim().isEmpty()) {
                living.append(" (").append(houseDetailField.getText()).append(")");
            }
        } else if (houseLoaned.isSelected()) {
            living.append("House: Loaned");
            if (!houseValueField.getText().trim().isEmpty()) {
                living.append(", Loan Value: Rs. ").append(houseValueField.getText());
            }
            if (!houseDetailField.getText().trim().isEmpty()) {
                living.append(" (").append(houseDetailField.getText()).append(")");
            }
        } else {
            living.append("House: No house");
        }
        
        living.append(". ");
        
        if (carOwned.isSelected()) {
            living.append("Car: Owned");
            if (!carValueField.getText().trim().isEmpty()) {
                living.append(", Value: Rs. ").append(carValueField.getText());
            }
            if (!carDetailField.getText().trim().isEmpty()) {
                living.append(" (").append(carDetailField.getText()).append(")");
            }
        } else if (carRented.isSelected()) {
            living.append("Car: Rented");
            if (!carDetailField.getText().trim().isEmpty()) {
                living.append(" (").append(carDetailField.getText()).append(")");
            }
        } else if (carLoaned.isSelected()) {
            living.append("Car: Loaned");
            if (!carValueField.getText().trim().isEmpty()) {
                living.append(", Loan Value: Rs. ").append(carValueField.getText());
            }
            if (!carDetailField.getText().trim().isEmpty()) {
                living.append(" (").append(carDetailField.getText()).append(")");
            }
        } else {
            living.append("Car: No car");
        }
        
        return living.toString();
    }
    
    private String getPastLoanHistory() {
        if (pastLoanNo.isSelected()) {
            return "No past loan records";
        } else {
            StringBuilder history = new StringBuilder();
            history.append("Previous Loan Amount: Rs. ").append(pastAmountField.getText());
            history.append(", Type: ").append(capitalizeWords(pastTypeField.getText()));
            history.append(", Duration: ").append(pastDurationField.getText()).append(" months");
            history.append(", Repaid: ").append(pastRepaidField.getText());
            history.append(", Outstanding: Rs. ").append(pastBalanceField.getText());
            history.append(", Provider: ").append(capitalizeWords(pastProviderField.getText()));
            return history.toString();
        }
    }
    
    private String getIncomeDetails() {
        String category = (String) categoryCombo.getSelectedItem();
        StringBuilder income = new StringBuilder();
        
        if (category.equals("Job")) {
            income.append("Employment: ");
            income.append("Company: ").append(capitalizeWords(companyField.getText()));
            income.append(", Position: ").append(capitalizeWords(positionField.getText()));
            income.append(", Salary: Rs. ").append(salaryField.getText()).append("/month");
            income.append(", Office Address: ").append(capitalizeWords(officeAddressField.getText()));
        } else if (category.equals("Business")) {
            income.append("Business: ");
            income.append("Name: ").append(capitalizeWords(businessNameField.getText()));
            income.append(", Type: ").append(capitalizeWords(businessTypeField.getText()));
            income.append(", Annual Income: Rs. ").append(businessIncomeField.getText());
            income.append(", Address: ").append(capitalizeWords(businessAddressField.getText()));
        } else if (category.equals("Student")) {
            income.append("Student - No regular employment income");
        } else {
            income.append("No income source");
        }
        
        return income.toString();
    }
    
    // ============================================================
    // NEW METHOD: Print Affidavit
    // ============================================================
    private void printAffidavit() {
        if (affidavitArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please generate affidavit first!",  "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Create a printable component
            Printable printable = new Printable() {
                @Override
                public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) 
                        throws PrinterException {
                    if (pageIndex > 0) {
                        return Printable.NO_SUCH_PAGE;
                    }
                    
                    Graphics2D g2d = (Graphics2D) graphics;
                    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                    
                    // Set font for printing
                    Font printFont = new Font("Courier New", Font.PLAIN, 10);
                    g2d.setFont(printFont);
                    
                    // Split text into lines and print
                    String[] lines = affidavitArea.getText().split("\n");
                    int y = 20;
                    
                    for (String line : lines) {
                        g2d.drawString(line, 10, y);
                        y += 15;
                        
                        // Check if we've reached the bottom of the page
                        if (y > pageFormat.getImageableHeight() - 50) {
                            // Would need to handle multiple pages here
                            break;
                        }
                    }
                    
                    return Printable.PAGE_EXISTS;
                }
            };
            
            // Get printer job
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable(printable);
            
            // Show print dialog
            if (job.printDialog()) {
                job.print();
                JOptionPane.showMessageDialog(this, "Affidavit sent to printer!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(this, "Printing failed: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void generateAffidavit() {
        if (!validateAllFields()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields correctly!", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validate age
        int age;
        try {
            age = Integer.parseInt(ageField.getText());
            if (age < 18 || age > 90) {
                JOptionPane.showMessageDialog(this, "Age must be between 18 and 90 years!", 
                "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid age (18-90)!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (criminalYes.isSelected()) {
            JOptionPane.showMessageDialog(this, "Loan cannot be granted due to criminal record!", 
                "Rejection", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String category = (String) categoryCombo.getSelectedItem();
        if (category.equals("Nothing")) {
            JOptionPane.showMessageDialog(this, "Loan cannot be granted. Person has no income source!", 
                "Rejection", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String name = capitalizeWords(nameField.getText().trim());
        String city = capitalizeWords(cityField.getText().trim());
        String phone = phoneField.getText().trim();
        String address = capitalizeWords(addressField.getText().trim());
        String credentials = credentialsField.getText().trim();
        String education = capitalizeWords(educationField.getText().trim());
        String living = getLivingSituationDescription();
        
        String incomeDetails = getIncomeDetails();
        String pastLoanDetails = getPastLoanHistory();
        
        double amount;
        try {
            amount = Double.parseDouble(loanAmountField.getText());
            if (amount < MIN_LOAN_AMOUNT) {
                JOptionPane.showMessageDialog(this, "Minimum loan amount is Rs. 50,000!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (amount <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid loan amount (minimum Rs. 50,000)!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        double loanLimit = getLoanLimit();
        if (amount > loanLimit) {
            JOptionPane.showMessageDialog(this, "Loan amount exceeds maximum limit of Rs. " + 
                String.format("%,.0f", loanLimit), 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String mortgageItem = (String) mortgageCombo.getSelectedItem();
        if (mortgageItem.equals("Select Mortgage Item")) {
            JOptionPane.showMessageDialog(this, "Please select a mortgage item!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        double mortgageAmount;
        try {
            mortgageAmount = Double.parseDouble(mortgageValueField.getText());
            if (mortgageAmount <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid mortgage value!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (mortgageAmount < amount) {
            JOptionPane.showMessageDialog(this, "Mortgage value must be equal to or greater than loan amount!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String mortgageDescription = mortgageDescriptionArea.getText().trim();
        
        for (int i = 0; i < 2; i++) {
            if (guarantorName[i].getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter name for Guarantor " + (i+1) + "!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (guarantorCriminalYes[i].isSelected()) {
                JOptionPane.showMessageDialog(this, "Guarantor " + (i+1) + " cannot have criminal record!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        if (!termsAccept.isSelected()) {
            JOptionPane.showMessageDialog(this, "You must accept the terms and conditions!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int durationValue;
        try {
            durationValue = Integer.parseInt(durationField.getText());
            if (durationValue <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid duration!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String unit = (String) durationUnitCombo.getSelectedItem();
        int returnDays;
        if (unit.equals("Months")) {
            returnDays = durationValue * 30;
        } else if (unit.equals("Years")) {
            returnDays = durationValue * 365;
        } else {
            returnDays = durationValue;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();
        Date returnDateObj = new Date(currentDate.getTime() + (returnDays * 24L * 60 * 60 * 1000));
        String returnDate = sdf.format(returnDateObj);
        
        double interest = calculateInterestRate(returnDays);
        double penalty = PENALTY_PER_DAY;
        
        boolean viaBank = bankYes.isSelected();
        String transactionId = viaBank ? transactionField.getText().trim() : "";
        String paymentMethod = viaBank ? "Bank Transfer" : "Cash";
        
        if (viaBank && transactionId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Transaction ID is required for bank transfer!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        LoanTaker loanTaker = new LoanTaker(name, city, phone, address, credentials, education, age,
                                           mortgageItem, mortgageAmount, mortgageDescription, category, 
                                           incomeDetails, pastLoanDetails, living);
        
        ArrayList<Person> guarantors = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            guarantors.add(new Person(
                capitalizeWords(guarantorName[i].getText()),
                capitalizeWords(guarantorCity[i].getText()),
                guarantorPhone[i].getText(),
                capitalizeWords(guarantorAddress[i].getText()),
                guarantorCred[i].getText(),
                guarantorOccupation[i].getText(),
                0 // Age not required for guarantors
            ));
        }
        
        LoanDetails loanDetails = new LoanDetails(amount, returnDays, returnDate, "Accepted", 
                                                 viaBank, transactionId, interest, penalty, paymentMethod);
        
        StringBuilder affidavit = new StringBuilder();
        int width = 70;
        String separator = repeatString("=", width);
        
        affidavit.append(separator).append("\n");
        affidavit.append(centerText("AFFIDAVIT", width)).append("\n");
        affidavit.append(separator).append("\n");
        //affidavit.append(centerText("AFFIDAVIT", width)).append("\n");
        //affidavit.append(separator).append("\n\n");
        
        SimpleDateFormat fullDate = new SimpleDateFormat("dd-MMMM-yyyy");
        affidavit.append("Date: ").append(fullDate.format(new Date())).append("\n\n");
        
        affidavit.append("I, ").append(loanTaker.name).append(", aged ").append(age).append(" years,\n");
        affidavit.append("residing at ").append(loanTaker.address).append(",\n");
        affidavit.append(loanTaker.city).append(", holder of CNIC ").append(loanTaker.credentials).append(",\n");
        affidavit.append("do hereby solemnly affirm and declare as under:\n\n");
        
        affidavit.append(repeatString("-", width)).append("\n");
        affidavit.append("1. PERSONAL INFORMATION:\n");
        affidavit.append(repeatString("-", width)).append("\n");
        affidavit.append("   Name:            ").append(loanTaker.name).append("\n");
        affidavit.append("   Age:             ").append(age).append(" years\n");
        affidavit.append("   City:            ").append(loanTaker.city).append("\n");
        affidavit.append("   Phone:           ").append(loanTaker.phone).append("\n");
        affidavit.append("   Address:         ").append(loanTaker.address).append("\n");
        affidavit.append("   CNIC/B-Form:     ").append(loanTaker.credentials).append("\n");
        affidavit.append("   Education:       ").append(loanTaker.educational_info).append("\n");
        affidavit.append("   Category:        ").append(loanTaker.category).append("\n");
        affidavit.append("   Criminal Record: No\n\n");
        
        affidavit.append(repeatString("-", width)).append("\n");
        affidavit.append("2. LIVING SITUATION:\n");
        affidavit.append(repeatString("-", width)).append("\n");
        affidavit.append("   ").append(loanTaker.livingSituation).append("\n\n");
        
        affidavit.append(repeatString("-", width)).append("\n");
        affidavit.append("3. EMPLOYMENT/BUSINESS DETAILS:\n");
        affidavit.append(repeatString("-", width)).append("\n");
        affidavit.append("   ").append(loanTaker.incomeDetails).append("\n\n");
        
        if (!loanTaker.pastLoanHistory.equals("No past loan records")) {
            affidavit.append(repeatString("-", width)).append("\n");
            affidavit.append("4. PAST LOAN HISTORY:\n");
            affidavit.append(repeatString("-", width)).append("\n");
            affidavit.append("   ").append(loanTaker.pastLoanHistory).append("\n\n");
        }
        
        affidavit.append(repeatString("-", width)).append("\n");
        affidavit.append("5. LOAN DETAILS:\n");
        affidavit.append(repeatString("-", width)).append("\n");
        affidavit.append("   Loan Amount:     Rs. ").append(String.format("%,.2f", amount)).append("\n");
        affidavit.append("   Return Duration: ").append(durationValue).append(" ").append(unit)
                  .append(" (").append(returnDays).append(" days)\n");
        affidavit.append("   Return Date:     ").append(returnDate).append("\n");
        affidavit.append("   Interest Rate:   ").append(interest).append("% per annum\n");
        affidavit.append("   Daily Penalty:   Rs. ").append(String.format("%,.2f", penalty)).append("\n");
        affidavit.append("   Payment Method:  ").append(paymentMethod).append("\n");
        if (viaBank) {
            affidavit.append("   Transaction ID:  ").append(transactionId).append("\n");
        }
        affidavit.append("\n");
        
        affidavit.append(repeatString("-", width)).append("\n");
        affidavit.append("6. MORTGAGE DETAILS:\n");
        affidavit.append(repeatString("-", width)).append("\n");
        affidavit.append("   Mortgage Item:   ").append(mortgageItem).append("\n");
        affidavit.append("   Mortgage Value:  Rs. ").append(String.format("%,.2f", mortgageAmount)).append("\n");
        if (!mortgageDescription.isEmpty()) {
            affidavit.append("   Description:     ").append(mortgageDescription).append("\n");
        }
        affidavit.append("\n");
        
        affidavit.append(repeatString("-", width)).append("\n");
        affidavit.append("7. GUARANTORS INFORMATION:\n");
        affidavit.append(repeatString("-", width)).append("\n");
        for (int i = 0; i < guarantors.size(); i++) {
            Person g = guarantors.get(i);
            affidavit.append("   Guarantor ").append(i + 1).append(":\n");
            affidavit.append("     Name:       ").append(g.name).append("\n");
            affidavit.append("     City:       ").append(g.city).append("\n");
            affidavit.append("     Phone:      ").append(g.phone).append("\n");
            affidavit.append("     Address:    ").append(g.address).append("\n");
            affidavit.append("     CNIC:       ").append(g.credentials).append("\n");
            affidavit.append("     Occupation: ").append(g.educational_info).append("\n");
            affidavit.append("     Background: Clean (No criminal record)\n\n");
        }
        
        double dailyInterestRate = (interest / 100.0) / 365;
        double totalInterest = dailyInterestRate * returnDays * amount;
        double totalPayable = amount + totalInterest;
        
        affidavit.append(repeatString("-", width)).append("\n");
        affidavit.append("8. FINANCIAL CALCULATIONS:\n");
        affidavit.append(repeatString("-", width)).append("\n");
        affidavit.append("   Principal Amount:    Rs. ").append(String.format("%,.2f", amount)).append("\n");
        affidavit.append("   Total Interest:      Rs. ").append(String.format("%,.2f", totalInterest)).append("\n");
        affidavit.append("   TOTAL PAYABLE:       Rs. ").append(String.format("%,.2f", totalPayable)).append("\n");
        affidavit.append("   (Penalty of Rs. ").append(String.format("%,.2f", penalty))
                  .append(" per day applies after ").append(returnDate).append(")\n\n");
        
        affidavit.append(repeatString("-", width)).append("\n");
        affidavit.append("DECLARATION:\n");
        affidavit.append(repeatString("-", width)).append("\n");
        affidavit.append("I hereby declare that all information provided above is true and correct.\n");
        affidavit.append("I agree to repay the loan amount with interest as per the terms.\n");
        affidavit.append("I understand that legal action may be taken in case of default.\n\n");
        
        affidavit.append(repeatString("-", width)).append("\n");
        affidavit.append("SIGNATURES:\n");
        affidavit.append(repeatString("-", width)).append("\n");
        affidavit.append("\nBorrower Signature: _________________________\n");
        affidavit.append("Name: ").append(loanTaker.name).append("\n");
        affidavit.append("Date: ").append(fullDate.format(new Date())).append("\n\n");
        
        affidavit.append("Guarantor 1 Signature: _________________________\n");
        affidavit.append("Name: ").append(guarantors.get(0).name).append("\n");
        affidavit.append("Date: _________________________\n\n");
        
        affidavit.append("Guarantor 2 Signature: _________________________\n");
        affidavit.append("Name: ").append(guarantors.get(1).name).append("\n");
        affidavit.append("Date: _________________________\n\n");
        
        affidavit.append(separator).append("\n");
        affidavit.append("                    END OF AFFIDAVIT\n");
        affidavit.append(separator);
        
        affidavitArea.setText(affidavit.toString());
        currentPanel = TOTAL_PANELS - 1;
        cardLayout.show(mainPanel, String.valueOf(currentPanel + 1));
        
        // Show print button after generating affidavit
        printButton.setVisible(true);
        generateButton.setVisible(false);
        
        JOptionPane.showMessageDialog(this, "Affidavit generated successfully! Click 'Print Affidavit' to print.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        StringBuilder centered = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            centered.append(" ");
        }
        centered.append(text);
        return centered.toString();
    }
    
    private boolean validateAllFields() {
        if (nameField.getText().trim().isEmpty() ||
            ageField.getText().trim().isEmpty() ||
            cityField.getText().trim().isEmpty() ||
            phoneField.getText().trim().isEmpty() ||
            addressField.getText().trim().isEmpty() ||
            credentialsField.getText().trim().isEmpty()) {
            return false;
        }
        
        try {
            Double.parseDouble(loanAmountField.getText());
        } catch (NumberFormatException e) {
            return false;
        }
        
        if (mortgageCombo.getSelectedIndex() == 0 || 
            mortgageValueField.getText().trim().isEmpty()) {
            return false;
        }
        
        for (int i = 0; i < 2; i++) {
            if (guarantorName[i].getText().trim().isEmpty() ||
                guarantorCred[i].getText().trim().isEmpty()) {
                return false;
            }
        }
        
        try {
            Integer.parseInt(durationField.getText());
        } catch (NumberFormatException e) {
            return false;
        }
        
        return true;
    }
    
    private double getLoanLimit() {
        String category = (String) categoryCombo.getSelectedItem();
        if (category.equals("Student") || category.equals("Job")) {
            return 2000000;
        } else if (category.equals("Business")) {
            return 5000000;
        }
        return 0;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoanManagementSystem();
        });
    }
}
