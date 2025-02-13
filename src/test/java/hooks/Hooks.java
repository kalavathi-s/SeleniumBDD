package hooks;

import core.drivers.DriverManager;
import core.drivers.DriverProvider;
import core.library.Constants;
import core.library.PropertyLoader;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import core.utils.ScreenshotUtil;


public class Hooks {

    @BeforeAll
    public static void setup() {

    }

    @Before
    public static void beforeTest() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        PropertyLoader.getInstance().setBrowser(Constants.Browser.CHROME);
        PropertyLoader.getInstance().setBaseUrl(Constants.Urls.BASE_URL);
        DriverProvider driverProvider = new DriverProvider();
        WebDriver driver = driverProvider.getDriver();
        DriverManager.setDriver(driver);
        driver.navigate().to(Constants.Urls.BASE_URL);
    }

    @After
    public static void afterTest(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();

        // If the scenario failed, capture a screenshot
        if (scenario.isFailed()) {
            ScreenshotUtil.captureScreenshot(driver, scenario.getName().toLowerCase().replaceAll("[^A-Za-z]", ""));
        }

        // Clean up and quit the driver after the test
        if (driver != null) {
            driver.quit();
        }
        DriverManager.removeDriver();
    }

}
