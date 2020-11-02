package exist.page;

import lab3.framework.element.Element;
import lab3.framework.element.TextContainer;

public class GaragePage {
    private TextContainer pageTitleContainer = new TextContainer("//div[contains(@class,'content-page')]//*[normalize-space(text())='Гараж']");

    private TextContainer carNameContainer = new TextContainer("//div[contains(@class,'car-info__description')]//div[contains(@class,'car-info__car-name')]");
    private TextContainer carReleaseYearContainer = new TextContainer("//div[contains(@class,'car-info__description')]//div[@id='cr-yr']");
    private Element removeCarBtn = new Element("//*[contains(@id,'RemoveCar')]");
    private Element removeCarConfirmationModal = new Element("//div[@id='deleteCarConfirm']");
    private Element confirmRemoveCarBtn = new Element("//div[@id='deleteCarConfirm']//*[@id='confirmRemoveOK']");
    private Element addCarAdvertisementContainer = new Element("//div[@id='gvCars']");
    private Element vinRequestBtn = new Element("//div[contains(@class,'mainmenu')]//a[contains(text(),'Запрос по VIN')]");

    public boolean isLoaded() {
        return this.pageTitleContainer.isVisible();
    }

    public String getCarName() {
        return this.carNameContainer.getText();
    }

    public String getCarReleaseYear() {
        return this.carReleaseYearContainer.getText();
    }

    public void removeCarFromGarage() {
        this.removeCarBtn.click();
    }

    public boolean isRemoveCarModalVisible() {
        return this.removeCarConfirmationModal.isVisible();
    }

    public void clickRemoveConfirm() {
        this.confirmRemoveCarBtn.click();
    }

    public boolean isGarageEmpty() {
        return this.addCarAdvertisementContainer.isVisible();
    }

    public VinRequestPage gotoVinRequests() {
        this.vinRequestBtn.click();
        return new VinRequestPage();
    }
}
