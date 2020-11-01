package exist.page;

import lab3.framework.element.Element;
import lab3.framework.element.ElementList;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchResultPage {
    private Element searchResultsContainer = new Element("//div[@id='cat-wrapper']");

    private ElementList resultsList = new ElementList("//*[contains(@class,'catalogs')]//a//span[not(contains(@class,'info')) and not(@id='carsCountLiteral')]");

    public String searchResultsExpected;

    public SearchResultPage(String query) {
        this.searchResultsExpected = query;
    }

    public boolean isLoaded() {
        return this.searchResultsContainer.isVisible();
    }

    public boolean isResultCorrect() {
        List<WebElement> elements = this.resultsList.getElements();
        for (WebElement elem : elements) {
            String text = elem.getText();
            if (!text.contains(this.searchResultsExpected))
                return false;
        }
        return true;
    }
}
