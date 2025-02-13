package core.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static void captureScreenshot(WebDriver driver, String screenshotName) {
        // Take screenshot
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        File screenshotFolder = new File("screenshots"); // Relative path
        if (!screenshotFolder.exists()) {
            screenshotFolder.mkdir(); // Create folder if it doesn't exist
        }
        // Create timestamp to avoid filename conflicts
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // Set screenshot file path (you can customize the path as needed)
        File destFile = new File("screenshots/" + screenshotName + "_" + timestamp + ".png");

        try {
            // Ensure the destination directory exists
            destFile.getParentFile().mkdirs();
            // Copy the screenshot to the destination folder
            FileHandler.copy(srcFile, destFile);
            System.out.println("Screenshot saved: " + destFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
