package exist.page;

import lab3.framework.browser.BrowserSingleton;
import lab3.framework.element.Element;
import lab3.framework.element.ElementList;
import lab3.utils.RandomGenerator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class AutopointMapPage {
    private Element mapContainer = new Element("//div[@class='YMap']");

    private String serviceCheckboxLocatorTemplate = "//*[normalize-space(text())='%s']//parent::div//input";

    private ElementList autopointsList = new ElementList("//*[@class='autopoint']//span[@class='h']");
    private ElementList autopointServicesList = new ElementList("//div[@id='Works']//input//parent::div//label");

    public int lastAutopointServiceClickedIndex;

    public boolean isLoaded() {
        return this.mapContainer.isVisible();
    }

    public boolean isServiceChecked(String serviceType) {
        Element serviceCheckbox = new Element(String.format(serviceCheckboxLocatorTemplate, serviceType));
        return serviceCheckbox.getAttribute("checked").equals("true");
    }

    public AutopointPage clickRandomAutopoint() {
        JavascriptExecutor executor = (JavascriptExecutor) BrowserSingleton.getInstance();
        int randomIndex = RandomGenerator.getRandomInt(this.autopointsList.getElements().size());
        WebElement randomElement = this.autopointsList.getElements().get(randomIndex);
        String text = randomElement.getText();
        executor.executeScript("arguments[0].click();", randomElement);
        return new AutopointPage(text);
    }

    public String clickRandomService() {
        JavascriptExecutor executor = (JavascriptExecutor) BrowserSingleton.getInstance();
        int randomIndex = RandomGenerator.getRandomInt(this.autopointServicesList.getElements().size());
        while (randomIndex == lastAutopointServiceClickedIndex) randomIndex = RandomGenerator.getRandomInt(this.autopointsList.getElements().size());
        WebElement randomElement = this.autopointServicesList.getElements().get(randomIndex);
        String text = randomElement.getText();
        executor.executeScript("arguments[0].click();", randomElement);
        lastAutopointServiceClickedIndex = randomIndex;
        return text;
    }
}
