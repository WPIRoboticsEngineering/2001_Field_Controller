package edu.wpi.rbe.rbe2001.fieldsimulator.gui;
import javafx.collections.FXCollections;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
public class InventoryManager {
    private String InventoryLoc;
    private String BorrowedInventoryLoc;
    private JSONObject Inventory;
    private JSONObject BorrowedInventory;

    public InventoryManager(String InventoryLocation, String BorrowedInventoryLocation){
        this.InventoryLoc = System.getProperty("user.home")+ File.separator+"Documents"+File.separator+"WareHouseRobot"+File.separator+InventoryLocation;
        this.BorrowedInventoryLoc = System.getProperty("user.home")+ File.separator+"Documents"+File.separator+"WareHouseRobot"+File.separator+BorrowedInventoryLocation;
        verifyFoldersExist();

    }
    public void verifyFoldersExist(){
        String localDirectory = System.getProperty("user.home")+ File.separator+"Documents";
        File dir = new File(localDirectory+File.separator+"WareHouseRobot");
        File Inventory = new File(InventoryLoc);
        File BorrowedInventory = new File(BorrowedInventoryLoc);
        try{//This verifies the files are there and if not creates them
            dir.mkdir();
            Inventory.createNewFile();
            BorrowedInventory.createNewFile();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void removeItemsFromInventory(ListViewPart part, int numBorrowed){
        JSONArray InventoryList = (JSONArray)Inventory.get("inventory");
        JSONArray tempList = new JSONArray();
        Iterator<JSONObject> iterator = InventoryList.iterator();
        while (iterator.hasNext()) {
            JSONObject it = iterator.next();
            if((it.get("name")).equals(part.getName())) {
                it.put("numberAvailable", (long)it.get("numberAvailable")-numBorrowed);

            }
            tempList.add(it);
        }
        Inventory.put("inventory", tempList);
        writeInventory();
        //refresh observable list
        Main.partList = FXCollections.observableArrayList();
        JSONArray inventoryList2 = (JSONArray) Inventory.get("inventory");
        Iterator<JSONObject> iterator2 = inventoryList2.iterator();
        int i = 0;
        while (iterator2.hasNext()) {
            JSONObject it = iterator2.next();
            Main.partList.add(new ListViewPart((String)it.get("name"), (long)it.get("numberAvailable"), (long)it.get("row"), (long)it.get("col"), (long)it.get("height"),(boolean)it.get("returnRequired")));
            i++;
        }
    }

    public void addBorrowedParts(ListViewPart part, int numBorrowed, long ID, String forClass){
        JSONArray borrowedList = (JSONArray)BorrowedInventory.get("borrowedInventory");
        Iterator<JSONObject> iterator = borrowedList.iterator();
        JSONArray tempBorrowedList = new JSONArray();
        boolean idFound = false;
        while (iterator.hasNext()) {
            JSONObject it = iterator.next();
            if((long)it.get("ID")==ID){
                idFound = true;
                JSONArray parts = (JSONArray)it.get("Parts");
                JSONObject newPart = new JSONObject();
                newPart.put("name", part.getName());
                newPart.put("numberBorrowed",numBorrowed);
                newPart.put("Class", forClass);
                parts.add(newPart);
                it.put("Parts", parts);
            }
            tempBorrowedList.add(it);
        }
        if(!idFound){
            JSONObject newUser = new JSONObject();
            newUser.put("ID", ID);
            JSONArray parts = new JSONArray();
            JSONObject newPart = new JSONObject();
            newPart.put("name", part.getName());
            newPart.put("numberBorrowed",numBorrowed);
            newPart.put("Class", forClass);
            parts.add(newPart);
            newUser.put("Parts", parts);
            tempBorrowedList.add(newUser);
        }
        BorrowedInventory.put("borrowedInventory", tempBorrowedList);
        writeBorrowedInventory();
    }

    public void loadInventory(){
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(InventoryLoc));
            Inventory = (JSONObject) obj;
            JSONArray inventoryList = (JSONArray) Inventory.get("inventory");
            Iterator<JSONObject> iterator = inventoryList.iterator();
            while (iterator.hasNext()) {
                JSONObject it = iterator.next();
                Main.partList.add(new ListViewPart((String)it.get("name"), (long)it.get("numberAvailable"), (long)it.get("row"), (long)it.get("col"),(long)it.get("height"), (boolean)it.get("returnRequired")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void loadBorrowedInventory(){
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(BorrowedInventoryLoc));
            BorrowedInventory = (JSONObject) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeInventory(){
        try (FileWriter file = new FileWriter(InventoryLoc)) {
            file.write(Inventory.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeBorrowedInventory(){
        try (FileWriter file = new FileWriter(BorrowedInventoryLoc)) {
            file.write(BorrowedInventory.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
