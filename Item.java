/**
 * @author Albert Huang ID#:112786492 Rec:R01
 * This class represents the item which consists of an itemCode(they key for the hashTable),
 * the name of the item, the quanity in store, average sales per day, the amount of the item
 * on order, it's arrival day, and price.
 */
public class Item {
    private String itemCode, name;
    private int qtyInStore, averageSalesPerDay, onOrder, arrivalDay;
    private double price;

    public Item(){
    }

    public Item(String itemCode, String name, int qtyInStore, int averageSalesPerDay, double price){
        this.itemCode = itemCode;
        this.name = name;
        this.qtyInStore = qtyInStore;
        this.averageSalesPerDay = averageSalesPerDay;
        this.price = price;
    }
    public String getItemCode(){
        return itemCode;
    }
    public int getQtyInStore(){
        return qtyInStore;
    }
    public int getAverageSalesPerDay(){
        return averageSalesPerDay;
    }
    public String getName(){
        return name;
    }
    public int getArrivalDay(){
        return arrivalDay;
    }
    public int getOnOrder(){
        return onOrder;
    }
    public void setQtyInStore(int qtyInStore){
        this.qtyInStore = qtyInStore;
    }
    public void setOnOrder(int onOrder){
        this.onOrder = onOrder;
    }
    public void setArrivalDay(int arrivalDay){
        this.arrivalDay = arrivalDay;
    }

    /**
     * toString method to return information of an item used for toString method in HashedGrocery
     * toString method.
     * @return
     * the item code, name, quantity in store, price, amount on order, and arrival day
     */
    public String toString(){
        String s = String.format("%-12s%-20s%3d%11d%8.2f%11d%15d",itemCode,name,qtyInStore,averageSalesPerDay,price,onOrder,arrivalDay);
        return s;
    }

}
