package exist.page;

import lab3.framework.element.Element;
import lab3.framework.element.TextContainer;

public class AutopointsPage {
    private TextContainer pageTitleContainer = new TextContainer("//div[contains(@class,'content-page')]//*[normalize-space(text())='Автоточки']");

    private String selectedServiceTypeLocatorTemplate = "//div[@class='serviceItem']//*[normalize-space(text())='%s']";

    public boolean isLoaded() {
        return this.pageTitleContainer.isVisible();
    }

    public AutopointMapPage selectService(String serviceType) {
        String serviceLocator = String.format(selectedServiceTypeLocatorTemplate, serviceType);
        Element autopointServiceBtn = new Element(serviceLocator);
        autopointServiceBtn.clickJs();
        return new AutopointMapPage();
    }
}
