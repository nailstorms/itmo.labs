package lab3.framework.browser;

import io.github.bonigarcia.wdm.WebDriverManager;
import lab3.utils.ConfigReader;
import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class BrowserSingleton {
    private static volatile WebDriver instance;
    private static JSONObject config;

    private BrowserSingleton() {
        config = ConfigReader.readJSONConfig("src/main/resources/settings.json");
        String browserName = config.get("browser").toString();
        switch (browserName) {
            case "chrome": {
                WebDriverManager.chromedriver().setup();
                instance = new ChromeDriver();
                break;
            }
            case "firefox": {
                WebDriverManager.firefoxdriver().setup();
                instance = new FirefoxDriver();
                break;
            }
            default: {
                throw new IllegalArgumentException(String.format("Browser %s is not supported.", browserName));
            }
        }
    }

    public static WebDriver getInstance() {
        WebDriver localInstance = instance;
        if (localInstance == null) {
            synchronized (BrowserSingleton.class) {
                localInstance = instance;
                if (localInstance == null) {
                    new BrowserSingleton();
                    localInstance = instance;
                }
            }
        }
        return localInstance;
    }

    public static void resetInstance() {
        instance.manage().deleteAllCookies();
        instance.quit();
        instance = null;
    }

    public static JSONObject getConfig() {
        if (config == null) {
            new BrowserSingleton();
        }
        return config;
    }

    public static int getWaitTime() {
        config = getConfig();
        return Integer.parseInt(config.get("waitTimeSec").toString());
    }
}
