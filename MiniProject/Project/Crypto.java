package Project;

public class Crypto extends Asset{
       
    //Constructor
    public Crypto(String name){
        super(name, 0.6); //sets the asset value to 0.6
    }

    public Crypto(String name, String type){
        super(name, type);
    }

    //This method sets the abstract method type to Crypto
    public String assetType(String type){
        type = "Crypto";
        return type;
    }

    //This assigns the unit to dollar (overrides the method in the superclass)
    public String assetUnit(){
        return "$";
    }


}
