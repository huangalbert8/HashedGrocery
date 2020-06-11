/**
 * @author Albert Huang ID#:112786492 Rec:R01
 * This class represents the HashedGrocery which is a hashtable of items that also contains
 * the business days.
 */
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.Collection;
import java.util.Iterator;
import java.io.*;
import java.util.Hashtable;

public class HashedGrocery extends Hashtable implements Serializable {
    private int businessDay = 1;
    private Hashtable<String, Item> hashtable;

    public HashedGrocery(){
        hashtable = new Hashtable<>();
    }

    public int getBusinessDay() {
        return businessDay;
    }

    public Hashtable<String, Item> getHashtable() {
        return hashtable;
    }

    /**
     * adds an item into the hashTable using the hashTables put method.
     * @param item
     */
    public void addItem(Item item){
        hashtable.put(item.getItemCode(), item);

    }

    /**
     * updates the quantity in store of an item
     * @param item the item that is whose quantity in store is to altered
     * @param adjustByQty how much the quantity in store is to be adjusted by
     */
    public void updateItem(Item item, int adjustByQty){
        hashtable.get(item.getItemCode()).setQtyInStore(adjustByQty);

    }

    /**
     * Using JSON this method reads a file in the parameter and adds the values into the hashTable
     * based on the code given on homework specifications
     * @param filename the file that is to be added into the hashTable
     * @throws IOException if the file does not exist this is thrown
     * @throws ParseException if the string cannot be converted to the desired cast
     */
    public void addItemCatalog(String filename) throws IOException, ParseException {
        FileInputStream fis = new FileInputStream(filename);
        InputStreamReader isr = new InputStreamReader(fis);
        JSONParser parser = new JSONParser();
        JSONArray objs = (JSONArray) parser.parse(isr); // objs is a JSONArray which contains all JSONObjects found in the InputStream
        JSONObject obj;
        for(int i =0; i < objs.size(); i++) {
            obj = (JSONObject) objs.get(i);      // obj is the first JSONObject in the objs JSONArray
            String itemCode = (String) obj.get("itemCode");
            String itemName = (String) obj.get("itemName");
            int avgSales = Integer.parseInt((String)obj.get("avgSales"));
            int qtyInStore = Integer.parseInt((String)obj.get("qtyInStore"));
            double price = Double.parseDouble((String)obj.get("price"));
            int onOrder = Integer.parseInt((String)obj.get("amtOnOrder"));
            if(hashtable.get(itemCode)==null){
            Item item = new Item(itemCode,itemName,qtyInStore,avgSales,price);
            item.setOnOrder(onOrder);
            addItem(item);
            System.out.println(itemCode+": "+itemName+" is added to inventory.");}
        }
    }

    /**
     * process the salse that have occurred based on a sales file that adjusts the quantity in the store
     * and if the quantity in store is not sufficient for the next 2 then an order will be made that is two
     * times the average sales and will arrive in the next 3 business days.
     * @param filename the sales file that will be processed
     * @throws IOException if the file is not found
     * @throws ParseException if the string cannot be converted to the desired cast
     */
    public void processSales(String filename) throws IOException, ParseException {
        FileInputStream fis = new FileInputStream(filename);
        InputStreamReader isr = new InputStreamReader(fis);
        JSONParser parser = new JSONParser();
        JSONArray objs = (JSONArray) parser.parse(isr); // objs is a JSONArray which contains all JSONObjects found in the InputStream
        JSONObject obj;
        for(int i =0; i < objs.size(); i++) {
            obj = (JSONObject) objs.get(i);
            String itemCode = (String) obj.get("itemCode");
            int qtySold = Integer.parseInt((String) obj.get("qtySold"));
            if (hashtable.get(itemCode) != null) {
                if(hashtable.get(itemCode).getQtyInStore()<qtySold)
                    System.out.println(itemCode+": Not enough stock for sale. Not updated.");
                else {
                    updateItem(hashtable.get(itemCode), hashtable.get(itemCode).getQtyInStore() - qtySold);
                    if ((hashtable.get(itemCode).getAverageSalesPerDay() * 3) > hashtable.get(itemCode).getQtyInStore()) {
                        hashtable.get(itemCode).setOnOrder(hashtable.get(itemCode).getAverageSalesPerDay() * 2);
                        System.out.println(itemCode + ": " + qtySold + " units of " + hashtable.get(itemCode).getName() + " are sold. Order has been placed for " + +hashtable.get(itemCode).getAverageSalesPerDay() * 2 + " more units.");
                        hashtable.get(itemCode).setArrivalDay(businessDay + 3);
                    } else
                        System.out.println(itemCode + ": " + qtySold + " units of " + hashtable.get(itemCode).getName() + " are sold.");
                }
            }
            else
                System.out.println(itemCode+": Cannot buy as it is not in the grocery store.");
        }
    }

    /**
     * Updated the business day to the next business day and determines if the order has arrived.
     * If the order has arrived then the quantity in store will be increased by the amount on order
     * and the business day that it's supposed to arrive is set to 0 based on an iterator from piazza
     * that checks each item in the hashtable.
     */
    public void nextBusinessDay(){
        businessDay = businessDay+1;
        String s1 = "Orders have arrived for:\n";
        String s2 = "";
        Collection values = hashtable.values(); // get the values, which are all Item objects
        Iterator collectionIterator = values.iterator();
        while(collectionIterator.hasNext()) { // use this to make sure you don't get a NullPointerException
            Item currentItem = (Item) collectionIterator.next();
            if (businessDay == currentItem.getArrivalDay()) {
                s2 += "\n" + currentItem.getItemCode() + ": " + currentItem.getOnOrder() + " units of " + currentItem.getName() + ".";
                currentItem.setQtyInStore(currentItem.getOnOrder()+currentItem.getQtyInStore());
                currentItem.setOnOrder(0);
                currentItem.setArrivalDay(0);
            }
        }
        if(s2.equals(""))
                System.out.println("No orders have arrived.");
            else
                System.out.println(s1+s2);

    }

    /**
     * Using an iterator from piazza that checks each item in the table, creates a table of all the items
     * @return the table with contents of the hashtable.
     */
    public String toString(){
        String s = "Item code   Name                Qty   AvgSales   Price    OnOrder    ArrOnBusDay\n" +
                "--------------------------------------------------------------------------------\n";
        Collection values = hashtable.values(); // get the values, which are all Item objects
        Iterator collectionIterator = values.iterator();
        while(collectionIterator.hasNext()) { // use this to make sure you don't get a NullPointerException
            Item currentItem = (Item)collectionIterator.next();
            s+=currentItem.toString()+"\n";
        }

        return s;
    }
}
