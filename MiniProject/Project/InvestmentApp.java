package Project;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;

public class InvestmentApp extends JFrame{

    private Account account = new Account();
    private JPanel AssetButtonsPanel;
    JFrame main = new JFrame("Investment App");
    JFrame frame = new JFrame("Assets");
    JList<Asset> list = new JList<>();
    DefaultListModel<Asset> model = new DefaultListModel<>();

    JLabel label = new JLabel();
    JLabel info = new JLabel();
    JPanel panel = new JPanel();
    JSplitPane splitPane = new JSplitPane();

    /*
     * We will use this to print messages to the user.
     */
    private static TextArea infoArea = new TextArea("InvestmentApp 0.5");

    public static void print(String text){
        infoArea.setText(text);
    }    

    //depsosits to the account balance
    public int depositAccount(int amount){
        account.deposit(amount);
        try {
            writeBalance(account);
        } catch (IOException e) {
            System.out.println(e);
        }
        return account.getBalance();
    }

    //withdrsws from the account balance
    public int withdrawAccount(int amount){
        account.withdraw(amount);
        try {
            writeBalance(account);
        } catch (IOException e) {
            System.out.println(e);
        }
        return account.getBalance();
    }

    // It updates the account balance in the text file 
    public static void writeBalance(Account account) throws IOException
    {
        final String filename = "AccountBalance.txt";
        Writer writer = new FileWriter(filename);
        writer.write(String.valueOf(account.getBalance()));
        writer.close();
        print("Account updated");

    }

    // Reads account balance from the file
    public static String readBalance(Account account) throws IOException  
    {
        File myObj = new File("AccountBalance.txt");
        Scanner myReader = new Scanner(myObj);
        String data = "";
        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
            print(data);
        }
        myReader.close();
        return data;
    }

    //deposits to the chosen asset and withdraws the amount from the account wallet
    public boolean deposit(String assetName, int amount, String type) throws Exception{
        boolean found = false;
        account.setBalance(Integer.parseInt(readBalance(account)));
        if(account.getBalance() >= amount){
            account.withdraw(amount);
            int balance = account.getBalance();
            account.setBalance(balance);
            try {
                writeBalance(account);
            } catch (IOException e) {
                System.out.println(e);
            }
            double value = shareOrCrypto(assetName, type);
            account.removeAsset(assetName);
            int deposit = (int)(amount * value);
            found = account.depositAsset(assetName, deposit); 
            print(deposit + " deposited into " + assetName + "!");
            if(found == false){
                print("That Asset cannot be found, please try again.");
                System.out.println("That Asset cannot be found, please try again.");
                throw new Exception();
            }
        }
        else{
            print("The Account balance is too low");
        }
        return found;
    }

    //withdraws from the chosen asset and deposits the amount into the account wallet
    public boolean withdraw(String assetName, int amount, String type) throws Exception{
        boolean found = false;
        account.setBalance(Integer.parseInt(readBalance(account)));
        if(account.getAssetBalance(assetName) >= amount){
            int deposit = amount;
            double value = shareOrCrypto(assetName, type);
            account.removeAsset(assetName);
            if(value <= 0.05){
                deposit = (int)(amount/value);
            }
            account.setBalance(Integer.parseInt(readBalance(account)));
            account.deposit(deposit);
            try {
                writeBalance(account);
            } catch (IOException e) {
                System.out.println(e);
            }
            found = account.withdrawAsset(assetName, amount); 
            print(amount + " withdrawn from " + assetName + "!");
            if(account.getAssetBalance(assetName) == 0){
                account.removeAsset(assetName);
                print(assetName + " removed!");
                int numAssets = AssetButtonsPanel.getComponentCount();
                AssetButtonsPanel.remove(numAssets-1);	
                this.setVisible(true);
            }
            if(found == false){
                print("That Asset cannot be found, please try again.");
                System.out.println("That Asset cannot be found, please try again.");
                throw new Exception();
            }
        }
        else{
            print("The Asset balance is too low");
        }
        return found;
    }

    /**
    * This method prints the names of all assets.
    */
    public void printAssets(){
        String text = account.getListOfAssetNames();
        print(text);
    }

    /**
    * This method prints the fund of the user.
     * @throws IOException
    */
    public void printAccount(Account account) throws IOException{
        String text;
            text = account.getAccount(account);
            print(text);
    }

    /**
    * This method prints the information of the asset with the given index.
    */
    public void printAssetInfo(int index){
        String text = account.getAssetInfo(index);
        print(text);	
    }

    /**
    * This method lets the user choose whether they are buying a crypto or share.
    */
    public double shareOrCrypto(String name, String type) throws Exception{
        if(type.equals("Crypto")){
            Crypto hehe = new Crypto(name);
            account.addAsset(hehe);
            String Type = hehe.getType();
            hehe.assetType(Type);
            return hehe.getValue();
        }
        else if(type.equals("Share")){
            Share ehe = new Share(name);
            account.addAsset(ehe);
            String Type = ehe.getType();
            ehe.assetType(Type);
            return ehe.getValue();
        }
        else{
            throw new Exception();
        }
    }

    /**
    * This method takes all the necessary steps when a Asset is added. 
    */
    public void addAsset(String name, String type){
        try {
            if(!(name.equals(account.checkAssetName(name)))){
                shareOrCrypto(name, type);
                Button btn = new Button(name);
                int numAssets = account.getNumberOfAssets();
                btn.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent evt){
                        int index = 0;
                        for(int i = 0; i < numAssets; i++){
                            printAssetInfo(index);
                            index++;
                        }
                    }
                });
                AssetButtonsPanel.add(btn);	
                this.setVisible(true);
                print(name + " Added!");
            }
            else{
                print("Error! You cannot add the same Asset twice!");
            }
        } catch (Exception e) {
            print("Error! input not recognised please enter again.");
        }
    }

    public InvestmentApp(){
        this.account = new Account();	
        this.setLayout(new FlowLayout());
        
        //Prints all assets
        JButton reportButton = new JButton("Print Asset list");
        reportButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                printAssets();
            }
        });			
        this.add(reportButton); 

        //prints the account wallet
        JButton reportAccButton = new JButton("Account"); 
        reportAccButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                try {
                    printAccount(account);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        });			
        this.add(reportAccButton);

        //Add asset button
        JButton addAssetButton = new JButton("Add Asset");
        addAssetButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) { //Makes the JList visible
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(splitPane);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
        this.add(addAssetButton); 
    
        //Deposits into a certain asset
        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                Prompt dps = new Prompt();

                Label AssetChoice = new Label("Which Asset Would You like to Deposit into?");
                dps.add(AssetChoice);
                TextField assetName = new TextField("", 20);
                dps.add(assetName);
                Label depositAmount = new Label("How much would you like to deposit?");
                dps.add(depositAmount);
                TextField amount = new TextField("", 20);
                dps.add(amount);
                Label i = new Label("What is the Asset type?");
                dps.add(i);
                TextField assetType = new TextField("",20);
                dps.add(assetType);

                dps.addSubmitListener(new ActionListener(){
                    public void actionPerformed(ActionEvent evt){
                        try {
                            deposit(assetName.getText(), Integer.parseInt(amount.getText()), assetType.getText());  
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                });
                dps.activate();
            }

        });
        this.add(depositButton);

        //Deposits into account wallet
        JButton depositAccountButton = new JButton("Deposit Account");
        depositAccountButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                Prompt dpsa = new Prompt();

                Label depositAmount = new Label("How much would you like to deposit?");
                dpsa.add(depositAmount);
                TextField amount = new TextField("", 20);
                dpsa.add(amount);

                dpsa.addSubmitListener(new ActionListener(){
                    public void actionPerformed(ActionEvent evt){
                        depositAccount(Integer.parseInt(amount.getText()));
                    }
                });
                dpsa.activate();
            }

        });
        this.add(depositAccountButton);
         
        //withdraws from an certain asset
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                Prompt wdr = new Prompt();
    
                Label assetChoice = new Label("Which Asset Would You like to Withdraw from?");
                wdr.add(assetChoice);
                TextField assetName = new TextField("", 20);
                wdr.add(assetName);
                Label withdrawAmount = new Label("How much would you like to withdraw?");
                wdr.add(withdrawAmount);
                TextField amount = new TextField("", 20);
                wdr.add(amount);
                Label i = new Label("What is the Asset type?");
                wdr.add(i);
                TextField assetType = new TextField("",20);
                wdr.add(assetType);
    
                wdr.addSubmitListener(new ActionListener(){
                    public void actionPerformed(ActionEvent evt){
                        try {
                            withdraw(assetName.getText(), (Integer.parseInt(amount.getText())), assetType.getText());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                });
                wdr.activate();
            }
        });
        this.add(withdrawButton);

        //withdraws from account wallet
        JButton withdrawAccountButton = new JButton("Withdraw Account");
        withdrawAccountButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                Prompt lll = new Prompt();

                Label withdrawAmount = new Label("How much would you like to withdraw?");
                lll.add(withdrawAmount);
                TextField amount = new TextField("", 20);
                lll.add(amount);
    
                lll.addSubmitListener(new ActionListener(){
                    public void actionPerformed(ActionEvent evt){
                        withdrawAccount((Integer.parseInt(amount.getText())));
                    }
                });
                lll.activate();
            }
        });
        this.add(withdrawAccountButton);

        String name = "";
        Crypto hehe = new Crypto(name);
        String type = "";
        Share ehe = new Share(name);

        list.setModel(model); //JList - my extra 
        model.addElement(new Crypto("Bitcoin", hehe.assetType(type)));
        model.addElement(new Crypto("Ethereum", hehe.assetType(type)));
        model.addElement(new Crypto("Solana", hehe.assetType(type)));
        model.addElement(new Crypto("Tether", hehe.assetType(type)));
        model.addElement(new Crypto("Shiba Inu", hehe.assetType(type)));
        model.addElement(new Share("RELIANCE INDUSTRIES LTD.", ehe.assetType(type)));
        model.addElement(new Share("TATA CONSULTANCY SERVICES LTD.", ehe.assetType(type)));
        model.addElement(new Share("HDFC Bank Ltd", ehe.assetType(type)));
        model.addElement(new Share("INFOSYS LTD.", ehe.assetType(type)));
        model.addElement(new Share("Nvidia", ehe.assetType(type)));

        ImageIcon[] assetImage = new ImageIcon[10]; //ImageIcon - another extra 
        assetImage[0] = new ImageIcon("Images/Bitcoin.png");
        assetImage[1] = new ImageIcon("Images/Ethereum.png");
        assetImage[2] = new ImageIcon("Images/Solana.png");
        assetImage[3] = new ImageIcon("Images/Tether.png");
        assetImage[4] = new ImageIcon("Images/ShibaInu.jpg");
        assetImage[5] = new ImageIcon("Images/RELIANCE INDUSTRIES LTD.jpg");
        assetImage[6] = new ImageIcon("Images/TATA CONSULTANCY SERVICES LTD.png");
        assetImage[7] = new ImageIcon("Images/HDFC Bank Ltd.png");
        assetImage[8] = new ImageIcon("Images/INFOSYsLTD.png");
        assetImage[9] = new ImageIcon("Images/Nvidia.png");

        //Shows the labels so it shows the graphs when we first open the JList
        Image selectedImage = assetImage[0].getImage(); // transform it 
        Image selectedNewImg = selectedImage.getScaledInstance(500, 400,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        ImageIcon selectedAssetImg = new ImageIcon(selectedNewImg);  // transform it back
        label.setIcon(selectedAssetImg);
        list.setSelectedIndex(0);
        Asset selected = list.getSelectedValue();
        info.setText("Name: " + selected.getName() + " ::: " + selected.getType());

        //this adds the Assets and all the information when we select that option in the JList
        list.getSelectionModel().addListSelectionListener(e -> {
            Boolean b = list.getValueIsAdjusting();
            if (b == true){
                int index = list.getSelectedIndex();
                Asset a = list.getSelectedValue();
                info.setText("Name: " + a.getName() + " ::: " + a.getType());
                addAsset(a.getName(), a.getType());
                Image image = assetImage[index].getImage(); // transform it 
                Image newimg = image.getScaledInstance(500, 400,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
                ImageIcon assetImg = new ImageIcon(newimg);  // transform it back
                label.setIcon(assetImg);      
            }
        });

        //This makes the split panel so we can have the stock graph and asset info to show when the user selects the asset
        splitPane.setLeftComponent(new JScrollPane(list));
        panel.add(label);
        panel.add(info);
        splitPane.setRightComponent(panel);
        splitPane.setSize(800, 500);
        
        // Output console
        infoArea.setEditable(false);
        this.add(infoArea);	
    
        // Asset button panel
        AssetButtonsPanel = new JPanel();

        this.getContentPane().setBackground(Color.PINK);

        // We add a couple of assets of testing purposes
        // this.addAsset("Bitcoin", "Crypto");
        // this.addAsset("Nvidia", "Share");
    
        AssetButtonsPanel.setLayout(new GridLayout(0,1));
        AssetButtonsPanel.setVisible(true);
        this.add(AssetButtonsPanel);
    
        // This is just so the X button closes our app
        WindowCloser wc = new WindowCloser();
            this.addWindowListener(wc); 
    
        this.setSize(500,500);// Self explanatory
        this.setLocationRelativeTo(null); // Centers the window on the screen
        this.setVisible(true);// Self explanatory
    }

    public static void main(String[] args) {
        try {
            Account account = new Account();
            writeBalance(account);
        } catch (IOException e) {
            System.out.println(e);
        }
        print("InvestmentApp 0.5");
        new InvestmentApp();
    }
}