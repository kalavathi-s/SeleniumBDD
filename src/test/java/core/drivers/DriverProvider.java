package core.drivers;

import core.library.Constants;
import core.library.PropertyLoader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class DriverProvider {
    private WebDriver driver;

    private final PropertyLoader properties = PropertyLoader.getInstance();

    public WebDriver getDriver() {
        if (driver == null) {
            initializeDriver();
        }
        return driver;
    }


    private void initializeDriver() {
        String browser = properties.getBrowser();

        switch (browser.toLowerCase()) {
            case Constants.Browser.CHROME:
                initializeChromeDriver();
                break;
            case Constants.Browser.FIREFOX:
                initializeFirefoxDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    private void initializeChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");
        driver = new ChromeDriver(options);
    }

    private void initializeFirefoxDriver() {

        driver = new FirefoxDriver();
    }
}
