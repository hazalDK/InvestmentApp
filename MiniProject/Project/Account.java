package Project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
public class Account{
    private int balance;
    private ArrayList<Asset> assets;
    
    //Constructor
    public Account(){
        this.balance=0;
        assets = new ArrayList<Asset>();
    }
    
    //Constructor
    public Account(int balance){
	    this();
        this.balance = balance;
    }

    //This method gets the number of Assets(size of arraylist)
    public int getNumberOfAssets(){
        return assets.size();
    }
    
    //This method gets the asset from the arraylist based on the index number given
    public Boolean getAsset(String assetName){
        Iterator<Asset> it = assets.iterator();
        while (it.hasNext()){
            Asset a = it.next();
            if (a.getName().equals(assetName)){
                return true;
            }
        }
        return false;
    }

    //This method deposits a certain amount into the asset
    public Boolean depositAsset(String assetName, int amount){
        balance = 0;
        Iterator<Asset> it = assets.iterator();
        while (it.hasNext()){
            Asset a = it.next();
            if (a.getName().equals(assetName)){ 
                a.deposit(amount);
                return true;
            }
        }
        return false;
    }

    //This method withdraws a certain amount from the asset
    public Boolean withdrawAsset(String assetName, int amount){
        Iterator<Asset> it = assets.iterator();
        while (it.hasNext()){
            Asset a = it.next();
            if (a.getName().equals(assetName)){
                a.withdraw(amount);
                return true;
            }
        }
        return false;
    }
    
    //This method gets the Aset method and returns it
    public int getAssetBalance(String assetName){
        int balance = 0;
        Iterator<Asset> it = assets.iterator();
        while (it.hasNext()){
            Asset a = it.next();
            if (a.getName().equals(assetName)){
                a.getBalance();
                return a.getBalance();
            }
        }
        return balance;
    }

    //This method gets the info to print when we click on the button of the Asset
    public String getAssetInfo(int assetNumber){
        Asset a = assets.get(assetNumber);
        String text = "";
        text += "Name: " + a.getName() + "\n";
        text += "Funds: " + a.getBalance() + a.setUnit() + "\n";
        text += "Type: " + a.getType() + "\n";
        return text;
    }
       
    //This method gets the list of Assets in the arraylist.
    public String getListOfAssetNames(){
        String text = "";
        Iterator<Asset> it = assets.iterator();
        while (it.hasNext()){
            Asset a = it.next();
            text += a.getName() + "\n";
        }
        return text;
    }

    //This method removes the Asset from the arraylist
    public boolean removeAsset(String assetName){
        Iterator<Asset> it = assets.iterator();
        while (it.hasNext()){
            Asset a = it.next();
            if (a.getName().equals(assetName)){
                assets.remove(a);
                return true;
            }
        }
        return false;
    }

    //This method retreives the assetName based on the number the user gives
    public String getAssetName(int assetNumber){
        Asset a = assets.get(assetNumber);
        return a.getName();
    }

    //This method checks the assetName based on the name given
    public String checkAssetName(String assetName){
        Iterator<Asset> it = assets.iterator();
        Asset a;
        while (it.hasNext()){
            a = it.next();
            if (a.getName().equals(assetName)){
                return a.getName();
            }
        }
        return "";
    }
        

    //This method adds an asset to the arraylist
    public void addAsset(Asset a){
        assets.add(a);
    }

    //This method shows the user how much they have using file I/O
    public String getAccount(Account account) throws IOException{
        String text = "";
        text += "Wallet: " + InvestmentApp.readBalance(account) + "\n";
        return text;
    }

    //This method gets the balance
    public int getBalance(){
        return this.balance;
    }    

    //This method sets the balance as a given amount
    public void setBalance(int amount){
        this.balance = amount;
    }   

    //This method deposits into account
    public void deposit(int amount){
        this.balance += amount;
    }
    
    //This method withdraws from account and has an error if there isnt enough balance
    public void withdraw(int amount){
	    if (amount > this.balance){
	    System.out.println("Error. The amount to be withdrawn exceeds this account's balance.");
	    } 
        else{
	    this.balance -= amount;
	}
    }    
}
