package exist.page;

import lab3.framework.browser.BrowserSingleton;
import lab3.framework.element.Element;
import lab3.framework.element.TextContainer;

public class VinRequestsListPage {
    private TextContainer pageTitleContainer = new TextContainer("//div[contains(@class,'content-page')]//*[normalize-space(text())='Запросы по VIN']");

    private TextContainer firstRequestPartNameContainer = new TextContainer("//div[@class='row']//div[@class='item']");
    private TextContainer firstRequestStatusContainer = new TextContainer("//div[@class='row']//div[@class='row__status']");
    private TextContainer cancelRequestBtn = new TextContainer("//div[@class='row']//div[@class='row__status']//div[@class='cancel']");
    private Element garageBtn = new Element("//div[contains(@class,'garage')]//*[contains(text(),'Гараж')]");
    private TextContainer carNameContainer = new TextContainer("//div[@class='car-info__description']//a");

    public boolean isLoaded() {
        return this.pageTitleContainer.isVisible();
    }

    public String getFirstRequestPartName() {
        return this.firstRequestPartNameContainer.getText();
    }

    public String getFirstRequestStatus() {
        return this.firstRequestStatusContainer.getText();
    }

    public void cancelRequest() {
        this.cancelRequestBtn.click();
        BrowserSingleton.getInstance().switchTo().alert().accept();
        BrowserSingleton.getInstance().switchTo().defaultContent();
        BrowserSingleton.waitMs();
    }

    public void clickGarageBtn() {
        this.garageBtn.click();
    }

    public boolean isCarNameVisible() {
        return this.carNameContainer.isVisible();
    }

    public GaragePage gotoGarage() {
        this.carNameContainer.clickJs();
        return new GaragePage();
    }
}
