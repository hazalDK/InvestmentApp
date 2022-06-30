package Project;

public class Share extends Asset{
    
    //Constructor
    public Share(String name){
        super(name, 0.005); //sets the asset value to 0.005
    }

    public Share(String name, String type){
        super(name, type);
    }

    //This method sets the abstract method type to Share
    public String assetType(String type){
        type = "Share";
        return type;
    }   

    //This assigns the unit to percentage (overrides the method in the superclass)
    public String assetUnit(){
        return "%";
    }
}
