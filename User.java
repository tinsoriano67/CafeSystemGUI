package CafeSystem;


public class User {
    private String id;
    private int boothType;
    private String user;
    private int hoursEntered;

    private int totalCost;
    
    static int counter = 1;
    
    User(int boothType, String user){
        this.boothType = boothType;
        this.user = user;
        this.id = String.format("%05d", counter++);
    }
    
    public String getId(){
        return "ID-" + id;
    }
    
    public String getUser(){
        return user;
    }
    
    public void addSession(int hoursEntered) {
        this.hoursEntered += hoursEntered;
    }
    
    public int calculateCost() {
        return switch(boothType){
            case 1 -> 300 * hoursEntered; 
            case 2 -> 200 * hoursEntered;
            case 3 -> 100 * hoursEntered;
            default -> 0 * hoursEntered;
        };
    }
    
    public String getBoothType(){
        return switch(boothType) {
            case 1 -> "VIP";
            case 2 -> "High-End Gaming PC";
            case 3 -> "Standard Browsing";        
            default -> "Invalid Booth";
        };
    }
    
    public void setBoothType(int boothType){
        this.boothType = boothType;
    }
    
    public void addCost(int cost) {
        totalCost += cost;
    }
    
    public int getTotalCost() {
        return totalCost;
    }
    
    public int getHoursEntered() {
        return hoursEntered;
    }
    
    
}