package lab3.framework.element;

import lab3.framework.browser.BrowserSingleton;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;

public class Element {
    private String locator;

    public Element(String locator) {
        this.locator = locator;
    }

    public void click() {
        Objects.requireNonNull(this.getElement()).click();
    }

    public String getAttribute(String attr) {
        return Objects.requireNonNull(this.getElement()).getAttribute(attr);
    }

    public boolean isExisting() {
        return checkElement(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
    }

    public boolean isVisible() {
        return checkElement(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
    }

    public boolean isClickable() {
        return checkElement(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
    }

    public boolean isNotDisplayed() {
        try {
            WebDriver driver = BrowserSingleton.getInstance();
            WebDriverWait wait = new WebDriverWait(driver, BrowserSingleton.getWaitTime());
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator)));
            return true;
        } catch (TimeoutException exc) {
            return false;
        }
    }

    boolean checkElement(ExpectedCondition<WebElement> condition) {
        try {
            WebDriver driver = BrowserSingleton.getInstance();
            WebDriverWait wait = new WebDriverWait(driver, BrowserSingleton.getWaitTime());
            wait.until(condition);
            return true;
        } catch (TimeoutException exc) {
            return false;
        }
    }

    WebElement getElement() {
        try {
            WebDriver driver = BrowserSingleton.getInstance();
            WebDriverWait wait = new WebDriverWait(driver, BrowserSingleton.getWaitTime());
            return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
        } catch (TimeoutException exc) {
            return null;
        }
    }
}
