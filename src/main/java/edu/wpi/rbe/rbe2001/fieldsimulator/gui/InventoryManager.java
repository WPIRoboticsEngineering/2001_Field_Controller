package edu.wpi.rbe.rbe2001.fieldsimulator.gui;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
public class InventoryManager {
    private String InventoryJSON;
    private String SignOutJSON;

    public InventoryManager(String InventoryLocation, String SignOutLocation){
        this.InventoryJSON = InventoryLocation;
        this.SignOutJSON = SignOutLocation;
    }
    public void loadInventory(){
        JSONParser parser = new JSONParser();
        try {
            InputStream stream = getClass().getResourceAsStream(InventoryJSON);
            Object obj = parser.parse(new InputStreamReader(stream, StandardCharsets.UTF_8));
            JSONObject MainObject = (JSONObject) obj;
            JSONArray inventoryList = (JSONArray) MainObject.get("inventory");
            Iterator<JSONObject> iterator = inventoryList.iterator();
            while (iterator.hasNext()) {
                JSONObject it = iterator.next();
                Main.partList.add(new ListViewPart((String)it.get("name"), (long)it.get("numberAvailable"), (long)it.get("row"), (long)it.get("col")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
