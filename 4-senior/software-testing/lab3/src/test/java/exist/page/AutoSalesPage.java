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

    private String carInfoChildrenLocator = ".//noindex";

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
    private Element transmissionDropdown = new Element("//div[@id='TransmissionType_chosen']");
    private ElementList transmissionSuggestionsList = new ElementList("//div[@id='TransmissionType_chosen']//*[contains(@class,'active-result')]");
    private InputField enginePowerFromInputField = new InputField("//input[@id='EnginePowerFrom']");
    private InputField enginePowerToInputField = new InputField("//input[@id='EnginePowerTo']");

    private Element searchBtn = new Element("//*[@id='searchSubmit']");
    private ElementList carResultsList = new ElementList("//div[@class='offer-car-name']");
    private TextContainer notFoundContainer = new TextContainer("//div[@class='notFound']");
    private ElementList priceResultsList = new ElementList("//div[@class='ads_table_carinfo']//div[contains(@class,'search_results__offer__price')]");
    private ElementList mileageResultsList = new ElementList("//div[@class='ads_table_carinfo']//div[contains(@class,'ads_table_miliage')]");
    private ElementList yearResultsList = new ElementList("//div[@class='ads_table_carinfo']//div[contains(@class,'ads_table_year')]");
    private ElementList carInfoResultsList = new ElementList("//div[@class='offer-car-info']");

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

    public String selectMakeNotImportant() {
        this.makeDropdown.click();
        List<WebElement> elements = this.makeSuggestionsList.getElements();
        WebElement randomElement = elements.get(0);
        String makeName = randomElement.getText().equals("Не важно") ? "" : randomElement.getText();
        randomElement.click();
        return makeName;
    }

    public String selectModelNotImportant() {
        this.modelDropdown.click();
        List<WebElement> elements = this.modelSuggestionsList.getElements();
        WebElement randomElement = elements.get(0);
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

    public String selectRandomTransmission() {
        this.transmissionDropdown.click();
        List<WebElement> elements = this.transmissionSuggestionsList.getElements();
        WebElement randomElement = elements.get(RandomGenerator.getRandomInt(elements.size()));
        String transmission = randomElement.getText().equals("Не важно") ? "" : randomElement.getText();
        randomElement.click();
        return transmission;
    }

    public void inputRandomPriceRange() {
        Random random = new Random();
        this.priceFromInputField.setValue(Integer.toString(random.nextInt(90000) + 10000));
        this.priceToInputField.setValue(Integer.toString(random.nextInt(500000) + 500000));
    }

    public void inputRandomYearRange() {
        Random random = new Random();
        this.yearFromInputField.setValue(Integer.toString(random.nextInt(30) + 1970));
        this.yearToInputField.setValue(Integer.toString(random.nextInt(17) + 2000));
    }

    public void inputRandomEnginePower() {
        Random random = new Random();
        this.enginePowerFromInputField.setValue(Integer.toString(random.nextInt(5)));
        this.enginePowerToInputField.setValue(Integer.toString(random.nextInt(994) + 7));
    }

    public void searchForCar() {
        this.searchBtn.click();
    }

    public boolean isResultListCorrect(String modelMake, String mileage, String transmission) {
        if (this.notFoundContainer.isVisible()) return true;

        List<WebElement> carResultsListElements = this.carResultsList.getElements();
        for (WebElement elem : carResultsListElements) {
            String text = elem.getText();
            if (!text.contains(modelMake))
                return false;
        }

        List<WebElement> priceResultsListElements = this.priceResultsList.getElements();
        int chosenPriceFrom = Integer.parseInt(this.priceFromInputField.getAttribute("value").trim());
        int chosenPriceTo = Integer.parseInt(this.priceToInputField.getAttribute("value").trim());
        for (WebElement elem : priceResultsListElements) {
            String text = elem.getText();
            int indexOfR = !text.contains("р") ? text.indexOf("Р") : text.indexOf("р");
            int currentPrice = Integer.parseInt(elem.getText().substring(0, indexOfR).trim().replace(" ", ""));
            if (currentPrice < chosenPriceFrom || currentPrice > chosenPriceTo) return false;
        }

        List<WebElement> mileageResultsListElements = this.mileageResultsList.getElements();
        for (WebElement elem : mileageResultsListElements) {
            String text = elem.getText();
            int indexOfK = !text.contains("к") ? text.indexOf("К") : text.indexOf("к");
            int currentMileage = Integer.parseInt(elem.getText().substring(0, indexOfK).trim().replace(" ", ""));

            switch (mileage) {
                case "": {
                    return true;
                }
                case "До 15 тыс. км.": {
                    if (currentMileage > 15000) return false;
                    break;
                }
                case "До 30 тыс. км.": {
                    if (currentMileage > 30000) return false;
                    break;
                }
                case "До 60 тыс. км.": {
                    if (currentMileage > 60000) return false;
                    break;
                }
                case "До 90 тыс. км.": {
                    if (currentMileage > 90000) return false;
                    break;
                }
                case "До 150 тыс. км.": {
                    if (currentMileage > 150000) return false;
                    break;
                }
                case "Более 150 тыс. км.": {
                    if (currentMileage < 150000) return false;
                    break;
                }
                default: {
                    return false;
                }
            }
        }

        List<WebElement> yearResultsListElements = this.yearResultsList.getElements();
        int chosenYearFrom = Integer.parseInt(this.yearFromInputField.getAttribute("value").trim());
        int chosenYearTo = Integer.parseInt(this.yearToInputField.getAttribute("value").trim());
        for (WebElement elem : yearResultsListElements) {
            int indexOfSpace = elem.getText().indexOf(" ");
            int currentYear = Integer.parseInt(elem.getText().substring(0, indexOfSpace));
            if (currentYear < chosenYearFrom || currentYear > chosenYearTo) return false;
        }

        List<WebElement> carInfoResultsListElements = this.carInfoResultsList.getElements();
        int chosenEnginePowerFrom = Integer.parseInt(this.enginePowerFromInputField.getAttribute("value").trim());
        int chosenEnginePowerTo = Integer.parseInt(this.enginePowerToInputField.getAttribute("value").trim());
        for (WebElement elem : carInfoResultsListElements) {
            int currentIndex = carInfoResultsListElements.indexOf(elem);
            List<WebElement> elements = this.carInfoResultsList.getChildrenOfElement(currentIndex, carInfoChildrenLocator);

            int indexOfSpace = elements.get(0).getText().indexOf(" ");
            int currentEnginePower = Integer.parseInt(elements.get(0).getText().substring(0, indexOfSpace));
            if (currentEnginePower < chosenEnginePowerFrom || currentEnginePower > chosenEnginePowerTo) return false;

            String text = elements.get(1).getText().trim();
            if (!text.contains(transmission)) return false;
        }
        return true;
    }
}
