package exist.page;

import lab3.framework.element.Element;
import lab3.framework.element.ElementList;
import lab3.framework.element.TextContainer;
import lab3.utils.RandomGenerator;

public class GeneralCatalogPage {
    private Element generalCatalogContainer = new Element("//div[contains(@class,'page-blocks')]");

    private String vendorPartLocatorTemplate = "//div[contains(@class,'page-blocks')]//div[@id='bmVendorTypesC%s']//a";

    private ElementList transportTypesList = new ElementList("//div[contains(@class,'page-blocks')]//a[@name='bmVendorTypes']");

    public boolean isLoaded() {
        return generalCatalogContainer.isVisible();
    }

    public int clickRandomTransportType() {
        int randomIndex = RandomGenerator.getRandomInt(this.transportTypesList.getElements().size());
        this.clickTransportType(randomIndex);
        return randomIndex;
    }

    public void clickTransportType(int index) {
        this.transportTypesList.getElements().get(index).click();
    }

    public VendorAutoPage clickRandomVendor(int transportType) {
        TextContainer container = new TextContainer(String.format(vendorPartLocatorTemplate, transportType));
        String containerText = container.getText();
        container.click();
        return new VendorAutoPage(containerText);
    }
}
