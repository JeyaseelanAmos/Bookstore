import java.util.Scanner;
import java.nio.file.*;
import java.io.*;  
import java.util.Formatter;
import java.util.*;
import java.util.ArrayList;

class Store {
    ArrayList<Book> booksInStore = new ArrayList<Book>();
    ArrayList<String> booksContent = new ArrayList<String>();


static void diplayForSetAndUpdate(int isbn,Store store){
    for(Book book : store.booksInStore){
        if(isbn==book.getISBNnumber()){
            System.out.println("+--------------------------------+----------------------+-------+----------+");
            System.out.println("| Book Name                      | Author               | Price | Quantity |");
            System.out.println("+--------------------------------+----------------------+-------+----------+");
            System.out.println(String.format("| %-30s | %-20s | $%-5.2f | %-8d |", book.getBookName(), book.getAuthorName(), book.getPrice(), book.getQuantity()));
            System.out.println("+--------------------------------+----------------------+-------+----------+");
            System.out.println();
            break;
        }
    }
}	
static void sayHi(){
	System.out.println("HI");
}
 void reStockableBooks() {
    System.out.println("                 Restockable books               ");
    System.out.println("─────────────────────────────────────────────────");
    System.out.printf("%-30s%-20s%n", "Book", "Status");
    System.out.println("─────────────────────────────────────────────────");

    for (Book book : booksInStore) {
        if (book.getQuantity() <= 5 && book.getQuantity() >= 0) {
            String status;
            if (book.getQuantity() == 0) {
                status = "\u001B[31mOut of Stock\u001B[0m";
            } else {
                status = "\u001B[33mLimited Stock (" + book.getQuantity() + " left)\u001B[0m";
            }
            System.out.printf("%-30s%-20s%n", book.getBookName().trim(), status);
        }
    }
    System.out.println("─────────────────────────────────────────────────");
}


static void searchBook(Store store, String searchTerm) {
    System.out.println("╔════════════════════════════════════════════════════════════════════════════╗");
    System.out.println("║                      Search results for: " + formatText(searchTerm) + "                           ║");
    System.out.println("╠════════════════════════════════════════════════════════════════════════════╣");

    boolean found = false;

    // Table header
    System.out.println("║ Title                  | Author                  | Genre                   ║");
    System.out.println("║────────────────────────────────────────────────────────────────────────────║");

    for (Book book : store.booksInStore) {
        if (book.getBookName().toLowerCase().contains(searchTerm.toLowerCase()) ||
            book.getAuthorName().toLowerCase().contains(searchTerm.toLowerCase()) ||
            book.getGenre().toLowerCase().contains(searchTerm.toLowerCase())) {

            String title = formatText(book.getBookName().trim());
            String author = formatText(book.getAuthorName());
            String genre = formatText(book.getGenre());

            // Adjusted spacing for alignment
            String formattedOutput = String.format("║ %-22s | %-23s | %-23s ║", title, author, genre);
            System.out.println(formattedOutput);
            found = true;
        }
    }

    if (!found) {
        System.out.println("║            No matching books found.                 ║");
    }
    System.out.println("╚════════════════════════════════════════════════════════════════════════════╝");
}



static String formatText(String text) {
    return text.trim() ;
}



 void displayBooks() {
    System.out.println("-------------------------------------------------------------------------------------------------------------");
    System.out.printf("%-30s%-10s%-30s%-15s%-20s%n", "Title", "ISBN", "Author", "Price", "Status");
    System.out.println("-------------------------------------------------------------------------------------------------------------");
    
    for (Book book : booksInStore) {
        int quantity = book.getQuantity();
        String status;
        if (quantity <= 0) {
            status = "\u001B[31mNot Available\u001B[0m"; 
        } else if (quantity <= 5) {
            status = "\u001B[33mLimited Stock (" + quantity + " left)\u001B[0m"; 
        } else {
            status = "\u001B[32mAvailable\u001B[0m";
        }

        System.out.printf("%-30s%-10s%-30s%-15.2f%-20s%n", book.getBookName().trim(), book.getISBNnumber(), book.getAuthorName().trim(), book.getPrice(), status);
    }
    System.out.println("-------------------------------------------------------------------------------------------------------------");
}

 	


    void setBookStorageToFile(){
    	String filename = "BookInfo.txt";
	    	try {
		    	FileWriter emptyFile = new FileWriter(filename);
		    	ArrayList<String> sample = new ArrayList<String>();
		    	for(Book book : booksInStore){
		    		sample.add(book.getBookName()+"~"+book.getAuthorName()+"~"+book.getPrice()+"~"+book.getQuantity()+"~"+book.getGenre());
		    	}
		    	booksContent=sample;
		    	emptyFile.write(booksContent.toString());
		    	emptyFile.close();
	        }
		    catch (IOException e) {
		        e.printStackTrace();
		    }
    }


    void printBookToFile(Store store){
    	String str = "";
    	try{
    		String filename = "BookInfo.txt";
    		File bookFile = new File(filename);
    		if(bookFile.createNewFile()){
    			FileWriter emptyFile = new FileWriter(filename);
    			booksContent.add("The Great Gatsby~F. Scott Fitzgerald~15.99~50");
    			booksContent.add("To Kill a Mockingbird~Harper Lee~12.50~40");
    			booksContent.add("1984~George Orwell~10.75~60");
    			booksContent.add("Pride and Prejudice~Jane Austen~14.25~35");
    			booksContent.add("The Catcher in the Rye~J.D. Salinger~11.99~45");
    			booksContent.add("Brave New World~Aldous Huxley~13.50~55");
    			booksContent.add("Moby-Dick~Herman Melville~16.75~30");
    			booksContent.add("The Hobbit~J.R.R. Tolkien~18.25~25");
    			booksContent.add("Frankenstein~Mary Shelley~9.99~48");
    			booksContent.add("The Lord of the Rings~J.R.R. Tolkien~22.50~20");
    			emptyFile.write(booksContent.toString());
    			emptyFile.close();
    			String booksData = booksContent.toString().substring(1, booksContent.toString().length() - 1);
				String[] individualBooks = booksData.split(", ");

				for (String book : individualBooks) {
				   String[] bookInfo = book.split("~");
					String bookName = bookInfo[0];
					String bookAuthor = bookInfo[1];
					double bookPrice = Double.parseDouble(bookInfo[2]);
					int bookQuantity = Integer.parseInt(bookInfo[3]);
                    String genre = bookInfo[4];
					Book newBook = new Book(bookName,bookAuthor,bookPrice,bookQuantity,genre);
					booksInStore.add(newBook);
    		    }
    	   }
    	   else{
    	   		ArrayList<String> fileContents = new ArrayList<>();
				try {
				    fileContents.addAll(Files.readAllLines(Paths.get("BookInfo.txt")));
				    String allContents = fileContents.get(0).substring(1,fileContents.get(0).length()-1);
				    String[] fileAllContents = allContents.split(",");
				    for (String line : fileAllContents) {
				        String[] bookDetails = line.split("~");
				        double price = Double.parseDouble(bookDetails[2]);
				        int total = Integer.parseInt(bookDetails[3]);
				        Book newBook = new Book(bookDetails[0], bookDetails[1], price, total, bookDetails[4]);
				        booksInStore.add(newBook);
				    }
				} catch (IOException e) {
				    e.printStackTrace();
				}

			}
        }
	    catch(IOException e){
	    		e.printStackTrace();
    	}
    }
}

class Book {
    static int count = 0;
    String bookName;
    int ISBN;
    String author;
    double price;
    int quantity;
    String genre;

    Book(String bookName, String author, double price, int quantity, String genre) {
        count++;
        this.bookName = bookName;
        ISBN = count;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.genre = genre;
    }

    String getBookName() {
        return bookName;
    }

    String getAuthorName() {
        return author;
    }

    int getISBNnumber() {
        return ISBN;
    }

    int getQuantity() {
        return quantity;
    }

    double getPrice() {
        return price;
    }

    void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    void updateQuantity(int quantity){
        this.quantity+= quantity;
    }

    void setPrice(double price) {
        this.price = price;
    }

     String getGenre() {
        return genre;
    }

     public String toString() {
        return String.format("Title: %-30s Author: %-30s Price: $%-10.2f Quantity: %-5d Genre: %-15s", bookName, author, price, quantity, genre);
    }
}

class person {
     String userName;
     String password;
     String role;
     static ArrayList<String> userNameList = new ArrayList<String>();
     static ArrayList<String> userPasswordList= new ArrayList<String>();
     static ArrayList<String> roleList= new ArrayList<String>();
     static person currentUser;

    void setName(String userName) {
        this.userName = userName;
    }

    void setPassword(String passWord) {
        this.password = passWord;
    }

    void setPersonInfo(String userName, String passWord) {
        this.userName = userName;
        this.password = passWord;
    }

    String getPersonName() {
        return this.userName;
    }

    String getPersonPassword() {
        return this.password;
    }

    void purchaseBook(Store ourStore) {
    	System.out.println("Override");
    }
    void addBookToStore(Store store, String bookname, String author, double price, int quantity,String genre) {
    	System.out.println("Override");
    }

    void displayUserInfo() {
        System.out.println("Username: " + this.userName + "\nPassword: " + this.password);
    }
void signup(String username, String password, int roleGive) {
    try {
        String fileName = "UserAccount.txt";
        File userFile = new File(fileName);
                // System.out.println(username);

        if (userFile.createNewFile()) {
            FileWriter userFileInput = new FileWriter(fileName, true);
            // person currentUser;

            if (roleGive == 1) {
                person.currentUser = new user();
                 person.currentUser .userName = username;
                 person.currentUser .password = password;
                 person.currentUser .role = "User";
            } else {
                currentUser = new owner();
                 person.currentUser .userName = username;
                 person.currentUser .password = password;
                 person.currentUser .role = "Owner";
            }

            person.userNameList.add( person.currentUser .userName);
            person.userPasswordList.add( person.currentUser .password);
            person.roleList.add( person.currentUser .role);

            userFileInput.write( person.currentUser.userName+ "~" +  person.currentUser.password + "~" +  person.currentUser.role + "\n");
            userFileInput.close();

            System.out.println("──────────────────────────────────");
			System.out.println("Account created successfully!");
			System.out.println("Welcome " +  person.currentUser.userName);
			System.out.println("──────────────────────────────────");

        } else {

            List<String> fileContents = Files.readAllLines(Paths.get("UserAccount.txt"));

            for (String line : fileContents) {
                String[] tempArr = line.split("~");
                person.userNameList.add(tempArr[0]);
                person.userPasswordList.add(tempArr[1]);
                person.roleList.add(tempArr[2]);
            }

            if (person.userNameList.contains(username)) {
                System.out.println("──────────────────────────────────");
				System.out.println(username + " already exists, please try again!");
				System.out.println("──────────────────────────────────");

            } else {
                person currentUser;

                if (roleGive == 1) {
                System.out.println(username);

                     person.currentUser  = new user();
                     person.currentUser.userName = username;
                     person.currentUser.password = password;
                     person.currentUser.role = "User";
                } else {
                    currentUser = new owner();
                    person.currentUser.userName = username;
                    person.currentUser.password = password;
                    person.currentUser.role = "Owner";
                }

                person.userNameList.add(person.currentUser.userName);
                person.userPasswordList.add(person.currentUser.password);
                person.roleList.add(person.currentUser.role);

                FileWriter userFileInput = new FileWriter(fileName, true);
                userFileInput.write(person.currentUser.userName + "~" + person.currentUser.password + "~" + person.currentUser.role + "\n");
                userFileInput.close();

                System.out.println("──────────────────────────────────");
				System.out.println("Account created successfully!");
				System.out.println("Welcome " + person.currentUser.userName);
				System.out.println("──────────────────────────────────");

            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

void signin(String username, String password) {
        boolean userFound = false;

        try {
            List<String> fileContents = Files.readAllLines(Paths.get("UserAccount.txt"));
            for (String line : fileContents) {
                String[] tempArr = line.split("~"); 
                person.userNameList.add(tempArr[0]);
                person.userPasswordList.add(tempArr[1]);
                person.roleList.add(tempArr[2]);
            }

            int index = person.userNameList.indexOf(username);
            if (index != -1 && person.userPasswordList.get(index).equals(password)) {
                String role = person.roleList.get(index);
                if (role.equals("User")) {
                    person.currentUser = new user();
                } else if (role.equals("Owner")) {
                    person.currentUser = new owner();
                }

                person.currentUser.userName = username;
                person.currentUser.password = password;
                person.currentUser.role = role;
                System.out.println("──────────────────────────────────");
				System.out.println("Login Successful! Welcome, " + person.currentUser.userName + ".");
				System.out.println("──────────────────────────────────");

                userFound = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!userFound) {
            System.out.println("Invalid username or password. Please try again.");
        }
    }
    void buyedBooksInfo(){}
    void savePurchasedBooksToFile(String username){}
    void retrievePurchasedBooksFromFile(String username){}
    void setBookQuantity(int bookname, Store ourStore,int quantitySet){}
    void setBookPrice(int bookname, Store ourStore,double priceSet){}
    void updateBookQuantity(int bookname, Store ourStore,int quantitySet){}
    void customerDetails(Store store){};
    static void topSeller(Store store){};
    static void revenue(Store store) {};


}
class owner extends person
{
	String role = "Owner";

	void addBookToStore(Store store, String bookname, String author, double price, int quantity,String genre) {
    Book newBook = new Book(bookname, author, price, quantity,genre);
    store.booksInStore.add(newBook);
    store.booksContent.add(newBook.getBookName() + "~" + newBook.getAuthorName() + "~" + newBook.getPrice() + "~" + newBook.getQuantity()+"~"+newBook.getGenre());

    System.out.println("-------------------------------------------------------");
    System.out.println("|                  Book Added to Store                 |");
    System.out.println("-------------------------------------------------------");
    System.out.println("| Book Name:      " + newBook.getBookName());
    System.out.println("| Author:         " + newBook.getAuthorName());
    System.out.printf(" | Price:          $%.2f%n", newBook.getPrice());
    System.out.println("| Quantity:       " + newBook.getQuantity());
    System.out.println("| Genre:          " + newBook.getGenre());
    System.out.println("-------------------------------------------------------");
  }


    void setBookQuantity(int bookname, Store ourStore,int quantitySet){
    	for(Book book: ourStore.booksInStore){
    		if(bookname==(book.getISBNnumber())){
                book.setQuantity(quantitySet);
                System.out.println("After Updating");
                System.out.println("+--------------------------------+----------------------+-------+----------+");
                System.out.println("| Book Name                      | Author               | Price | Quantity |");
                System.out.println("+--------------------------------+----------------------+-------+----------+");
                System.out.println(String.format("| %-30s | %-20s | $%-5.2f | %-8d |", book.getBookName(), book.getAuthorName(), book.getPrice(), book.getQuantity()));
                System.out.println("+--------------------------------+----------------------+-------+----------+");
                System.out.println();
    			break;
    		}
    	}
    }
    void updateBookQuantity(int bookname, Store ourStore,int quantityUpdate){
        for(Book book : ourStore.booksInStore){
            if(bookname==(book.getISBNnumber())){
                System.out.println("Before Updating");
                System.out.println("+--------------------------------+----------------------+-------+----------+");
                System.out.println("| Book Name                      | Author               | Price | Quantity |");
                System.out.println("+--------------------------------+----------------------+-------+----------+");
                System.out.println(String.format("| %-30s | %-20s | $%-5.2f | %-8d |", book.getBookName(), book.getAuthorName(), book.getPrice(), book.getQuantity()));
                System.out.println("+--------------------------------+----------------------+-------+----------+");
                System.out.println();
                book.updateQuantity(quantityUpdate);
                System.out.println("After Updating");
                System.out.println("+--------------------------------+----------------------+-------+----------+");
                System.out.println("| Book Name                      | Author               | Price | Quantity |");
                System.out.println("+--------------------------------+----------------------+-------+----------+");
                System.out.println(String.format("| %-30s | %-20s | $%-5.2f | %-8d |", book.getBookName(), book.getAuthorName(), book.getPrice(), book.getQuantity()));
                System.out.println("+--------------------------------+----------------------+-------+----------+");
                System.out.println();
                break;
            }
        }
    }
    void setBookPrice(int bookname, Store ourStore,double priceSet){
    	for(Book book : ourStore.booksInStore){
    		if(bookname==(book.getISBNnumber())){
    			System.out.println("Before Updating");
                System.out.println("+--------------------------------+----------------------+-------+----------+");
                System.out.println("| Book Name                      | Author               | Price | Quantity |");
                System.out.println("+--------------------------------+----------------------+-------+----------+");
                System.out.println(String.format("| %-30s | %-20s | $%-5.2f | %-8d |", book.getBookName(), book.getAuthorName(), book.getPrice(), book.getQuantity()));
                System.out.println("+--------------------------------+----------------------+-------+----------+");
                System.out.println();
                book.setPrice(priceSet);
                System.out.println("After Updating");
                System.out.println("+--------------------------------+----------------------+-------+----------+");
                System.out.println("| Book Name                      | Author               | Price | Quantity |");
                System.out.println("+--------------------------------+----------------------+-------+----------+");
                System.out.println(String.format("| %-30s | %-20s | $%-5.2f | %-8d |", book.getBookName(), book.getAuthorName(), book.getPrice(), book.getQuantity()));
                System.out.println("+--------------------------------+----------------------+-------+----------+");
                System.out.println();
    			break;
    		}
    	}
    }



void customerDetails(Store store) {
    ArrayList<String> userList = new ArrayList<>();
    ArrayList<String> userRole = new ArrayList<>();
    try {
        List<String> fileContents = Files.readAllLines(Paths.get("UserAccount.txt"));
        for (String line : fileContents) {
            String[] tempArr = line.split("~");
            userList.add(tempArr[0]);
            userRole.add(tempArr[2]);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    for (int i = 0; i < userList.size(); i++) {
        if (userRole.get(i).equals("User")) {
            try {
                String fileName = userList.get(i) + "_PurchasedBooks.txt";
                File file = new File(fileName);

                if (file.exists()) {
                    Scanner scanner = new Scanner(file);
                    // System.out.println("──────────────────────────────────");
                    // System.out.println(String.format("%-20s%-30s", "Customer details", ""));
                    System.out.println("──────────────────────────────────────────────────────────────────");
                    System.out.println(String.format("%-20s%-30s", "User", "Purchased Books"));
                    System.out.println("──────────────────────────────────────────────────────────────────");
                    System.out.println(String.format("%-20s%-30s", userList.get(i), ""));

                    while (scanner.hasNextLine()) {
                        System.out.println(String.format("%-20s%-30s", "", scanner.nextLine().trim()));
                    }
                    System.out.println();
                    scanner.close();
                } else {
                    System.out.println("\u001B[31mNo purchased books found for user: " + userList.get(i) + "\u001B[0m");
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}




   static void topSeller(Store store) {
    int maxCount = 0;
    Book topSellerBook = null; // Initialize the top seller book reference

    for (Book book : store.booksInStore) {
        int totalCount = 0;

        for (int i = 0; i < person.userNameList.size(); i++) {
            int count = 0;
            if (person.roleList.get(i).equals("User")) {
                try {
                    String fileName = person.userNameList.get(i) + "_PurchasedBooks.txt";
                    File file = new File(fileName);

                    if (file.exists()) {
                        Scanner scanner = new Scanner(file);
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine().trim();
                            String[] splitted = line.split("\\(");
                            if (splitted.length == 2) {
                                int countx = Integer.parseInt(splitted[1].replaceAll("[^0-9]", ""));
                                if (splitted[0].trim().equals(book.getBookName().trim())) {
                                    count += countx;
                                }
                            }
                        }
                        scanner.close();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            totalCount += count;
        }
        if (totalCount > maxCount) {
            maxCount = totalCount;
            topSellerBook = book;
        }
    }

    if (topSellerBook != null) {
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║               Top Seller Book Information         ║");
        System.out.println("╠═══════════════════════════════════════════════════╣");
        System.out.println("║ Top Seller Book        | Total Sale Count         ║");
        System.out.println("╠════════════════════════╪══════════════════════════╣");
        System.out.printf("║ %-22s | %-24d ║%n", topSellerBook.getBookName().trim(), maxCount);
        System.out.println("╚════════════════════════╧══════════════════════════╝");



    } else {
        System.out.println("No top seller found."); 
    }
}
static void revenue(Store store) {
    System.out.println("----------------------------------------------------------------------------------------------------");
    System.out.printf("%-30s%-15s%-15s%-20s%n", "Title", "Price", "Sold Count", "Total Income");
    System.out.println("----------------------------------------------------------------------------------------------------");

    double totalStoreRevenue = 0;
    int totalBooksSold = 0;

    for (Book book : store.booksInStore) {
        int totalSales = 0;
        double bookRevenue = 0;

        for (int i = 0; i < person.userNameList.size(); i++) {
            int userSales = 0;

            if (person.roleList.get(i).equals("User")) {
                try {
                    String fileName = person.userNameList.get(i) + "_PurchasedBooks.txt";
                    File file = new File(fileName);

                    if (file.exists()) {
                        Scanner scanner = new Scanner(file);
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine().trim();
                            String[] splitted = line.split("\\(");
                            if (splitted.length == 2) {
                                int countx = Integer.parseInt(splitted[1].replaceAll("[^0-9]", ""));
                                if (splitted[0].trim().equals(book.getBookName().trim())) {
                                    userSales += countx;
                                }
                            }
                        }
                        scanner.close();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            totalSales += userSales;
            bookRevenue = totalSales * book.getPrice();
        }

        if (totalSales > 0) {
            double bookTotalRevenue = bookRevenue;
            totalStoreRevenue += bookTotalRevenue;
            totalBooksSold += totalSales;

            System.out.printf("%-30s%-15.2f%-15d%-20.2f%n",
                    book.getBookName().trim(),
                    book.getPrice(),
                    totalSales,
                    bookTotalRevenue);
        }
    }

    System.out.println("----------------------------------------------------------------------------------------------------");
    System.out.println("Total Store Revenue: $" + totalStoreRevenue);
    System.out.println("Total Books Sold: " + totalBooksSold);
    System.out.println("----------------------------------------------------------------------------------------------------");
}



}
class user extends person {
    String role = "Customer";
     ArrayList<String> buyedBooks = new ArrayList<String>();

    String getRole() {
        return this.role;
    }



    void purchaseBook(Store ourStore) {
        Scanner scanner = new Scanner(System.in);
        ourStore.displayBooks();
        System.out.println("Enter the ISBN number of the book you want to purchase: ");
        int selectedBookIBSN = scanner.nextInt();
        Book selectedBook = null;
        for (Book book : ourStore.booksInStore) {
            if (selectedBookIBSN == book.getISBNnumber()) {
                selectedBook = book;
                break;
            }
        }
        if (selectedBook != null) {
            System.out.print("Enter the quantity: ");
            int quantity = scanner.nextInt();

            if (quantity > 0 && quantity <= selectedBook.getQuantity()) {
                selectedBook.setQuantity(selectedBook.getQuantity() - quantity);
                double totalPrice = selectedBook.getPrice() * quantity;
                System.out.println("Pay the amount $ "+totalPrice);
                double  payedamount = scanner.nextDouble();
                if(totalPrice==payedamount){
                	System.out.println("Book(s) purchased successfully! Total price: $" + totalPrice);
                	this.buyedBooks.add(selectedBook.getBookName()+" ("+quantity+")");
                	savePurchasedBooksToFile(this.userName);
                }
                else{
                	System.out.println("Check your amount");
                }
            }
            else {

                System.out.println("Invalid quantity or insufficient stock.");
            }
        }
         else {
            System.out.println("Book not found in the store.");
        }
    }
void savePurchasedBooksToFile(String username) {
    try {
        String fileName = username + "_PurchasedBooks.txt";
        FileWriter userFileInput = new FileWriter(fileName);
        BufferedWriter bufferedWriter = new BufferedWriter(userFileInput);

        for (String book : this.buyedBooks) {
            bufferedWriter.write(book);
            bufferedWriter.newLine();
        }

        bufferedWriter.close();
        // System.out.println("Purchased books saved to file for user: " + username);
    } catch (IOException e) {
        e.printStackTrace();
    }
}

void retrievePurchasedBooksFromFile(String username) {
    try {
    	if (username == null || username.isEmpty()) {
            System.out.println("Invalid username. Please provide a valid username.");
            return;
        }

        String fileName = username + "_PurchasedBooks.txt";
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("No purchased books found for user: " + username);
            try {
                if (file.createNewFile()) {
                    System.out.println("New file created for user: " + username);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        Scanner scanner = new Scanner(file);
        ArrayList<String> purchasedBooks = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String book = scanner.nextLine();
            purchasedBooks.add(book);
        }
        scanner.close();
        this.buyedBooks = purchasedBooks;
        // System.out.println("Retrieved purchased books for user: " + username);
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
}


  void buyedBooksInfo() {
    if (buyedBooks.size() == 0) {
        System.out.println("You haven't purchased any books yet.");
    } else {
        System.out.println("--------------------------------------------------------");
        System.out.println("|                    Purchased Books                    |");
        System.out.println("--------------------------------------------------------");
        for (String book : buyedBooks) {
            System.out.printf("| %-50s |%n", book.trim());
        }
        System.out.println("--------------------------------------------------------");
    }
}




}



class mainProject {
	public static void main(String[] args) {	
    String title = "░█▀▀▀█ ░█▀▀▀█ ░█─░█ ░█▀▀▀█ 　 ░█▀▀█ ░█▀▀▀█ ░█▀▀▀█ ░█─▄▀ 　 ░█▀▀▀█ ▀▀█▀▀ ░█▀▀▀█ ░█▀▀█ ░█▀▀▀ \n"+
                   "─▄▄▄▀▀ ░█──░█ ░█▀▀█ ░█──░█ 　 ░█▀▀▄ ░█──░█ ░█──░█ ░█▀▄─ 　 ─▀▀▀▄▄ ─░█── ░█──░█ ░█▄▄▀ ░█▀▀▀ \n"+
                   "░█▄▄▄█ ░█▄▄▄█ ░█─░█ ░█▄▄▄█ 　 ░█▄▄█ ░█▄▄▄█ ░█▄▄▄█ ░█─░█ 　 ░█▄▄▄█ ─░█── ░█▄▄▄█ ░█─░█ ░█▄▄▄";
    System.out.println(title);
    Store myStore = new Store();
    myStore.printBookToFile(myStore);
    System.out.println("──────────────────────────────────");
	System.out.println("Welcome to our Application.");
	System.out.println("\nCould you please Sign up for an Account or Sign in if you already have one.\n");
	System.out.println("1. Sign up.\n2. Sign in\n");
	System.out.println("Please Enter the corresponding number.");
	System.out.println("──────────────────────────────────");

    Scanner getData = new Scanner(System.in);
    int loginNum = getData.nextInt();
    
    System.out.println("Enter your user Name");
    getData.nextLine();
    String userName = getData.nextLine();
    
    System.out.println("Enter your password");
    String password = getData.nextLine();
    person.currentUser = null;
    
    if (loginNum == 1) {
        // System.out.println("Enter your Role type 1 for user 2 for Admin");
        // int role = getData.nextInt();
        // if (role == 1) {
            person.currentUser = new user();
        // } else if (role == 2) {
            // person.currentUser = new owner();
        // } else {
        //     System.out.println("Invalid role selected.");
        // }

        if (person.currentUser != null) {
            System.out.println("SignUp");
            person.currentUser.signup(userName, password, 1);
        }
    } else if (loginNum == 2) {
        person.currentUser = new person(); 
        person.currentUser.signin(userName, password);

    } else {
        System.out.println("Invalid input");
    }

    boolean loggedIn = person.currentUser != null; 

    if (loggedIn) {
    	person.currentUser.retrievePurchasedBooksFromFile(person.currentUser.getPersonName());
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("──────────────────────────────────");
			System.out.println("Select an action:");
			System.out.println("1. Display Available Books");
            if (person.currentUser.role.equals("User")) {
                System.out.println("2. Purchase a Book");
            }
            else{
                System.out.println("2. Add a New Book");
            }
			if (person.currentUser.role.equals("User")){
                System.out.println("3. Show Purchased Books");
            }
            else{
                System.out.println("3. Set Book Price and Quantity");
            }
			System.out.println("4. User Info");
			System.out.println("5. SearchBook (BookName or Author or genre)");
            System.out.println("6. Top Seller");
            if (person.currentUser.role.equals("Owner")) {
                 System.out.println("7. Customer details");
                 System.out.println("8. Revenue details");
                 System.out.println("9. Restock books");
                 System.out.println("10. Exit");
                 
            }
            else{
                System.out.println("7. Exit");
            }
			System.out.println("──────────────────────────────────");

            int action = scanner.nextInt();

            switch (action) {
            	case 1:
            			System.out.println();
                        myStore.displayBooks();
                        System.out.println();
                        break;
                    case 2:
                    	if (person.currentUser != null) {
						    // Your code that uses currentUser, for example:
						    if (person.currentUser.role.equals("User")) {
						    	System.out.println();
						        person.currentUser.purchaseBook(myStore);
						        System.out.println();
						    } else {
						        Scanner input = new Scanner(System.in);
                                System.out.println("Enter book name:");
                                String bookName = input.nextLine();
                                System.out.println("Enter author name:");
                                String author = input.nextLine();
                                System.out.println("Enter price:");
                                double price = input.nextDouble();
                                System.out.println("Enter quantity:");
                                int quantity = input.nextInt();
                                input.nextLine();
                                System.out.println("Enter genre:");
                                String genreget = input.nextLine();
                                
                                person.currentUser.addBookToStore(myStore, bookName, author, price, quantity,genreget);
						    }
						} else {
						    System.out.println("Current user is null!");
						}

                        break;
                    case 3:
                       // Inside your main logic or authentication section

						if (loggedIn) {
						    if (person.currentUser.role.equals("User")) {
						        System.out.println("Purchased Books:");
						        System.out.println(person.currentUser.getPersonName());
						        System.out.println(person.currentUser.userName);
						        person.currentUser.retrievePurchasedBooksFromFile(person.currentUser.getPersonName());
						        person.currentUser.buyedBooksInfo();
						        person.currentUser.savePurchasedBooksToFile(person.currentUser.getPersonName());
						    }  else {
						    	Scanner input = new Scanner(System.in);
                                System.out.println();
                                System.out.println("Select an option:");
                                System.out.println("1. setBookPrice");
                                System.out.println("2. setBookQuantity");
                                System.out.println("3. both");
                                int choice = input.nextInt(); // Read an integer choice

                                input.nextLine(); // Consume the newline character after nextInt()
                                myStore.displayBooks();
                                    if (choice >= 1 && choice <= 3) {
                                        System.out.println("Enter Book ISBN:");
                                        int bookname = input.nextInt();
                                        System.out.println("Before Updating");
                                        Store.diplayForSetAndUpdate(bookname,myStore);

                                        if (choice == 2 || choice == 3) {
                                            System.out.println("Enter quantity:");
                                            int quantity = input.nextInt();
                                            System.out.println("Do you want update quantity or set quantity");
                                            System.out.println("1. Update Quantity");
                                            System.out.println("2. Set Quantity");
                                            int choiseSet = input.nextInt();
                                            if(choiseSet==1){
                                                person.currentUser.updateBookQuantity(bookname,myStore,quantity);
                                                System.out.println("Added Successfully");
                                                 System.out.println("After Updating");
                                                Store.diplayForSetAndUpdate(bookname,myStore);
                                            }
                                            else if (choiseSet==2){
                                                System.out.println("Before Updating");
                                                Store.diplayForSetAndUpdate(bookname,myStore);
                                                person.currentUser.setBookQuantity(bookname,myStore,quantity);
                                                System.out.println("Set Successfully");
                                                System.out.println("After Updating");
                                                Store.diplayForSetAndUpdate(bookname,myStore);
                                            }
                                            else{
                                                System.out.println("Invalid choice");
                                            }
                                            
                                        }
                                        if (choice == 1 || choice == 3) {
                                            System.out.println("Enter price:");
                                            double price = input.nextDouble();
                                            person.currentUser.setBookPrice(bookname,myStore,price);
                                            System.out.println("Set Successfully");
                                        }
                                    }
                                         else {
                                            System.out.println("───────────────────────────────────────");
                                            System.out.println("            Invalid choise");
                                            System.out.println("───────────────────────────────────────");
                                        }
            						    }
						}

                        break;
                case 4:
                    if(person.currentUser.role.equals("Owner")){
                        System.out.println("──────────────────────────────────");
                        System.out.println("           User Details            ");
                        System.out.println("──────────────────────────────────");
                        System.out.println("Name: " + person.currentUser.getPersonName());
                        System.out.println("Role: " + "Admin");
                        System.out.println("──────────────────────────────────");

                    }
                    else{
                        System.out.println("──────────────────────────────────");
                        System.out.println("           User Details            ");
                        System.out.println("──────────────────────────────────");
                        System.out.println("Name: " + person.currentUser.getPersonName());
                        System.out.println("Role: " + person.currentUser.role);
                        person.currentUser.retrievePurchasedBooksFromFile(person.currentUser.getPersonName());
                        person.currentUser.buyedBooksInfo();
                        person.currentUser.savePurchasedBooksToFile(person.currentUser.getPersonName());
                        System.out.println("──────────────────────────────────");

                    }
                    break;
                case 5:
                	 Scanner input = new Scanner(System.in);
                    System.out.println("╔══════════════════════╗");
                    System.out.println("║   🌟 Search Bar 🌟   ║");
                    System.out.println("╚══════════════════════╝");
                    String searchItem = input.nextLine();
                    Store.searchBook(myStore,searchItem);
                    break;
				case 7:
                    if(person.currentUser.role.equals("Owner")){
                        System.out.println("──────────────────────────────────");
                        System.out.println("        Customer details     ");
                        System.out.println("──────────────────────────────────");
                        person.currentUser.customerDetails(myStore);
                    }
                    else{
                        running = false;
                        System.out.println("──────────────────────────────────");
                        System.out.println("   Exiting the application...     ");
                        System.out.println("──────────────────────────────────");
                        myStore.setBookStorageToFile();
                    }
                    break;
                case 8:
                    if(person.currentUser.role.equals("Owner")){
                        owner.revenue(myStore);
                    }
                    else{
                         System.out.println("⚠️ Invalid action selected. Please choose a valid option from the menu. ⚠️");
                    }
                    break;
                case 6:
                    owner.topSeller(myStore);
                    break;
                case 9:
                    if(person.currentUser.role.equals("Owner")){
                        myStore.reStockableBooks();
                    }
                    else{
                        System.out.println("⚠️ Invalid action selected. Please choose a valid option from the menu. ⚠️");
                    }
                    break;
                case 10:
                    running = false;
                    System.out.println("──────────────────────────────────");
                    System.out.println("   Exiting the application...     ");
                    System.out.println("──────────────────────────────────");
                    myStore.setBookStorageToFile();
                    break;
                default:
                   System.out.println("⚠️ Invalid action selected. Please choose a valid option from the menu. ⚠️");
                    break;
            }
        }
    }
    else{
    	System.out.println("Please Login");
    }
}
}
  
