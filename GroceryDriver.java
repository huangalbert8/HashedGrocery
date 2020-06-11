/**
 * @author Albert Huang ID#:112786492 Rec:R01
 * The driver class that allows the user to add their files and process their sales.
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.json.simple.parser.ParseException;
class AlreadyExistException extends Exception{}

public class GroceryDriver {
    public static void main(String[] args) throws IOException, ParseException {
        Scanner in = new Scanner(System.in);
        HashedGrocery hashedGrocery= new HashedGrocery();
        System.out.println("Business Day 1.");
        String menu =
                        "\nMenu: \n\n"+
                        "L) Load item catalog\n"+
                        "A) Add items\n"+
                        "B) Process Sales\n"+
                        "C) Display all items\n"+
                        "N) Move to next business day\n"+
                        "Q) Quit";

        while(!in.equals("q")) {
            System.out.println(menu + "\n\n" + "Enter a selection:");
            String selection = in.next().toLowerCase();
            if(selection.equals("l")){
                try{
                System.out.println("Enter file to load: ");
                String filename = in.next();
                hashedGrocery.addItemCatalog(filename);}
                catch(FileNotFoundException ex){
                    System.out.println("File not found.");
                }
            }
            else if(selection.equals("a")){
                System.out.println("Enter item code:");
                String itemCode = in.next();
                System.out.println("Enter item name: ");
                String itemName = in.next();
                System.out.println("Enter Quantity in store: ");
                int qtyInStore = in.nextInt();
                System.out.println("Enter Average sales per day: ");
                int averageSalesPerDay = in.nextInt();
                System.out.println("Enter Price: ");
                double price = in.nextDouble();
                try{
                if(hashedGrocery.getHashtable().get(itemCode)!=null){
                    throw new AlreadyExistException();}
                Item item = new Item(itemCode,itemName,qtyInStore,averageSalesPerDay,price);
                hashedGrocery.addItem(item);
                System.out.println(itemCode+": "+itemName+" added to inventory.");}
                catch (AlreadyExistException ex){
                    System.out.println(itemCode+": Cannot add item as item code already exists.");
                }

            }
            else if(selection.equals("b")){
                try{
                System.out.println("Enter filename");
                String fileName = in.next();
                hashedGrocery.processSales(fileName);}
                catch (FileNotFoundException ex){
                    System.out.println("File not found.");
                }
            }
            else if(selection.equals("c")){
                System.out.println(hashedGrocery.toString());
            }
            else if(selection.equals("n")){
                System.out.println("Advancing business day...\nBusiness Day "+(hashedGrocery.getBusinessDay()+1)+".\n");
                hashedGrocery.nextBusinessDay();
            } else if(selection.equals("q")){
                System.exit(0);
            }
            else
                System.out.println("Invalid Input");


        }
    }
}
