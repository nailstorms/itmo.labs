package exist.page;

import lab3.framework.element.Element;
import lab3.framework.element.ElementList;
import lab3.framework.element.InputField;
import lab3.framework.element.TextContainer;
import lab3.utils.RandomGenerator;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Random;

public class AutoSalesPage {
    private TextContainer pageTitleContainer = new TextContainer("//div[contains(@class,'content-page')]//*[normalize-space(text())='Купить автомобили']");

    private Element makeDropdown = new Element("//div[@id='MakeId_chosen']");
    private ElementList makeSuggestionsList = new ElementList("//div[@id='MakeId_chosen']//*[contains(@class,'active-result')]");
    private Element modelDropdown = new Element("//div[@id='ModelId_chosen']");
    private ElementList modelSuggestionsList = new ElementList("//div[@id='ModelId_chosen']//*[contains(@class,'active-result')]");
    private InputField priceFromInputField = new InputField("//input[@id='PriceFrom']");
    private InputField priceToInputField = new InputField("//input[@id='PriceTo']");
    private InputField yearFromInputField = new InputField("//input[@id='YearFrom']");
    private InputField yearToInputField = new InputField("//input[@id='YearTo']");
    private Element mileageDropdown = new Element("//div[@id='Milage_chosen']");
    private ElementList mileageSuggestionsList = new ElementList("//div[@id='Milage_chosen']//*[contains(@class,'active-result')]");
    private Element fuelTypeDropdown = new Element("//div[@id='FuelType_chosen']");
    private ElementList fuelTypeSuggestionsList = new ElementList("//div[@id='FuelType_chosen']//*[contains(@class,'active-result')]");
    private Element transmissionDropdown = new Element("//div[@id='TransmissionType_chosen']");
    private ElementList transmissionSuggestionsList = new ElementList("//div[@id='TransmissionType_chosen']//*[contains(@class,'active-result')]");
    private Element bodyTypeDropdown = new Element("//div[@id='BodyType_chosen']");
    private ElementList bodyTypeSuggestionsList = new ElementList("//div[@id='BodyType_chosen']//*[contains(@class,'active-result')]");
    private InputField engineVolumeFromInputField = new InputField("//input[@id='EngineVolumeLFrom']");
    private InputField engineVolumeToInputField = new InputField("//input[@id='EngineVolumeLTo']");
    private InputField enginePowerFromInputField = new InputField("//input[@id='EnginePowerFrom']");
    private InputField enginePowerToInputField = new InputField("//input[@id='EnginePowerTo']");

    private Element searchBtn = new Element("//*[@id='searchSubmit']");
    private ElementList carResultsList = new ElementList("//div[@class='offer-car-name']");
    private TextContainer notFoundContainer = new TextContainer("//div[@class='notFound']");
    private ElementList priceSuggestionsList = new ElementList("//div[@class='offer-car-name']//div[contains(@class,'search_results__offer__price')]");

    public boolean isLoaded() {
        return this.pageTitleContainer.isVisible();
    }

    public String selectRandomMake() {
        this.makeDropdown.click();
        List<WebElement> elements = this.makeSuggestionsList.getElements();
        WebElement randomElement = elements.get(RandomGenerator.getRandomInt(elements.size()));
        String makeName = randomElement.getText().equals("Не важно") ? "" : randomElement.getText();
        randomElement.click();
        return makeName;
    }

    public String selectRandomModel() {
        this.modelDropdown.click();
        List<WebElement> elements = this.modelSuggestionsList.getElements();
        WebElement randomElement = elements.get(RandomGenerator.getRandomInt(elements.size()));
        String modelName = randomElement.getText().equals("Не важно") ? "" : randomElement.getText();
        randomElement.click();
        return modelName;
    }

    public String selectRandomMileage() {
        this.mileageDropdown.click();
        List<WebElement> elements = this.mileageSuggestionsList.getElements();
        WebElement randomElement = elements.get(RandomGenerator.getRandomInt(elements.size()));
        String mileageName = randomElement.getText().equals("Не важно") ? "" : randomElement.getText();
        randomElement.click();
        return mileageName;
    }

    public String selectRandomFuelType() {
        this.fuelTypeDropdown.click();
        List<WebElement> elements = this.fuelTypeSuggestionsList.getElements();
        WebElement randomElement = elements.get(RandomGenerator.getRandomInt(elements.size()));
        String mileageName = randomElement.getText().equals("Не важно") ? "" : randomElement.getText();
        randomElement.click();
        return mileageName;
    }

    public String selectRandomTransmission() {
        this.transmissionDropdown.click();
        List<WebElement> elements = this.transmissionSuggestionsList.getElements();
        WebElement randomElement = elements.get(RandomGenerator.getRandomInt(elements.size()));
        String mileageName = randomElement.getText().equals("Не важно") ? "" : randomElement.getText();
        randomElement.click();
        return mileageName;
    }

    public String selectRandomBodyType() {
        this.bodyTypeDropdown.click();
        List<WebElement> elements = this.bodyTypeSuggestionsList.getElements();
        WebElement randomElement = elements.get(RandomGenerator.getRandomInt(elements.size()));
        String mileageName = randomElement.getText().equals("Не важно") ? "" : randomElement.getText();
        randomElement.click();
        return mileageName;
    }

    public void inputRandomPriceRange() {
        this.priceFromInputField.setValue(RandomGenerator.generateRandomNumber(4));
        this.priceToInputField.setValue(RandomGenerator.generateRandomNumber(5));
    }

    public void inputRandomYearRange() {
        Random random = new Random();
        this.yearFromInputField.setValue(Integer.toString(random.nextInt(30) + 1970));
        this.yearToInputField.setValue(Integer.toString(random.nextInt(19) + 2000));
    }

    public void inputRandomEngineVolume() {
        Random random = new Random();
        this.engineVolumeFromInputField.setValue(Integer.toString(random.nextInt(5)));
        this.engineVolumeToInputField.setValue(Integer.toString(random.nextInt(1000)+7));
    }

    public void inputRandomEnginePower() {
        Random random = new Random();
        this.enginePowerFromInputField.setValue(Integer.toString(random.nextInt(5)));
        this.enginePowerToInputField.setValue(Integer.toString(random.nextInt(1000)+7));
    }

    public void searchForCar() {
        this.searchBtn.click();
    }

    public boolean isResultListCorrect(String modelMake) {
        if (this.notFoundContainer.isVisible()) return true;

        List<WebElement> elements = this.carResultsList.getElements();
        for (WebElement elem : elements) {
            String text = elem.getText();
            if (!text.contains(modelMake))
                return false;
        }
        return true;
    }
}
