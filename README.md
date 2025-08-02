# Project 5 - Marketplace

<h3>How to Compile and Run the Marketplace</h3>
<p>1. Clone the repository from GitHub</p>
<p>2. After cloning, compile the HardwareHarborClient.java and HardwareHarborServer.java files</p>
<p>3. Run the HardwareHarborServer.java, then run as many of the HardwareHarborClient.java clients as desired</p>
<p>4. The marketplace is run through a run method in HardwareHarborServer.java</p>

<h3>Submissions</h3>
<p>Ella Budack - Submitted Report on Brightspace</p>
<p>Ella Budack - Submitted Vocareum workspace</p>

<h3>Class Descriptions</h3>

<h4>Buyer.java</h4>
The buyer class creates a buyer role. User roles are specified in the HardwareHarbor.java when the user creates a new account. Buyer class methods include getShoppingCart, getItemsInCart, getPurchaseHistory, setShoppingCart, and makePurchase. All of the getters return values, and the setter sets the vaule. The makePurchase method allows the buyer to make a purchase. The buyer class extends the user superclass. Testing for this class include calling the methods in the main method in HardwareHarbor and ensuring they perform the correct functions.

<h4>Files.java</h4>
The files class implements the ability to import and export files. Users with the role of seller can import a file of products to their store (uploading) and export a file of products from their store (exporting). Users with the role of customer can export a file with their purchase history. All of the files involved in importing/exporting are comma separated value files, or .csv files. This class is used in HardwareHarbor.java as a menu item/option for buyers and sellers. When a buyer chooses to export their purchase history, the exportPurchaseHistory method is called. Similarly, when sellers choose to upload or export their products, importProducts and exportProducts are called (respectively). Testing for this class include calling the methods and using example/sample data to ensure the data is imported and exported smoothly and in the correct format.

<h4>HardwareHarbor.java</h4>
HardwareHarbor.java is where our main marketplace is housed. This class initializes the marketplace and brings all of our other classes together. The methods in this class include initializeApplication, buyerMenu, sellerMenu, addListingsToStore, uploadProduct, exportProducts, exportPurchaseHistory, changePasswordSeller, deleteAccountSeller, changePasswordBuyer, deleteAccountBuyer, storeDataSeller, readDataSeller, storeDataBuyer, readDataBuyer, and a main method. The method initializeApplication runs the log-in/sign-up sequence and checks for username uniqueness, ensures passwords have the necessary requirements, and handles if the user enters an invalid number or an account cannot be found. The methods buyerMenu and sellerMenu work very similarly: both methods display a menu with action options based on which role the user is (buyer or seller). The addListingToStore method asks a seller for all the product details associated with a new product listing and creates a listing out of it. The uploadProduct, exportProducts, exportPurchase history are all methods that call to the respective methods in Files.java. The methods changePasswordSeller and changePasswordBuyer both change the passwords of the seller and buyer respectively. The methods deleteAccountSeller and deleteAccountBuyer both delete accounts (of sellers and buyers respectively). To store the data of the buyers and sellers, we created the methods storeDataBuyer and storeDataSeller. To read this stored data, we created the methods readDataBuyer and readDataSeller. The main method is the method that calls all these methods and is the main way we tested every class as we went along.

<h4>Listing.java</h4>
The listing class is how individual products are represented. This class uses a constructor to create a Listing, consisting of the product's name, the name of the store the product belongs to, the description of the product, the price of the product, and the quantity of the product available. The methods include getStore, getQuantity, getPrice, getName, setPrice, setName, setDescription, toString, toString2, dataWrite, and dataWrite2. All of the getters return values, and the setter sets the vaule. The toString returns a string with the store name and the product details. The toString2 returns a string of only the product details (no store name). The method dataWrite writes the product details (excluding the store name). The method dataWrite2 writes the product details and the store name. This listing connects the the store class because each store has an array list of listings. Testing this class involves calling the methods of the listing class in the store class and the HardwareHarbor.java class.

<h4>Seller.java</h4>
The seller class creates a seller role. User roles are specified in the HardwareHarbor.java when the user creates a new account. Seller class methods include getStores, setStores, addStore, and dataWrite. The getter returns a value and the setter sets the vaule. The addStore method allows the seller to create a store. The seller class extends the user superclass. Testing for this class include calling the methods in the main method in HardwareHarbor and ensuring they perform the correct functions.

<h4>Store.java</h4>
The store class represents a store. The constructor has the store's name and an array list of products. The class has getName, setName, addItem, removeItem, displayStore, displayStore2, getItemInStore, dataWrite, and getProducts methods. The getters return values and the setters set values. The methods addItem and removeItem adds listings to and removes listings from the store. The method displayStore displays the products in the store and calls displayStore2. The method displayStore2 displays each of the products's name and price. Sellers have the ability to create any number of stores they desire. Testing for this class include calling the methods in the main method in HardwareHarbor and ensuring they perform the correct functions.

<h4>User.java</h4>
The class user is a superclass. It has two inheritors: the buyer class and the seller class. The user class creates a user that has a username and a password. The methods in this class are getUsername, getPassword, setUsername, and setPassword. The getters return values and the setters set values. This class is tested when the initializeApplication method in HardwareHarbor.java is called. We have ran the initializeApplication method multiple times to ensure it runs as expected and correctly handles improper input.

<h4>Statistics.java</h4>
The class statistics contains methods for displaySellerDashboard, displayBuyerDashboard, sortByItemsPurchased, sortByAmount, and checkInput. The displaySellerDashboard and displayBuyerDashboard are similar, they display similar options for the seller and buyer to sort information. Sellers can sort customers by the number of items that they have purchased and sort products by the number of sales. Each of these can be sorted least to greatest or greatest to least. Customers can sort stores by the number of products sold (which store is most popular/has the most sales) and sort stores by number of items purchased (by the buyer). Just like the seller sort options, both of sort functions can be sorted least to greatest or greatest to least. Testing for this class include calling the methods in the main method in HardwareHarbor and ensuring they sort the data correctly.

<h4>CartGUI.java</h4>
This java file creates the GUI for the cart. Within the main method this file has a run method and an action listener. The run method will create the cart GUI when needed and the action listener will listen for when an action is performed and dispose the frame if the ok button is clicked.

<h4>CartItemsGUI.java</h4>
This java file creates the GUI for the cart items. Within the main method this file has a run method and an action listener. The run method will create a GUI for the cart items when needed and the action listener will listen for when an action is performed and dispose the frame if the ok button is clicked.

<h4>HardwareHarborClient.java</h4>
This java file creates the client for the HardwareHarbor marketplace. There is a method that creates a new socket, buffered reader, print writer, and a main method. This class displays all of the client side things like GUI's. This class also interacts with the HardwareHarborServer.java file. Together, the client and server allow for socket programming and for multiple users to log-in/interact with the market place at once (concurrency implementation).

<h4>HardwareHarborServer.java</h4>
This java file creates the server for the HardwareHarbor marketplace. There is a method that creates a new socket, a main method, and a run method. This class handles the welcome sequence for the HardwareHarbor marketplace with the run method. The main method allows the client to connect to the server. It also allows for multiple clients to connect at a time to this one server. This class also interacts with the HardwareHarborClient.java file. Together, the client and server allow for socket programming and for multiple users to log-in/interact with the market place at once (concurrency implementation).

<h4>MarketplaceGUI.java</h4>
This java file creates the GUI for the HardwareHarbor marketplace. Within the main method this file has a run method and an action listener. The run method will the cart itemswhen needed and the action listener will listen for when an action is performed and respond accordingly. For instance if the user chooses to filter the items, the MarketplaceGUI will then present GUI that allows the user to make a choice on how to sort items; selecting 'add item to cart' will add the item to the user's cart.

<h4>PhGUI.java</h4>
This java file creates the GUI for a user's purchase history. Within the main method this file has a run method and an action listener. The run method will create a GUI for to display the user's purchase history when needed and the action listener will listen for when an action is performed and dispose the frame if the ok button is clicked.

<h4>StoresRevenueGUI.java</h4>
This java file creates the GUI for a store's revenue. Within the main method this file has a run method and an action listener. The run method will create a GUI for to display the store's revenue when needed and the action listener will listen for when an action is performed and dispose the frame if the ok button is clicked.

<h4>ViewStoresGUI.java</h4>
This java file creates the GUI for viewing stores. This file has a run method and an action listener. The run method will create a GUI for to display the stores for viewing and the action listener will listen for when an action is performed and dispose the frame if the ok button is clicked. There is also a setter for the ViewStoresGUI method that sets the values for the GUI.

<h4>Test Cases Note</h4>
Test cases are simulated with expected results in the Tests.md file.
