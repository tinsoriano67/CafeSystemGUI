package CafeSystem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OrderListPanel extends JPanel{
    private User user; 
    private Order order;
    private Menu menu;
    private Receipt receipt;
    private JFrame parentFrame;
    
    private JTextField itemCounterDisplay;
    private JButton cancelOrderBtn;
    private JButton confirmOrderBtn;
    private JPanel mainWhiteSheet; 
    private JLabel totalCostLabel;
    
    private int runningTotalFoodCost = 0;
    private int runningTotalItemsCount = 0;
    
    public OrderListPanel(User user, Order order, Menu menu, Receipt receipt, int totalItemsOrderedCount){
        this.user = user;
        this.order = order;
        this.menu = menu;
        this.receipt = receipt; 
        
        setLayout(new BorderLayout());
        setBackground(new Color(255, 228, 225));
        
        JLabel headerTitle = new JLabel("Customer Orders:", SwingConstants.CENTER);
        headerTitle.setFont(new Font("Arial", Font.BOLD, 22));
        headerTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(headerTitle, BorderLayout.NORTH);
        
        mainWhiteSheet = new JPanel();
        mainWhiteSheet.setLayout(new BoxLayout(mainWhiteSheet, BoxLayout.Y_AXIS));
        mainWhiteSheet.setBackground(Color.WHITE);
        mainWhiteSheet.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
       refreshOrderDisplayList();

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(getBackground());
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        centerWrapper.add(mainWhiteSheet, BorderLayout.CENTER);
        add(centerWrapper, BorderLayout.CENTER);

        JPanel bottomPanel = createBottomControlPanel();
        add(bottomPanel, BorderLayout.SOUTH);
    }
    private void refreshOrderDisplayList() {
        mainWhiteSheet.removeAll();
        runningTotalFoodCost = 0;
        runningTotalItemsCount = 0;

        ArrayList<String> lines = receipt.getReceiptLines();
        boolean hasFood = false;

        for (String lineText : lines) {
            if (lineText.contains("SYSTEM NOTE") || lineText.contains("Hour(s)") || lineText.contains("VIP")) {
                continue; 
            }
            hasFood = true;
            addDynamicOrderRow(mainWhiteSheet, lineText);
        }

        if (!hasFood) {
            JLabel emptyLabel = new JLabel("Your food cart is currently empty.");
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            emptyLabel.setForeground(Color.GRAY);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainWhiteSheet.add(emptyLabel);
        }

        mainWhiteSheet.add(Box.createVerticalGlue());

        JSeparator separator = new JSeparator();
        mainWhiteSheet.add(separator);
        mainWhiteSheet.add(Box.createVerticalStrut(10));

        totalCostLabel = new JLabel("Total: ₱" + runningTotalFoodCost);
        totalCostLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalCostLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainWhiteSheet.add(totalCostLabel);

        if (itemCounterDisplay != null) {
            itemCounterDisplay.setText(runningTotalItemsCount + " items");
        }

        mainWhiteSheet.revalidate();
        mainWhiteSheet.repaint();
    }
  private void addDynamicOrderRow(JPanel container, String fullLineText) {
        String itemName = fullLineText;
        int quantity = 1;
        int unitPrice = 0;
        boolean formatValid = false;

        try {
        if (fullLineText.contains("x ") && fullLineText.contains(" - ₱")) {
            String[] firstSplit = fullLineText.split("x ", 2);
            quantity = Integer.parseInt(firstSplit[0].trim());
            
            String[] secondSplit = firstSplit[1].split(" - ₱", 2);
            itemName = secondSplit[0].trim();
            int totalLinePrice = Integer.parseInt(secondSplit[1].trim());
            unitPrice = totalLinePrice / quantity;
            formatValid = true;
        }
    } catch (Exception ex) {
        formatValid = false; 
    }
        if (!formatValid) {
        itemName = fullLineText;
        quantity = 1;
        unitPrice = 0;
        try {
            if (fullLineText.contains("₱")) {
                String[] parts = fullLineText.split("₱");
                unitPrice = Integer.parseInt(parts[1].trim());
            }
        } catch(Exception e) {
            unitPrice = 0;
        }
    }
        runningTotalFoodCost += (unitPrice * quantity);
        runningTotalItemsCount += quantity;

        final String finalItemName = itemName;
        final int finalUnitPrice = unitPrice;
        final int currentQty = quantity;
        final boolean isStandardFormat = formatValid; 

        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        
       JLabel itemDetails;
        if (isStandardFormat) {
        itemDetails = new JLabel("• " + finalItemName + " - ₱" + (finalUnitPrice * currentQty));
        } else {
        itemDetails = new JLabel("• " + fullLineText);
    }
    itemDetails.setFont(new Font("Arial", Font.PLAIN, 16));
    row.add(itemDetails, BorderLayout.WEST);
    
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        controls.setBackground(Color.WHITE);

        JLabel minusIcon = new JLabel("➖");
        minusIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        JLabel qtyLabel = new JLabel(String.valueOf(currentQty));
        qtyLabel.setFont(new Font("Arial", Font.BOLD, 15));

        JLabel plusIcon = new JLabel("➕");
        plusIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        minusIcon.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            if (finalUnitPrice > 0) {
                user.addCost(-finalUnitPrice);
            }
            
            ArrayList<String> receiptLines = receipt.getReceiptLines();
            for (int i = 0; i < receiptLines.size(); i++) {
                if (receiptLines.get(i).equals(fullLineText)) {
                    if (currentQty - 1 <= 0) {
                        receiptLines.remove(i);
                    } else {
                        if (isStandardFormat) {
                            int newQty = currentQty - 1;
                            int newPrice = finalUnitPrice * newQty;
                            receiptLines.set(i, newQty + "x " + finalItemName + " - ₱" + newPrice);
                        } else {
                            receiptLines.remove(i);
                        }
                    }
                    break;
                }
            }
            refreshOrderDisplayList(); 
        }
    });
        
        plusIcon.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            if (isStandardFormat) {
                user.addCost(finalUnitPrice);
                ArrayList<String> receiptLines = receipt.getReceiptLines();
                for (int i = 0; i < receiptLines.size(); i++) {
                    if (receiptLines.get(i).equals(fullLineText)) {
                        int newQty = currentQty + 1;
                        int newPrice = finalUnitPrice * newQty;
                        receiptLines.set(i, newQty + "x " + finalItemName + " - ₱" + newPrice);
                        break;
                    }
                }
                refreshOrderDisplayList();
            } else {
                JOptionPane.showMessageDialog(null, "Cannot dynamically multiply this unstructured list record line item.");
            }
        }
    });
    controls.add(minusIcon);
    controls.add(qtyLabel);
    controls.add(plusIcon);
    row.add(controls, BorderLayout.EAST);
    
    container.add(row);
    container.add(Box.createVerticalStrut(8));
    
}
    
    private JPanel createBottomControlPanel(){
        JPanel bottomPanel = new JPanel(); 
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(getBackground());

        JPanel inputRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        inputRow.setBackground(getBackground());

        itemCounterDisplay = new JTextField(runningTotalItemsCount + " items", 12); 
        itemCounterDisplay.setEditable(false); 
        itemCounterDisplay.setBackground(Color.WHITE); 
        itemCounterDisplay.setFont(new Font("Arial", Font.PLAIN, 14));
        itemCounterDisplay.setBorder(BorderFactory.createTitledBorder("ITEM_NUMBER in list"));
        
        JLabel backToCatalogLink = new JLabel("<HTML><U>Back to Menu</U></HTML>");
        backToCatalogLink.setFont(new Font("Arial", Font.BOLD, 13));
        backToCatalogLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backToCatalogLink.setForeground(new Color(219, 112, 147));
        
        backToCatalogLink.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                Container parent = getParent();
                while (parent != null && !(parent instanceof CafeDashboard)) {
                    parent = parent.getParent();
                }
                if (parent instanceof CafeDashboard) {
                    ((CafeDashboard) parent).showFoodPanel(); 
                }
            }
        });

        inputRow.add(itemCounterDisplay);
        inputRow.add(backToCatalogLink);

        JPanel buttonRow = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonRow.setPreferredSize(new Dimension(0, 55));
        
        cancelOrderBtn = new JButton("CANCEL ORDER");
        cancelOrderBtn.setBackground(new Color(240, 128, 128));
        cancelOrderBtn.setFont(new Font("Arial", Font.BOLD, 16));
        cancelOrderBtn.addActionListener(e -> {
            user.addCost(-user.getTotalCost()); 
            JOptionPane.showMessageDialog(this, "Order canceled.");
            backToCatalogLink.getMouseListeners()[0].mouseClicked(null);
        });

        confirmOrderBtn = new JButton("CONFIRM ORDER");
        confirmOrderBtn.setBackground(new Color(193, 242, 193)); 
        confirmOrderBtn.setFont(new Font("Arial", Font.BOLD, 16));
        confirmOrderBtn.addActionListener(e -> {
            if (runningTotalItemsCount == 0 || runningTotalFoodCost == 0) {
                JOptionPane.showMessageDialog(this, 
                    "You have no orders yet! Please add food or drinks first.", 
                    "Empty Cart", 
                    JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Order Confirmed! Your transaction receipt is processing.");
            }
        });

        buttonRow.add(cancelOrderBtn);
        buttonRow.add(confirmOrderBtn);

        bottomPanel.add(inputRow);
        bottomPanel.add(buttonRow);

        return bottomPanel;
    }
}
