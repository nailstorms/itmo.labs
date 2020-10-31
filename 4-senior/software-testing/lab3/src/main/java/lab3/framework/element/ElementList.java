package lab3.framework.element;

import lab3.framework.browser.BrowserSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ElementList {
    private String locator;

    public ElementList(String locator) {
        this.locator = locator;
    }

    public List<WebElement> getElements() {
        try {
            WebDriver driver = BrowserSingleton.getInstance();
            WebDriverWait wait = new WebDriverWait(driver, BrowserSingleton.getWaitTime());
            return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));
        } catch (TimeoutException exc) {
            return null;
        }
    }
}
