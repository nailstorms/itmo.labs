package exist.page;

import lab3.framework.element.Element;
import lab3.framework.element.TextContainer;

public class VendorAutoPage {
    private Element vendorCatalogContainer = new Element("//div[contains(@class,'page-blocks')]");

    private TextContainer currentVendorName = new TextContainer("//div[contains(@class,'content-page')]//span[contains(@class,'menu-curent-node')]");

    public String currentVendorExpected;

    public VendorAutoPage (String vendor) {
        this.currentVendorExpected = vendor;
    }

    public boolean isLoaded() {
        return this.vendorCatalogContainer.isVisible();
    }

    public String getCurrentVendorFromPage() {
        return this.currentVendorName.getText();
    }
}
