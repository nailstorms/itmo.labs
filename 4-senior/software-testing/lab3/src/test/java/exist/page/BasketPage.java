package exist.page;

import lab3.framework.element.Element;
import lab3.framework.element.TextContainer;

public class BasketPage {
    private TextContainer pageTitleContainer = new TextContainer("//div[contains(@class,'content-page')]//*[normalize-space(text())='Корзина']");

    private TextContainer productNameContainer = new TextContainer("//div[contains(@class,'prc-table__art-name')]");
    private TextContainer productPriceContainer = new TextContainer("//div[contains(@class,'prc-table__prices')]//div[contains(@class,'sum') and @id]");
    private Element deleteProductBtn = new Element("//*[contains(@class,'item-remove')]");
    private TextContainer emptyBasketMessageContainer = new TextContainer("//*[normalize-space(text())='В вашей корзине пусто!']");
    private Element proceedToOrderBtn = new Element("//div[contains(@class,'proceed')]//*[contains(@class,'trdBtn')]");

    public boolean isLoaded() {
        return this.pageTitleContainer.isVisible();
    }

    public String getProductName(){
        return this.productNameContainer.getText();
    }

    public String getProductPrice(){
        return this.productPriceContainer.getText();
    }

    public void clickDeleteProductBtn(){
        this.deleteProductBtn.clickJs();
    }

    public boolean isBasketEmpty() {
        return this.emptyBasketMessageContainer.isVisible();
    }

    public OrderPage gotoOrder(){
        this.proceedToOrderBtn.clickJs();
        return new OrderPage();
    }
}
