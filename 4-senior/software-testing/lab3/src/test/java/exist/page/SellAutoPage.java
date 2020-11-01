package exist.page;

import lab3.framework.element.Element;

public class SellAutoPage {
    private Element logoContainer = new Element("//div[@class='logo']");

    public boolean isLoaded() {
        return logoContainer.isVisible();
    }
}
