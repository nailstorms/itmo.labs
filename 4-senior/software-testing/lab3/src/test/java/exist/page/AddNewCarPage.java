package exist.page;

import lab3.framework.element.Element;
import lab3.framework.element.InputField;
import lab3.framework.element.TextContainer;

public class AddNewCarPage {
    private TextContainer pageTitleContainer = new TextContainer("//div[contains(@class,'content-page')]//*[normalize-space(text())='Добавить транспорт']");

    private InputField vinInputField = new InputField("//div[contains(@class,'typevin')]//input");
    private Element proceedBtn = new Element("//input[@class='trdBtn']");
    private TextContainer foundCarNameContainer = new TextContainer("//div[@id='selectCarPopup']//*[contains(@class,'h')]");
    private TextContainer carReleaseYearContainer = new TextContainer("//span[contains(@id,'updYear')]//div[@class='tablerow']//*[@class='h']");
    private TextContainer carProducerContainer = new TextContainer("//div[contains(@id,'updProducer')]//*[@class='h']");
    private TextContainer carModelContainer = new TextContainer("//div[contains(@id,'updModel') and not(contains(@id,'Type'))]//*[@class='h']");
    private TextContainer carModelTypeContainer = new TextContainer("//div[contains(@id,'updModelType')]//*[@class='h']");
    private TextContainer carEngineContainer = new TextContainer("//div[contains(@id,'updEngine')]//*[@class='h']");
    private TextContainer carPowerContainer = new TextContainer("//div[contains(@id,'updCarParams')]//input");
    private Element saveBtn = new Element("//div[contains(@id,'updSubmit')]//input[contains(@id,'bnSave')]");

    public boolean isLoaded() {
        return this.pageTitleContainer.isVisible();
    }

    public void enterCarVin(String vin) {
        this.vinInputField.setValue(vin);
        this.proceedBtn.click();
    }

    public boolean isCarFound() {
        return this.foundCarNameContainer.isVisible();
    }

    public boolean isFoundCarMenuInvisible() {
        return this.foundCarNameContainer.isNotDisplayed();
    }

    public String getFoundCarName() {
        return this.foundCarNameContainer.getText();
    }

    public void selectFoundCar() {
        this.foundCarNameContainer.click();
    }

    public String getCarReleaseYear() {
        return this.carReleaseYearContainer.getText();
    }

    public String getCarProducer() {
        return this.carProducerContainer.getText();
    }

    public String getCarModel() {
        return this.carModelContainer.getText();
    }

    public String getCarModelType() {
        return this.carModelTypeContainer.getText();
    }

    public String getCarEngine() {
        return this.carEngineContainer.getText();
    }

    public String getCarPower() {
        return this.carPowerContainer.getAttribute("value");
    }

    public GaragePage saveCar() {
        this.saveBtn.click();
        return new GaragePage();
    }
}
