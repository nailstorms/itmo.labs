package exist.page;

import lab3.framework.element.Element;
import lab3.framework.element.TextContainer;

public class AutopointPage {
    private Element mapContainer = new Element("//div[@id='YMapsID']");

    private String autopointServiceNameLocatorTemplate = "//li[normalize-space(text())='%s']";

    private TextContainer autopointNameContainer = new TextContainer("//div[contains(@id,'pnlName')]");

    public String autopointNameExpected;

    public AutopointPage(String name) {
        this.autopointNameExpected = name;
    }

    public boolean isLoaded() {
        return this.mapContainer.isVisible();
    }

    public String getAutopointName() {
        return this.autopointNameContainer.getText().trim();
    }

    public boolean isServiceInList(String serviceName) {
        TextContainer autopointServiceNameContainer = new TextContainer(String.format(autopointServiceNameLocatorTemplate, serviceName));
        return autopointServiceNameContainer.isExisting() && autopointServiceNameContainer.isVisible();
    }
}
