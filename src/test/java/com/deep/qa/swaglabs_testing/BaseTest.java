package com.deep.qa.swaglabs_testing;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.deep.qa.swaglabs_testing.config.ConfigReader;

import com.deep.qa.swaglabs_testing.reports.ExtentManager;
import com.deep.qa.swaglabs_testing.utils.ScreenshotUtil;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentReports;
import com.deep.qa.swaglabs_testing.reports.ExtentManager;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import com.aventstack.extentreports.ExtentTest;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import java.lang.reflect.Method;


public class BaseTest {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    protected ExtentReports extent;

    public WebDriver getDriver() {
        return driver.get();
    }

    public ExtentTest getTest() {
        return test.get();
    }

    @BeforeSuite
    public void beforeSuite() {
        extent = ExtentManager.getExtentReports();

        if (extent == null) {
            throw new RuntimeException("ExtentReports NOT initialized");
        }
    }

    @BeforeMethod
    public void setUp(Method method) {

        if (extent == null) {
            extent = ExtentManager.getExtentReports();
        }

        WebDriver localDriver = new ChromeDriver();
        driver.set(localDriver);

        getDriver().manage().window().maximize();
        getDriver().get(ConfigReader.get("url"));

        test.set(extent.createTest(method.getName()));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {

        ExtentTest currentTest = test.get();

        try {

            if (result.getStatus() == ITestResult.SUCCESS) {
                currentTest.pass("Test passed");
            }

            else if (result.getStatus() == ITestResult.FAILURE) {

                String path = ScreenshotUtil.captureScreenshot(
                        getDriver(),
                        result.getName() + "_" + Thread.currentThread().getId()
                );

                currentTest.fail(result.getThrowable());
                currentTest.addScreenCaptureFromPath(path);
            }

            else if (result.getStatus() == ITestResult.SKIP) {
                currentTest.skip("Test skipped");
            }

        } catch (Exception e) {
            System.out.println("TEARDOWN ERROR: " + e.getMessage());
        } finally {

            if (getDriver() != null) {
                getDriver().quit();
            }

            driver.remove();
            test.remove();
        }
    }

    @AfterSuite
    public void afterSuite() {
        if (extent != null) {
            extent.flush();
        }
    }

    protected void loginToApp() {

        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.login(
                ConfigReader.get("username"),
                ConfigReader.get("password")
        );
    }
}






//
//public class BaseTest {
//
//    //protected WebDriver driver;
//	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
//	
//	
//    protected ExtentReports extent;
//    //protected ExtentTest test;
//    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
//
///*    
//    public ExtentTest getTest() {
//        return test.get();
//    }
//*/
//    
//    public ExtentTest getTest() {
//        if (test.get() == null) {
//            throw new RuntimeException("ExtentTest is not initialized for this thread");
//        }
//        return test.get();
//    }
//    
//    public WebDriver getDriver() {
//        return driver.get();
//    }
//    
//    
//    @BeforeSuite
//    public void beforeSuite() {
//        extent = ExtentManager.getExtentReports();
//    }
//    
//    
//    @BeforeMethod
//    public void setUp(Method method) {
//
//        WebDriver localDriver = new ChromeDriver();
//        driver.set(localDriver);
//
//        getDriver().get(ConfigReader.get("url"));
//
//        String testName = method.getName();
//
//        ExtentTest extentTest = extent.createTest(testName);
//        test.set(extentTest);
//    }
//    
//    @AfterMethod
//    public void tearDown(ITestResult result) {
//
//        try {
//
//            ExtentTest currentTest = test.get();
//
//            if (currentTest != null) {
//
//                if (result.getStatus() == ITestResult.FAILURE) {
//
//                    String path = ScreenshotUtil.captureScreenshot(getDriver(), result.getName());
//
//                    currentTest.fail(result.getThrowable());
//                    currentTest.addScreenCaptureFromPath(path);
//
//                } else if (result.getStatus() == ITestResult.SUCCESS) {
//
//                    currentTest.pass("Test passed");
//                }
//            }
//
//        } catch (Exception e) {
//            System.out.println("Teardown error ignored: " + e.getMessage());
//        } finally {
//
//            try {
//                if (driver.get() != null) {
//                    driver.get().quit();
//                }
//            } catch (Exception ignored) {}
//
//            driver.remove();
//            test.remove();
//        }
//    }   
//        @AfterSuite
//        public void afterSuite() {
//            if (extent != null) {
//                extent.flush();
//            }
//        }
//        
//        // ✅ reusable login method
//        protected void loginToApp() {
//            //LoginPage loginPage = new LoginPage(driver);
//        	
//        	
//        	
//        	LoginPage loginPage = new LoginPage(getDriver());
//            loginPage.login(
//            	    ConfigReader.get("username"),
//            	    ConfigReader.get("password")
//            	);
//            
//            
//            
//            //loginPage.login("standard_user", "secret_sauce");
//        }
//        
//    }
//    
/*    
    @BeforeMethod
    public void setUp(Method method) {
        driver = new ChromeDriver();
        driver.get(ConfigReader.get("url"));

        test.set(extent.createTest(method.getName()));
    }
*/
   
    
    
   ///@BeforeMethod
    ///public void setUp(Method method) {
        ///driver = new ChromeDriver();
        ///driver.get(ConfigReader.get("url"));
        
        //ExtentTest extentTest = extent.createTest(method.getName());
        //test.set(extentTest);
        
        //ExtentTest extentTest = extent.createTest(getClass().getSimpleName() 
        //        + " - " + System.identityHashCode(Thread.currentThread()));

        /*ExtentTest extentTest = extent.createTest(
                getClass().getSimpleName()
        );
        
        test.set(extentTest);
        */
        
        //test = extent.createTest(getClass().getSimpleName());
        
        
        //extent = ExtentManager.getExtentReports();
        
        //driver.get("https://www.saucedemo.com");
        
        //LoginPage loginPage = new LoginPage(driver);
        //loginPage.login("standard_user", "secret_sauce");
    ///}


/*    @AfterMethod
    public void tearDown() {
    	//if (extent != null) {
        //    extent.flush();
        //}
    	
    	driver.quit();
        System.out.println("tearDown executed");
        
    }
*/    
    
/*    @AfterMethod
    public void tearDown(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test passed");
        }

        driver.quit();
        System.out.println("tearDown executed");
    }
*/
    
/*    @AfterMethod
    public void tearDown(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {

            String path = ScreenshotUtil.captureScreenshot(driver, result.getName());

            getTest().fail(result.getThrowable());

            // attach screenshot to report
            getTest().addScreenCaptureFromPath(path);

        } else if (result.getStatus() == ITestResult.SUCCESS) {

        	getTest().pass("Test passed");
        }

        driver.quit();
    }
*/
 
/*    
    @AfterMethod
    public void tearDown(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        if (result.getParameters().length > 0) {
            testName = testName + " - " + result.getParameters()[0];
        }

        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);

        if (result.getStatus() == ITestResult.FAILURE) {

            String path = ScreenshotUtil.captureScreenshot(driver, result.getName());

            getTest().fail(result.getThrowable());
            getTest().addScreenCaptureFromPath(path);

        } else if (result.getStatus() == ITestResult.SUCCESS) {

            getTest().pass("Test passed");
        }

        driver.quit();
    }
*/
    
/*    
    @AfterMethod
    public void tearDown(ITestResult result) {

        String screenshotName = result.getMethod().getMethodName()
                + "_" + System.currentTimeMillis();

        if (result.getStatus() == ITestResult.FAILURE) {

            String path = ScreenshotUtil.captureScreenshot(driver, screenshotName);

            getTest().fail(result.getThrowable());
            getTest().addScreenCaptureFromPath(path);

        } else if (result.getStatus() == ITestResult.SUCCESS) {

            getTest().pass("Test passed");
        }

        driver.quit();
    }
*/
    
/*    
    @AfterMethod
    public void tearDown(ITestResult result) {

        if (result.getStatus() == ITestResult.FAILURE) {

            String path = ScreenshotUtil.captureScreenshot(getDriver(), result.getName());

            getTest().fail(result.getThrowable());

            getTest().addScreenCaptureFromPath(path);

        } else if (result.getStatus() == ITestResult.SUCCESS) {

            getTest().pass("Test passed");
        }

        getDriver().quit();
        driver.remove();
    }
*/
    
    
    
