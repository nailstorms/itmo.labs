package exist.page;

import lab3.framework.browser.BrowserSingleton;
import lab3.framework.element.Element;
import lab3.framework.element.InputField;
import lab3.framework.element.TextContainer;

public class VinRequestPage {
    private TextContainer pageTitleContainer = new TextContainer("//div[contains(@class,'content-page')]//*[normalize-space(text())='Для создания запроса выберите транспорт']");

    private Element carContainer = new Element("//div[@id='gvCars']//*[@class='cell']");
    private TextContainer vinRequestHeaderContainer = new TextContainer("//div[contains(@class,'page-blocks')]//*[normalize-space(text())='Новый запрос по VIN']");
    private InputField partInputField = new InputField("//div[@id='partslistarea']//input[contains(@id,'PartDescription')]");
    private Element addPartBtn = new Element("//div[@id='partslistarea']//input[contains(@id,'AddPart')]");
    private TextContainer addedPartTextContainer = new TextContainer("//*[@class='partsList']//li");
    private InputField mileageInputField = new InputField("//input[@id='txtMileage']");
    private Element postRequestBtn = new Element("//div[@class='yourQuery']//input[contains(@id,'bnSendQuery')]");

    public boolean isLoaded() {
        return this.pageTitleContainer.isVisible();
    }

    public void selectCar() {
        this.carContainer.click();
    }

    public boolean isOnVinRequestPage() {
        return this.vinRequestHeaderContainer.isVisible();
    }

    public void selectPart(String part) {
        this.partInputField.setValue(part);
        this.addPartBtn.click();
    }

    public String getAddedPartText() {
        return this.addedPartTextContainer.getText();
    }

    public void enterMileage(String mileage) {
        this.mileageInputField.setValue(mileage);
    }

    public VinRequestsListPage submitRequest() {
        this.postRequestBtn.click();
        BrowserSingleton.getInstance().switchTo().alert().accept();
        BrowserSingleton.getInstance().switchTo().defaultContent();
        return new VinRequestsListPage();
    }
}
