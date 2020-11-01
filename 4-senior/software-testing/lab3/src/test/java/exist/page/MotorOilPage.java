package exist.page;

import lab3.framework.browser.BrowserSingleton;
import lab3.framework.element.Element;
import lab3.framework.element.TextContainer;

public class MotorOilPage {
    private TextContainer pageTitleContainer = new TextContainer("//div[contains(@class,'content-page')]//*[normalize-space(text())='Масла моторные']");

    private String iframeId = "fancybox-frame";

    private TextContainer firstProductNameContainer = new TextContainer("//*[contains(@class,'catheader')]//div[contains(@class,'wrap')]/p");
    private TextContainer firstProductPriceContainer = new TextContainer("//*[contains(@class,'catheader')]//span[contains(@class,'ucatprc')]");
    private Element selectedProductInfoContainer = new Element("//div[@id='fancybox-outer']");
    private Element selectedProductInfoIframe = new Element("//div[@id='fancybox-outer']//iframe[@id='fancybox-frame']");
    private TextContainer selectedProductNameContainer = new TextContainer("//table[@id='datalayer']//*[contains(@class,'identifier')]");
    private TextContainer selectedProductPriceContainer = new TextContainer("//table[@id='datalayer']//*[contains(@class,'ucatprc')]");
    private Element addToBasketBtn = new Element("//table[@id='datalayer']//a[contains(@class,'basket')]");
    private Element closeProductWindowBtn = new Element("//a[contains(@id, 'close')]");
    private Element gotoBasketBtn = new Element("//*[contains(@class,'shop-functions__cart')]");

    public boolean isLoaded() {
        return this.pageTitleContainer.isVisible();
    }

    public void clickFirstProduct() {
        this.firstProductNameContainer.click();
    }

    public String getFirstProductName() {
        return this.firstProductNameContainer.getText();
    }

    public String getFirstProductPrice() {
        return this.firstProductPriceContainer.getText();
    }

    public boolean isSelectedProductWindowVisible() {
        return this.selectedProductInfoContainer.isVisible() && this.selectedProductInfoIframe.isExisting();
    }

    public boolean isSelectedProductWindowInvisible() {
        return this.selectedProductInfoContainer.isNotDisplayed();
    }

    public void switchToMainWindow() {
        BrowserSingleton.getInstance().switchTo().defaultContent();
    }

    public void switchToCurrentProductFrame() {
        BrowserSingleton.getInstance().switchTo().frame(this.iframeId);
    }

    public String getSelectedProductName() {
        return this.selectedProductNameContainer.getText();
    }

    public String getSelectedProductPrice() {
        return this.selectedProductPriceContainer.getText();
    }

    public void clickAddToBasketBtn() {
        this.addToBasketBtn.click();
    }

    public void closeSelectedProductWindow() {
        this.closeProductWindowBtn.click();
    }

    public BasketPage gotoBasket() {
        this.gotoBasketBtn.click();
        return new BasketPage();
    }
}

