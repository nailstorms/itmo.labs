package exist.page;

import lab3.framework.element.Element;

public class AutopointMapPage {
    private Element mapContainer = new Element("//div[@class='YMap']");

    private String serviceCheckboxLocatorTemplate = "//*[normalize-space(text())='%s']//parent::div//input";

    public boolean isLoaded() {
        return this.mapContainer.isVisible();
    }

    public boolean isServiceChecked(String serviceType) {
        Element serviceCheckbox = new Element(String.format(serviceCheckboxLocatorTemplate, serviceType));
        return serviceCheckbox.getAttribute("checked").equals("true");
    }
}
