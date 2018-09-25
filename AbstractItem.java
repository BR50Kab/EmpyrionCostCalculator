/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empyrioncostcalculator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Martien
 */
public class AbstractItem {
    private String itemType;
    private String itemName;
    private int itemPriceCoins;
    private int itemPriceCoinCents;
    //int inputQuantity;
    private double outputQuantity = 1d;
    private boolean isOre;
    private final Map<String, Double> inputItems; 
    
    public AbstractItem() {
        inputItems = new HashMap<>();
        isOre = false;
    }
    
    public String getName() {
        return itemName;
    }
    
    public void setName(String name) {
        itemName = name;
    }
    
    public boolean getIsOre(){
        return this.isOre;
    }
    
    public void setIsOre(boolean isOre){
        this.isOre = isOre;
    }
    
    public void addInputItem(AbstractItem item, Quantity quantity){
        inputItems.put(item.getName(), quantity.getQuantity());
    }
    
    public Map<String, Double> getInputItems(){
        return inputItems;
    }
    
    //wrong way
    public String listInputItem(int n){
        return "Item " + inputItems.get(n).toString()
        + " Quantity " + inputItems.get(n);
        //return "";
    }
    
    public String listInputItems() {
        String result = "";
        Set set = this.getInputItems().entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            result = result + "Input item: " + mentry.getKey() + " amount: " + mentry.getValue();
        }
        return result;
    }
    
    public int getInputItemCount() {
        return this.inputItems.size();
    }
            
    public double getOutputQuantity(){
        return outputQuantity;
    }
    
    public void setOutputQuantity(double outputQuantity){
        this.outputQuantity = outputQuantity;
    }
    
   
    
}


