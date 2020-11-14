package exist.page;

import lab3.framework.element.Element;
import lab3.framework.element.ElementList;
import lab3.utils.RandomGenerator;
import org.openqa.selenium.WebElement;

public class AutopointMapPage {
    private Element mapContainer = new Element("//div[@class='YMap']");

    private String serviceCheckboxLocatorTemplate = "//*[normalize-space(text())='%s']//parent::div//input";

    private ElementList autopointsList = new ElementList("//*[@class='autopoint']//span[@class='h']");

    public boolean isLoaded() {
        return this.mapContainer.isVisible();
    }

    public boolean isServiceChecked(String serviceType) {
        Element serviceCheckbox = new Element(String.format(serviceCheckboxLocatorTemplate, serviceType));
        return serviceCheckbox.getAttribute("checked").equals("true");
    }

    public AutopointPage clickRandomAutopoint() {
        int randomIndex = RandomGenerator.getRandomInt(this.autopointsList.getElements().size());
        WebElement randomElement = this.autopointsList.getElements().get(randomIndex);
        String text = randomElement.getText();
        randomElement.click();
        return new AutopointPage(text);
    }
}
