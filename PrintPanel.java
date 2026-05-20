package CafeSystem;

import javax.swing.*;
import java.awt.*;

public class PrintPanel extends JPanel{
    private User user; 
    private Order order;
    private Menu menu; 
    private Receipt receipt; 
    
    public PrintPanel(User user, Order order, Menu menu, Receipt receipt){
        this.user = user; 
        this.order = order; 
        this.menu = menu; 
        this.receipt = receipt; 
        
        setLayout(new BorderLayout());
        setBackground(new Color(255, 228, 225));
        
        JLabel headerTitle = new JLabel("Print/Photocopy Service", SwingConstants.CENTER);
        headerTitle.setFont(new Font("Arial", Font.BOLD, 22));
        headerTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(headerTitle, BorderLayout.NORTH);
        
        JPanel innerSheet = new JPanel();
        innerSheet.setLayout(new GridBagLayout());
        innerSheet.setBackground(new Color(255, 240, 245));
        innerSheet.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 182, 193), 2),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0; 
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 25, 0);
        
        JLabel instructionLabel = new JLabel("Send files here:");
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 18));
        innerSheet.add(instructionLabel, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(6, 0, 6, 0); 

        gbc.gridy = 1;
        JLabel emailLabel = new JLabel("Email: krichandreicafe@gmail.com");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        innerSheet.add(emailLabel, gbc);

        gbc.gridy = 2;
        JLabel fbLabel = new JLabel("FB: Krich Andrei Internet Cafe");
        fbLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        innerSheet.add(fbLabel, gbc);

        gbc.gridy = 3;
        JLabel bluetoothLabel = new JLabel("Bluetooth: Krichblth1");
        bluetoothLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        innerSheet.add(bluetoothLabel, gbc);

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(getBackground());
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(10, 40, 40, 40));
        centerWrapper.add(innerSheet, BorderLayout.CENTER);
        add(centerWrapper, BorderLayout.CENTER);
    }
}
