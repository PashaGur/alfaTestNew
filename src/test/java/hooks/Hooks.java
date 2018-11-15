package hooks;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.junit.AfterClass;
import org.openqa.selenium.WebDriver;


public class Hooks {
    public static WebDriver getDriver() {
        return driver;
    }

    public static WebDriver driver;

    /**
     * Настройки вебдрайвера, осуществляется перед запуском каждого теста
     */
    @Before
    public static void setup() {
        System.out.println("Доступные имена браузеров 'chrome/firefox/edge', меняются в предыстории");
        System.setProperty("webdriver.gecko.driver", "src\\main\\resources\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        System.setProperty("webdriver.edge.driver", "src\\main\\resources\\MicrosoftWebDriver.exe");
    }
}