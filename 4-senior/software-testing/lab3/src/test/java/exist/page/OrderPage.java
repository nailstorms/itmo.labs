package exist.page;

import lab3.framework.element.TextContainer;

public class OrderPage {
    private TextContainer pageTitleContainer = new TextContainer("//div[contains(@class,'content-page')]//*[normalize-space(text())='Оформление заказа' and not(self::span)]");

    private TextContainer productNameContainer = new TextContainer("//div[contains(@class,'prc-table__art-name')]");
    private TextContainer productPriceContainer = new TextContainer("//div[contains(@class,'prc-table')]/div[@id]" +
            "//div[contains(@class,'prc-table__cust-info')]//div[contains(@class,'sum')]");
    private TextContainer clientNameContainer = new TextContainer("//div[@class='client-info']//*[@class='user']");
    private TextContainer clientPhoneContainer = new TextContainer("//div[@class='client-info']//*[@class='client-phone']");
    private TextContainer clientEmailContainer = new TextContainer("//div[@class='client-info']//*[@class='email']");

    public boolean isLoaded() {
        return this.pageTitleContainer.isVisible();
    }

    public String getOrderedProductName(){
        return this.productNameContainer.getText();
    }

    public String getOrderedProductPrice(){
        return this.productPriceContainer.getText();
    }

    public String getClientName(){
        return this.clientNameContainer.getText();
    }

    public String getClientPhone(){
        return this.clientPhoneContainer.getText();
    }

    public String getClientEmail(){
        return this.clientEmailContainer.getText();
    }
}
