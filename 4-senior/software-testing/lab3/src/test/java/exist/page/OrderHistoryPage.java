package exist.page;

import lab3.framework.element.TextContainer;
import org.apache.commons.lang3.StringUtils;

public class OrderHistoryPage {
    private TextContainer pageTitleContainer = new TextContainer("//div[contains(@class,'content-page')]//*[normalize-space(text())='Заказы']");

    private TextContainer currentOrdersBtn = new TextContainer("//div[contains(@class,'menu-tab')]//*[@id='hlHistory']");
    private TextContainer archivedOrdersBtn = new TextContainer("//div[contains(@class,'menu-tab')]//*[@id='hlArcHistory']");

    public boolean isLoaded() {
        return this.pageTitleContainer.isVisible();
    }

    public String getCurrentOrderCountStr() {
        this.currentOrdersBtn.click();
        return StringUtils.substringBetween(this.currentOrdersBtn.getText(), "(", ")");
    }

    public String getArchivedOrderCountStr() {
        this.archivedOrdersBtn.click();
        return StringUtils.substringBetween(this.archivedOrdersBtn.getText(), "(", ")");
    }
}
