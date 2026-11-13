// Main.java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);   // reading input: provided for you
        Product[] products = new Product[10];  // an array with room for 10 products
        int count = 0;                         // how many products are stored so far
        int choice;

        do {
            System.out.println("\n--- Campus Cafe Inventory ---");
            System.out.println("1. Add product");
            System.out.println("2. Display all products");
            System.out.println("3. Total stock value");
            System.out.println("4. Average price");
            System.out.println("5. Restock a product");
            System.out.println("6. Duplicate a product");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            choice = readInt(sc);

            if (choice < 0 || choice > 6) {
                System.out.println("Invalid choice, try again.");
            }

            switch (choice) {
                case 1:
                    System.out.print("Name: ");
                    String name = readLine(sc).trim();
                    System.out.print("Price: ");
                    double price = readDouble(sc);
                    System.out.print("Quantity: ");
                    int quantity = readInt(sc);

                    if (count == products.length) {
                        System.out.println("Inventory is full, cannot add more than " + products.length + " products.");
                    } else if (name.isEmpty()) {
                        System.out.println("Error: name cannot be empty.");
                    } else if (price <= 0 || quantity < 0) {
                        System.out.println("Error: price must be above 0 and quantity cannot be negative.");
                    } else {
                        products[count] = new Product(name, price, quantity);
                        count++;
                        System.out.println("Product added.");
                    }
                    break;

                case 2:
                    if (count == 0) {
                        System.out.println("No products to display.");
                    } else {
                        for (int i = 0; i < count; i++) {
                            System.out.print((i + 1) + ". ");
                            products[i].display();
                        }
                    }
                    break;

                case 3:
                    if (count == 0) {
                        System.out.println("No products, total stock value is 0.");
                    } else {
                        double total = 0;
                        for (int i = 0; i < count; i++) {
                            total += products[i].stockValue();
                        }
                        System.out.println("Total stock value: Rs " + total);
                    }
                    break;

                case 4:
                    if (count == 0) {
                        System.out.println("No products, cannot calculate an average.");
                    } else {
                        double sum = 0;
                        for (int i = 0; i < count; i++) {
                            sum += products[i].price;
                        }
                        double average = sum / count;   // double / int keeps the decimal part
                        System.out.println("Average price: Rs " + average);
                        System.out.println("Rounded down: Rs " + (int) average);
                    }
                    break;

                case 5:
                    System.out.print("Product number: ");
                    int num = readInt(sc);
                    System.out.print("Quantity to add: ");
                    int extra = readInt(sc);

                    if (num < 1 || num > count) {
                        System.out.println("Error: there is no product number " + num + ".");
                    } else if (extra <= 0) {
                        System.out.println("Error: quantity to add must be above 0.");
                    } else if (extra > Integer.MAX_VALUE - products[num - 1].quantity) {
                        // the sum would not fit in an int and would wrap round to a negative number
                        System.out.println("Error: that would make the quantity too large.");
                    } else {
                        products[num - 1].quantity += extra;   // product number 1 is index 0
                        System.out.println(products[num - 1].name + " now has qty " + products[num - 1].quantity + ".");
                    }
                    break;

                case 6:
                    System.out.print("Product number: ");
                    int n = readInt(sc);

                    if (n < 1 || n > count) {
                        System.out.println("Error: there is no product number " + n + ".");
                    } else if (count == products.length) {
                        System.out.println("Inventory is full, cannot duplicate.");
                    } else {
                        Product copy = new Product(products[n - 1]);
                        copy.name = copy.name + " (copy)";
                        products[count] = copy;
                        count++;
                        System.out.println("Added " + copy.name + ".");
                    }
                    break;

                case 0:
                    System.out.println("Goodbye!");
                    break;
            }
        } while (choice != 0);

        System.out.println("Products created in total: " + Product.totalProducts);
    }

    // Reads a whole number; asks again if the user types anything else
    static int readInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            readLine(sc);   // throw away the bad line
            System.out.print("Please enter a whole number: ");
        }
        int value = sc.nextInt();
        clearLine(sc);
        return value;
    }

    // Reads a number such as 15 or 10.5; asks again for anything else, including NaN and Infinity
    static double readDouble(Scanner sc) {
        while (true) {
            if (sc.hasNextDouble()) {
                double value = sc.nextDouble();
                clearLine(sc);
                if (!Double.isNaN(value) && !Double.isInfinite(value)) {
                    return value;
                }
            } else {
                readLine(sc);   // throw away the bad line
            }
            System.out.print("Please enter a number: ");
        }
    }

    // Reads a whole line of text, such as a product name
    static String readLine(Scanner sc) {
        if (!sc.hasNextLine()) {
            endOfInput();
        }
        return sc.nextLine();
    }

    // Clears the rest of the line, including the newline left after nextInt() or nextDouble()
    static void clearLine(Scanner sc) {
        if (sc.hasNextLine()) {
            sc.nextLine();
        }
    }

    // Input ran out (for example Ctrl+D) before the user chose 0: exit cleanly instead of crashing
    static void endOfInput() {
        System.out.println();
        System.out.println("Input ended. Goodbye!");
        System.out.println("Products created in total: " + Product.totalProducts);
        System.exit(0);
    }
}
