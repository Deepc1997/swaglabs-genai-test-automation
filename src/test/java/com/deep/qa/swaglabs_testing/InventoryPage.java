package com.deep.qa.swaglabs_testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage {

    WebDriver driver;

    By addToCartBtn = By.id("add-to-cart-sauce-labs-backpack");
    By cartBadge = By.className("shopping_cart_badge");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }

    public void addItemToCart() {
        driver.findElement(addToCartBtn).click();
    }

    public boolean isItemAddedToCart() {
        return driver.findElements(cartBadge).size() > 0;
    }
}