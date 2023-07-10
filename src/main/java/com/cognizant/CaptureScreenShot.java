package com.cognizant;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CaptureScreenShot {
    static WebDriver driver = DriverSetup.getDriver("chrome");

    public  void takeScreenshot(){

        TakesScreenshot shot = (TakesScreenshot) driver;
        File screenshotFile =  shot.getScreenshotAs(OutputType.FILE);
        String folderPath = "screenshots";
        createFolderIfNotExists(folderPath);

        // Get the current date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String timeStamp = dateFormat.format(new Date());

        // Define the file name with date and time
        String fileName = folderPath + "/screenshot_" + timeStamp + ".png";

        // Save the screenshot to the specified file
        try {
            Files.move(screenshotFile.toPath(), Paths.get(fileName));
            System.out.println("Screenshot saved successfully: " + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  void createFolderIfNotExists(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
            System.out.println("Folder created: " + folderPath);
        }
    }
}
