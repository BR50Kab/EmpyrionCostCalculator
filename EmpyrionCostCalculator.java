/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empyrioncostcalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Martien
 */
public class EmpyrionCostCalculator {

    /**
     * @param args the command line arguments
     */
    
    //private final static Map<Item> Items = new HashMap<>();
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        Map<String, Item> Items = new HashMap<>();
        
        //for ( int i = 0, i < itemCount; i=i+2;){
        var A = new Item();
        var B = new Item();
        var C = new Item();
        var D = new Item();
        var E = new Item();
        A.setName("Iron Ore");
        A.setIsOre(true);
        
        B.setName( "Iron Ingot");
        var q = new Quantity(0.5d);
        B.setOutputQuantity(1d);
        //int result = B.getInputItemCount();
        var result = B.getInputItemCount();
        B.addInputItem(A, q);
        int result2 = B.getInputItemCount();
        System.out.println( B.getName() + " : Input item count has gone from " + result + " to " + result2);
        
        C.setName("Cobalt Ore");
        C.setIsOre(true);
        D.setName("Cobalt Ingot");
        D.setOutputQuantity(1d);
        D.addInputItem(C, q);
        E.setName("Cobalt Alloy");
        E.setOutputQuantity(1d);
        q = new Quantity(1d);
        E.addInputItem(B, q);
        q = new Quantity(1.5d);
        E.addInputItem(D, q);
        //Items.put(A.getName(), A);
        Items.put(B.getName(), B);
        //Items.put(C.getName(), C);
        Items.put(D.getName(), D);
        Items.put(E.getName(), E);
        
        System.out.println( E.getName() + " needs " );
        Set set = E.getInputItems().entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            System.out.print("Input item: "+ mentry.getKey() + " amount: ");
            System.out.println(mentry.getValue());
        }
        
        
        System.out.println(E.getInputItems());
        //for( int i = 0; i < E.getInputItemCount(); i++){
        //    E.listInputItem(i);
        //}
        Item myItem;
        //myItem = Items.get(A.getName());
        //System.out.println("my item: " + myItem.getName());
        //System.out.println("is it an ore? : " + myItem.getIsOre());
        myItem = Items.get("Cobalt Ingot");
        System.out.println("my item: " + myItem.getName());
        System.out.println("is it an ore? : " + myItem.getIsOre());
        System.out.println(" ");
        System.out.println("////////////////////////////////////////////////////////////////////////");
        System.out.println(" ");
        
        Map<String, Double> substitution = substituteItems( E, Items );
        //System.out.println("The result of the substitution is " + substitution);
        
        //Quantity myInputItemQuantity = myItem.getInputItems().getKey((AbstractItem)B);
    }
    
     public static Map<String, Double> substituteItems( Item source, Map<String, Item> Items ){
        boolean foundSubstitution = false;
        double count;
        double amount;
        Map<String, Double> result = source.getInputItems();
        Item working = new Item();
        Item current;
        Item replace;
        //Set set = source.getInputItems().entrySet();
        Set set = result.entrySet();
        Iterator iterator = set.iterator();
        
        //check the inputitems and see if we can substitute
        while(iterator.hasNext()) {
            System.out.println("walking the entry set");
            Map.Entry mentry = (Map.Entry)iterator.next();
            System.out.print("Input item: "+ mentry.getKey() + " amount: ");
            System.out.println(mentry.getValue());
            amount = (double) mentry.getValue();
            
                    
            if ( Items.containsKey(mentry.getKey()) ){
                 
                current = Items.get(mentry.getKey());
                //dont replace with current but with current.getInputItems * amount
                Map<String, Double> replacement = current.getInputItems();
                Set replacementSet = replacement.entrySet();
                Iterator replacementIterator = replacementSet.iterator();
                //now replace for every entry in replacement
                while(replacementIterator.hasNext()){
                    
                    Map.Entry replaceMentry = (Map.Entry)replacementIterator.next();
                    if ( Items.containsKey(replaceMentry.getKey())){
                        replace = Items.get(replaceMentry.getKey());
                        if (working.getInputItems().containsKey(replaceMentry.getKey())){
                            //increase values
                            count = (double) replaceMentry.getValue() * amount;
                            count = count + working.getInputItems().get(replaceMentry.getKey());
                            working.addInputItem(replace, new Quantity(count) );
                        }
                        else {
                            //put on the working inputItems
                            working.addInputItem(replace, new Quantity(amount));

                        }
                        foundSubstitution = true;
                    }
                }
                
                if ( current.getInputItemCount() > 0 ){
                    
                }
                else {
                    //nothing tp do?
                    System.out.println("item to substitute found with count of zero or less: "+ mentry.getKey());
                }
                System.out.println("item to substitute found: "+ mentry.getKey());
                System.out.println( "Current item is : " + current.getName());
                //substitute it now
                count = (double) mentry.getValue();
                System.out.println("with quantity: "+ count);
                System.out.println("result size: "+ result.size());
                //result.remove(mentry.getKey());
                //iterator.remove();
                if( Items.containsKey(mentry.getKey())){
                    
                }
                    
                System.out.println("result size: "+ result.size());
                //result.add  all items in the item list we found on Items.containsKey(mentry.getKey)
                //foundSubstitution = true;  
            }
        }
        if ( foundSubstitution) {
            System.out.println("Going one level deeper " + working.listInputItems());
            return substituteItems( working, Items );
        }
        else {
            System.out.println("End of substitutions " + source.listInputItems() );
            return result;
        }
        
    }
    
}
