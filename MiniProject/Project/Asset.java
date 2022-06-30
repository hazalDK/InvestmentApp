package Project;

public abstract class Asset{

    private int balance;
    private double value;
    private String name;
    private String type;

    //Constructor
    public Asset(String name, double assetValue){
        this.name = name;
        this.balance = 0;
        this.value = assetValue;

    }

    //String
    public Asset(String name, String type){
        this.name = name;
        this.type = assetType(type);
    }

    //Constructor
    public Asset(int balance){
        this.balance = balance;
    }

    //Getter for the asset Unit
    public String assetUnit(){
        return "£";
    }

    //abstract method that assigns the asset type
    public abstract String assetType(String type);
    
    //Sets the orginal value of the asset to 1(is overriden in subclasses)
    public void setValue(){
        value = 1;
    }

    //Sets the currency to the string returned in assetUnit(overriden)
    public String setUnit(){
        String currency = assetUnit();
        return currency;
    }

    //getter to retrieve the value of the asset
    public double getValue(){
        return this.value;
    }

    //getter method to retreive the type of the asset
    public String getType(){
        type = assetType(type);
        return this.type;
    }

    //getter method to retreive the name of the asset
    public String getName(){
        return this.name;
    }

    //getter method to retreive the balance of the asset
    public int getBalance(){
        return this.balance;
    }    

    //Sets the balance as the amount given
    public void setBalance(int amount){
        this.balance = amount;
    }

    //Makes Jlist have the name for the asset not a random number.
    @Override
    public String toString() {
        return name;
    }

    //depsits the amount given into the asset
    public void deposit(int amount){
        this.balance += amount;
    }
    
    //withdraws the amount given from the asset and has an error if there isnt enough balance
    public void withdraw(int amount){
	    if (amount > this.balance){
            System.out.println("Error. The amount to be withdrawn exceeds this assets's balance.");
	    } 
        else{
            this.balance -= amount;
	    }
    }
        
}