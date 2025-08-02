# Test Cases

<h3>Test 1: User Sign Up</h3>

Steps:

 1. User launches application.
 2. User selects create account.
 3. User selects the username textbox.
 4. User enters a username via the keyboard.
 5. User selects "OK" button.
 6. User selects the password textbox.
 7. User creates a password via the keyboard.
 8. User selects the "OK" button. 
 9. User selects a role (buyer or seller).
 10. User clicks "OK" button.

Expected result: Application creates the user's account and loads their marketplace options automatically.

Test Status: Passed. 


<h3>Test 2: User Log-In</h3>

Steps:

1.  User launches application.
2.  User selects sign in.
3.  User selects the username textbox.
4.  User enters username via the keyboard
5. User selects "OK" button.
6. User selects the password textbox.
7. User enters password via the keyboard.
8. User selects the "OK" button.
9. User sees account found message.
10. User clicks "OK" button.

Expected result: Application finds the user's account and loads their marketplace options automatically.

Test Status: Passed.


<h3>Test 3: User Log-In Failed</h3>

Steps:

1. User launches application.
2. User selects sign in.
3. User selects the username textbox.
4. User enters username via the keyboard.
5. User selects "OK" button.
6. User selects the password textbox.
7. User enters incorrect password via the keyboard.
8. User selects the "OK" button.
9. User sees account not found message.
10. User clicks "OK" button.
11. User reasked for account username.

Expected result: Application displays an account not found message since an incorrect password was entered. A windown pops up asking the user to reenter the username (another attempt to log in).

Test Status: Passed. 

<h3>Test 4: Create New Store</h3>

Steps:

 1. User selects create new store.
 2. User enters new store name via the keyboard.
 3. User selects "OK" button.
 4. User selects yes to add new listing.
 5. User enters new item name via the keyboard.
 6. User selects the "OK" button.
 7. User enters item price via the keyboard.
 8. User selects the "OK" button.
 9. User enters item description via the keyboard.
 10. User selects the "OK" button.
 11. User enters item quantity via the keyboard.
 12. User selects the "OK" button.
 13. User asked if they would like to create another listing.
 14. User selects no.
 15. User sees store added successfully message.
 16. User clicks "OK" button.
 17. User is returned to their marketplace options.

Expected result: Application allows the seller to successfully add a new store with as many listings as the seller desires.

Test Status: Passed.

<h3>Test 5: Delete Store</h3>

Steps:

 1. User selects create new store.
 2. User enters new store name via the keyboard.
 3. User selects "OK" button.
 4. User selects yes to add new listing.
 5. User enters new item name via the keyboard.
 6. User selects the "OK" button.
 7. User enters item price via the keyboard.
 8. User selects the "OK" button.
 9. User enters item description via the keyboard.
 10. User selects the "OK" button.
 11. User enters item quantity via the keyboard.
 12. User selects the "OK" button.
 13. User asked if they would like to create another listing.
 14. User selects no.
 15. User sees store added successfully message.
 16. User clicks "OK" button.
 17. User selects delete store.
 18. User enters name of store that they just created.
 19. User sees store removed successfully
 20. User returned to the seller screen.

Expected result: Application allows the seller to successfully delete as many stores as they want.

Test Status: Passed.

<h3>Test 6: Delete Store Error</h3>

Steps:

 1. User selects delete store.
 2. User enters a name of a store that they don't have via the keyboard.
 3. User selects "OK" button.
 4. User sees no stores found with that name.
 5. User asked if they would like to remove a different store.
 6. User selects no.
 7. User selects "OK" button.
 8. User returned to the seller screen.

Expected result: Application sucessfully prompts an error message if user tries to delete the name of a store that doesn't exist.

Test Status: Passed.

<h3>Test 7: User Logout</h3>

Steps:

 1. User selects logout.
 2. User selecs "OK" button.

Expected result: Application sucessfully logs out a user.

Test Status: Passed.

<h3>Test 8: Add a listing to Store</h3>

Steps:

 1. User selects create new listing.
 2. User selecs "OK" button.
 3. User asked what store they would like to add items to.
 4. User enters a name of a store that they have previously created.
 5. User selects the "OK" button.
 6. User asked what is the name of the item.
 7. User enters a name.
 8. User selects the "OK" button.
 9. User asked what is the price of the item.
 10. User enters a price.
 11. User selects the "OK" button.
 12. User asked what is the description of the item.
 13. User enters a description.
 14. User selects the "OK" button.
 15. User asked what is the quantity of the item.
 16. User enters a quantity.
 17. User selects the "OK" button.
 18. User sees item added successfully.
 19. User selects the "OK" button.
 20. User returned to the seller screen.

Expected result: Application sucessfully adds listing to pre existing stores.

<h3>Test 9: Edits a listing from a Store</h3>

Steps:

 1. User selects edit listing.
 2. User selects "OK" button.
 3. User selects edit listing.
 4. User selects "OK" button.
 5. User asked what store the item is in.
 6. User enters the name of the store that the item exists in.
 7. User selects the "OK" button.
 8. User asked what is the name of the item.
 9. User enters a name.
 10. User selects the "OK" button.
 11. User asked what is the new price of the item.
 12. User enters a price.
 13. User selects the "OK" button.
 14. User asked what is the new description of the item.
 15. User enters a description.
 16. User selects the "OK" button.
 17. User asked what is the new quantity of the item.
 18. User enters a quantity.
 19. User selects the "OK" button.
 20. User returned to the seller screen.
 

Expected result: Application sucessfully edits listings from pre existing stores.

<h3>Test 10: Remove a listing from a Store</h3>

Steps:

 1. User selects edit listing.
 2. User selects "OK" button.
 3. User selects remove listing.
 4. User selects "OK" button.
 5. User asked what store the item is in.
 6. User enters the name of the store that the item exists in.
 7. User selects the "OK" button.
 8. User asked what is the name of the item.
 9. User enters a name.
 10. User selects the "OK" button.
 11. User returned to the seller screen.

Expected result: Application sucessfully removes listings from pre existing stores.

Test Status: Passed.

<h3>Test 11: Remove a listing from a Store Error</h3>

Steps:

 1. User selects edit listing.
 2. User selects "OK" button.
 3. User selects remove listing.
 4. User selects "OK" button.
 5. User asked what store the item is in.
 6. User enters the incorrect name of the store that the item exists in.
 7. User selects the "OK" button.
 8. User sees store not found.
 9. User selects the "OK" button.
 10. User returned to the seller screen.

Expected result: Application sucessfully displays an error message when trying to remove a listing that doesn't exist.

Test Status: Passed.

<h3>Test 12: Export Products from your Store</h3>

Steps:

1. Sign in as a Seller.
2. User selects Export Products.
3. User Selects OK.
4. User is asked what store they would like to export.
5. User types their chosen store.
6. User hits OK.
7. User is shown Export Successful Message.
8. They select Ok and are returned to Seller Menu.

Expected Result: The User's Exported Store is available as a .csv file with all their products visible.

Test Status: Passed

<h3>Test 13: Delete Account</h3>

1. Sign in as either Buyer or Seller.
2. User selects edit account.
3. User selects OK.
4. User selects Delete Account.
5. User Selects OK.

Expected Result: The user receives the confirmation message that their account was deleted.

Test Status: Passed.

<h3>Test 14: Edit Password</h3>

1. Sign in as either Buyer or Seller.
2. User selects edit account.
3. User selects OK.
4. User selects Change Password.
5. User Selects OK.
6. User is prompted to enter their new password.
7. User types new chosen password.
8. Select OK.

Expected Result: The user's password has been changed and upon next sign-in shall be used.

Test Status: Passed.

<h3>Test 15: To contact seller Error</h3>

Steps:

1. Sign in as a Buyer.
2. User selects contact seller.
3. User Selects OK.
4. User is asked to enter name of the store whose owner they would like to contact.
5. User enters name of a seller that does not exist in the marketplace.
6. User Selects OK.
7. User is shown no seller with given name.
8. User Selects OK.
9. User is shown sellers email for contact is null.
10. User selects Ok and are returned to Seller Menu.

Expected Result: An appropriate error message is shown when trying to contact a seller that doesn't exist in the marketplace.

Test Status: Passed

<h3>Test 16: Edit Password Error</h3>

1. Sign in as either Buyer or Seller.
2. User selects edit account.
3. User selects OK.
4. User selects Change Password.
5. User Selects OK.
6. User is prompted to enter their new password.
7. User leaves the prompt blank or enters an invalid password that doesn't meet requirements.
8. Select OK.

Expected Result: The user receives the proper error message informing them that their new password is invalid.

Test Status: Passed.
