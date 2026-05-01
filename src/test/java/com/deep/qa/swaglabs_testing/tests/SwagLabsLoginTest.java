package com.deep.qa.swaglabs_testing.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import com.deep.qa.swaglabs_testing.BaseTest;
import com.deep.qa.swaglabs_testing.HomePage;
import com.deep.qa.swaglabs_testing.LoginPage;
import com.deep.qa.swaglabs_testing.utils.JsonReader;

import org.testng.annotations.DataProvider;




public class SwagLabsLoginTest extends BaseTest {

    
    
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {

        Object[][] data = JsonReader.getLoginData(
                System.getProperty("user.dir") +
                        "/src/test/resources/testdata/loginData.json"
        );

        System.out.println("DATA PROVIDER SIZE = " + data.length);

        return data;
    }
    
    
    
    
    
    
    
    
    
    
    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password) {

        getTest().info("User: " + username);

        LoginPage loginPage = new LoginPage(getDriver());
        HomePage homePage = new HomePage(getDriver());

        loginPage.login(username, password);

        boolean result = homePage.isLoginSuccessful();

        getTest().info("Result: " + result);

        Assert.assertTrue(result, "Login failed for: " + username);

        getTest().pass("Completed: " + username);
    }
}









//public class SwagLabsLoginTest extends BaseTest{
//
//
//	
//	@DataProvider(name = "loginData")
//	public Object[][] getLoginData() {
//	    return JsonReader.getLoginData(
//	        System.getProperty("user.dir") +
//	        "/src/test/resources/testdata/loginData.json"
//	    );
//	}
//    
//	
//	@Test(dataProvider = "loginData")
//	public void loginTest(String username, String password) {
//
//	    getTest().info("Starting login test: " + username);
//
//	    LoginPage loginPage = new LoginPage(getDriver());
//	    HomePage homePage = new HomePage(getDriver());
//
//	    loginPage.login(username, password);
//
//	    boolean result = homePage.isLoginSuccessful();
//	    getTest().info("Login result: " + result);
//
//	    Assert.assertTrue(result, "Login failed for user: " + username);
//
//	    getTest().pass("Completed: " + username);
//	}
//}	
//	












/*
    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com");
    }
*/   
    
/*
    @Test
    public void loginTest() {

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");

        driver.findElement(By.id("login-button")).click();

        boolean loggedIn = driver.findElements(By.id("inventory_container")).size() > 0;

        Assert.assertTrue(loggedIn, "Login failed");
    }


*/
    
/*    
    @Test
    public void loginTest() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("standard_user", "secret_sauce");

        boolean loggedIn = driver.findElements(By.id("inventory_container")).size() > 0;

        Assert.assertTrue(loggedIn, "Login failed");
        
    }
*/    
    
/*    
    @Test
    public void loginTest() {

        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        loginPage.login("standard_user", "secret_sau2ce");

        Assert.assertTrue(homePage.isLoginSuccessful(), "Login failed");
    }
    
    
*/
	
/*	public Object[][] getLoginData() {
	    return new Object[][] {
	        {"standard_user", "secret_sauce"},
	        {"locked_out_user", "secret_sauce"},
	        {"problem_user", "secret_sauce"},
	        {"abc", "secret_sauce"}
	    };
	}
 */
	

    
/*    @Test
    public void loginTest() {
    	

    	//test.info("Starting Login Test");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com");

        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        loginPage.login("standard_user", "secret_sauce");

        Assert.assertTrue(homePage.isLoginSuccessful(), "Login failed");
        System.out.println("1 pass");
        
        //test.pass("Login test completed successfully");
        
    }  
*/    
    
   
/*	@Test(dataProvider = "loginData")
	public void loginTest(String username, String password) {

	    test.info("Starting login test with: " + username);

	    WebDriver driver = new ChromeDriver();
	    driver.get("https://www.saucedemo.com");

	    LoginPage loginPage = new LoginPage(driver);
	    HomePage homePage = new HomePage(driver);

	    loginPage.login(username, password);

	    boolean result = homePage.isLoginSuccessful();

	    Assert.assertTrue(result, "Login failed for user: " + username);

	    test.pass("Login completed for: " + username);

	    driver.quit();
	}
*/
	
/*	@Test(dataProvider = "loginData")
	public void loginTest(String username, String password) {

		getTest().info("Starting login test: " + username);

	    LoginPage loginPage = new LoginPage(driver);
	    HomePage homePage = new HomePage(driver);

	    loginPage.login(username, password);

	    Assert.assertTrue(homePage.isLoginSuccessful(),
	            "Login failed for user: " + username);

	    getTest().pass("Completed: " + username);
	}
*/
	

/*    
    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
    
*/
