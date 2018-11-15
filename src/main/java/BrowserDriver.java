import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;

public class BrowserDriver {
    private static WebDriver driver;
    private synchronized static WebDriver getCurrentDriver() {
        if (driver ==null) {
            try {
                driver = new ChromeDriver();
            } finally{
                Runtime.getRuntime().addShutdownHook(
                        new Thread(new BrowserCleanup()));
            }
        }
        return driver;
    }
    private static class BrowserCleanup implements Runnable {
        public void run() {
            System.out.println("Закрытие браузера");
            close();
        }
    }
    private static void close() {
        try {
            getCurrentDriver().quit();
            driver = null;
            System.out.println("Закрытие браузера");
        } catch (UnreachableBrowserException e) {
            System.out.println("Невозможно закрыть браузер: unreachable browser");
        }
    }


    public static void loadPage(String url){;
        System.out.println("Directing browser to:" + url);
        getCurrentDriver().get(url);
    }

}
