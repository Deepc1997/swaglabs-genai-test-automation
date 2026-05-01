package com.deep.qa.swaglabs_testing.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.deep.qa.swaglabs_testing.BaseTest;
import com.deep.qa.swaglabs_testing.InventoryPage;


public class SwagLabsCartTest extends BaseTest {

    @Test
    public void addToCartTest() {

        getTest().info("Cart Test Started");

        loginToApp();

        InventoryPage inventoryPage = new InventoryPage(getDriver());

        inventoryPage.addItemToCart();

        Assert.assertTrue(inventoryPage.isItemAddedToCart(),
                "Item not added");

        getTest().pass("Cart test passed");
    }

    @Test
    public void addToCartFailureTest() {

        getTest().info("Failure Test Started");

        loginToApp();

        InventoryPage inventoryPage = new InventoryPage(getDriver());

        inventoryPage.addItemToCart();

        Assert.assertTrue(false, "Forced failure");

        getTest().pass("This will never run");
    }
}








//
//public class SwagLabsCartTest extends BaseTest  {
//	
//	 @Test
//	    public void addToCartTest() {
//
//	        //LoginPage loginPage = new LoginPage(driver);
//	        
//		    getTest().info("Starting adToCart Test");
//		 
//		 	loginToApp();
//		 	InventoryPage inventoryPage = new InventoryPage(getDriver());
//
//	        // Step 1: login
//	        //loginPage.login("standard_user", "secret_sauce");
//
//	        // Step 2: add item
//	        inventoryPage.addItemToCart();
//
//	        // Step 3: verify
//	        Assert.assertTrue(inventoryPage.isItemAddedToCart(), "Item not added to cart");
//	        System.out.println("2 pass");
//	        
//	        getTest().pass("adToCarte test completed successfully");
//	        
//	    }
//	 
//	 
//	 @Test
//	 public void addToCartFailureTest() {
//
//		 getTest().info("Starting add to cart FAILURE test");
//
//	     InventoryPage inventoryPage = new InventoryPage(getDriver());
//
//	     getTest().info("Adding item to cart");
//
//	     inventoryPage.addItemToCart();
//
//	     getTest().info("Forcing incorrect validation");
//
//	     // ❌ INTENTIONAL FAILURE
//	     Assert.assertTrue(false, "Intentional failure to test reporting");
//
//	     getTest().pass("This will never execute");
//	 }
//	 
//
//}
//
