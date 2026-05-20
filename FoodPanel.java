package CafeSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FoodPanel extends JPanel{
    private User user; 
    private Order order;
    private Menu menu;
    private Receipt receipt;
    
    private JTextField itemCounterDisplay;
    private JTextField amountField;
    private JButton addToOrderBtn;
    private JButton cancelOrderBtn;
    private JLabel viewListLink;
    
    private Item selectedItem = null;
    private int totalItemsOrderedCount = 0;
    
    private ArrayList<JPanel> allCardPanels = new ArrayList<>();
    
    private int currentSessionFoodCost = 0; 
    
    public FoodPanel(User user, Order order, Menu menu, Receipt receipt){
        this.user = user;
        this.order = order;
        this.menu = menu;
        this.receipt = receipt; 
        
        setLayout(new BorderLayout());
        setBackground(new Color(255, 228, 225));
        
        ArrayList<Item> allItems = menu.getItems();
        
        JPanel mainScrollableContent = new JPanel();
        mainScrollableContent.setLayout(new BoxLayout(mainScrollableContent, BoxLayout.Y_AXIS));
        mainScrollableContent.setBackground(getBackground());
        mainScrollableContent.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        ArrayList<Item> foodList = new ArrayList<>();
        if(allItems.size() > 0) foodList.add(allItems.get(0));
        if(allItems.size() > 1) foodList.add(allItems.get(1));
        String[] foodImages = {"PC.jpg", "hatdog.jpg"};
        
        JPanel foodGrid = createItemGrid("FOOD", foodList, foodImages);
        mainScrollableContent.add(foodGrid);
        mainScrollableContent.add(Box.createVerticalStrut(20)); 
        
        ArrayList<Item> drinkList = new ArrayList<>();
        if(allItems.size() >2) drinkList.add(allItems.get(2));
        if(allItems.size() >3) drinkList.add(allItems.get(3));
        if(allItems.size() >4) drinkList.add(allItems.get(4));
        String[] drinkImages = {"coke.jpg", "md.jpg", "wotah.jpg"};
        
        JPanel drinksGrid = createItemGrid("DRINKS", drinkList, drinkImages);
        mainScrollableContent.add(drinksGrid);
        
        JScrollPane scrollPane = new JScrollPane(mainScrollableContent);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(getBackground());
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = createBottomControlPanel();
        add(bottomPanel, BorderLayout.SOUTH);
        
    }
    private JPanel createItemGrid(String sectionTitle, ArrayList<Item> itemList, String[] imagePaths){
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(new Color(255, 228, 225));
        
        JLabel titleLabel = new JLabel(sectionTitle);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        container.add(titleLabel);
        container.add(Box.createVerticalStrut(10));
        
        JPanel grid = new JPanel(new GridLayout(1, 0, 15, 0));
        grid.setBackground(new Color(255, 228, 225));
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);

            JPanel itemCard = new JPanel();
            itemCard.setLayout(new BoxLayout(itemCard, BoxLayout.Y_AXIS));
            itemCard.setBackground(Color.WHITE);
            itemCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            
            allCardPanels.add(itemCard); 

            ImageIcon icon = new ImageIcon(imagePaths[i]);
            Image scaled = icon.getImage().getScaledInstance(140, 110, Image.SCALE_SMOOTH); 
            JLabel imgLabel = new JLabel(new ImageIcon(scaled));
            imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            itemCard.add(imgLabel);
            itemCard.add(Box.createVerticalStrut(8));

            JLabel nameLabel = new JLabel(item.getName());
            nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            itemCard.add(nameLabel);

            JLabel priceLabel = new JLabel("₱" + item.getPrice());
            priceLabel.setFont(new Font("Arial", Font.PLAIN, 13));
            priceLabel.setForeground(Color.DARK_GRAY);
            priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            itemCard.add(priceLabel);

            itemCard.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for(JPanel panel : allCardPanels) {
                        panel.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                            BorderFactory.createEmptyBorder(10, 10, 10, 10)
                        ));
                        panel.setBackground(Color.WHITE);
                    }
                    
                    itemCard.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(30, 144, 255), 3), 
                        BorderFactory.createEmptyBorder(8, 8, 8, 8)
                    ));
                    itemCard.setBackground(new Color(230, 242, 255)); 
                    
                    selectedItem = item; 
                }
            });

            grid.add(itemCard);
        }

        container.add(grid);
        return container;
    }
    private JPanel createBottomControlPanel(){
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBackground(new Color(255, 228, 225));
        
        JPanel inputRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        inputRow.setBackground(new Color(255, 228, 225));
        
        itemCounterDisplay = new JTextField("0 items", 12);
        itemCounterDisplay.setEditable(false);
        itemCounterDisplay.setBackground(Color.WHITE);
        itemCounterDisplay.setFont(new Font("Arial", Font.PLAIN, 14));
        itemCounterDisplay.setBorder(BorderFactory.createTitledBorder("Items in Cart"));
        
        amountField = new JTextField(8);
        amountField.setFont(new Font("Arial", Font.PLAIN, 14));
        amountField.setBorder(BorderFactory.createTitledBorder("AMOUNT"));

        viewListLink = new JLabel("<HTML><U>View order list</U></HTML>");
        viewListLink.setFont(new Font("Arial", Font.BOLD, 13));
        viewListLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        viewListLink.setForeground(new Color(219, 112, 147));
        
        viewListLink.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        Container parent = getParent();
        while (parent != null && !(parent instanceof CafeDashboard)) {
            parent = parent.getParent();
        }
        
        if (parent instanceof CafeDashboard) {
            OrderListPanel listPanel = new OrderListPanel(user, order, menu, receipt, totalItemsOrderedCount);
            ((CafeDashboard) parent).setContent(listPanel);
        }
    }
});

        inputRow.add(itemCounterDisplay);
        inputRow.add(amountField);
        inputRow.add(viewListLink);
        
        JPanel buttonRow = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonRow.setPreferredSize(new Dimension(0, 55));

        cancelOrderBtn = new JButton("CANCEL ORDER");
        cancelOrderBtn.setBackground(new Color(240, 128, 128));
        cancelOrderBtn.setFont(new Font("Arial", Font.BOLD, 16));
        cancelOrderBtn.addActionListener(e -> {
            if (currentSessionFoodCost > 0) {
                user.addCost(-currentSessionFoodCost);
            }
            
            receipt.getReceiptLines().clear();
            
            selectedItem = null;
            totalItemsOrderedCount = 0; 
            currentSessionFoodCost = 0;
            
            itemCounterDisplay.setText("0 items");
            amountField.setText("");
            
            for(JPanel panel : allCardPanels) {
                panel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                panel.setBackground(Color.WHITE);
            }
            JOptionPane.showMessageDialog(this, "Order cancelled! Cart has been reset.");
        });

  
        addToOrderBtn = new JButton("ADD TO ORDER");
        addToOrderBtn.setBackground(new Color(193, 242, 193));
        addToOrderBtn.setFont(new Font("Arial", Font.BOLD, 16));
        addToOrderBtn.addActionListener(e -> {
            if (selectedItem == null) {
                JOptionPane.showMessageDialog(this, "Please select a food or a drink", "Selection Missing", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                int quantity = Integer.parseInt(amountField.getText().trim());
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Amount must be greater than 0.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

        
                int totalTransactionCost = selectedItem.getPrice() * quantity;
                user.addCost(totalTransactionCost); 
                currentSessionFoodCost += totalTransactionCost;
                receipt.addLine(quantity + "x " + selectedItem.getName() + " - ₱" + totalTransactionCost);

                
                totalItemsOrderedCount += quantity;
                itemCounterDisplay.setText(totalItemsOrderedCount + " items");

                JOptionPane.showMessageDialog(this, "Success! You added " + quantity + " " + selectedItem.getName() + " in your cart.");
                amountField.setText("");
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Error! Input correct amount.", "Type Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonRow.add(cancelOrderBtn);
        buttonRow.add(addToOrderBtn);

        bottomPanel.add(inputRow);
        bottomPanel.add(buttonRow);
        return bottomPanel;
        
    }
}
        