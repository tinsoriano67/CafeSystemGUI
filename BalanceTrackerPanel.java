package CafeSystem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList; 

public class BalanceTrackerPanel extends JPanel 
{
   private User user;
    private Order order;
    private Menu menu;
    private Receipt receipt;

        public BalanceTrackerPanel(User user, Order order, Menu menu, Receipt receipt){
            this.user = user;
            this.order = order;
            this.menu = menu; 
            this.receipt = receipt; 
            
            setLayout(new BorderLayout());
            setBackground(new Color(255, 228, 225));
            
            JLabel headerTitle = new JLabel("Balance Tracker", SwingConstants.CENTER);
            headerTitle.setFont(new Font("Arial", Font.BOLD, 22));
            headerTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
            add(headerTitle, BorderLayout.NORTH);
            
            JPanel receiptPaper = new JPanel();
            receiptPaper.setLayout(new GridBagLayout());
            receiptPaper.setBackground(new Color(255, 250, 240)); 
            receiptPaper.setBorder(BorderFactory.createEmptyBorder(30, 45, 30, 45));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1.0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = GridBagConstraints.CENTER; 
            gbc.insets = new Insets(0, 0, 15, 0);
            
            JLabel receiptTitle = new JLabel("========= RECEIPT =========", SwingConstants.CENTER);
            receiptTitle.setFont(new Font("Monospaced", Font.BOLD, 18));
            receiptPaper.add(receiptTitle, gbc);
            
            gbc.gridy++;
            gbc.insets = new Insets(4, 0, 4, 0);
            JLabel nameLabel = new JLabel("Customer: " + user.getUser(), SwingConstants.CENTER);
            nameLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
            receiptPaper.add(nameLabel, gbc);

            gbc.gridy++;
            JLabel boothLabel = new JLabel("Booth: " + user.getBoothType(), SwingConstants.CENTER);
            boothLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
            receiptPaper.add(boothLabel, gbc);
        
            gbc.gridy++;
            gbc.insets = new Insets(15, 0, 15, 0);
            JSeparator sep1 = new JSeparator();
            receiptPaper.add(sep1, gbc);

            gbc.insets = new Insets(6, 0, 6, 0);
            ArrayList<String> allLines = receipt.getReceiptLines();
        
        if (allLines.isEmpty()) {
            gbc.gridy++;
            JLabel emptyLabel = new JLabel("No transactions recorded.", SwingConstants.CENTER);
            emptyLabel.setFont(new Font("Monospaced", Font.PLAIN, 15));
            receiptPaper.add(emptyLabel, gbc);
        } else {
            for (String line : allLines) {
                if (line.contains("SYSTEM NOTE")) continue;
                gbc.gridy++;
                JLabel itemLabel = new JLabel("• " + line, SwingConstants.CENTER);
                itemLabel.setFont(new Font("Monospaced", Font.PLAIN, 15));
                receiptPaper.add(itemLabel, gbc);
            }
        }

        gbc.gridy++;
        gbc.insets = new Insets(15, 0, 15, 0);
        JSeparator sep2 = new JSeparator();
        receiptPaper.add(sep2, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 10, 0);
        String formattedTotal = String.format("%.2f", (double)user.getTotalCost());
        JLabel totalLabel = new JLabel("TOTAL: ₱" + formattedTotal, SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        receiptPaper.add(totalLabel, gbc);

        JPanel scrollContentWrapper = new JPanel(new BorderLayout());
        scrollContentWrapper.setBackground(new Color(255, 250, 240));
        scrollContentWrapper.add(receiptPaper, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(scrollContentWrapper);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 182, 193), 2));
        scrollPane.setBackground(getBackground());

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(getBackground());
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(0, 50, 20, 50));
        centerWrapper.add(scrollPane, BorderLayout.CENTER);
        add(centerWrapper, BorderLayout.CENTER);

        JButton finishBtn = new JButton("DONE / LOGOUT");
        finishBtn.setBackground(new Color(193, 242, 193));
        finishBtn.setFont(new Font("Arial", Font.BOLD, 15));
        finishBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Thank you for visiting! System will now reset.");
            System.exit(0); 
        });
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(getBackground());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        bottomPanel.add(finishBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addReceiptLine(JPanel container, String text, boolean isBold) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Monospaced", isBold ? Font.BOLD : Font.PLAIN, 15));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        container.add(label);
        container.add(Box.createVerticalStrut(5));
    }
}
        
