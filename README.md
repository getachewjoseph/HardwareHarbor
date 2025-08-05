HardwareHarbor Marketplace

HardwareHarbor is a Java-based client-server application that simulates a hardware product marketplace. Users can sign up as buyers or sellers, manage product listings or purchase history, and interact with a multi-client server using a GUI interface. File import/export, user authentication, data persistence, and analytics dashboards are all supported features.
Getting Started
Requirements

    Java 8 or higher

    Git (for cloning the repository)

Setup Instructions

    Clone the Repository

git clone https://github.com/yourusername/hardwareharbor.git
cd hardwareharbor

Compile the Source Code
Compile the client and server Java files:

javac HardwareHarborServer.java HardwareHarborClient.java

Run the Server

java HardwareHarborServer

Run the Client(s)
You can launch multiple clients to simulate multiple users:

    java HardwareHarborClient

System Overview
User Roles

    Buyers: Can browse listings, manage a shopping cart, and view purchase history.

    Sellers: Can manage stores, upload/export product data, and analyze store performance.

Client-Server Interaction

    Built using sockets to support concurrent clients.

    Server handles login, account management, and transaction logic.

    Clients handle user interaction through GUI interfaces.

Components
Core Classes

    User
    Abstract class for common user attributes and methods, such as username and password management.

    Buyer
    Inherits from User. Manages shopping cart, purchase history, and related actions.

    Seller
    Inherits from User. Manages stores and listings.

    Listing
    Represents a product listing with fields for name, store, description, price, and quantity.

    Store
    Contains product listings. Sellers can create and manage multiple stores.

    Files
    Handles import/export of CSV files for both product listings and purchase history.

    Statistics
    Provides analytics for buyers and sellers, including sales tracking and customer sorting.

    HardwareHarbor
    Primary application logic. Manages authentication, menus, data persistence, and high-level control flow.

GUI Components

    MarketplaceGUI
    Main interface for browsing and interacting with listings.

    CartGUI
    Displays the user's shopping cart.

    CartItemsGUI
    Visualizes individual items in the cart.

    PhGUI
    Displays purchase history.

    StoresRevenueGUI
    Displays store revenue analytics for sellers.

    ViewStoresGUI
    Allows buyers to browse available stores.

Network Classes

    HardwareHarborClient
    Client application that connects to the server, manages user interface interactions, and sends/receives data.

    HardwareHarborServer
    Handles client connections, user sessions, and shared marketplace state.

Features

    Multi-client concurrency using socket programming

    Role-based user experience (buyer vs seller)

    GUI interfaces for interaction and visualization

    File import/export (CSV) for product data and purchase history

    Persistent user and product data storage

    Sort and filter capabilities for buyers and sellers

    Password management and account deletion

    Analytics and dashboards for revenue and activity

Testing

Unit tests and simulations for expected behavior are documented in the Tests.md file. Manual testing was also conducted through the GUI and console interfaces to verify correct integration of features.
