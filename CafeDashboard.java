package CafeSystem;


import javax.swing.*;
import java.awt.*;
import java.awt.Color;


public class CafeDashboard extends JFrame {
    private JPanel contentPanel;   
    private User user;
    private Menu menu;
    private Order order;
    private Receipt receipt;

    public CafeDashboard(User user) {
        this.user = user;
        this.menu = new Menu();
        this.order = new Order();
        this.receipt = new Receipt();

        setTitle("Cafe System Dashboard");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        
        JLabel header = new JLabel("Internet Cafe System", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        add(header, BorderLayout.NORTH);

        
        JPanel sidebar = new JPanel(new GridLayout(0, 1));
        JButton timeBtn = new JButton("Time/Package");
        JButton foodBtn = new JButton("Food/Drinks");
        JButton printBtn = new JButton("Print/Photocopy");
        JButton balanceBtn = new JButton("Balance Tracker");
        

        sidebar.add(timeBtn);
        sidebar.add(foodBtn);
        sidebar.add(printBtn);
        sidebar.add(balanceBtn);
        

        add(sidebar, BorderLayout.WEST);

        
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(new JLabel("Select a service from the left.", SwingConstants.CENTER), BorderLayout.CENTER);
        add(contentPanel, BorderLayout.CENTER);

        
        timeBtn.addActionListener(e -> showTimePanel());
        foodBtn.addActionListener(e -> showFoodPanel());
        printBtn.addActionListener(e -> showPrintPanel());
        balanceBtn.addActionListener(e -> showBalanceTrackerPanel());
    }

    private void showTimePanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(new Color(255, 228, 225)); 

    Font labelFont = new Font("Arial", Font.BOLD, 20);   
    Font fieldFont = new Font("Arial", Font.PLAIN, 16);  
    Font buttonFont = new Font("Arial", Font.BOLD, 20);  

    
    JPanel boothRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
    boothRow.setBackground(panel.getBackground());
    JLabel boothLabel = new JLabel("SELECTED BOOTH: " + user.getBoothType());
    boothLabel.setFont(labelFont);
    boothRow.add(boothLabel);

    
    JPanel rateRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
    rateRow.setBackground(panel.getBackground());
    int rate = switch (user.getBoothType()) {
        case "VIP" -> 300;
        case "High-End Gaming PC" -> 200;
        case "Standard Browsing" -> 100;
        default -> 0;
    };
    JLabel rateLabel = new JLabel("Rate/Hr: ₱" + rate);
    rateLabel.setFont(labelFont);
    rateRow.add(rateLabel);

    
    JPanel timeRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
    timeRow.setBackground(panel.getBackground());
    JLabel timeLabel = new JLabel("Remaining Time: " + user.getHoursEntered() + ":00hrs");
    timeLabel.setFont(labelFont);
    timeRow.add(timeLabel);

    
    JPanel hoursRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
    hoursRow.setBackground(panel.getBackground());
    JLabel hoursLabel = new JLabel("Enter amount of hours:");
    hoursLabel.setFont(labelFont);
    JTextField hoursField = new JTextField(5);
    hoursField.setMaximumSize(new Dimension(150, 50));
    hoursField.setFont(fieldFont);
    hoursRow.add(hoursLabel);
    hoursRow.add(hoursField);

    
    JPanel costRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
    costRow.setBackground(panel.getBackground());
    JLabel costLabel = new JLabel("Cost: ₱" + user.calculateCost());
    costLabel.setFont(labelFont);
    costRow.add(costLabel);

    
    JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonRow.setBackground(panel.getBackground());
    JButton confirmBtn = new JButton("Confirm");
    confirmBtn.setFont(buttonFont);
    buttonRow.add(confirmBtn);

    
    panel.add(Box.createVerticalStrut(10));
    panel.add(boothRow);
    panel.add(rateRow);
    panel.add(timeRow);
    panel.add(hoursRow);
    panel.add(costRow);
    panel.add(Box.createVerticalStrut(10));
    panel.add(buttonRow);

    
    confirmBtn.addActionListener(e -> {
        try {
            int hours = Integer.parseInt(hoursField.getText().trim());
            user.addSession(hours);
            int cost = user.calculateCost();
            user.addCost(cost);

            timeLabel.setText("Remaining Time: " + user.getHoursEntered() + ":00hrs");
            costLabel.setText("Cost: ₱" + cost);

            receipt.addLine(hours + " Hour(s) " + user.getBoothType() + " - ₱" + cost);
            JOptionPane.showMessageDialog(this, "Added " + hours + " hours. Cost: ₱" + cost);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of hours.");
        }
    });

    setContent(panel);
}
    public void showFoodPanel(){
    FoodPanel foodPanel = new FoodPanel(this.user, this.order, this.menu, this.receipt);
    setContent(foodPanel);
}

    public void setContent(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        User user = new User(1, "DemoUser");
        SwingUtilities.invokeLater(() -> new CafeDashboard(user).setVisible(true));
    }
    
    public void showPrintPanel() {
    PrintPanel printPanel = new PrintPanel(this.user, this.order, this.menu, this.receipt);
    setContent(printPanel);
    }
    
    public void showBalanceTrackerPanel() {
    BalanceTrackerPanel balancePanel = new BalanceTrackerPanel(this.user, this.order, this.menu, this.receipt);
    setContent(balancePanel);
    }
}