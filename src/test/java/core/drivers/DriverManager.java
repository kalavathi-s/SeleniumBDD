package core.drivers;


import org.openqa.selenium.WebDriver;

public class DriverManager {
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    public static WebDriver getDriver() {
        return driverThread.get();
    }
    public static void setDriver(WebDriver webDriver) {
        driverThread.set(webDriver);
    }

    public static void removeDriver() {
        WebDriver driver = getDriver();
        if (driver != null) {
            driver.quit();
            driverThread.remove();
        }
    }
}
