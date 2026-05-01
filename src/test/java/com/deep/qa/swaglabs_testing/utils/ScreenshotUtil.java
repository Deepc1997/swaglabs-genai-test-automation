package com.deep.qa.swaglabs_testing.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ScreenshotUtil {


    public static String captureScreenshot(WebDriver driver, String testName) {

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        //String path = System.getProperty("user.dir") + "/screenshots/" + testName + ".png";

        String path = System.getProperty("user.dir") + "/screenshots/" 
                + testName + "_" 
                + System.currentTimeMillis() 
                + ".png";
        
        File dest = new File(path);

        try {
            Files.createDirectories(dest.getParentFile().toPath());
            Files.copy(src.toPath(), dest.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }

	
	

}