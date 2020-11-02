package exist.page;

import lab3.framework.element.Element;
import lab3.framework.element.ElementList;
import lab3.framework.element.TextContainer;
import lab3.utils.RandomGenerator;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AutoSalesPage {
    private TextContainer pageTitleContainer = new TextContainer("//div[contains(@class,'content-page')]//*[normalize-space(text())='Купить автомобили']");

    private Element makeDropdown = new Element("//div[@id='MakeId_chosen']");
    private ElementList makeSuggestionsList = new ElementList("//div[@id='MakeId_chosen']//*[contains(@class,'active-result')]");
    private Element modelDropdown = new Element("//div[@id='ModelId_chosen']");
    private ElementList modelSuggestionsList = new ElementList("//div[@id='ModelId_chosen']//*[contains(@class,'active-result')]");
    private Element searchBtn = new Element("//*[@id='searchSubmit']");
    private ElementList carResultsList = new ElementList("//div[@class='offer-car-name']");

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

    public void searchForCar() {
        this.searchBtn.click();
    }

    public boolean isResultListCorrect(String modelMake) {
        List<WebElement> elements = this.carResultsList.getElements();
        for (WebElement elem : elements) {
            String text = elem.getText();
            if (!text.contains(modelMake))
                return false;
        }
        return true;
    }
}
