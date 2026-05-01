package com.deep.qa.swaglabs_testing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    WebDriver driver;

    By inventoryContainer = By.id("inventory_container");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isLoginSuccessful() {
        return driver.findElements(inventoryContainer).size() > 0;
    }
}