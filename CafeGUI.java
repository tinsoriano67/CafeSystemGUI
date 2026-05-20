package CafeSystem;


import javax.swing.*;
import java.awt.*;
import java.awt.Color;

public class CafeGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private User user;
    private CafeDashboard CafeDashboard;
    
    public CafeGUI() {
        setTitle("Internet Cafe System");
        setSize(500, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        buildWelcomePanel();
        buildLoginPanel();

        add(mainPanel);
        cardLayout.show(mainPanel, "Welcome");
    }
    
    private void buildWelcomePanel() {
    JPanel welcomePanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    welcomePanel.setBackground(new Color(255, 182,193));

    
    java.net.URL imgURL = getClass().getResource("logo.png"); 
    ImageIcon logoIcon;

    if (imgURL != null) {
        logoIcon = new ImageIcon(imgURL);
    } else {
        // Fallback: Ito ang luma mong code kung sakaling hindi mahanap sa package resources
        logoIcon = new ImageIcon("logo.png");
    }

    Image img = logoIcon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
    logoIcon = new ImageIcon(img);
    JLabel logoLabel = new JLabel(logoIcon);

    gbc.gridx = 0; 
    gbc.gridy = 0; 
    gbc.anchor = GridBagConstraints.EAST; 
    gbc.insets = new Insets(30, 40, 10, 15); 
    gbc.weighty = 0.3;
    gbc.weightx = 0.5;
    welcomePanel.add(logoLabel, gbc);
    
    
    JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'>Welcome to Krich Andrei <br>"
    + "Internet Cafe!</div></html>");
    welcomeLabel.setFont(new Font("Algerian", Font.BOLD, 16));

    gbc.gridx = 1; 
    gbc.gridy = 0; 
    gbc.anchor = GridBagConstraints.WEST; 
    gbc.insets = new Insets(30, 15, 10, 40);
    gbc.weightx = 0.5;
    welcomePanel.add(welcomeLabel, gbc);

    
    JButton continueBtn = new JButton("START / CONTINUE");
    continueBtn.setFont(new Font("Arial", Font.PLAIN, 16));
    

    gbc.gridx = 0;
    gbc.gridy = 2; 
    gbc.gridwidth = 2; 
    gbc.anchor = GridBagConstraints.NORTH;
    gbc.insets = new Insets(10, 0, 40, 0);
    gbc.weighty = 0.7;
    gbc.weightx = 0;
    welcomePanel.add(continueBtn, gbc);

    continueBtn.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

    mainPanel.add(welcomePanel, "Welcome");
}
    
    private void buildLoginPanel() {
      
    JPanel loginPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); 
    gbc.fill = GridBagConstraints.HORIZONTAL; 
    loginPanel.setBackground(new Color(255, 182, 193));
    
    JLabel nameRow = new JLabel("Enter Your Name:");
    nameRow.setFont(new Font("Arial", Font.BOLD, 18));
    
    JTextField userField = new JTextField();
    userField.setFont(new Font("Arial", Font.PLAIN, 16));
    userField.setPreferredSize(new Dimension(250, 35));
    
    JLabel boothRow = new JLabel("Choose Your Booth:");
    boothRow.setFont(new Font("Arial", Font.BOLD, 15));
    
    JButton vipbtn = new JButton("VIP");
    JButton gamingbtn = new JButton("High-end Gaming PC");
    JButton stdbtn = new JButton("Standard Browsing");

    
    gbc.gridx = 0; 
    gbc.gridy = 0; 
    loginPanel.add(nameRow, gbc);

    
    gbc.gridx = 1; // column 1
    gbc.gridy = 0;
    loginPanel.add(userField, gbc);

    
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 2; 
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.NONE;
    loginPanel.add(boothRow, gbc);
    
    gbc.gridwidth = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    vipbtn.setFont(new Font("Arial", Font.BOLD, 18));
    vipbtn.setPreferredSize(new Dimension(250, 50));
    gbc.insets = new Insets(10, 10, 2, 10);
    loginPanel.add(vipbtn, gbc);

    
    gbc.gridy = 3;
    gamingbtn.setFont(new Font("Arial", Font.BOLD, 18));
    gamingbtn.setPreferredSize(new Dimension(250, 50));
    gbc.insets = new Insets(2, 10, 2, 10);
    loginPanel.add(gamingbtn, gbc);

    
    gbc.gridy = 4;
    stdbtn.setFont(new Font("Arial", Font.BOLD, 18));
    stdbtn.setPreferredSize(new Dimension(250, 50));
    gbc.insets = new Insets(2, 10, 10, 10);
    loginPanel.add(stdbtn, gbc);
    
    vipbtn.addActionListener(e -> {
        String username = userField.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name first!");
            return;
        }
        user = new User(1, username); 
        JOptionPane.showMessageDialog(this, "<html>     Welcome " + user.getUser() + "! Please proceed to your booth!<br>"
        + "<div style='text-align:center;'>"
        + user.getBoothType() + user.getId()
        + "</div></html");
        new CafeDashboard(user).setVisible(true);
        dispose();
    });

    gamingbtn.addActionListener(e -> {
        String username = userField.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name first!");
            return;
        }
        user = new User(2, username); 
        JOptionPane.showMessageDialog(this, "<html>     Welcome " + user.getUser() + "! Please proceed to your booth!<br>"
        + "<div style='text-align:center;'>"
        + user.getBoothType() + user.getId()
        + "</div></html");
        new CafeDashboard(user).setVisible(true);
        dispose();
    });

    stdbtn.addActionListener(e -> {
        String username = userField.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name first!");
            return;
        }
        user = new User(3, username); 
        JOptionPane.showMessageDialog(this, "<html>     Welcome " + user.getUser() + "! Please proceed to your booth!<br>"
        + "<div style='text-align:center;'>"
        + user.getBoothType() + user.getId()
        + "</div></html");  
        new CafeDashboard(user).setVisible(true);
        dispose();
    });

    mainPanel.add(loginPanel, "Login");
}
    

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CafeGUI().setVisible(true));
    }
}